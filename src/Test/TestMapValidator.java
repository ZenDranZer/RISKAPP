package Test;

import controllers.GameEngine;
import controllers.MapGenerator;
import controllers.MapValidator;
import models.GameContinent;
import models.GameCountry;
import models.GameMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.GraphUtil;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMapValidator {
    MapValidator validator;
    MapGenerator mapGenerator;
    GraphUtil graphUtil;
    GameEngine gameEngine;
    GameMap gameMap;

    @Before
    public void setUp() throws Exception {
        validator = new MapValidator(gameMap);
        mapGenerator = new MapGenerator(gameMap);
        mapGenerator.readConquestFile("C:\\Users\\shiva\\Desktop\\Africa.map");
        graphUtil = mapGenerator.buildGraph();
        gameMap = gameEngine.getGameState().getGameMapObject();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHasValidNumberOfNeighbors(){
        for (String s : gameMap.getCountryHashMap().keySet()){
            assertTrue(validator.hasValidNumberOfNeighbors(gameMap.getCountryHashMap().get(s)));
        }

    }
    @Test
    public void testHasNeighbour(){

        for (String s : gameMap.getCountryHashMap().keySet()){
            assertTrue(validator.hasNeighbor(gameMap.getCountryHashMap().get(s)));
        }

    }
    @Test
    public void testNumberOfContinents(){

        assertTrue(validator.hasValidNumberOfContinents(new ArrayList<>(gameMap.getContinentHashMap().values())));

    }
    @Test
    public void TestNumberOfCountries(){

        assertTrue(validator.hasValidNumberOfCountries(new ArrayList<>(gameMap.getCountryHashMap().values())));
    }
    @Test
    public void TestIsWholeGraphConnected(){

        assertTrue(validator.isWholeMapConnected(graphUtil));
    }
    @Test
    public void TestMapValidity(){
       String output = mapGenerator.validateMap();
       assertEquals("SUCCESS",output);
    }


}
