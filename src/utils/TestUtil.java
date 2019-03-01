package utils;

import models.GameCountry;
import models.Player;

import java.util.ArrayList;

/**A utility class that provides basic inputs for testing.
 * They can be used as stubs.*/
public class TestUtil {

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


}
