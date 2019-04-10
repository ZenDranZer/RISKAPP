package models;

import controllers.RiskCardController;

import java.io.Serializable;
import java.util.*;

/**
 * Class that represents and holds the data for the GamePlay Contains all the
 * players and the map
 */
public class GameState extends Observable implements Serializable {

	private ArrayList<Player> players;

	private GameMap gameMap;
	private Player activePlayer;
	private RiskCardController riskController;
	public int attacked = 0;
	public String phase;
	public String logs;

	public GameState() {
		players = new ArrayList<>();
		gameMap = new GameMap();
		riskController = new RiskCardController();
	}

	/**
	 * Returns riskCardController Object
	 */
	public RiskCardController getRiskController() {
		return riskController;
	}

	/**
	 * Gets the list of player
	 * 
	 * @return ArrayList of active players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * This function sets the players passed in arraylist
	 * @param lstPlayers contains player data
	 */
	public void setPlayers(ArrayList<Player> lstPlayers) {
		this.players = players;
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
	 * @param objPlayer Player data
	 */
	public void setActivePlayer(Player objPlayer) {
		activePlayer = objPlayer;
	}

	/**
	 *Gets the GameMap object
	 * @return GameMap object
	 */

	public GameMap getGameMapObject() { return gameMap; }

	/**
	 * Returns the next player from Player ArrayList
	 */
	public Player getNextPlayer(Player activePlayer, boolean checkInitialAllocation) {

		int currentIndex = players.indexOf(activePlayer);
		Player nextPlayer = players.get(currentIndex);

		if (checkInitialAllocation) {

			int allocatedPlayers = 0;
			do {
				currentIndex += 1;
				if (currentIndex >= players.size()) {
					currentIndex = 0;
				}

				if (players.get(currentIndex).getRemainingArmies() > 0 && !players.get(currentIndex).isAllocationComplete() ) {
					nextPlayer = players.get(currentIndex);
					break;
				}
				allocatedPlayers++;

			} while (allocatedPlayers < players.size());
			System.out.println(nextPlayer.getName());
			return nextPlayer;
		}

		currentIndex = currentIndex + 1;
		if (currentIndex >= players.size()) {
			currentIndex = 0;
		}

		return players.get(currentIndex);
	}

	/**
	 * Checks if army allocation is complete for all the Players in the game
	 * @return True if allocation complete. False otherwise
	 */

	public boolean isAllocationComplete() {
		boolean isAllocated = true;
		for (Player pl : players) {
			if (pl.getRemainingArmies() > 0  && !pl.isAllocationComplete()) {
				isAllocated = false;
				break;
			}
		}
		return isAllocated;
	}

	/**
	 * Observer for viewing change in GameState
	 */
	public void notifyGameStateChange() {
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Invokes the reinforcement for active player
	 * @param activePlayer
	 * @param country
	 * @param armies
	 */
	public void reinforcement(Player activePlayer, String country, int armies) {
		activePlayer.reinforcement(country, armies);
		notifyGameStateChange();
	}
	/**
	 * Observer for viewing change in Game based on attack phase
	 * @param defender Defender
	 * @param attackingCountry Country from which attacker is attacking
	 * @param defendingCountry Country on which attacker is attacking
	 * @param redDice Dice count
	 * @param whiteDice Dice Count
	 */

	public void attack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry, int redDice, int whiteDice) {
		String status = activePlayer.attack(defender, attackingCountry, defendingCountry, redDice, whiteDice);
		if(status.equalsIgnoreCase("Success"))
			attacked=1;
		setChanged();
		notifyObservers(status);
	}

	/**
	 * Observer for viewing change in Game based on all-out attack phase
	 * @param defender Defender
	 * @param attackingCountry Country from which attacker is attacking
	 * @param defendingCountry Country on which attacker is attacking
	 */
	public void allOutAttack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry) {
		RiskCard card = new RiskCard();
		String status = activePlayer.allOutAttack(defender, attackingCountry, defendingCountry);
		setChanged();
		notifyObservers(status);
	}

	/**
	 * Observer for viewing change in Game based on fortification Phase
	 * @param countryToFortify Country to fortify
	 * @param fortifyFrom Country from which attacker is fortifying the other country
	 * @param armies Number of armies to be transferred
	 */
	public void fortification(String countryToFortify, String fortifyFrom, int armies) {
		activePlayer.fortify(countryToFortify, fortifyFrom, armies);
		notifyGameStateChange();
	}

	/**
	 * Sets current phase
	 * @param phase phase to be set as current
	 */
	public void setCurrentPhase(String phase) {
		this.phase = phase;
	}

	/**
	 * Gets current phase
	 * @return curretn phase
	 */
	public String getCurrentPhase() {
		return this.phase;
	}

	/**
	 * Sets logs for given players
	 * @param gamePlayLogs logs
	 */
	public void setLogs(String gamePlayLogs) {
		this.logs = gamePlayLogs;
	}

	/**
	 * Gets logs for the given phase
	 * @return logs
	 */
	public String getLogs() {
		return this.logs;
	}

	/**
	 * Updates the logs
	 * @param message message to be displayed
	 */
	public void updateLogs(String message) {
		this.logs += message;
	}
}
