package controllers;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BotController {
	GameState gameState;
	String logger;
	RiskCardController riskCardController;
	public BotController(GameState state) {
		gameState = state;
		logger = "";
		riskCardController = gameState.getRiskController();
		riskCardController.initRiskCardDeck(gameState.getGameMapObject());
	}

	public String getLogs()
	{
		String messages = this.logger;
		this.logger = "";
		return messages;
	}

	/**
	 * Assigns remaining armies to players
	 */
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

	/**
	 * Executes each turn for the player
	 * @param pl Player object
	 * @return Success or error message
	 */
	public String executeTurn(Player pl) {
		try {
			tradeRiskCard(pl,riskCardController);
			logger += pl.reinforcement(pl.getRemainingArmies());
			String message = pl.attack();
			logger += message;
			if (message.split("\\|")[0].equals("winner")) {
				return "winner";
			}
			else if(message.contains("success")){
				tradeRiskCard(pl,riskCardController);
				RiskCard riskCard = riskCardController.allocateRiskCard();
				pl.addRiskCard(riskCard);
				tradeRiskCard(pl,riskCardController);
			}
			logger += pl.fortify();
			return message;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Trades Risk Cards for armies
	 * @param currentPlayer Current player
	 * @param riskCardController Controller for risk cards
	 */
	private void tradeRiskCard(Player currentPlayer,RiskCardController riskCardController){
		if(currentPlayer.getCardsHeld().size()>=5) {
			HashMap<String, ArrayList<RiskCard>> possibleSet = riskCardController.getPossibleSets(currentPlayer);
			for (ArrayList<RiskCard> set:possibleSet.values()) {
				riskCardController.tradeCards(currentPlayer,set);
				System.out.println("Traded "+ set.toString());
				logger+="Traded "+ set.toString();
			}
		}
	}
}
