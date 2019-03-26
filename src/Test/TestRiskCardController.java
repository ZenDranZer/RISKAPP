package Test;

import utils.*;
import controllers.*;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRiskCardController {

   // RiskCardController
    GameMap gameMap;
    GameEngine gameEngine;
    GameState gameState;
    RiskCardController riskCardController;

    @Before
    public void setUp() throws Exception {

        gameMap = new GameMap();
        for (int i=0;i<10;i++){
            GameCountry country = new GameCountry();
            country.setCountryName("country"+(i+1));
            gameMap.addCountry(country);
        }
        riskCardController = new RiskCardController();
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
        System.out.println(gameMap.getAllCountries().size());
        System.out.println(riskCardController.getCardDeck().size());
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

    }

}
