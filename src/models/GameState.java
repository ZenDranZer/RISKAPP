package models;

import java.util.*;

/**
 * Class that represents and holds the data for the GamePlay
 * Contains all the players and the map
 */
public class GameState {

	private ArrayList<Player> lstPlayers;
	private String mapPath;
	private HashMap<String, RiskCard> cardPile;

	/**
	 * Gets the list of player
	 * @return ArrayList of active players
	 */
	public ArrayList<Player> getPlayers() {
		return lstPlayers;
	}

	public void setPlayers(ArrayList<Player> lstPlayers) {
		this.lstPlayers = lstPlayers;
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

}
