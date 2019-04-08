package controllers;

import models.*;

public class BotController {
    GameState gameState;
    Player activePlayer;
    public BotController(GameState state) {
        gameState=state;
        activePlayer = gameState.getActivePlayer();
    }

    //Before calling this method, method allocateInitialArmies of  the GameEngine must be called .
    public void assignRemainingArmies() {
        while(activePlayer.getRemainingArmies()!=0) {
            for(GameCountry country : activePlayer.getCountries()) {
                country.addArmies(1);
                activePlayer.updateRemainingArmies(1);
                if(activePlayer.getRemainingArmies()==0)
                    return;
            }
        }
    }
    
    /**
     * check if player is a bot
     * @param pl Player object
     * @return true if player is bot
     */
	public boolean isBot(Player pl) {
		System.out.println("pl insatnce check " + (pl instanceof AggressivePlayer));
		if (pl instanceof AggressivePlayer || pl instanceof RandomPlayer || pl instanceof BenevolentPlayer
				|| pl instanceof CheaterPlayer) {
			return true;
		}
		return false;
	}

	public void executeTurn(Player pl) {
		pl.reinforcement();
		pl.attack();
		pl.fortify();
	}
}
