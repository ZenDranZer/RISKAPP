package Test;

import models.*;
import org.junit.Before;
import org.junit.Test;
import views.GamePlay;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestAggressivePlayer {
    Player attacker ;
    Player defender;
    GameCountry attackingCountry1;
    GameCountry defendingCountry1;
    GameCountry attackingCountry2;
    GameCountry defendingCountry2;
    RiskCard riskCard;
    GameMap gameMap;
    GameContinent gameContinent;
    GamePlay gamePlay ;
    GameState gameState;
    @Before
    public void setup(){

        gameMap = new GameMap();
        gameContinent =new GameContinent();
        gameContinent.setContinentName("Asia");
        riskCard =new RiskCard();
        riskCard.setArmyType(2);
        riskCard.setCountryName("IRAN");

        ArrayList<RiskCard> arrayList = new ArrayList<>();
        arrayList.add(riskCard);

        attackingCountry1 = new GameCountry();
        attackingCountry1.setArmies(5);
        attackingCountry1.setCountryName("IRAN");
        attackingCountry1.setContinent(gameContinent);
        attackingCountry1.setArmies(2);
        attackingCountry1.setArmies(3);
        defendingCountry1 = new GameCountry();
        defendingCountry1.setArmies(6);
        defendingCountry1.setCountryName("INDIA");
        defendingCountry1.setContinent(gameContinent);

        attackingCountry1.addNeighbouringCountry(defendingCountry1);
        defendingCountry1.addNeighbouringCountry(attackingCountry1);
        //---
        attackingCountry2 = new GameCountry();
        attackingCountry2.setArmies(1);
        attackingCountry2.setCountryName("RUSSA");
        attackingCountry2.setContinent(gameContinent);
        attackingCountry2.setArmies(2);
        attackingCountry2.setArmies(3);
        defendingCountry2 = new GameCountry();
        defendingCountry2.setArmies(1);
        defendingCountry2.setCountryName("CHINA");
        defendingCountry2.setContinent(gameContinent);

        attackingCountry1.addNeighbouringCountry(defendingCountry1);
        attackingCountry1.addNeighbouringCountry(defendingCountry2);
        defendingCountry1.addNeighbouringCountry(attackingCountry1);
        defendingCountry2.addNeighbouringCountry(attackingCountry1);

        gameMap.countryHashMap.put(attackingCountry1.getCountryName() , attackingCountry1);
        gameMap.countryHashMap.put(defendingCountry1.getCountryName() , defendingCountry1);
        gameMap.countryHashMap.put(attackingCountry2.getCountryName() , attackingCountry2);
        gameMap.countryHashMap.put(defendingCountry2.getCountryName() , defendingCountry2);
        gameMap.continentHashMap.put(gameContinent.getContinentName() , gameContinent);


        gameState = new GameState();
        gameState.setActivePlayer(attacker);


        gameContinent.setCountries(attackingCountry1);
        gameContinent.setCountries(attackingCountry2);
        gameContinent.setCountries(defendingCountry1);
        gameContinent.setCountries(defendingCountry2);

        gameMap.addCountry(attackingCountry1);
        gameMap.addCountry(attackingCountry2);
        gameMap.addCountry(defendingCountry1);
        gameMap.addCountry(defendingCountry2);
        attacker = new AggressivePlayer("Naghmeh" , 1 , gameMap);
        defender = new Player("Lin" , 2, gameMap);
        defender.setContinents(gameContinent);
        attacker.setCountries(attackingCountry1);
        System.out.println(attackingCountry1.getNeighbouringCountries().size() + "333");
        defender.setPlayerArmies(3);
        attacker.addRiskCard(riskCard);
        defender.addRiskCard(riskCard);
        attacker.setCardsHeld(arrayList);
        attacker.setPlayerArmies(1);
        attacker.setPlayerArmies(2);
        attacker.setPlayerArmies(7);
        defender.setCardsHeld(arrayList);
        attacker.setContinents(gameContinent);
        attackingCountry1.setCurrentPlayer(attacker);
        defendingCountry2.setCurrentPlayer(defender);
        defendingCountry1.setCurrentPlayer(defender);
    }


    @Test
    public void TestAttackFunctionRedDice() {
        assertEquals( "eliminated| attack by Naghmeh to Lin" , attacker.attack());


    }

    @Test
    public void TestReinforcement() {
        assertEquals( "Naghmeh moved 4 number of armies to IRAN\n" , attacker.reinforcement(4));
    }
}
