package shchepinda.labfourth.task3;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
/**
 * Клас, що відповідає Program
 *
 * Дисципліна «Поглиблений курс програмування Java
 * Лабораторна робота №3, Варіант №23, завдання 1
 *
 * @author Щепін Данило, KH-221a
 */
public class FileUtils {
    static Logger logger = LogManager.getLogger(FileUtils.class);
    /**
     * Writes exhibition and day data to the specified file
     *
     * @param exhibition exhibition
     * @param fileName file name
     */
    public static void writeToTxt(ExhibitionWithStreams exhibition, String fileName) {
        logger.info("Write to text file");
        try {
            Files.write(Path.of(fileName), exhibition.toListOfStrings());
        }
        catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }
    /**
     * Reads exhibition and day data from the specified file
     *
     * @param fileName file name
     * @return the object that was created
     */
    public static ExhibitionWithStreams readFromTxt(String fileName) {
        ExhibitionWithStreams exhibition = new ExhibitionWithStreams();
        logger.info("Reading from text file");
        try {
            List<String> list = Files.readAllLines(Path.of(fileName));
            exhibition.fromListOfStrings(list);
        }
        catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
        return exhibition;
    }
    /**
     * Serializes exhibition and day data to the specified XML file
     *
     * @param exhibition exhibition
     * @param fileName file name
     */
    public static void serializeToXML(ExhibitionWithStreams exhibition, String fileName) {
        logger.info("Serializing to XML");
        XStream xStream = new XStream();
        xStream.alias("exhibition", ExhibitionWithStreams.class);
        String xml = xStream.toXML(exhibition);
        try (FileWriter fw = new FileWriter(fileName); PrintWriter out = new PrintWriter(fw)) {
            out.println(xml);
        }
        catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }
    /**
     * Deserializes exhibition and day data from the specified XML file

     * @param fileName file name
     * @return the object that was created
     */
    public static ExhibitionWithStreams deserializeFromXML(String fileName) {
        logger.info("Deserializing from XML");
        try {
            XStream xStream = new XStream();
            xStream.addPermission(AnyTypePermission.ANY);
            xStream.alias("exhibition", ExhibitionWithStreams.class);
            xStream.alias("days", DayWithStreams.class);
            ExhibitionWithStreams exhibition = (ExhibitionWithStreams) xStream.fromXML(new File(fileName));
            return exhibition;
        }
        catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }
    /**
     * Serializes exhibition and day data to the specified JSON file
     *
     * @param exhibition exhibition
     * @param fileName file name
     */
    public static void serializeToJSON(ExhibitionWithStreams exhibition, String fileName) {
        logger.info("Serializing to JSON");
        XStream xStream = new XStream(new JettisonMappedXmlDriver());
        xStream.alias("exhibition", ExhibitionWithStreams.class);
        xStream.alias("days", DayWithStreams.class);
        String xml = xStream.toXML(exhibition);
        try (FileWriter fw = new FileWriter(fileName); PrintWriter out = new PrintWriter(fw)) {
            out.println(xml);
        }
        catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }
    /**
     * Deserializes exhibition and day data from the specified JSON file

     * @param fileName file name
     * @return the object that was created
     */
    public static ExhibitionWithStreams deserializeFromJSON(String fileName) {
        logger.info("Deserializing from JSON");
        try {
            XStream xStream = new XStream(new JettisonMappedXmlDriver());
            xStream.addPermission(AnyTypePermission.ANY);
            xStream.alias("exhibition", ExhibitionWithStreams.class);
            xStream.alias("days", DayWithStreams.class);
            ExhibitionWithStreams exhibition = (ExhibitionWithStreams) xStream.fromXML(new File(fileName));
            return exhibition;
        }
        catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }
    /**
     * Demonstration of the program operation.
     * TXT, XML, and JSON data are written and read sequentially.
     * At the same time, events are recorded in the system log

     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        logger.info("Program started");
        ExhibitionWithStreams exhibition = ExhibitionWithStreams.createExhibitionWithStreams();
        writeToTxt(exhibition, "exhibition.txt");
        exhibition = readFromTxt("exhibition.txt");
        System.out.println("Read from txt file:\n" + exhibition);
        serializeToXML(exhibition, "exhibition.xml");
        exhibition = deserializeFromXML("exhibition.xml");
        System.out.println("Read from XML file:\n" + exhibition);
        serializeToJSON(exhibition, "exhibition.json");
        exhibition = deserializeFromJSON("exhibition.json");
        System.out.println("Read from JSON file:\n" + exhibition);
        logger.info("Program finished");
    }
}
