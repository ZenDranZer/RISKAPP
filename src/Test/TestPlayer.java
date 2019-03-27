package Test;

import models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestPlayer {
    Player attacker ;
    Player defender;
    GameCountry attackingCountry1;
    GameCountry defendingCountry1;
    GameCountry attackingCountry2;
    GameCountry defendingCountry2;
    RiskCard riskCard;
    GameMap gameMap;
    GameContinent gameContinent;
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
        attackingCountry1.setArmies(1);
        attackingCountry1.setCountryName("IRAN");
        attackingCountry1.setContinent(gameContinent);
        attackingCountry1.setArmies(2);
        attackingCountry1.setArmies(3);
        defendingCountry1 = new GameCountry();
        defendingCountry1.setArmies(1);
        defendingCountry1.setCountryName("INDIA");
        defendingCountry1.setContinent(gameContinent);
        defendingCountry1.setCurrentPlayer(defender);
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
        defendingCountry2.setCurrentPlayer(defender);

        gameMap.countryHashMap.put(attackingCountry1.getCountryName() , attackingCountry1);
        gameMap.countryHashMap.put(defendingCountry1.getCountryName() , defendingCountry1);
        gameMap.countryHashMap.put(attackingCountry2.getCountryName() , attackingCountry2);
        gameMap.countryHashMap.put(defendingCountry2.getCountryName() , defendingCountry2);
        attacker = new Player("Naghmeh" , 1 , gameMap);
        defender = new Player("Lin" , 2, gameMap);

        attacker.addRiskCard(riskCard);
        defender.addRiskCard(riskCard);
        attacker.setCardsHeld(arrayList);
        defender.setCardsHeld(arrayList);
        attackingCountry1.setCurrentPlayer(attacker);


    }

    @Test
    public void TestAttackFunctionRedDice() {
         assertEquals( "invalid" , attacker.attack(defender , attackingCountry1 , defendingCountry1 , 4 , 1));
    }

    @Test
    public void TestAttackFunctionWhiteDice() {
        assertEquals( "invalid" , attacker.attack(defender , attackingCountry1 , defendingCountry1 , 3 , 4));
    }

    @Test
    public void TestNegativeDiceNumber() {
        assertEquals( "invalid" , attacker.attack(defender , attackingCountry1 , defendingCountry1 , -6 , 1));
    }
   @Test
    public void TestEliminatingPlayer() {
        assertEquals( "eliminated" , attacker.attack(defender , attackingCountry1 , defendingCountry1 , 2 , 1));
   }

   @Test
    public void TestAlloutAttack() {

        assertEquals("Attack was Successful" , attacker.allOutAttack(defender , attackingCountry1 , defendingCountry1));
   }


}
