package models;

import java.util.*;

/**
 * Class that represents and holds the data for the GamePlay Contains all the
 * players and the map
 */
public class GameState extends Observable {

	private ArrayList<Player> players;
	private String mapPath;
	private HashMap<String, RiskCard> cardPile;
	private GameMap gameMap;

	public GameState() {
		players = new ArrayList<>();
		cardPile = new HashMap<>();
		gameMap = new GameMap();
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

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	public HashMap<String, RiskCard> getCardPile() {
		return cardPile;
	}

	public void setCardPile(HashMap<String, RiskCard> cardPile) {
		this.cardPile = cardPile;
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

	public void eliminatePlayer()
	{
		
	}
}
