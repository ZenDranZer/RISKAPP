package Test;

import utils.*;
import controllers.*;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRiskCardController {

   // RiskCardController
    GameMap gameMap;
    Player p1;
    Player p2;
    GameState gameState;
    RiskCardController riskCardController;
    RiskCard r1;
    RiskCard r2;

    @Before
    public void setUp() throws Exception {

        gameMap = new GameMap();
        p1  =new Player();
        p2 = new Player();

        riskCardController = new RiskCardController();

        for (int i=1;i<=4;i++){
            GameCountry country = new GameCountry();
            country.setCountryName("country"+(i));
            gameMap.addCountry(country);
        }

        //riskCardController.initRiskCardDeck(gameMap);


    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Checks if the card deck constructed from Gamemap is correct or not
     */
    @Test
    public void checkDeckSize() {

        riskCardController.initRiskCardDeck(gameMap);
        assertEquals(gameMap.getAllCountries().size(),riskCardController.getCardDeck().size());
    }

    @Test
    public void afterReinforcementCardStatus() {

    }

    @Test
    public void afterAttackCardStatus() {

    }

    @Test
    public void totalCards() {
        int totalCards = 0;
        riskCardController.initRiskCardDeck(gameMap);
        p1.addRiskCard(riskCardController.allocateRiskCard());
        p2.addRiskCard(riskCardController.allocateRiskCard());
        totalCards = p1.getCardsHeld().size() + p2.getCardsHeld().size() + riskCardController.getCardDeck().size();
        assertEquals(4,totalCards);
    }

}
