package utils;

import models.GameCountry;
import models.Player;

import java.util.ArrayList;

public class TestUtil {

    public static ArrayList<Player> getPlayerArrayList(){
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player("Jil","0");
        players.add(player);
        player = new Player("Shivam","1");
        players.add(player);
        player = new Player("Sidhant","2");
        players.add(player);
        player = new Player("Sarvesh","3");
        players.add(player);
        player = new Player("Naghmeh","4");
        players.add(player);
        return players;
    }

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
