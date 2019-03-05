package controllers;

import models.*;
import java.util.*;
import static utils.Constants.ARMY_DISTRIBUTION.*;

/**
 * 
 * @author Sidhant Gupta, Jil Mehta
 *
 *         Controller for handling turn based game logic
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
	 * 
	 * @param activePlayer
	 *            current player
	 * @return availableArmies
     *            number of new armies to allocate
	 */
	public int calculateNewArmies(Player activePlayer) {

		int countryCount = activePlayer.getCountries().size();

		availableArmies = 3;
		if (countryCount / 3 > 3)
			availableArmies = countryCount / 3;

		for (GameContinent continent : activePlayer.getContinents()) {
			availableArmies = continent.getContinentValue();
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

			// get object from list where name matches the given object
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
	 * @return returns
     *            message showing success or failure of fortification
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
	 * @return armiesForeachPlayer
     *          Armies that are assigned to each player based on game rules
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
	 * Assigns 1 army to each country possessed by the players
	 * 
	 * @param activePlayers
	 *            ArrayList containing player data
	 * @param remainingArmies
	 *            ArrayList containing the remaining number of armies with
	 *            player after initial allocation of armies
	 * @param armiesForeachPlayer
	 *            Number of armies assigned to the player
     * @return remainingArmies
     *            Armies left with the player after initial allocation of armies
	 */
	public ArrayList<Integer> allocateInitialArmy(ArrayList<Player> activePlayers, int armiesForeachPlayer) {

		ArrayList<Integer> remainingArmies = new ArrayList<Integer>();

		for (int i = 0; i < activePlayers.size(); i++) {
			activePlayers.get(i).setPlayerArmies(armiesForeachPlayer);
			remainingArmies.add(i, armiesForeachPlayer);
			int tempRemainingArmies = remainingArmies.get(i);
			for (int j = 0; j < activePlayers.get(i).getCountries().size(); j++) {
				if (tempRemainingArmies > 0) {
					activePlayers.get(i).getCountries().get(j).setArmies(1);
					tempRemainingArmies--;
				}
			}
			remainingArmies.set(i, tempRemainingArmies);
		}
		return remainingArmies;
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
