package shchepinda.labfourth.task3;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Клас, що відповідає DbUtils
 *
 * Дисципліна «Поглиблений курс програмування Java
 * Лабораторна робота №4, Варіант №23, завдання 3
 *
 * @author Щепін Данило, KH-221a
 */
public class DbUtils {
    public enum Show {SORTED, UNSORTED};

    public static final String DROP_TABLES = "DROP TABLES IF EXISTS days, exhibitions";
    public static final String DROP_DATABASE = "DROP DATABASE IF EXISTS exhibitionsDB";
    public static final String CREATE_DATABASE = "CREATE DATABASE exhibitionsDB";

    public static final String UPDATE_EXHIBITION_NAME = "UPDATE exhibitionsDB.exhibitions SET Name = ? WHERE ExhibitionID = ?";
    public static final String UPDATE_EXHIBITION_ARTIST = "UPDATE exhibitionsDB.exhibitions SET ArtSurname = ? WHERE ExhibitionID = ?";

    public static final String CREATE_TABLE_EXHIBITIONS = """
            CREATE TABLE exhibitionsDB.exhibitions (
            ExhibitionID INT NOT NULL AUTO_INCREMENT,
            Name VARCHAR(128) NULL,
            ArtSurname VARCHAR(128) NULL,
            PRIMARY KEY (ExhibitionID));
          """;
    public static final String CREATE_TABLE_DAYS = """
            CREATE TABLE exhibitionsDB.days (
            DayID INT NOT NULL AUTO_INCREMENT,
            VisitorsNum INT NULL,
            ExhibitionID INT NULL,
            Comment VARCHAR(256) NULL,
            PRIMARY KEY (DayID),
            INDEX ExhibitionID_idx (ExhibitionID ASC) VISIBLE,
            CONSTRAINT ExhibitionID
                FOREIGN KEY (ExhibitionID)
                REFERENCES exhibitionsDB.exhibitions (ExhibitionID)
                ON DELETE NO ACTION
                ON UPDATE NO ACTION);
                
            """;

    private static final String INSERT_INTO_EXHIBITIONS = """
            INSERT INTO exhibitionsDB.exhibitions (Name, ArtSurname) VALUES (?, ?);
            """;
    private static final String INSERT_INTO_DAYS = """
            INSERT INTO exhibitionsDB.days (VisitorsNum, ExhibitionID, Comment) VALUES (?, ?, ?);
            """;
    private static final String SELECT_BY_NAME = "SELECT * FROM exhibitionsDB.exhibitions WHERE Name = ?";
    private static final String SELECT_ALL_EXHIBITIONS = "SELECT * FROM exhibitionsDB.exhibitions";
    private static final String SELECT_FROM_DAYS = "SELECT * FROM exhibitionsDB.days WHERE ExhibitionID = ?";
    private static final String SELECT_VISITORS = "SELECT VisitorsNum FROM exhibitionsDB.days WHERE ExhibitionID = ?";
    private static final String DELETE_DAYS = "DELETE FROM exhibitionsDB.days WHERE ExhibitionID = ?";
    private static final String SELECT_FROM_DAYS_ORDER_BY_VISITORS =
            "SELECT * FROM exhibitionsDB.days WHERE ExhibitionID = ? ORDER BY VisitorsNum";
    private static final String SELECT_FROM_DAYS_ORDER_BY_COMMENTS =
            "SELECT * FROM exhibitionsDB.days WHERE ExhibitionID = ? ORDER BY Comment ASC";
    private static final String SELECT_FROM_DAYS_WHERE_WORD = """
              SELECT c.DayID, c.VisitorsNum, c.Comment, l.Name FROM exhibitionsDB.days c\s
              INNER JOIN exhibitionsDB.exhibitions l ON c.ExhibitionID = l.ExhibitionID WHERE c.Comment LIKE '%key_word%';
            """;
    private static final String DELETE_BY_VISITORS = "DELETE FROM exhibitionsDB.days WHERE ExhibitionID=? AND VisitorsNum=?";
    private static final String DELETE_BY_DAY_ID = "DELETE FROM exhibitionsDB.days WHERE DayID=?";

