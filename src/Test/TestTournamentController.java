package Test;

import controllers.*;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestTournamentController {

    ArrayList<String> player1;
    TournamentController tc;
    int numOfMaps;

    @Before
    public void setUp() throws Exception {

        numOfMaps = 3;
        player1 = new ArrayList<>();
        tc = new TournamentController();
        player1.add("Cheater");
        player1.add("Random");
        tc.setPlayer(player1);
        tc.setMaps(numOfMaps);
    }

    @Test
    public void testBots() {
        assertEquals("class models.CheaterPlayer",tc.getTournament().getBots().get(0).getClass().toString());
    }

    @Test
    public void testBotsArray() {
        assertTrue(tc.getTournament().getBots().size() == 2);
    }

    @Test
    public void testSetMaps() {
        assertEquals(tc.getTournament().getGamestate().size(), numOfMaps);
    }

    @Test
    public void testMapPaths() {
        for (int i=0;i<numOfMaps;i++)
            assertNotNull(tc.getTournament().getMapPaths().get(i));
    }

    @Test
    public void testNumOfMapPathsGenerated() {
        assertTrue(numOfMaps <= tc.getTournament().getMapPaths().size());
    }
}
