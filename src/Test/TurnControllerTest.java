package Test;

import utils.*;
import controllers.TurnController;
import models.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TurnControllerTest {
    private ArrayList<Player> lstPlayers;
    
    TestUtil testData;
    TurnController objTurnController;
    @Before
    public void setUp() throws Exception {
        testData = new TestUtil();
        objTurnController = new TurnController();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test function to test if the total number of armies with the player countires
     * is same as that assigned to the player initially
     * @throws Exception
     */
    @Test
    public void testPlayerArmies() throws Exception {
        /*Check if the number of armies possessed by the player is not more than the
          initial number of armies assigned to the player*/
        TurnController t = new TurnController();
        for(int i=0 ; i<lstPlayers.size();i++) {
            assertEquals(t.getEachPlayerArmy(lstPlayers),lstPlayers.get(i).getPlayerArmies());
        }
    }

    /**
     * Test function to test if each continent has atleast 1 army stationed on it
     * @throws Exception
     */
    @Test
    public void testInitialArmiesContinent() throws Exception {
        /* Check if all the continents contained by the players has atleast 1 army assigned to it */
        for(int i=0;i<lstPlayers.size();i++) {
            for (int j=0; j<lstPlayers.get(i).getCountries().size();j++) {
                assertTrue(lstPlayers.get(i).getCountries().get(i).getArmiesStationed() > 1);
            }
        }
    }
    
    /**
     * Test the calculation on new armies to allocated at the beginning of each turn 
     */
    @Test
    public void testCalculateNewArmies()
    {
    	int expectedArmies = 3;
    	
    	int actualArmies = objTurnController.calculateNewArmies(testData.lstPlayers.get(0));
    	assertEquals(expectedArmies, actualArmies);
    }

    /**
     * Tests allocation of initial armies to each player
     */
    @Test
    public void testGetEachPlayerArmy()
    {
    	int expectedArmies = 40;
    	int actualArmies = objTurnController.getEachPlayerArmy(testData.lstPlayers);
    	assertEquals(expectedArmies,actualArmies);
    }
    
}