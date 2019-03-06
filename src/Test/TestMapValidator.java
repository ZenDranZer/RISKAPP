package Test;

import controllers.MapGenerator;
import controllers.MapValidator;
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

    @Before
    public void setUp() throws Exception {
        validator = new MapValidator();
        mapGenerator = new MapGenerator();
        mapGenerator.readConquestFile("C:\\Users\\shiva\\Desktop\\Africa.map");
        graphUtil = mapGenerator.buildGraph();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHasValidNumberOfNeighbors(){
        for (String s : MapGenerator.countryHashMap.keySet()){
            assertTrue(validator.hasValidNumberOfNeighbors(MapGenerator.countryHashMap.get(s)));
        }

    }
    @Test
    public void testHasNeighbour(){

        for (String s : MapGenerator.countryHashMap.keySet()){
            assertTrue(validator.hasNeighbor(MapGenerator.countryHashMap.get(s)));
        }

    }
    @Test
    public void testNumberOfContinents(){

        assertTrue(validator.hasValidNumberOfContinents(new ArrayList<>(MapGenerator.continentHashMap.values())));

    }
    @Test
    public void TestNumberOfCountries(){

        assertTrue(validator.hasValidNumberOfCountries(new ArrayList<>(MapGenerator.countryHashMap.values())));
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
