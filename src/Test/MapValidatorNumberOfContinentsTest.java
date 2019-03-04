package Test;

import controllers.MapGenerator;
import controllers.MapValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.GraphUtil;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MapValidatorNumberOfContinentsTest {
    MapValidator validator;
    MapGenerator mapGenerator;
    GraphUtil graphUtil;

    @Before
    public void setUp() throws Exception {
        validator = new MapValidator();
        mapGenerator = new MapGenerator();
        mapGenerator.readConquestFile("/home/naghmeh/IdeaProjects/RISKAPP_new/src/mapFiles/ABC_Map.map");
        graphUtil = new GraphUtil();
        graphUtil.setCountryGraph(MapGenerator.countryHashMap);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mapValidationTest(){

        assertTrue(validator.hasValidNumberOfContinents(new ArrayList<>(MapGenerator.continentHashMap.values())));
       /* assertTrue(validator.hasValidNumberOfCountries(new ArrayList<>(MapGenerator.countryHashMap.values())));
        for (String s : MapGenerator.countryHashMap.keySet()){
            assertTrue(validator.hasNeighbor(MapGenerator.countryHashMap.get(s)));
            assertTrue(validator.hasValidNumberOfNeighbors(MapGenerator.countryHashMap.get(s)));
        }

        assertTrue(validator.isWholeMapConnected(graphUtil, new ArrayList<>(MapGenerator.countryHashMap.values()) ));*/
    }

}