    private static final String DELETE_BY_EXHIBITION_ID = "DELETE FROM exhibitionsDB.exhibitions WHERE ExhibitionID=?";

    private static Connection connection;

    /**
     * Import data from JSON
     * @param filename path ti file
     * @return Exhibitions
     */
    public static Exhibitions importFromJSON(String filename) {
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.addPermission(AnyTypePermission.ANY);
            xStream.alias("exhibitions", Exhibitions.class);
            xStream.alias("exhibition", ExhibitionForDB.class);
            xStream.alias("day", DayForDB.class);
            return (Exhibitions) xStream.fromXML(new File(filename));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Export data to JSON
     * @param exhibitions data to export
     * @param filename path to save file
     */
    public static void exportToJSON(Exhibitions exhibitions, String filename) {
        XStream xStream = new XStream(new JettisonMappedXmlDriver());
        xStream.alias("exhibitions", Exhibitions.class);
        xStream.alias("exhibition", ExhibitionForDB.class);
        xStream.alias("day", DayForDB.class);
        String xml = xStream.toXML(exhibitions);
        try (FileWriter fw = new FileWriter(filename); PrintWriter out = new PrintWriter(fw)) {
            out.println(xml);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Export current data to JSON
     * @param filename path to file
     */
    public static void exportToJSON(String filename) {
        Exhibitions exhibitions = getExhibitionsFromDB();
        exportToJSON(exhibitions, filename);
    }

    /**
     * Create connection with DB
     */
    public static void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mysql?user=root&password=root");
            System.out.println("Connected to the database.");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create clear DB
     * @return true if DB successfully created, false if error
     */
    public static boolean createDatabase() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DROP_TABLES);
            statement.executeUpdate(DROP_DATABASE);
            statement.executeUpdate(CREATE_DATABASE);
            statement.executeUpdate(CREATE_TABLE_EXHIBITIONS);
            statement.executeUpdate(CREATE_TABLE_DAYS);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add all exhibitions
     * @param exhibitions
     */
    public static void addAll(Exhibitions exhibitions) {
        for (ExhibitionForDB e : exhibitions.getList()) {
            addExhibition(e);
        }
    }

    /**
     * Add exhibition to db
     * @param exhibition
     */
    public static void addExhibition(ExhibitionForDB exhibition) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_EXHIBITIONS);
            preparedStatement.setString(1, exhibition.getName());
            preparedStatement.setString(2, exhibition.getArtSurname());
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get exhibition from DB
     * @return exhibitions
     */
    public static Exhibitions getExhibitionsFromDB() {
        try {
            Exhibitions exhibitions = new Exhibitions();
            Statement statement = connection.createStatement();
            ResultSet setOfExhibitions = statement.executeQuery(SELECT_ALL_EXHIBITIONS);
            while (setOfExhibitions.next()) {
                exhibitions.getList().add(getExhibitionFromDB(setOfExhibitions));
            }
            return exhibitions;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get exhibition by name
     * @param name name of exhibition
     * @return exhibition for db object
     */
    @SuppressWarnings("DuplicatedCode")
    public static ExhibitionForDB getExhibitionByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet setOfExhibitions = preparedStatement.executeQuery();
            setOfExhibitions.next();
            return getExhibitionFromDB(setOfExhibitions);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get exhibition from db
     * @param setOfExhibitions resultset object
     * @return  exhibition
     * @throws SQLException
     */
    public static ExhibitionForDB getExhibitionFromDB(ResultSet setOfExhibitions) throws SQLException {
        ExhibitionForDB exhibition = new ExhibitionForDB(setOfExhibitions.getString("Name"), setOfExhibitions.getString("ArtSurname"));
        int id = setOfExhibitions.getInt("ExhibitionID");
        exhibition.setId(id);
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_DAYS);
        preparedStatement.setInt(1, id);
        ResultSet setOfDays = preparedStatement.executeQuery();
        while (setOfDays.next()) {
            DayForDB day = new DayForDB(setOfDays.getInt("VisitorsNum"),
                    setOfDays.getString("Comment"));
            day.setId(setOfDays.getInt("DayID"));
            exhibition.addDay(day);
        }
        return exhibition;
    }

    /**
     * Get id by name
     * @param name
     * @return id
     */
    public static int getIdByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("ExhibitionID");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Shows all info
     */
    public static void showAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EXHIBITIONS);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> names = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                names.add(name);
            }
            resultSet.close();
            for (String name : names) {
                showExhibition(name, Show.UNSORTED);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show info about certain exhibition
     * @param name
     * @param byVisitors sort order
     */
    public static void showExhibition(String name, Show byVisitors) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.printf("%s\t  %s\t  %s%n", "ID", "Exhibition name", "Artist Surname");
            resultSet.next();
            System.out.printf("%s\t  %s\t  %s%n", resultSet.getString("ExhibitionID"),
                    resultSet.getString("Name"), resultSet.getString("ArtSurname"));
            resultSet.close();
            PreparedStatement anotherStatement;
            if (byVisitors == Show.SORTED) {
                anotherStatement = connection.prepareStatement(SELECT_FROM_DAYS_ORDER_BY_VISITORS);
            }
            else {
                anotherStatement = connection.prepareStatement(SELECT_FROM_DAYS);
            }
            anotherStatement.setInt(1, getIdByName(name));
            ResultSet anotherSet = anotherStatement.executeQuery();
            System.out.printf("\t%s\t  %s \t%s%n", "ID", "Visitors number", "Comment");
            while (anotherSet.next()) {
                System.out.printf("\t%s\t  %s\t \t%s%n",
                        anotherSet.getString("DayID"), anotherSet.getString("VisitorsNum"),
                        anotherSet.getString("Comment"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sort exhibition by visitors ASC
     * @param name
     * @return exhibitionForDB
     */
    public static ExhibitionForDB sortByVisitors(String name) {
        try {
            ExhibitionForDB exhibition = new ExhibitionForDB(name, getExhibitionByName(name).getArtSurname());
            PreparedStatement anotherStatement;
            anotherStatement = connection.prepareStatement(SELECT_FROM_DAYS_ORDER_BY_VISITORS);
            anotherStatement.setInt(1, getIdByName(name));
            ResultSet anotherSet = anotherStatement.executeQuery();
            while (anotherSet.next()) {
                DayForDB day = new DayForDB(anotherSet.getInt("VisitorsNum"),
                        anotherSet.getString("Comment"));
                day.setId(anotherSet.getInt("DayID"));
                exhibition.addDay(day);
            }
            return exhibition;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sort by comments by alphabet
     * @param name
     * @return sorted exhibition
     */
    public static ExhibitionForDB sortByComments(String name) {
        try {
            ExhibitionForDB exhibition = new ExhibitionForDB(name, getExhibitionByName(name).getArtSurname());
            PreparedStatement anotherStatement;
            anotherStatement = connection.prepareStatement(SELECT_FROM_DAYS_ORDER_BY_COMMENTS);
            anotherStatement.setInt(1, getIdByName(name));
            ResultSet anotherSet = anotherStatement.executeQuery();
            while (anotherSet.next()) {
                DayForDB day = new DayForDB(anotherSet.getInt("VisitorsNum"),
                        anotherSet.getString("Comment"));
                day.setId(anotherSet.getInt("DayID"));
                exhibition.addDay(day);
            }
            return exhibition;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get exhibition by name
     * @param name
     * @return exhibition
     * @throws SQLException
     */
    public static ExhibitionForDB getExhibition(String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new ExhibitionForDB(resultSet.getString("Name"), resultSet.getString("ArtSurname"));
    }

    public static void showSortedByVisitors(String name) {
        showExhibition(name, Show.SORTED);
    }

    /**
     * Find and print days by certain word
     * @param word
     */
    public static void findWord(String word) {
        try {
            String query = SELECT_FROM_DAYS_WHERE_WORD.replace("key_word", word);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("ID\tExhibition\tVisitors number\tComment");
            while (resultSet.next()) {
                System.out.printf("%s\t  %s\t  %s\t\t%s%n",
                        resultSet.getString("DayID"), resultSet.getString("Name"),
                        resultSet.getString("VisitorsNum"), resultSet.getString("Comment"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to search days by certain word
     * @param word
     * @return list of daysForDB
     */
    public static List<DayForDB> searchByWord(String word) {
        try {
            word = word.toLowerCase();
            String query = "SELECT * FROM exhibitionsDB.days WHERE Comment LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + word + "%");
            ResultSet resultSet = statement.executeQuery();

            List<DayForDB> list = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("DayID");
                int visitorsNum = resultSet.getInt("VisitorsNum");
                String comments = resultSet.getString("Comment").toLowerCase();

                list.add(new DayForDB(visitorsNum, comments, id));
            }
            resultSet.close();
            statement.close();

            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds day to db
     * @param name
     * @param day
     */
    public static void addDay(String name, DayForDB day) {
        ExhibitionForDB exhibition = getExhibitionByName(name);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_DAYS);
            preparedStatement.setInt(1, day.getVisitorsNum());
            preparedStatement.setInt(2, getIdByName(exhibition.getName()));
            preparedStatement.setString(3, day.getComment());
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove day from db
     * @param name
     * @param visitorsNum
     */
    public static void removeDay(String name, int visitorsNum) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_VISITORS);
            preparedStatement.setInt(1, getIdByName(name));
            preparedStatement.setInt(2, visitorsNum);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove exhibition from db by id
     * @param id
     */
    public static void removeExhibition(long id) {
        try {
            removeDaysByExhibitionID(id);
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_EXHIBITION_ID);
            statement.setLong(1, id);

            statement.executeUpdate();
            statement.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes day by ID
     * @param id
     */
    public static void removeDayByID(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_DAY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes all days of certain exhibition by id
     * @param id
     */
    public static void removeDaysByExhibitionID(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DAYS);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes connection with DB
     */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates exhibition name
     * @param exhibition
     * @param newName
     * @throws SQLException
     */
    public static void updateExhibitionName(ExhibitionForDB exhibition, String newName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_EXHIBITION_NAME);
        System.out.println(newName);
        statement.setString(1, newName);
        statement.setLong(2, exhibition.getId());
        statement.executeUpdate();
    }

    /**
     * Updates artist surname
     * @param exhibition
     * @param newName
     * @throws SQLException
     */
    public static void updateArtist(ExhibitionForDB exhibition, String newName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_EXHIBITION_ARTIST);
        System.out.println(newName);
        statement.setString(1, newName);
        statement.setLong(2, exhibition.getId());
        statement.executeUpdate();
    }

    /**
     * Get exhibitionID by DayID
     * @param id
     * @return id
     */
    public static long getExhibitionIdInDay(long id) {
        try {
            String query = "SELECT ExhibitionID FROM exhibitionsDB.days WHERE DayID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                long exhibitionID = resultSet.getLong("ExhibitionID");
                return exhibitionID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getAllVisitors(String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_VISITORS);
        statement.setLong(1, getExhibitionByName(name).getId());
        ResultSet resultSet = statement.executeQuery();
        int visitors = 0;
        while (resultSet.next()) {
            int visitorsNum = resultSet.getInt("VisitorsNum");
            visitors += visitorsNum;
        }
        return visitors;
    }

    public static DayForDB getLeastVisitedDay(String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_FROM_DAYS);
        statement.setLong(1, getExhibitionByName(name).getId());
        ResultSet resultSet = statement.executeQuery();
        int minVisitors = Integer.MAX_VALUE;
        DayForDB leastVisitedDay = new DayForDB();
        while (resultSet.next()) {
            int visitorsNum = resultSet.getInt("VisitorsNum");
            if (visitorsNum < minVisitors) {
                minVisitors = visitorsNum;
                leastVisitedDay = new DayForDB(visitorsNum, resultSet.getString("Comment"));
            }
        }
        return leastVisitedDay;
    }
}
