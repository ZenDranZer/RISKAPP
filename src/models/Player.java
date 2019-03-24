package models;

import javafx.util.Pair;
import utils.Dice;

import java.util.ArrayList;
import java.util.Observable;

/**
 * The Player class is a model which contains player related data.
 */
public class Player extends Observable {

	private String name;
	private int id;
	private int playerArmies;
	private ArrayList<GameCountry> countries;
	private ArrayList<GameContinent> continents;
	private ArrayList<RiskCard> playerCards;
	private int remainingArmies;
	private boolean isActive;
	private GameMap gameMap;

	/**
	 * Initializes the Player
	 */
	public Player() {
		countries = new ArrayList<>();
		continents = new ArrayList<>();
	}

	/**
	 * Creates a player with the given name and Id
	 * 
	 * @param name
	 *            Player Name
	 * @param id
	 *            Player Id
	 */
	public Player(String name, int id, GameMap gameMap) {
		this.name = name;
		this.id = id;
		this.gameMap = gameMap;
		playerArmies = 0;
		countries = new ArrayList<>();
		continents = new ArrayList<>();
		playerCards = new ArrayList<>();
	}

	/**
	 * Gets the number of unallocated armies
	 * 
	 * @return number of unallocated armies
	 */
	public int getRemainingArmies() {
		return remainingArmies;
	}

	/**
	 * sets the number of unallocated armies
	 * 
	 * @param armies
	 *            number of unallocated armies
	 */
	public void setRemainingArmies(int armies) {
		this.remainingArmies = armies;
	}

	/**
	 * updates the number of unallocated armies
	 * 
	 * @param armies
	 *            number of armies to subtract from
	 */
	public void updateRemainingArmies(int armies) {
		this.remainingArmies -= armies;
	}

	/**
	 * gets the player name
	 * 
	 * @return a string that represents name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the player
	 * 
	 * @param playerName
	 *            string that represents the name of the player
	 */
	public void setName(String playerName) {
		this.name = playerName;
	}

	/**
	 * gets the Id of the player
	 * 
	 * @return integer that represents the player id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the Id of the player
	 * 
	 * @param id
	 *            integer that represents the player Id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * gets the number armies allocated to the player
	 * 
	 * @return integer that represents the number of armies allocated to the
	 *         player
	 */
	public int getPlayerArmies() {
		return playerArmies;
	}

	/**
	 * sets the number of armies allocated to the player
	 * 
	 * @param player_armies
	 *            integer that represents the number of armies allocated to the
	 *            player
	 */
	public void setPlayerArmies(int player_armies) {
		this.playerArmies = player_armies;
	}

	public void addPlayerArmies(int player_armies) {
		this.playerArmies += player_armies;
	}

	public void removePlayerArmies(int player_armies) {
		this.playerArmies -= player_armies;
	}

	/**
	 * Gets the list of countries owned by the player
	 * 
	 * @return ArrayList that represents the countries owned by the player
	 */
	public ArrayList<GameCountry> getCountries() {
		return countries;
	}

	/**
	 * Sets the list of countries owned by the player
	 * 
	 * @param country
	 *            GameCountry that represents the country to be added to the
	 *            player
	 */
	public void setCountries(GameCountry country) {
		country.setCurrentPlayer(this);
		this.countries.add(country);
	}

	public void removeCountry(GameCountry country) {
		this.countries.remove(country);
	}

	/**
	 * Gets the list of continents owned by the player
	 * 
	 * @return ArrayList that represents the List of continents owned by the
	 *         player
	 */
	public ArrayList<GameContinent> getContinents() {
		return continents;
	}

	/**
	 * Updates the list of continents owned by the player
	 * 
	 * @param continent
	 *            GameContinent that has to be added to the list of continents
	 *            owned by the player
	 */
	public void setContinents(GameContinent continent) {
		this.continents.add(continent);
	}

	/**
	 * Gets the RISK cards held by the player
	 * 
	 * @return String that represents the RISK Cards held by the player
	 */
	public ArrayList<RiskCard> getCardsHeld() {
		return playerCards;
	}

	/**
	 * Sets the RISK Cards held by the player
	 * 
	 * @param cardsHeld
	 *            String that represents RISK cards to be allocated to the
	 *            player
	 */
	public void setCardsHeld(ArrayList<RiskCard> cardsHeld) {
		for (RiskCard rc : cardsHeld) {
			playerCards.add(rc);
		}
	}

	public void emptyCards() {
		playerCards.clear();
	}

