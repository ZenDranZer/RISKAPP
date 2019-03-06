package controllers;

import models.*;
import java.util.*;
import static utils.Constants.ARMY_DISTRIBUTION.*;

/**
 * Controller for handling turn based game logic
 */
public class TurnController {

	Player activePlayer;
	private int availableArmies;

	public TurnController() {
		availableArmies = 3;
	}

	/**
	 * Set available armies
	 * 
	 * @param armies
	 *            amount of armies to allocate
	 */
	public void setAvailableArmies(int armies) {
		this.availableArmies = armies;
	}

	/**
	 * function to return available armies in the current turn
	 * 
	 * @return available armies
	 */
	public int getAvailableArmies() {
		return this.availableArmies;
	}

	/**
	 * function to get the current player
	 * 
	 * @return current player
	 */
	public Player getActivePlayer() {
		return activePlayer;
	}

	/**
	 * Function to allocate the active player for the next turn
	 * 
	 * @param objPlayer
	 */
	public void setActivePlayer(Player objPlayer) {
		activePlayer = objPlayer;
	}

	/**
	 * function to calculate the new army allocation for the player. Minimum 3
	 * armies are allocated at each turn
	 * @param activePlayer current player
	 * @param mapGenerator MapGenerator object
	 * @return integer number of new armies
	 */
	public int calculateNewArmies(Player activePlayer, MapGenerator mapGenerator) {

		int countryCount = activePlayer.getCountries().size();

		availableArmies = 3;
		if (countryCount / 3 > 3)
			availableArmies = countryCount / 3;

		ArrayList<GameContinent> playerContinents = mapGenerator.checkContinentsOwnedByOnePlayer(activePlayer.getId());

		if (playerContinents != null && playerContinents.size() > 0) {
			for (GameContinent continent : playerContinents) {
				activePlayer.setContinents(continent);
				availableArmies += continent.getContinentValue();
			}
		}

		activePlayer.setRemainingArmies(availableArmies);
		return availableArmies;
	}

	/**
	 * function to reinforce a country with the given armies
	 * 
	 * @param activePlayer
	 *            current player who decides the country and armies
	 * @param country
	 *            country to reinforce
	 * @param armies
	 *            amount of armies to add to a country
	 */
	public void placeArmy(Player activePlayer, String country, int armies) {

		try {
			ArrayList<GameCountry> lstPlayerCountries = activePlayer.getCountries();
			GameCountry matchedCountry = lstPlayerCountries.stream()
					.filter(cntry -> cntry.getCountryName().equals(country)).findFirst().get();
			matchedCountry.setArmies(matchedCountry.getArmiesStationed() + armies);
			System.out.println("Reinforced Country : " + matchedCountry.getArmiesStationed());
			this.availableArmies = this.availableArmies - armies;
		} catch (Exception ex) {

		}
	}

	/**
	 * This function implements the fortification phase moving player arnies
	 * from one country to another
	 * 
	 * @param Countries
	 *            List of game countries
	 * @return returns message showing success or failure of fortification
	 */
	public String fortification(List<GameCountry> Countries) {
		String moveTo = "";
		String moveFrom = "";
		int numOfArmies = 0;
		String returnMessage = "";

		GameCountry moveToCountry = MapGenerator.countryHashMap.get(moveTo);
		GameCountry moveFromCountry = MapGenerator.countryHashMap.get(moveFrom);

		if ((moveFromCountry.getArmiesStationed() - numOfArmies) > 1) {
			moveFromCountry.setArmies(moveFromCountry.getArmiesStationed() - numOfArmies);
			moveToCountry.setArmies(moveToCountry.getArmiesStationed() + numOfArmies);
			returnMessage = "Armies Moved !!!";
		} else {
			returnMessage = "Atleast one army should remain in the country !!!";
		}
		return returnMessage;
	}

	/**
	 * This method allocates the initial number of armies to each player based
	 * upon the number of players in the game
	 * 
	 * @param activePlayers
	 *            Player arraylist containing player data
	 * @return armiesForeachPlayer Armies that are assigned to each player based
	 *         on game rules
	 */
	public int getEachPlayerArmy(ArrayList<Player> activePlayers) {
		int numOfPlayers = activePlayers.size();
		int armiesForeachPlayer = 0;
		switch (numOfPlayers) {
		case 2:
			armiesForeachPlayer = twoPlayerDistribution.getArmyStrength();
			break;
		case 3:
			armiesForeachPlayer = threePlayerDistribution.getArmyStrength();
			break;
		case 4:
			armiesForeachPlayer = fourPlayerDistribution.getArmyStrength();
			break;
		case 5:
			armiesForeachPlayer = fivePlayerDistribution.getArmyStrength();
			break;
		}
		return armiesForeachPlayer;
	}

	/**
	 * Initial allocation of countries to the players at the beginning of the
	 * game. The country allocation happens in a round robin manner
	 *
	 * @param activePlayers
	 *            List of players
	 * @param countries
	 *            List of countries in the map
	 */
	public void allocateCountries(ArrayList<Player> activePlayers, ArrayList<GameCountry> countries) {
		int i = 0;
		Collections.shuffle(countries);

		for (int k = 0; k < countries.size(); k++) {
			if (i == (activePlayers.size())) {
				i = 0;
			}
			countries.get(k).setCurrentPlayer(activePlayers.get(i));
			activePlayers.get(i).setCountries(countries.get(k));
			i++;
		}
	}

}
