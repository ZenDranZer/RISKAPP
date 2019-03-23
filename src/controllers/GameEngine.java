package controllers;

import java.util.ArrayList;
import java.util.Scanner;
import models.*;

/**
 * Controller class to maintain the game state and handle object allocation for the game
 * @author Sidhant Gupta
 *
 */
public class GameEngine {

	private static MapGenerator mapGenerator;
	private RiskCardController riskCards;
	private String mapPath;
	private TurnController turn;
	private int numberOfPlayers;
	private GameState gameState;
	private RiskCardController riskCardController;

	public GameEngine() {

		gameState = new GameState();
		turn = new TurnController(gameState);
		mapGenerator = new MapGenerator(gameState.getGameMapObject());
		riskCards = new RiskCardController();
		riskCardController = new RiskCardController();
	}
	public GameState getGameState(){
		return gameState;
	}
	/**
	 * Gets the TurnController object
	 * @return TurnController object representing the current turn
	 */
	public TurnController getTurmController() {
		if (turn == null) {
			turn = new TurnController(gameState);
		}
		return turn;
	}

	public RiskCardController getRiskCardController() {
		return riskCardController;
	}

	/**
	 * Gets the Map Generator
	 * @return MapGenerator object representing the Map Generator
	 */
	public MapGenerator getMapGenerator() {
		return mapGenerator;
	}

	/**
	 * Gets the file path of the current map
	 * @return String absolute path of the map file
	 */
	public String getMapPath() {
		return mapPath;
	}

	public RiskCardController getRiskCardDeck() {
		return riskCards;
	}

	/**
	 * Sets the current map path
	 * @param mapPath String absolute path of the map file
	 */
	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	/**
	 * function to initialize the and set the initial players for the game
	 * 
	 * @param listActivePlayers
	 *            list of player objects
	 */
	public void setListActivePlayers(ArrayList<String> listActivePlayers) {
		int i = 1;
		for (String name : listActivePlayers) {
			Player player = new Player(name, i);
			gameState.getPlayers().add(player);
			i++;
		}
	}

	/**
	 * Assigns List of players to Active players 
	 * @param lstPlayers ArrayList of player objects
	 */
	public void setActivePlayers(ArrayList<Player> lstPlayers) {
		gameState.setPlayers(lstPlayers);
	}
	
	/**
	 * Initialize the game engine with the initial set of players, randomly
	 * allocate countries and assign initial set of armies
	 */
	public void initialiseEngine() {

		try {
			setNumberOfPlayers(gameState.getPlayers().size());
			turn.allocateCountries(gameState.getPlayers(),getGameState().getGameMapObject().getAllCountries());
			allocateInitialArmies();
			gameState.setActivePlayer(gameState.getPlayers().get(0));
			riskCards.initRiskCardDeck(gameState.getGameMapObject());
		} catch (NullPointerException nullEx) {
			System.out.println(nullEx.toString());
			System.out.println("\n\n");
			nullEx.printStackTrace();
			System.out.println("Objects not initialized properly");
		} catch (Exception ex) {
			System.out.println("Something went wrong during initialization");
		}
	}

	/**
	 * allocate initial army to each country. Every allocated country should
	 * have at least 1 army at the beginning
	 */
	public void allocateInitialArmies() {
		int initialArmies = turn.getEachPlayerArmy(gameState.getPlayers());

		for (GameCountry country : getGameState().getGameMapObject().getAllCountries()) {
			country.setArmies(1);
		}

		for (Player player : gameState.getPlayers()) {
			int countryCount = player.getCountries().size();
			player.setPlayerArmies(countryCount);
			player.setRemainingArmies(initialArmies - countryCount);
		}
	}

	/**
	 * function to change the current player to the next player 
	 * @param activePlayer current player
	 */
	public void setNextPlayer(Player activePlayer,boolean checkInitialAllocation) {		
		gameState.setActivePlayer(gameState.getNextPlayer(activePlayer,checkInitialAllocation));
	}

	/**
	 * Sets the number of players in the game
	 * @param noOfPlayers integer number of players
	 */
	public void setNumberOfPlayers(int noOfPlayers) {
		this.numberOfPlayers = noOfPlayers;
	}

	/**
	 * Gets the number of players in the game
	 * @return integer number of players
	 */
	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}
}