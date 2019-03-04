package Test;

import controllers.TurnController;
import models.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TurnControllerTest {
    private ArrayList<Player> players;

    @Before
    public void setUp() throws Exception {
        players = new ArrayList<Player>();
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
        for(int i=0 ; i<players.size();i++) {
            assertEquals(t.getEachPlayerArmy(players),players.get(i).getPlayerArmies());
        }
    }

    /**
     * Test function to test if each continent has atleast 1 army stationed on it
     * @throws Exception
     */
    @Test
    public void testInitialArmiesContinent() throws Exception {
        /* Check if all the continents contained by the players has atleast 1 army assigned to it */
        for(int i=0;i<players.size();i++) {
            for (int j=0; j<players.get(i).getCountries().size();j++) {
                assertTrue(players.get(i).getCountries().get(i).getArmiesStationed() > 1);
            }
        }
    }
}