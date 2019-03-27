package Test;

import controllers.*;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test Class for Risk Cards
 */
public class TestRiskCardController {

    GameMap gameMap;
    Player p1;
    Player p2;
    GameState gameState;
    RiskCardController riskCardController;
    RiskCard riskcard;
    GameCountry attackingCountry;
    GameCountry defendingCountry;

    @Before
    public void setUp() throws Exception {

        gameMap = new GameMap();
        p1  =new Player();
        p2 = new Player();
        riskcard = new RiskCard();

        riskCardController = new RiskCardController();

        for (int i=1;i<=8;i++){
            GameCountry country = new GameCountry();
            country.setCountryName("country"+(i));
            gameMap.addCountry(country);
        }

    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Checks if the card deck constructed from Gamemap is correct or not
     */
    @Test
    public void testCheckDeckSize() {

        riskCardController.initRiskCardDeck(gameMap);
        assertEquals(gameMap.getAllCountries().size(),riskCardController.getCardDeck().size());
    }

    @Test
    public void testAfterReinforcementCardStatus() {

        HashMap<String,ArrayList<RiskCard>> possibleset = new HashMap<>();
        ArrayList<RiskCard> chooseSet;
        int initialArmies =0;
        int armiesAfterTrade = 0;


        riskCardController.initRiskCardDeck(gameMap);
        for(int i=0;i< 8;i++)
            p1.addRiskCard(riskCardController.allocateRiskCard());

        initialArmies = p1.getRemainingArmies();

        while (p1.getCardsHeld().size() >= 5) {
            possibleset = riskCardController.getPossibleSets(p1);
            chooseSet = possibleset.get("1");

            riskCardController.tradeCards(p1,chooseSet);
        }

        armiesAfterTrade = p1.getRemainingArmies();

        assertTrue(p1.getCardsHeld().size()<5);
        assertTrue(initialArmies < armiesAfterTrade);
    }

    @Test
    public void testTotalCards() {
        int totalCards = 0;
        riskCardController.initRiskCardDeck(gameMap);
        p1.addRiskCard(riskCardController.allocateRiskCard());
        p2.addRiskCard(riskCardController.allocateRiskCard());
        totalCards = p1.getCardsHeld().size() + p2.getCardsHeld().size() + riskCardController.getCardDeck().size();
        assertEquals(8,totalCards);
    }
}
