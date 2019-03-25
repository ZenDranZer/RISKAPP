package models;

import controllers.RiskCardController;

import java.util.*;

/**
 * Class that represents and holds the data for the GamePlay Contains all the
 * players and the map
 */
public class GameState extends Observable {

	private ArrayList<Player> players;
	private String mapPath;

	private GameMap gameMap;
	private Player activePlayer;
	private RiskCardController riskController;

	public GameState() {
		players = new ArrayList<>();
		gameMap = new GameMap();
		riskController = new RiskCardController();
	}

	/**
	 * Gets the list of player
	 * 
	 * @return ArrayList of active players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

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
	 * @param objPlayer
	 */
	public void setActivePlayer(Player objPlayer) {
		activePlayer = objPlayer;
	}
	
	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	public GameMap getGameMapObject() {
		return gameMap;
	}

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
			return nextPlayer;
		}

		currentIndex = currentIndex + 1;
		if (currentIndex >= players.size()) {
			currentIndex = 0;
		}

		return players.get(currentIndex);
	}

	public boolean isAllocationComplete() {
		boolean isAllocated = true;
		for (Player pl : players) {
			if (pl.getRemainingArmies() > 0) {
				isAllocated = false;
				break;
			}
		}
		return isAllocated;
	}

	public void notifyGameStateChange() {
		setChanged();
		notifyObservers();
	}

	//reinforce
	//attack
	public void attack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry, int redDice, int whiteDice)
	{
		String status = activePlayer.attack(defender, attackingCountry, defendingCountry, redDice, whiteDice);
		RiskCard card = new RiskCard();
		if (status.equalsIgnoreCase("success")) {
			card = riskController.allocateRiskCard();
		}
		this.activePlayer.getCardsHeld().add(card);
		setChanged();
		notifyObservers(status);
	}
	
	public void allOutAttack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry)
	{
		activePlayer.allOutAttack(defender, attackingCountry, defendingCountry);
		setChanged();
		notifyObservers("done!");
	}
	
	//fortify
	public void fortification(String countryToFortify, String fortifyFrom, int armies)
	{
		activePlayer.fortify(countryToFortify, fortifyFrom, armies);
		notifyGameStateChange();
	}
}
