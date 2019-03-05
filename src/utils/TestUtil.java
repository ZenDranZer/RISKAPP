package utils;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;

/**A utility class that provides basic inputs for testing.
 * They can be used as stubs.*/
public class TestUtil {

	public ArrayList<Player> lstPlayers;
	
	public HashMap<String,GameCountry> testCountryHashMap;
	public HashMap<String,GameContinent> testContinentHashMap;
	
	public TestUtil()
	{
		makeContinentList();
		makeCountryList();
		addCountriesToContinents();
		makePlayerList();
	}
	
	
    /**This static method is used to get the array list of players ccontaining name and ID.
     * @return ArrayList of players with 6 players.*/
    public static ArrayList<Player> getPlayerArrayList(){
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
    public static ArrayList<GameCountry> getGameCountryList(){
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
    
    public void makePlayerList()
    {
    	Player objPlayer1 = new Player("Player 1",1);
    	objPlayer1.setCountries(testCountryHashMap.get("India"));
    	objPlayer1.setCountries(testCountryHashMap.get("England"));
    	
    	Player objPlayer2 = new Player("Player 2",2);
    	objPlayer2.setCountries(testCountryHashMap.get("China"));
    	objPlayer2.setCountries(testCountryHashMap.get("Russia"));
    }
    
    public void makeContinentList()
    {
    	GameContinent continent1 = new GameContinent();
    	continent1.setContinentName("Asia");
    	continent1.setContinentValue(4);
    	
    	GameContinent continent2 = new GameContinent();
    	continent2.setContinentName("Europe");
    	continent2.setContinentValue(3); 	
    	
    	testContinentHashMap.put("Asia", continent1);
    	testContinentHashMap.put("Europe", continent2);
    }
    
    public void makeCountryList(){
    	
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
    }
    
    public void addCountriesToContinents()
    {
    	GameContinent continent = testContinentHashMap.get("Asia");
    	continent.setCountries(testCountryHashMap.get("India"));
    	continent.setCountries(testCountryHashMap.get("China"));
    	
    	continent = testContinentHashMap.get("Europe");
    	continent.setCountries(testCountryHashMap.get("England"));
    	continent.setCountries(testCountryHashMap.get("Russia"));
    	
    }
}
