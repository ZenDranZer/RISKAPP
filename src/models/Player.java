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
		playerCards = new ArrayList<>();
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
		setChanged();
		notifyObservers();
	}

	/**
	 * updates the number of unallocated armies
	 * 
	 * @param armies
	 *            number of armies to subtract from
	 */
	public void updateRemainingArmies(int armies) {
		this.remainingArmies -= armies;
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
	}

	public void removePlayerArmies(int player_armies) {
		this.playerArmies -= player_armies;
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
	}

	/**
	 * Removes the country from the list of countries in current object
	 *
	 * @param country
	 *            The country object to be removed from the list.
	 */
	public void removeCountry(GameCountry country) {
		this.countries.remove(country);
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
	}

	public void addRiskCard(RiskCard riskCard) {
		playerCards.add(riskCard);
		setChanged();
		notifyObservers();
	}

	/**
	 * Clears the card allocated to the player
	 *
	 */
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
		setChanged();
		notifyObservers();
	}

	/**
	 * Adds the specified number of armies in the specified country
	 *
	 * @param countryName
	 *            Name of the country in which armies need to be added
	 * @param armies
	 *            Number of armies to be added
	 */
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

	/**
	 * The Reinforcement method that takes care of placing army on a specified
	 * country
	 *
	 * @param countryName
	 *            The country name
	 * @param armies
	 *            Number of armies to be placed
	 */
	public void reinforcement(String countryName, int armies) {
		placeArmy(countryName, armies);
	}

	/**
	 * The method that contains the logic of fortification phase
	 *
	 * @param countryToFortify
	 *            The country to be fortified
	 * @param fortifyFrom
	 *            The country from which the fortification is carried out
	 * @param armies
	 *            Number of armies to be used to fortify
	 */
	public void fortify(String countryToFortify, String fortifyFrom, int armies) {
		GameCountry toCountry = countries.stream().filter(cntry -> cntry.getCountryName().equals(countryToFortify))
				.findFirst().get();
		GameCountry fortifyingCountry = countries.stream().filter(cntry -> cntry.getCountryName().equals(fortifyFrom))
				.findFirst().get();

		toCountry.setArmies(toCountry.getArmiesStationed() + armies);
		fortifyingCountry.setArmies(fortifyingCountry.getArmiesStationed() - armies);
	}

	/**
	 * Check if the countries have maximum number of armies possible
	 *
	 * @return Returns true if successful, false otherwise
	 */
	public boolean isAllocationComplete() {
		for (GameCountry country : countries) {
			if (country.getArmiesStationed() < 12) {
				return false;
			}
		}
		remainingArmies = 0;
		return true;
	}

	/**
	 * The Logic of attack phase of the game
	 *
	 * @param defender
	 *            The player who's country is being attacked
	 * @param attackingCountry
	 *            The country from where the attack takes place
	 * @param defendingCountry
	 *            The country on which the attack takes place
	 * @param redDice
	 *            Number of dice selected by attacker
	 * @param whiteDice
	 *            Number of dice selected by defender
	 * @return returns the status of the method execution
	 */
	public String attack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry, int redDice,
			int whiteDice) {
		ArrayList<Pair> diceSets;
		int successfulAttack = 0;
		int successfulDefend = 0;
		String status = "";

		// Validation of Dice
		if (attackingCountry.getArmiesStationed() < 2) {
			status = "invalid";
			System.out.println("number of armies should be more than 2 in attacking country");
			return status;
		}
		if (redDice > attackingCountry.getArmiesStationed()) {
			status = "invalid";
			System.out.println("number of dice selected can not be more than armies in attacking country");
			return status;
		}

		if (whiteDice > defendingCountry.getArmiesStationed()) {
			status = "invalid";
			System.out.println("number of dice selected should be more than armies in defending country");
			return status;
		}

		if (redDice < 1 || whiteDice < 1 || redDice > 3 || whiteDice > 3) {
			status = "invalid";
			System.out.println("Invalid dice number selection");
			return status;
		}

		// dice logic - get dice sets
		diceSets = Dice.getDiceSets(redDice, whiteDice);
		for (Pair diceSet : diceSets) {
			System.out.println(diceSet.getKey().toString() + "	" + diceSet.getValue().toString());
			if (Integer.parseInt(diceSet.getKey().toString()) <= Integer.parseInt(diceSet.getValue().toString())) {
				successfulDefend++;
			} else {
				successfulAttack++;
			}
		}
		if (successfulAttack != 0) {

			System.out.println("Opponent armies eliminated: " + successfulAttack);
			defendingCountry.removeArmies(successfulAttack);
			defender.removePlayerArmies(successfulAttack);
		}
		if (successfulDefend != 0) {
			System.out.println("Host armies eliminated  : " + successfulDefend);
			attackingCountry.removeArmies(successfulDefend);
			this.removePlayerArmies(successfulDefend);
		}
		if (defendingCountry.getArmiesStationed() == 0) {
			System.out.println("Country captured by attacker : " + defendingCountry.getCountryName());
			// give country to attacker
			defender.removeCountry(defendingCountry);
			this.setCountries(defendingCountry);
			this.updateContinents();
			// following lines move the army from the country that won to the
			// country with no current armies.
			defendingCountry.setArmies(attackingCountry.getArmiesStationed() - 1);
			attackingCountry.removeArmies(attackingCountry.getArmiesStationed() - 1);
			status = "success";
		} else {
			status = "unsuccessful";
		}

		// check player elimination logic
		if (defender.countries.size() == 0) {
			this.getCardsHeld().addAll(defender.getCardsHeld());
			eliminate(defender);
			status = "eliminated";
			if (hasPlayerWon(this)) {
				// Return somehow that the player has won
				status = "winner";
			}
		}

		// check if attacker has enough armies for next attack
		return status;
	}

	/**
	 * this function implements all out attack mode
	 * 
	 * @param defender
	 * @param attackingCountry
	 * @param defendingCountry
	 * @return String message for status of attack
	 */
	public String allOutAttack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry) {
		int numberOfArmies_attacker = attackingCountry.getArmiesStationed();
		int numberOfArmies_defender = defendingCountry.getArmiesStationed();
		String status = "";
		while (numberOfArmies_attacker > 1) {

			int redDice = 0;
			// get max red dice
			// if (numberOfArmies_attacker == 1) {
			// redDice = 1;
			// } else
			if (numberOfArmies_attacker == 2) {
				redDice = 1;
			} else if (numberOfArmies_attacker == 3) {
				redDice = 2;
			} else {
				redDice = 3;
			}

			int whiteDice = 0;
			// get max white dice
			if (numberOfArmies_defender == 1) {
				whiteDice = 1;
			} else if (numberOfArmies_defender >= 2) {
				whiteDice = 2;
			}
			// else {
			// whiteDice = 3;
			// }
			status = attack(defender, attackingCountry, defendingCountry, redDice, whiteDice);
			numberOfArmies_attacker = attackingCountry.getArmiesStationed();
			numberOfArmies_defender = defendingCountry.getArmiesStationed();
		}
		if ( status.equals("eliminated") || defendingCountry.getCurrentPlayer().getId() == attackingCountry.getCurrentPlayer().getId()) {
			return "Attack was Successful";
		} else {
			return "Attack was not Successful";
		}
	}

	/**
	 * Checks if the player is active or dead
	 *
	 * @return true if active, false otherwise
	 */
	public boolean getIsActive() {
		return this.isActive;
	}

	/**
	 * Set the state of the player
	 *
	 * @param state
	 *            the state of the player to be set
	 */
	public void setIsActive(boolean state) {
		this.isActive = state;
	}

	/**
	 * Award risk cards to the current player
	 *
	 * @param cards
	 *            cards to be awarded to the player
	 */
	public void addRiskCards(ArrayList<RiskCard> cards) {
		this.playerCards.addAll(cards);
	}

	/**
	 * Contains the logic to eliminate the player
	 *
	 * @param eliminatedPlayer
	 *            Player to be eliminated
	 */
	public void eliminate(Player eliminatedPlayer) {
		eliminatedPlayer.setIsActive(false);
		this.addRiskCards(eliminatedPlayer.getCardsHeld());
		eliminatedPlayer.setCardsHeld(new ArrayList<RiskCard>());
	}

	/**
	 * Checks if player has won the game
	 *
	 * @param player
	 *            Player object
	 * @return True if player won, false otherwise
	 */
	public boolean hasPlayerWon(Player player) {
		if (player.getCountries().size() == gameMap.getCountryHashMap().size()) {
			return true;
		}
		return false;
	}

	/**
	 * check and update player continent
	 */
	public void updateContinents() {
		ArrayList<GameContinent> lstContinents = gameMap.checkContinentsOwnedByOnePlayer(this.getId());

		if (lstContinents != null && lstContinents.size() > 0) {
			for (GameContinent continent : lstContinents) {
				if (!this.getContinents().contains(continent)) {
					this.setContinents(continent);
				}
			}
		}
	}
}