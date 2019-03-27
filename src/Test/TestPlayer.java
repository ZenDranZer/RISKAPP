package Test;

import models.*;
import org.junit.Before;
import org.junit.Test;
import views.GamePlay;

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
        attacker.setPlayerArmies(1);
        attacker.setPlayerArmies(2);
        attacker.setPlayerArmies(3);
        defender.setCardsHeld(arrayList);
        attackingCountry1.setCurrentPlayer(attacker);
        gameState = new GameState();
        gameState.setActivePlayer(attacker);
        defender.setPlayerArmies(1);


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

        assertEquals("eliminated" , attacker.allOutAttack(defender , attackingCountry1 , defendingCountry1));
   }

   @Test
    public void TestNumberOfCountriesGettingEliminated() {
       attacker.attack(defender , attackingCountry1 , defendingCountry1 , 2 , 1);
       assertEquals( 0,defender.getCountries().size());
   }

   @Test
    public void TestNumberOfArmiesAddedtoWinner() {
       gameState.attack(defender , attackingCountry1 , defendingCountry1 , 1 , 1);
       if(gameState.attacked==1){
           assertEquals(3,gameState.getActivePlayer().getPlayerArmies());
       }
       else{
           assertEquals(2,gameState.getActivePlayer().getPlayerArmies());

       }

   }


}
