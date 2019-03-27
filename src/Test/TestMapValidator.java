package Test;

import controllers.GameEngine;
import controllers.MapGenerator;
import controllers.MapValidator;
import models.GameContinent;
import models.GameMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.GraphUtil;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test Class for Map Validator Module
 */
public class TestMapValidator {
    MapValidator validator;
    MapGenerator mapGenerator;
    GraphUtil graphUtil;
    GameEngine gameEngine;
    GameMap gameMap;

    @Before
    public void setUp() {
        gameEngine = new GameEngine();
        mapGenerator = gameEngine.getMapGenerator();
        mapGenerator.readConquestFile("C:\\Users\\shiva\\Desktop\\Africa.map");
        gameMap = gameEngine.getGameState().getGameMapObject();
        graphUtil = mapGenerator.buildGraph();
        validator = new MapValidator(gameMap);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHasValidNumberOfNeighbors() {
        for (String s : gameMap.getCountryHashMap().keySet()) {
            assertTrue(validator.hasValidNumberOfNeighbors(gameMap.getCountryHashMap().get(s)));
        }
    }

    @Test
    public void testHasNeighbour() {

        for (String s : gameMap.getCountryHashMap().keySet()) {
            assertTrue(validator.hasNeighbor(gameMap.getCountryHashMap().get(s)));
        }
    }

    @Test
    public void testNumberOfContinents() {

        assertTrue(validator.hasValidNumberOfContinents(new ArrayList<>(gameMap.getContinentHashMap().values())));

    }

    @Test
    public void TestNumberOfCountries() {

        assertTrue(validator.hasValidNumberOfCountries(new ArrayList<>(gameMap.getCountryHashMap().values())));
    }

    @Test
    public void TestIsWholeGraphConnected() {

        assertTrue(validator.isWholeMapConnected(graphUtil));
    }

    @Test
    public void TestMapValidity() {
       String output = mapGenerator.validateMap();
       assertEquals("SUCCESS",output);
    }

    @Test
    public void TestIsContinentFullyLinked(){
        for(GameContinent continent : gameMap.getContinentHashMap().values()){
            assertTrue(validator.isContinentFullyLinked(continent,graphUtil));
        }
    }
}
