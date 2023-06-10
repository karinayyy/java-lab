package com.karinayyy.lab4.taskone;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The FileUtils class provides methods for serializing and deserializing SubwayStation objects to and from XML format.
 */
public class FileUtils {
    /**
     * Serializes a given SubwayStation object to XML format.
     *
     * @param metroStream The metroStream object to be serialized.
     * @param fileName    The fileName where metroStream will be saved.
     */
    public static void serializeToXML(MetroStream metroStream, String fileName) throws IOException {
        XStream xstream = new XStream();
        FileWriter writer = new FileWriter(fileName);
        xstream.autodetectAnnotations(true);
        xstream.toXML(metroStream, writer);
        writer.close();
    }

    /**
     * Deserializes a SubwayStation object from a given XML string.
     *
     * @param fileName The XML string representing a SubwayStation object.
     * @return A MetroStream object deserialized from the given XML string.
     */
    public static MetroStream deserializeFromXML(String fileName) throws Exception {
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.processAnnotations(MetroStream.class);
        try {
            FileReader reader = new FileReader(fileName);
            return (MetroStream) xstream.fromXML(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void serializeToJSON(MetroStream obj, String filename) throws IOException {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        FileWriter writer = new FileWriter(filename);
        xstream.autodetectAnnotations(true);
        String json = xstream.toXML(obj);
        Files.writeString(new File(filename).toPath(), json);
    }
    public static MetroStream deserializeFromJSON(String filename) throws IOException {

        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.processAnnotations(MetroStream.class);
        try {
            String json = Files.readString(new File(filename).toPath());
            return (MetroStream) xstream.fromXML(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The main method of the XMLSerializer class for testing purposes.
     * <p>
     * Serializes a MetroStream object to XML format, deserializes it, and prints the result to the console.
     *
     * @param args The command-line arguments.
     * @throws IOException if an I/O error occurs.
     */
    public static void main(String[] args) throws Exception {
        MetroStream station = MetroStream.createSubwayStation();
        String f = "test.xml";
//        String fjson = "output.json";
        String fjson = "newItakeskus.json";
//        serializeToXML(station, f);
//
//        MetroStream deserializedStation = deserializeFromXML(f);
//
//        assert deserializedStation != null;
//        System.out.printf("%s\t%s", deserializedStation.getName(), deserializedStation.getOpeningYear());
//        System.out.println("\n" + station.toStringHours(deserializedStation.getHours()));
//
//        serializeToXML(deserializedStation, f);

        serializeToJSON(station, fjson);
//
//        station = deserializeFromJSON(fjson);
//        System.out.println("//////////////////" + station.toString());


//        DatabaseHandler dbHandler = new DatabaseHandler();
//        dbHandler.writeToDBFromJSON(fjson);
//        dbHandler.getAllHoursForStation(station);
//        dbHandler.writeStation(metroStream.getName(), metroStream.getOpeningYear());
//        dbHandler.writeHour(hou, metroStream.getOpeningYear());
//        dbHandler.writeHour(result.get().getComment(), result.get().getPassengersNumber(), 1);
    }
}
