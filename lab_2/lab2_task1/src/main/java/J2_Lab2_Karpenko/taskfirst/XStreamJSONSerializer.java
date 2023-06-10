package J2_Lab2_Karpenko.taskfirst;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class provides methods for serializing and deserializing ListSubwStream objects to and from JSON format.
 */
public class XStreamJSONSerializer {
    static Logger logger = LogManager.getLogger(Program.class);

    /**
     * A main method for testing purposes that creates a sample ListSubwStream object, serializes it to JSON format,
     * and then deserializes it back to a ListSubwStream object.
     *
     * @param args The command line arguments (not used in this method).
     * @throws IOException If there is an error reading from or writing to a file.
     */
    public static void main(String[] args) throws IOException {
        ListSubwStream station = ListSubwStream.createSubwayStation();
        serialize(station, "station.json");
        ListSubwStream deserializedStation = deserialize("station.json");
        System.out.println(deserializedStation.toString());
    }

    /**
     * Serializes a given ListSubwStream object to JSON format and writes it to a file with the given filename.
     *
     * @param obj      The ListSubwStream object to serialize.
     * @param filename The name of the file to write the serialized JSON to.
     * @throws IOException If there is an error writing to the file.
     */
    public static void serialize(ListSubwStream obj, String filename) throws IOException {
        logger.info("Write to json_file");
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.addPermission(AnyTypePermission.ANY);
        String json = xstream.toXML(obj);
        Files.writeString(new File(filename).toPath(), json);
    }

    /**
     * Deserializes a ListSubwStream object from JSON format stored in a file with the given filename.
     *
     * @param filename The name of the file containing the serialized JSON.
     * @return The deserialized ListSubwStream object.
     * @throws IOException If there is an error reading from the file.
     */
    public static ListSubwStream deserialize(String filename) throws IOException {
        logger.info("Read from json_file");
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.addPermission(AnyTypePermission.ANY);
        String json = Files.readString(new File(filename).toPath());
        return (ListSubwStream) xstream.fromXML(json);
    }
}


