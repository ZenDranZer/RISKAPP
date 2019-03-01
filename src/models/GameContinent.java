package models;

import java.util.ArrayList;
import java.util.HashMap;

public class GameContinent {

    String continentName;
    int continentValue;
    String playerID;
    HashMap<String,GameCountry> countries;

    public GameContinent() {
        /*this.continentName = continentName;
        this.continentValue = continentValue;
        this.playerID = playerID;*/
        countries = new HashMap<>();
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public int getContinentValue() {
        return continentValue;
    }

    public void setContinentValue(int continentValue) {
        this.continentValue = continentValue;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public HashMap<String, GameCountry> getCountries() {
        return countries;
    }

    public void setCountries(GameCountry inCountry) {
        this.countries.put(inCountry.getCountryName(),inCountry);
    }
}

