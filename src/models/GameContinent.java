package models;

import java.util.ArrayList;

public class GameContinent {

    String continentName;
    int continentValue;
    String playerID;
    ArrayList<GameCountry> countries;

    public GameContinent() {
        /*this.continentName = continentName;
        this.continentValue = continentValue;
        this.playerID = playerID;*/
        countries = new ArrayList<>();
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

    public ArrayList<GameCountry> getCountries() {
        return countries;
    }

    public void setCountries(GameCountry inCountry) {
        this.countries.add(inCountry);
    }
}

