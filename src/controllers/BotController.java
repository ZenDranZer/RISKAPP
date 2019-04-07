package controllers;

import models.GameCountry;
import models.GameState;
import models.Player;

public class BotController {
    GameState gameState;
    Player activePlayer;
    public BotController(GameState state){
        gameState=state;
        activePlayer = gameState.getActivePlayer();
    }
    //Before calling this method, method allocateInitialArmies of  the GameEngine must be called .
    public void assignRemainingArmies(){
        while(activePlayer.getRemainingArmies()!=0){
            for(GameCountry country : activePlayer.getCountries()){
                country.addArmies(1);
                activePlayer.updateRemainingArmies(1);
                if(activePlayer.getRemainingArmies()==0)
                    return;
            }
        }
    }
}