	/**
	 * Gets the list of country name owned by the player
	 * 
	 * @return ArrayList of strings that represents the Country names owned by
	 *         the player
	 */
	public ArrayList<String> getCountryNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (GameCountry country : countries) {
			names.add(country.getCountryName());
		}
		return names;
	}

	/**
	 * Adds armies to the player allocated armies by the intended amount
	 * 
	 * @param armies
	 *            integer that represents the number of armies to add
	 */
	public void addPlayerArmy(int armies) {
		this.playerArmies += armies;
	}

	public void placeArmy(String countryName, int armies) {
		ArrayList<GameCountry> lstPlayerCountries = this.getCountries();
		GameCountry matchedCountry = lstPlayerCountries.stream()
				.filter(cntry -> cntry.getCountryName().equals(countryName)).findFirst().get();
		matchedCountry.setArmies(matchedCountry.getArmiesStationed() + armies);
		// System.out.println("Reinforced Country : " +
		// matchedCountry.getArmiesStationed());
		this.addPlayerArmy(armies);
		this.updateRemainingArmies(armies);
		// this.availableArmies = this.availableArmies - armies;
	}

	public void reinforcement(String countryName, int armies) {
		placeArmy(countryName, armies);
	}

	public void fortify(String countryToFortify, String fortifyFrom, int armies) {
		GameCountry toCountry = countries.stream().filter(cntry -> cntry.getCountryName().equals(countryToFortify))
				.findFirst().get();
		GameCountry fortifyingCountry = countries.stream().filter(cntry -> cntry.getCountryName().equals(fortifyFrom))
				.findFirst().get();

		toCountry.setArmies(toCountry.getArmiesStationed() + armies);
		fortifyingCountry.setArmies(fortifyingCountry.getArmiesStationed() - armies);
	}

	public boolean isAllocationComplete() {
		for (GameCountry country : countries) {
			if (country.getArmiesStationed() < 12) {
				return false;
			}
		}
		remainingArmies = 0;
		return true;
	}

	public String attack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry, int redDice,
			int whiteDice) {
		ArrayList<Pair> diceSets;
		int successfulAttack = 0;
		int successfulDefend = 0;
		String status= "";
		if (attackingCountry.getArmiesStationed() < 2) {
			status = "invalid";
			return status;
		}

		// dice logic - get dice sets
		diceSets = Dice.getDiceSets(redDice, whiteDice);
		for (Pair diceSet : diceSets) {
			System.out.println(diceSet.getKey().toString()+"	"+ diceSet.getValue().toString());
			if (Integer.parseInt(diceSet.getKey().toString()) <= Integer.parseInt(diceSet.getValue().toString())) {
				successfulDefend++;
			} else {
				successfulAttack++;
			}
		}
		if (successfulAttack != 0) {
			
			System.out.println("Attack success : " +successfulAttack);
			defendingCountry.removeArmies(successfulAttack);
			defender.removePlayerArmies(successfulAttack);
		}
		if (successfulDefend != 0) {
			System.out.println("Defended : " + successfulDefend);
			attackingCountry.removeArmies(successfulDefend);
			this.removePlayerArmies(successfulDefend);
		}
		if (defendingCountry.getArmiesStationed() == 0) {
			System.out.println("Country won : "+ defendingCountry.getCountryName());
			// give country to attacker
			defender.removeCountry(defendingCountry);
			this.setCountries(defendingCountry);
			// following lines move the army from the country that won to the
			// country with no current armies.
			defendingCountry.setArmies(attackingCountry.getArmiesStationed() - 1);
			attackingCountry.removeArmies(attackingCountry.getArmiesStationed() - 1);
			status= "success";
		}
		// } else if (attackingCountry.getArmiesStationed() == 0) {
		// // give country to defender
		// this.removeCountry(defendingCountry);
		// defender.setCountries(defendingCountry);
		// // placeArmy()
		// // following lines move the army from the country that won to the
		// // country with no current armies.
		// attackingCountry.setArmies(defendingCountry.getArmiesStationed() -
		// 1);
		// defendingCountry.removeArmies(defendingCountry.getArmiesStationed() -
		// 1);
		// }

		// check player elimination logic
		if (defender.countries.size() == 0) {
			eliminate(defender);
			if (hasPlayerWon(this)) {
				// Return somehow that the player has won
				status= "winner";
			}
		}
		
		// check if attacker has enough armies for next attack

		return status;
	}

	public void allOutAttack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry) {
		int redDice = 0;
		// get max red dice

		int whiteDice = 0;
		// get max white dice

		attack(defender, attackingCountry, defendingCountry, redDice, whiteDice);
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean state) {
		this.isActive = state;
	}

	public void addRiskCards(ArrayList<RiskCard> cards) {
		this.playerCards.addAll(cards);
	}

	public void eliminate(Player eliminatedPlayer) {
		eliminatedPlayer.setIsActive(false);
		this.addRiskCards(eliminatedPlayer.getCardsHeld());
		eliminatedPlayer.setCardsHeld(null);
	}

	public boolean hasPlayerWon(Player player) {
		if (player.getCountries().size() == gameMap.getCountryHashMap().size()) {
			return true;
		}
		return false;
	}
}