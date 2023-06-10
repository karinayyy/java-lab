package J2_Lab2_Karpenko.taskfirst;

import org.junit.Test;

import java.io.IOException;

/**
 * This class provides unit tests for the XStreamJSONSerializer class.
 */
public class XStreamJSONSerializerTest {
    /*
    * Tests that a ListSubwStream object can be serialized to and deserialized from JSON format without losing data.
    * @throws IOException If there is an error reading from or writing to a file.
    */
    @Test
    public void testSerializeAndDeserialize() throws IOException {
        // create test data
        ListSubwStream station = ListSubwStream.createSubwayStation();

        // serialize test data to json file
        String filename = "station_test.json";
        XStreamJSONSerializer.serialize(station, filename);

        // deserialize test data from json file
        ListSubwStream deserializedStation = XStreamJSONSerializer.deserialize(filename);

    }
}


