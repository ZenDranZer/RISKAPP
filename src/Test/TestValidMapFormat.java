package Test;

import controllers.GameEngine;
import controllers.MapGenerator;
import models.GameMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class TestValidMapFormat {

    MapGenerator mapGenerator;
    GameEngine gameEngine;
    GameMap gameMap ;
    @Before
    public void setUp() throws Exception {
        gameEngine = new GameEngine();
        gameMap = new GameMap();
         mapGenerator = new MapGenerator(gameMap);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testValidMapFormat() throws IOException {

        String output = mapGenerator.readConquestFile("C:\\Users\\shiva\\Desktop\\Africa.map");
        assertEquals("SUCCESS",output);

    }
    @Test
    public void testInvalidMapFormat() throws IOException {

        String output = mapGenerator.readConquestFile("C:\\Users\\shiva\\Desktop\\new.map");
        assertNotEquals("SUCCESS",output);

    }


}