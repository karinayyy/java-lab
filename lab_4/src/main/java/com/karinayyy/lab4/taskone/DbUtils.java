package com.karinayyy.lab4.taskone;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class DbUtils {
    public enum Show { SORTED_BY_PASSENGER_DEC, SORTED_BY_COMMENT_LEN_DEC, SORTED_BY_COMMENT_ALP, UNSORTED };
    // Константи, які містять необхідні SQL-запити:
    public static final String DROP_TABLES = "DROP TABLES IF EXISTS hours, metros";
    public static final String DROP_DATABASE = "DROP DATABASE IF EXISTS metrosDB";
    public static final String CREATE_DATABASE = "CREATE DATABASE metrosDB";
    public static final String CREATE_TABLE_METROS = """
            CREATE TABLE metrosDB.metros (
              MetroID INT NOT NULL AUTO_INCREMENT,
              Name VARCHAR(128),
              Year INT,
              PRIMARY KEY (MetroID)
            );
            """;
    public static final String CREATE_TABLE_HOURS = """
            CREATE TABLE metrosDB.hours (
              HourID INT NOT NULL AUTO_INCREMENT,
              PassengerCount INT NULL,
              MetroID INT NULL,
              Comment VARCHAR(256) NULL,
              PRIMARY KEY (HourID),
              INDEX MetroID_idx (MetroID ASC) VISIBLE,
              CONSTRAINT MetroID
                FOREIGN KEY (MetroID)
                REFERENCES metrosDB.metros (MetroID)
                ON DELETE NO ACTION
                ON UPDATE NO ACTION);                        
          
            """;
    private static final String INSERT_INTO_MERTOS = """
        INSERT INTO metrosDB.metros (Name, Year) VALUES (?, ?);
        """;
    private static final String INSERT_INTO_HOURS = """
        INSERT INTO metrosDB.hours (PassengerCount, MetroID, Comment) VALUES (?, ?, ?);
        """;
    private static final String SELECT_BY_NAME = "SELECT * FROM metrosDB.metros WHERE Name = ?";
    private static final String SELECT_ALL_METROS = "SELECT * FROM metrosDB.metros";
    private static final String SELECT_FROM_HOURS = "SELECT * FROM metrosDB.hours WHERE MetroID = ?";
    private static final String SELECT_FROM_HOURS_ORDER_BY_PASSENGER_DEC =
            "SELECT * FROM metrosDB.hours WHERE MetroID = ? ORDER BY PassengerCount DESC";
    private static final String SELECT_FROM_HOURS_ORDER_BY_COMMENT_LEN_DEC =
            "SELECT * FROM metrosDB.hours WHERE MetroID = ? ORDER BY LENGTH(Comment) DESC";
    private static final String SELECT_FROM_HOURS_ORDER_BY_COMMENT_ALP =
            "SELECT * FROM metrosDB.hours WHERE MetroID = ? ORDER BY Comment ASC";

    private static final String SELECT_TOTAL_PASSENGER_COUNT =
            "SELECT SUM(PassengerCount) AS TotalPassengerCount FROM metrosDB.hours WHERE MetroID = ?";

    private static final String SELECT_MINIMUM_PASSENGER_COUNT =
            "SELECT * FROM metrosDB.hours WHERE MetroID = ? ORDER BY PassengerCount ASC LIMIT 1";
    private static final String SELECT_MAX_WORD_IN_COMMENT =
            "SELECT * FROM metrosDB.hours WHERE MetroID = ? ORDER BY LENGTH(Comment) DESC, HourID ASC LIMIT 1";
    private static final String SELECT_FROM_CENSUSES_WHERE_WORD = """
             SELECT c.CensusID, c.Year, c.Population, c.Comment, l.Name FROM countriesDB.censuses c 
             INNER JOIN countriesDB.countries l ON c.MetroID = l.MetroID WHERE c.Comment LIKE '%key_word%';
        """;
    private static final String DELETE_BY_ID = "DELETE FROM metrosDB.hours WHERE MetroID=? AND HourID=?";

    private static Connection connection;
    public static Metros importFromJSON(String fileName) {
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.addPermission(AnyTypePermission.ANY);
            xStream.alias("metros", Metros.class);
            xStream.alias("metro", MetroForDB.class);
            xStream.alias("hour", HourForDB.class);
            return (Metros) xStream.fromXML(new File(fileName));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void exportToJSON(Metros metros, String fileName) {
        XStream xStream = new XStream(new JettisonMappedXmlDriver());
        xStream.alias("metros", Metros.class);
        xStream.alias("metro", MetroForDB.class);
        xStream.alias("hour", HourForDB.class);
        String xml = xStream.toXML(metros);
        try (FileWriter fw = new FileWriter(fileName); PrintWriter out = new PrintWriter(fw)) {
            out.println(xml);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void exportToJSON(String fileName) {
        Metros metros = getMetrosFromDB();
        exportToJSON(metros, fileName);
    }
    public static void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?user=root&password=1234");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean createDatabase() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DROP_TABLES);
            statement.executeUpdate(DROP_DATABASE);
            statement.executeUpdate(CREATE_DATABASE);
            statement.executeUpdate(CREATE_TABLE_METROS);
            statement.executeUpdate(CREATE_TABLE_HOURS);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }
    public static void addAll(Metros metros) {
        for (MetroForDB c : metros.getList()) {
            addMetro(c);
        }
    }
    public static void addMetro(MetroForDB metro) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_MERTOS);
            preparedStatement.setString(1, metro.getName());
            preparedStatement.setInt(2, metro.getOpeningYear());
            preparedStatement.execute();
            for (int i = 0; i < metro.hoursCount(); i++) {
//                addCensus(country.getName(), (CensusForDB) country.getCensus(i));
                addHour(metro.getName(),  metro.getHour(i));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Metros getMetrosFromDB() {
        try {
            Metros metros = new Metros();
            Statement statement = connection.createStatement();
            ResultSet setOfCountries = statement.executeQuery(SELECT_ALL_METROS);
            while (setOfCountries.next()) {
                metros.getList().add(getMetroFromDB(setOfCountries));
            }
            return metros;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static MetroForDB getMetroByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet setOfCountries = preparedStatement.executeQuery();
            setOfCountries.next();
            return getMetroFromDB(setOfCountries);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static MetroForDB getMetroFromDB(ResultSet setOfMetros) throws SQLException {
        MetroForDB metro = new MetroForDB(setOfMetros.getString("Name"), setOfMetros.getInt("Year"));
        int id = setOfMetros.getInt("MetroID");
        metro.setId(id);
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_HOURS);
        preparedStatement.setInt(1, id);
        ResultSet setOfCensuses = preparedStatement.executeQuery();
        while (setOfCensuses.next()) {
            HourForDB hour = new HourForDB(setOfCensuses.getInt("PassengerCount"), setOfCensuses.getString("Comment"));
            hour.setId(setOfCensuses.getInt("MetroID"));
            metro.addHour(hour);
        }
        return metro;
    }
    public static int getIdByName(String metroName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, metroName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("MetroID");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_METROS);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> names = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                names.add(name);
            }
            resultSet.close();
            for (String name : names) {
                showMetro(name, Show.UNSORTED);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void showMetro(String metroName, Show by) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, metroName);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.printf("%s\t  %s\t  %s%n", "ID", "Metro", "Year");
            resultSet.next();
            System.out.printf("%s\t  %s\t  %s%n", resultSet.getString("MetroID"),
                    resultSet.getString("Name"), resultSet.getString("Year"));
            resultSet.close();
            PreparedStatement anotherStatement;
            if (by == Show.SORTED_BY_PASSENGER_DEC) {
                anotherStatement = connection.prepareStatement(SELECT_FROM_HOURS_ORDER_BY_PASSENGER_DEC);
            }
            else if (by == Show.SORTED_BY_COMMENT_LEN_DEC) {
                anotherStatement = connection.prepareStatement(SELECT_FROM_HOURS_ORDER_BY_COMMENT_LEN_DEC);
            }
            else if (by == Show.SORTED_BY_COMMENT_ALP) {
                anotherStatement = connection.prepareStatement(SELECT_FROM_HOURS_ORDER_BY_COMMENT_ALP);
            }
            else {
                anotherStatement = connection.prepareStatement(SELECT_FROM_HOURS);
            }
            anotherStatement.setInt(1, getIdByName(metroName));
            ResultSet anotherSet = anotherStatement.executeQuery();
            System.out.printf("%s\t  %s\t  \t%s%n", "ID", "PassengerCount", "Comment");
            while (anotherSet.next()) {
                System.out.printf("%s\t  %s\t  \t\t%s%n",
                        anotherSet.getString("HourID"), anotherSet.getInt("PassengerCount"),
                        anotherSet.getString("Comment"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showSortedByPassengerDec(String metroName) {
        showMetro(metroName, Show.SORTED_BY_PASSENGER_DEC);
    }
    public static void showSortedByCommentLenDec(String metroName) {
        showMetro(metroName, Show.SORTED_BY_COMMENT_LEN_DEC);
    }
    public static void showSortedByCommentAlp(String metroName) {
        showMetro(metroName, Show.SORTED_BY_COMMENT_ALP);
    }
    public static void findWord(String word) {
        try {
            String query = SELECT_FROM_CENSUSES_WHERE_WORD.replace("key_word", word);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.printf("%s\t  %s\t  %s\t  %s\t\t%s%n",
                        resultSet.getString("CensusID"), resultSet.getString("Name"),
                        resultSet.getString("Year"), resultSet.getString("Population"), resultSet.getString("Comment"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addHour(String metroName, Hour hours) {
        MetroForDB country = getMetroByName(metroName);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_HOURS);
            preparedStatement.setInt(1, hours.getPassengersNumber());
            preparedStatement.setInt(2, getIdByName(country.getName()));
            preparedStatement.setString(3, hours.getComment());
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void displayMetroInfo(String metroName) {
        try {
            // Retrieve sum of passenger counts
            PreparedStatement sumStatement = connection.prepareStatement(SELECT_TOTAL_PASSENGER_COUNT);
            sumStatement.setInt(1, getIdByName(metroName));
            ResultSet sumResult = sumStatement.executeQuery();
            if (sumResult.next()) {
                int totalPassengerCount = sumResult.getInt("TotalPassengerCount");
                System.out.println("Total Passenger Count: " + totalPassengerCount);
            }

            // Retrieve hour(s) with the minimum passenger count
            PreparedStatement minStatement = connection.prepareStatement(SELECT_MINIMUM_PASSENGER_COUNT);
            minStatement.setInt(1, getIdByName(metroName));
            ResultSet minResult = minStatement.executeQuery();
            if (minResult.next()) {
                int hourID = minResult.getInt("HourID");
                int passengerCount = minResult.getInt("PassengerCount");
                System.out.println("Hour(s) with Minimum Passenger Count:");
                System.out.println("Hour ID: " + hourID);
                System.out.println("Passenger Count: " + passengerCount);
            }

            // Retrieve hour(s) with the maximum number of words in the comment
            PreparedStatement maxStatement = connection.prepareStatement(SELECT_MAX_WORD_IN_COMMENT);
            maxStatement.setInt(1, getIdByName(metroName));
            ResultSet maxResult = maxStatement.executeQuery();
            if (maxResult.next()) {
                int hourID = maxResult.getInt("HourID");
                String comment = maxResult.getString("Comment");
                int wordCount = comment.split("\\s+").length;
                System.out.println("Hour(s) with Maximum Number of Words in Comment:");
                System.out.println("Hour ID: " + hourID);
                System.out.println("Comment: " + comment);
                System.out.println("Word Count: " + wordCount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeHour(String countryName, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, getIdByName(countryName));
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
