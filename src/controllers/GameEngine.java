package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.*;

public class GameEngine {

	private ArrayList<Player> listActivePlayers = new ArrayList<Player>();
	private ArrayList<Player> listEliminatedPlayers = new ArrayList<Player>();
	private static MapGenerator mapGenerator = new MapGenerator();

	private String mapPath;
	private TurnController turn;

	// object initialization
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

	/**
	 * This method takes in the number of players and their names
	 *
	 * @return: list of player objects
	 */
	public ArrayList<Player> getInitialPlayers() {

		try {
			int i = 0;
			getPlayerInfo(listActivePlayers);
		} catch (Exception ex) {
			System.out.println("Error getting player data");
		}
		return listActivePlayers;
	}

	public void setListActivePlayers(ArrayList<String> listActivePlayers) {
		int i = 1;
		for (String name : listActivePlayers) {
			Player player = new Player(name, i);
			this.listActivePlayers.add(player);
			i++;
		}
	}

	// should return list of player objects
	// GUI
	// public List<Player> getInitialPlayers(int numberOfplayers){
	// return new ArrayList<Player>();
	// }

	// initial allocation of countries
	// preferably in round robin fashion
	public void allocateCountries(ArrayList<Player> activePlayers, ArrayList<GameCountry> countries) {

	}

	public void selectMap() {

	}

	public void initialise() {
		getInitialPlayers();
	}

	public void initialiseEngine() throws IOException {
		// setListActivePlayers(lstPlayerNames);
		mapGenerator.readConquestFile(mapPath);
		turn.allocateCountries(listActivePlayers, new ArrayList<GameCountry>(MapGenerator.countryHashMap.values()));
		allocateInitialArmies();
		// turn.allocateArmies(listActivePlayers);
		turn.setActivePlayer(listActivePlayers.get(0));
		for (Player pl : listActivePlayers) {
			System.out.println(pl.getCountries().size());
		}
	}

	public void allocateInitialArmies() {
		int initialArmies = turn.getEachPlayerArmy(listActivePlayers);
		
		for(GameCountry country : MapGenerator.countryHashMap.values())
		{
			country.setArmies(1);
		}

		for (Player player : listActivePlayers) {
			int countryCount = player.getCountries().size(); 
			player.setPlayerArmies(countryCount);
			player.setRemainingArmies(initialArmies- countryCount);
		}
	}

	public void setNextPlayer(Player activePlayer) {
		int currentIndex = listActivePlayers.indexOf(activePlayer);
		currentIndex = currentIndex + 1;
		System.out.println("::::::" +currentIndex);
		if (currentIndex >= listActivePlayers.size()) {
			currentIndex = 0;
		}
		Player nextPlayer = listActivePlayers.get(currentIndex);
		turn.setActivePlayer(nextPlayer);
	}
}