package J2_Lab2_Karpenko.taskfirst;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.TypePermission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * The XMLSerializer class provides methods for serializing and deserializing SubwayStation objects to and from XML format.
 */
public class XMLSerializer {
    /*

    The logger used for logging messages.
    */
    static Logger logger = LogManager.getLogger(Program.class);
    /**
     * The XStream object used for serializing and deserializing SubwayStation objects.
     */
    private static final XStream xstream = new XStream(new DomDriver());

    static {
        xstream.alias("station", ListSubwStream.class);
        xstream.alias("hour", Hour.class);
        xstream.alias("hour-with-dates", HourWithDates.class);

        TypePermission listSubwStreamPermission = new ListSubwStreamPermission();
        xstream.addPermission(listSubwStreamPermission);
    }

    /**
     * Serializes a given SubwayStation object to XML format.
     *
     * @param station The SubwayStation object to be serialized.
     * @return A string representing the XML format of the given SubwayStation object.
     */
    public static String serialize(ListSubwStream station) {
        logger.info("Write to xml_file");
        return xstream.toXML(station);
    }

    /**
     * Deserializes a SubwayStation object from a given XML string.
     *
     * @param xml The XML string representing a SubwayStation object.
     * @return A ListSubwStream object deserialized from the given XML string.
     */
    public static ListSubwStream deserialize(String xml) {
        logger.info("Read from xml_file");
        return (ListSubwStream) xstream.fromXML(xml);
    }

    /**
     * The main method of the XMLSerializer class for testing purposes.
     * <p>
     * Serializes a SubwayStation object to XML format, deserializes it, and prints the result to the console.
     *
     * @param args The command-line arguments.
     * @throws IOException if an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        ListSubwStream station = ListSubwStream.createSubwayStation();
        String xml = XMLSerializer.serialize(station);
        ListSubwStream deserializedStation = XMLSerializer.deserialize(xml);

        System.out.printf("%s\t%s", deserializedStation.getName(), deserializedStation.getOpeningYear());
        System.out.println(station.toStringHours(deserializedStation.getHours()));

    }
}
