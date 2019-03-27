package Test;

import models.GameCountry;
import models.Player;
import models.RiskCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestPlayer {
    Player player ;
    GameCountry attackingCountry;
    GameCountry defendingCountry;
    RiskCard riskCard;

    @Before
    public void setup(){
        riskCard =new RiskCard();
        riskCard.setArmyType(2);
        riskCard.setCountryName("IRAN");
        player = new Player();
        player.addRiskCard(riskCard);
        ArrayList<RiskCard> arrayList = new ArrayList<>();
        arrayList.add(riskCard);
        player.setCardsHeld(arrayList);
        attackingCountry = new GameCountry();
        attackingCountry.setArmies(1);
        attackingCountry.setArmies(2);
        attackingCountry.setArmies(3);
        defendingCountry = new GameCountry();
        defendingCountry.setArmies(1);
    }

    @Test
    public void TestAttackFunctionRedDice(){
         assertEquals( "invalid" , player.attack(player , attackingCountry , defendingCountry , 4 , 1));
    }

    @Test
    public void TestAttackFunctionWhiteDice(){
        assertEquals( "invalid" , player.attack(player , attackingCountry , defendingCountry , 3 , 4));
    }

    @Test
    public void TestNegetiveDiceNumber(){
        assertEquals( "invalid" , player.attack(player , attackingCountry , defendingCountry , -6 , 1));
    }
//    @Test
//    public void TestWinning(){
//        assertEquals( "winner" , player.attack(player , attackingCountry , defendingCountry , 2 , 1));
//    }
//


}
