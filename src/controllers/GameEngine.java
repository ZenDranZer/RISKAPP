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
	private static MapGenerator mapGenerator = new MapGenerator();

	private String mapPath;
	private TurnController turn;

	private int numberOfPlayers;

	public GameEngine() {
		turn = new TurnController();
	}

	public TurnController getTurnComtroller() {
		if (turn == null) {
			turn = new TurnController();
		}
		return turn;
	}

	public MapGenerator getMapGenerator() {
		return mapGenerator;
	}

	// CUI
	public void getPlayerInfo(ArrayList<Player> activePlayers) {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter number of players");
		int noOfPlayers = keyboard.nextInt();
		if (noOfPlayers < 2) {
			System.out.println("Minimum number of players is 2");
		} else if (noOfPlayers > 5) {
			System.out.println("Maximum number of players is 5");
		} else {
			for (int i = 1; i <= noOfPlayers; i++) {
				Player p = new Player();
				System.out.println("Enter player name " + i + "\n");
				p.setId(i);
				p.setName(keyboard.next());
				listActivePlayers.add(p);
			}
		}
		keyboard.close();
	}

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	// cui
	/**
	 * This method takes in the number of players and their names
	 *
	 * @return: list of player objects
	 */
	public ArrayList<Player> getInitialPlayers() {

		try {
			getPlayerInfo(listActivePlayers);
		} catch (Exception ex) {
			System.out.println("Error getting player data");
		}
		return listActivePlayers;
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
	
	//CUI
	public void initialise() {
		getInitialPlayers();
	}

	/**
	 * Initialize the game engine with the initial set of players, randomly
	 * allocate countries and assign initial set of armies
	 */
	public void initialiseEngine() {

		try {
			setNumberOfPlayers(listActivePlayers.size());
			turn.allocateCountries(listActivePlayers, new ArrayList<GameCountry>(MapGenerator.countryHashMap.values()));
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

		for (GameCountry country : MapGenerator.countryHashMap.values()) {
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

	public void setNumberOfPlayers(int noOfPlayers) {
		this.numberOfPlayers = noOfPlayers;
	}

	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}
}