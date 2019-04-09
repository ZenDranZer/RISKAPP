package controllers;

import models.*;

public class BotController {
	GameState gameState;
	String logger;
	// Player activePlayer;
	public BotController(GameState state) {
		gameState = state;
		logger = "";
		// activePlayer = gameState.getActivePlayer();
	}

	public String getLogs()
	{
		String messages = this.logger;
		this.logger = "";
		return messages;
	}
	// Before calling this method, method allocateInitialArmies of the
	// GameEngine must be called .
	public void assignRemainingArmies() {
		while (gameState.getActivePlayer().getRemainingArmies() != 0
				&& !gameState.getActivePlayer().isAllocationComplete()) {
			for (GameCountry country : gameState.getActivePlayer().getCountries()) {
				country.addArmies(1);
				gameState.getActivePlayer().updateRemainingArmies(1);
				gameState.getActivePlayer().addPlayerArmy(1);
				if (gameState.getActivePlayer().getRemainingArmies() == 0)
					return;
			}
		}
		gameState.getActivePlayer().setRemainingArmies(0);
	}

	/**
	 * check if player is a bot
	 * 
	 * @param pl
	 *            Player object
	 * @return true if player is bot
	 */
	public boolean isBot(Player pl) {

		if (pl instanceof AggressivePlayer || pl instanceof RandomPlayer || pl instanceof BenevolentPlayer
				|| pl instanceof CheaterPlayer) {
			return true;
		}
		return false;
	}

	public String executeTurn(Player pl) {
		try {
			
			logger += "\n"+ pl.reinforcement(pl.getRemainingArmies());
			String message = pl.attack();
			logger += "\n"+message;
			if (message.split("\\|")[0].equals("winner")) {
				return "winner";
			}
			logger += "\n"+pl.fortify();
			return message;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
