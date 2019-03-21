package utils;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;

import controllers.MapGenerator;

/**A utility class that provides basic inputs for testing.
 * They can be used as stubs.*/
public class TestUtil {

	public ArrayList<Player> lstPlayers;
	
	public HashMap<String,GameCountry> testCountryHashMap;
	public HashMap<String,GameContinent> testContinentHashMap;
	public MapGenerator mapGenerator;
	public GameMap gameMap;
	public TestUtil() {
		gameMap = new GameMap();
		mapGenerator = new MapGenerator(this.gameMap);
		lstPlayers = new ArrayList<Player>();
		testCountryHashMap = new HashMap<String,GameCountry>();
		testContinentHashMap = new HashMap<String,GameContinent>();
		makeContinentList();
		makeCountryList();
		addCountriesToContinents();
		makePlayerList();
		updateGlobals();
	}
	
	
    /**This static method is used to get the array list of players ccontaining name and ID.
     * @return ArrayList of players with 6 players.*/
    public static ArrayList<Player> getPlayerArrayList() {
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player("Jil",1);
        players.add(player);
        player = new Player("Shivam",2);
        players.add(player);
        player = new Player("Sidhant",3);
        players.add(player);
        player = new Player("Sarvesh",4);
        players.add(player);
        player = new Player("Naghmeh",5);
        players.add(player);
        return players;
    }

    
    /**This static method is used to provide an array list of countries for testing.
     * @return Array list of countries with 10 countries.*/
    public static ArrayList<GameCountry> getGameCountryList() {
        ArrayList<GameCountry> countries = new ArrayList<>();
        GameCountry country = new GameCountry("India");
        countries.add(country);
        country = new GameCountry("Sri Lanka");
        countries.add(country);
        country = new GameCountry("Iran");
        countries.add(country);
        country = new GameCountry("China");
        countries.add(country);
        country = new GameCountry("Canada");
        countries.add(country);
        country = new GameCountry("USA");
        countries.add(country);
        country = new GameCountry("Russia");
        countries.add(country);
        country = new GameCountry("England");
        countries.add(country);
        country = new GameCountry("Nepal");
        countries.add(country);
        country = new GameCountry("Japan");
        countries.add(country);
        return countries;
    }
    
    public void makePlayerList() {
    	Player objPlayer1 = new Player("Player 1",1);
    	objPlayer1.setCountries(testCountryHashMap.get("India"));
    	objPlayer1.setCountries(testCountryHashMap.get("China"));
    	
    	testCountryHashMap.get("India").setCurrentPlayer(objPlayer1);
    	testCountryHashMap.get("China").setCurrentPlayer(objPlayer1);
    	
    	
    	Player objPlayer2 = new Player("Player 2",2);
    	objPlayer2.setCountries(testCountryHashMap.get("England"));
    	objPlayer2.setCountries(testCountryHashMap.get("Russia"));
    	
    	testCountryHashMap.get("England").setCurrentPlayer(objPlayer2);
    	testCountryHashMap.get("Russia").setCurrentPlayer(objPlayer2);
    	
    	lstPlayers.add(objPlayer1);
    	lstPlayers.add(objPlayer2);
    }
    
    public void makeContinentList() {
    	GameContinent continent1 = new GameContinent();
    	continent1.setContinentName("Asia");
    	continent1.setContinentValue(5);
    	
    	GameContinent continent2 = new GameContinent();
    	continent2.setContinentName("Europe");
    	continent2.setContinentValue(5); 	
    	
    	testContinentHashMap.put("Asia", continent1);
    	testContinentHashMap.put("Europe", continent2);
    }
    
    public void makeCountryList() {
    	
    	GameCountry country1 = new GameCountry("India");
    	country1.setContinent(testContinentHashMap.get("Asia"));
    	
    	GameCountry country2 = new GameCountry("China");
    	country2.setContinent(testContinentHashMap.get("Asia"));
    	
    	GameCountry country3 = new GameCountry("England");
    	country3.setContinent(testContinentHashMap.get("Europe"));
    	
    	GameCountry country4 = new GameCountry("Russia");
    	country4.setContinent(testContinentHashMap.get("Europe"));
    	
    	
    	country1.addNeighbouringCountry(country2);
    	
    	country2.addNeighbouringCountry(country1);
    	country2.addNeighbouringCountry(country3);
    	
    	country3.addNeighbouringCountry(country4);
    	country3.addNeighbouringCountry(country2);
    	
    	country4.addNeighbouringCountry(country3);
    	
    	testCountryHashMap.put(country1.getCountryName(),country1);
    	testCountryHashMap.put(country2.getCountryName(),country2);
    	testCountryHashMap.put(country3.getCountryName(),country3);
    	testCountryHashMap.put(country4.getCountryName(),country4);
    }
    
    public void addCountriesToContinents() {
    	GameContinent continent = testContinentHashMap.get("Asia");
    	continent.setCountries(testCountryHashMap.get("India"));
    	continent.setCountries(testCountryHashMap.get("China"));
    	
    	continent = testContinentHashMap.get("Europe");
    	continent.setCountries(testCountryHashMap.get("England"));
    	continent.setCountries(testCountryHashMap.get("Russia"));
    }
    
    public void updateGlobals() {

    	for(GameContinent c : testContinentHashMap.values()) {
    		mapGenerator.getGameMap().continentHashMap.put(c.getContinentName(), c);
    	}
    	
    	for(GameCountry cntry : testCountryHashMap.values()) {
    		mapGenerator.getGameMap().countryHashMap.put(cntry.getCountryName(), cntry);
    	}

    }
    
}
