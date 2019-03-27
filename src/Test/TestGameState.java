package Test;

import controllers.MapGenerator;
import models.*;
import org.junit.Before;
import org.junit.Test;
import utils.TestUtil;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGameState {
    public ArrayList<Player> lstPlayers;

    public HashMap<String, GameCountry> testCountryHashMap;
    public HashMap<String, GameContinent> testContinentHashMap;
    public MapGenerator mapGenerator;
    TestUtil testUtil;
    public static GameMap gameMap;
        @Before
        public void setUp() throws Exception {
            lstPlayers = new ArrayList<Player>();
            testCountryHashMap = new HashMap<String,GameCountry>();
            testContinentHashMap = new HashMap<String,GameContinent>();
            gameMap = new GameMap();
            lstPlayers = new ArrayList<Player>();
            testCountryHashMap = new HashMap<String,GameCountry>();
            testContinentHashMap = new HashMap<String,GameContinent>();
            mapGenerator = new MapGenerator(this.gameMap);
            makeContinentList();
            makeCountryList();
            addCountriesToContinents();
            makePlayerList();
            updateGlobals();


        }

    @Test
    public void testFortification(){
        GameState gameState= new GameState();
        gameState.setActivePlayer(lstPlayers.get(0));
        gameState.fortification(lstPlayers.get(0).getCountries().get(0).getCountryName(),lstPlayers.get(0).getCountries().get(1).getCountryName(),2);
        System.out.println(lstPlayers.get(0).getCountries().get(0).getArmiesStationed()+" "+lstPlayers.get(0).getCountries().get(1).getArmiesStationed());
            assertEquals(7,lstPlayers.get(0).getCountries().get(0).getArmiesStationed());
            assertEquals(3,lstPlayers.get(0).getCountries().get(1).getArmiesStationed());
    }
    @Test
    public void testGameEnd(){
        GameState gameState= new GameState();
        System.out.println(hasPlayerWon(lstPlayers.get(0)));
        assertTrue(hasPlayerWon(lstPlayers.get(0)));
    }

    public void makePlayerList() {
        Player objPlayer1 = new Player("Player 1",1,gameMap);
        objPlayer1.setCountries(testCountryHashMap.get("India"));
        objPlayer1.setCountries(testCountryHashMap.get("China"));
        objPlayer1.setCountries(testCountryHashMap.get("England"));
        objPlayer1.setCountries(testCountryHashMap.get("Russia"));
        testCountryHashMap.get("India").setCurrentPlayer(objPlayer1);
        testCountryHashMap.get("China").setCurrentPlayer(objPlayer1);
        testCountryHashMap.get("England").setCurrentPlayer(objPlayer1);
        testCountryHashMap.get("Russia").setCurrentPlayer(objPlayer1);

        Player objPlayer2 = new Player("Player 2",2,gameMap);
       /* objPlayer2.setCountries(testCountryHashMap.get("England"));
        objPlayer2.setCountries(testCountryHashMap.get("Russia"));*/

     /*   testCountryHashMap.get("England").setCurrentPlayer(objPlayer2);
        testCountryHashMap.get("Russia").setCurrentPlayer(objPlayer2);*/

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
        gameMap.setContinentHashMap(testContinentHashMap);
    }

    public void makeCountryList() {

        GameCountry country1 = new GameCountry("India");
        country1.setArmies(5);
        country1.setContinent(testContinentHashMap.get("Asia"));

        GameCountry country2 = new GameCountry("China");
        country2.setArmies(5);
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
        gameMap.setCountryHashMap(testCountryHashMap);
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
    public boolean hasPlayerWon(Player player) {
        if (player.getCountries().size() == this.testCountryHashMap.size()) {
            return true;
        }
        return false;
    }
}
