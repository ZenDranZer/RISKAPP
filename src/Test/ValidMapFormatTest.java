package Test;

import controllers.MapGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class ValidMapFormatTest {

    MapGenerator mapGenerator;
    @Before
    public void setUp() throws Exception {
         mapGenerator = new MapGenerator();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testValidMapFormat() throws IOException {

        String output = mapGenerator.readConquestFile("C:\\Users\\shiva\\Desktop\\Africa.map");
        assertEquals(output,"SUCCESS");
    }


}