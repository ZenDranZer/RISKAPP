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

	private ArrayList<Player> listActivePlayers = new ArrayList<Player>();
	private ArrayList<Player> listEliminatedPlayers = new ArrayList<Player>();
	private static MapGenerator mapGenerator;
	private String mapPath;
	private TurnController turn;
	private int numberOfPlayers;
	private GameState gameState;

	public GameEngine() {
		turn = new TurnController();
		gameState = new GameState();
		mapGenerator = new MapGenerator();
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
			turn = new TurnController();
		}
		return turn;
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
			this.listActivePlayers.add(player);
			i++;
		}
	}

	/**
	 * Assigns List of players to Active players 
	 * @param lstPlayers ArrayList of player objects
	 */
	public void setActivePlayers(ArrayList<Player> lstPlayers)
	{
		this.listActivePlayers = lstPlayers;
	}
	
	/**
	 * Initialize the game engine with the initial set of players, randomly
	 * allocate countries and assign initial set of armies
	 */
	public void initialiseEngine() {

		try {
			setNumberOfPlayers(listActivePlayers.size());
			turn.allocateCountries(listActivePlayers, getGameState().getGameMapObject().getAllCountries());
			allocateInitialArmies();
			turn.setActivePlayer(listActivePlayers.get(0));
		} catch (NullPointerException nullEx) {
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
		int initialArmies = turn.getEachPlayerArmy(listActivePlayers);

		for (GameCountry country : getGameState().getGameMapObject().getAllCountries()) {
			country.setArmies(1);
		}
		for (Player player : listActivePlayers) {
			int countryCount = player.getCountries().size();
			player.setPlayerArmies(countryCount);
			player.setRemainingArmies(initialArmies - countryCount);
		}
	}

	/**
	 * function to change the current player to the next player 
	 * @param activePlayer current player
	 */
	public void setNextPlayer(Player activePlayer) {
		int currentIndex = listActivePlayers.indexOf(activePlayer);
		currentIndex = currentIndex + 1;
		System.out.println("::::::" + currentIndex);
		if (currentIndex >= listActivePlayers.size()) {
			currentIndex = 0;
		}
		Player nextPlayer = listActivePlayers.get(currentIndex);
		turn.setActivePlayer(nextPlayer);
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