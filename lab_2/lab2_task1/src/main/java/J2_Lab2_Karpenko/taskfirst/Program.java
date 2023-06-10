package J2_Lab2_Karpenko.taskfirst;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Program class provides utility methods for reading and writing
 * <p>
 * ListSubwStream objects to and from text files.
 */
public class Program {
    static Logger logger = LogManager.getLogger(Program.class);

    /**
     * Write the given ListSubwStream object to a text file.
     *
     * @param station  the ListSubwStream object to write
     * @param filename the name of the file to write to
     * @throws IOException if an I/O error occurs
     */
    public static void writeToFileTxt(ListSubwStream station, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            logger.info("Write to text file");
            writer.write(station.toString());
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Read a ListSubwStream object from a text file.
     *
     * @param filename the name of the file to read from
     * @return the ListSubwStream object read from the file
     * @throws IOException if an I/O error occurs
     */
    public static ListSubwStream readFromFileTxt(String filename) throws IOException {
        ListSubwStream station = ListSubwStream.createSubwayStation();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            logger.info("Read from the text file");
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String name = parts[1].substring(parts[1].indexOf("'") + 1, parts[1].lastIndexOf("'"));
                    int year = Integer.parseInt(parts[2].substring(parts[2].indexOf("=") + 1, parts[2].lastIndexOf(",")));
                    HourWithDates[] hours = parseHours(reader);
                    station = new ListSubwStream(name, year);
                    station.setHours(hours);
                }
            }
        }
        return station;
    }

    /**
     * Parse HourWithDates objects from a BufferedReader.
     *
     * @param reader the BufferedReader to read from
     * @return an array of HourWithDates objects parsed from the reader
     * @throws IOException if an I/O error occurs
     */
    private static HourWithDates[] parseHours(BufferedReader reader) throws IOException {
        List<HourWithDates> hours = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null && line.startsWith("{") && line.endsWith("}")) {
            String[] parts = line.split(", ");
            if (parts.length == 3) {
                int passengers = Integer.parseInt(parts[0].substring(parts[0].indexOf(":") + 1, parts[0].lastIndexOf(",")));
                String comment = parts[1].substring(parts[1].indexOf("'") + 1, parts[1].lastIndexOf("'"));
                ZonedDateTime time = ZonedDateTime.parse(parts[2].substring(parts[2].indexOf(":") + 1), DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'ã.'"));
                hours.add(new HourWithDates(passengers, comment, time));
            }
        }
        return hours.toArray(new HourWithDates[0]);
    }

    public static void check_write_read_txt() throws IOException {
        ListSubwStream stationWrite =
                ListSubwStream.createSubwayStation();
        writeToFileTxt(stationWrite, "station.txt");
        System.out.println("\nWrite_to_file....\n");
        System.out.printf("%s\t%s", stationWrite.getName(), stationWrite.getOpeningYear());
        System.out.println(stationWrite.toStringHours(stationWrite.getHours()));

        System.out.println("Read_from_file....\n");
        ListSubwStream station = readFromFileTxt("station.txt");
        System.out.printf("%s\t%s", station.getName(), station.getOpeningYear());
        System.out.println(station.toStringHours(station.getHours()));
    }

    public static void main(String[] args) {
        try {
            check_write_read_txt();
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }
}
