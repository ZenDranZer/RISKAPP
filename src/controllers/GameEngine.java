package controllers;

import models.AggressivePlayer;
import models.BenevolentPlayer;
import models.CheaterPlayer;
import models.GameCountry;
import models.GameState;
import models.Player;
import models.RandomPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controller class to maintain the game state and handle object allocation for
 * the game
 *
 */
public class GameEngine {

	private MapGenerator mapGenerator;
	private String mapPath;
	private TurnController turn;
	private int numberOfPlayers;
	private GameState gameState;
	private BotController objBotController;

	public GameEngine() {

		gameState = new GameState();
		turn = new TurnController(gameState);
		mapGenerator = new MapGenerator(gameState.getGameMapObject());
		objBotController = new BotController(gameState);
	}

	/**
	 * Gets the game state for current game
	 * @return gamestate
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Gets the TurnController object
	 * 
	 * @return TurnController object representing the current turn
	 */
	public TurnController getTurnController() {
		if (turn == null) {
			turn = new TurnController(gameState);
		}
		return turn;
	}

	/**
	 * Gets the bots for current game
	 * @return
	 */
	public BotController getBotController() {
		if(this.objBotController == null) {
			this.objBotController = new BotController(gameState);
		}
		return this.objBotController;
	}

	/**
	 * Gets the Map Generator
	 * 
	 * @return MapGenerator object representing the Map Generator
	 */
	public MapGenerator getMapGenerator() {
		return mapGenerator;
	}

	/**
	 * Gets the file path of the current map
	 * 
	 * @return String absolute path of the map file
	 */
	public String getMapPath() {
		return mapPath;
	}

	/**
	 * Sets the current map path
	 * 
	 * @param mapPath
	 *            String absolute path of the map file
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
			switch (name) {
			case "Aggressive":
				AggressivePlayer aggPlayer = new AggressivePlayer(name + "_" + i, i, gameState.getGameMapObject());
				gameState.getPlayers().add(aggPlayer);
				break;
			case "Benevolent":
				BenevolentPlayer benevolentPlayer = new BenevolentPlayer(name + "_" + i, i,
						gameState.getGameMapObject());
				gameState.getPlayers().add(benevolentPlayer);
				break;

			case "Random":
				RandomPlayer randomPlayer = new RandomPlayer(name + "_" + i, i, gameState.getGameMapObject());
				gameState.getPlayers().add(randomPlayer);
				break;
			case "Cheater":
				CheaterPlayer cheaterPlayer = new CheaterPlayer(name + "_" + i, i, gameState.getGameMapObject());
				gameState.getPlayers().add(cheaterPlayer);
				break;

			default:
				Player player = new Player(name, i, gameState.getGameMapObject());
				gameState.getPlayers().add(player);
				break;
			}
			i++;
		}
	}

	/**
	 * Assigns List of players to Active players
	 * 
	 * @param lstPlayers
	 *            ArrayList of player objects
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
			turn.allocateCountries(gameState.getPlayers(), getGameState().getGameMapObject().getAllCountries());
			allocateInitialArmies();
			gameState.setActivePlayer(gameState.getPlayers().get(0));
			//allocateBots();
			System.out.println("bots done ");
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
	 * 
	 * @param activePlayer
	 *            current player
	 */
	public String setNextPlayer(Player activePlayer, boolean checkInitialAllocation) {

		Player nextPlayer = gameState.getNextPlayer(activePlayer, checkInitialAllocation);
		gameState.setActivePlayer(nextPlayer);
		System.out.println("pl check  : " + nextPlayer.getClass());
		String message = "";
		while (objBotController.isBot(nextPlayer) && message != "winner" ) {
			if (!checkInitialAllocation) {
				turn.calculateNewArmies(nextPlayer, mapGenerator);
				message = objBotController.executeTurn(nextPlayer);
			} else {
				objBotController.assignRemainingArmies();
			}
			if(message != "winner") {
				nextPlayer = gameState.getNextPlayer(nextPlayer, checkInitialAllocation);
				gameState.setActivePlayer(nextPlayer);
			}
		}
		return message;
	}

	/**
	 * Sets the number of players in the game
	 * 
	 * @param noOfPlayers
	 *            integer number of players
	 */
	public void setNumberOfPlayers(int noOfPlayers) {
		this.numberOfPlayers = noOfPlayers;
	}

	/**
	 * Gets the number of players in the game
	 * 
	 * @return integer number of players
	 */
	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}

	/**
	 * Resets the Game
	 */
	public void resetGame() {
		this.gameState = new GameState();
		this.turn = new TurnController(this.gameState);
	}

	/**
	 * Save Game State to file
	 * 
	 * @param phase
	 *            game state
	 * @param logs
	 *            action logs till save point
	 * @param fileName
	 *            saved file name
	 */
	public void saveGame(String phase, String logs, String fileName) {
		try {
			gameState.setCurrentPhase(phase);
			gameState.setLogs(logs);
			FileOutputStream fileOut;
			ObjectOutputStream objectOut;
			fileOut = new FileOutputStream(fileName);
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(gameState);
			objectOut.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Loads the game back to resume game play
	 * @param saveFilePath File where game is saved
	 */
	public void loadGame(String saveFilePath) {
		try {
			FileInputStream fileIn = new FileInputStream(saveFilePath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			this.gameState = (GameState) objectIn.readObject();
			objectIn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Allocates bots to the Player objects
	 */
	public void allocateBots() {
		Player activePlayer = gameState.getActivePlayer();
		while(objBotController.isBot(activePlayer) && !activePlayer.isAllocationComplete() && activePlayer.getRemainingArmies() >0) {
			objBotController.assignRemainingArmies();
			activePlayer = gameState.getNextPlayer(activePlayer, true);
			gameState.setActivePlayer(activePlayer);
		}
	}
	
	/**
	 * check if all players are bots
	 * @return True if all players are bots, False otherwise
	 */
	public boolean areAllBots() {
		for(Player pl : gameState.getPlayers()) {
			if(!objBotController.isBot(pl)) {
				return false;
			}
		}
		return true;
	}
}