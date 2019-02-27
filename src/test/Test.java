
package test;
import controllers.MapGenerator;
import org.junit.*;
import org.junit.Assert;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Test {

    @org.junit.Test
    public void testWriteConquestFile() throws IOException {
        MapGenerator mapGenerator = new MapGenerator();

        assertEquals( new MapGenerator().writeConquestFile(), "SUCCESS");

    }
}
