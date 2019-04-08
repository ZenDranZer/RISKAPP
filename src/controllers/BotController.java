package controllers;

import models.*;

public class BotController {
    GameState gameState;
   // Player activePlayer;
    public BotController(GameState state) {
        gameState=state;
        //activePlayer = gameState.getActivePlayer();
    }

    //Before calling this method, method allocateInitialArmies of  the GameEngine must be called .
    public void assignRemainingArmies() {
        while(gameState.getActivePlayer().getRemainingArmies()!=0 && !gameState.getActivePlayer().isAllocationComplete()) {
            for(GameCountry country : gameState.getActivePlayer().getCountries()) {
                country.addArmies(1);
                gameState.getActivePlayer().updateRemainingArmies(1);
                if(gameState.getActivePlayer().getRemainingArmies()==0)
                    return;
            }
        }
        gameState.getActivePlayer().setRemainingArmies(0);
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
		String message = pl.attack();
		if(message.split(",")[0].equals("winner"))
		{
			System.out.println(message);
		}
		pl.fortify();
	}
}
