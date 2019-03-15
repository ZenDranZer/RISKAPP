package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Represents the Continent Model in the game
 * Each country can only belong to one continent
 */
public class GameContinent extends Observable {

    private String continentName;
    private int continentValue;
    private int playerID;
    private HashMap<String,GameCountry> countries;

    public GameContinent() {
        countries = new HashMap<>();
        continentValue=0;
    }

    /**
     * Gets the continent name
     * @return String that represents the continent name
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * Sets the continent name
     * @param continentName string that represents the name of the continent
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * Gets the continent values. Armies are allocated according to this
     * @return integer that represents the continent value
     */
    public int getContinentValue() {
        return continentValue;
    }

    /**
     * Sets the continent value
     * @param continentValue integer that represents the continent value
     */
    public void setContinentValue(int continentValue) {
        this.continentValue = continentValue;
    }

    /**
     * Gets the player id for the player who owns the continent
     * @return integer that represents the current player id
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Sets the player id for the player owning the continent
     * @param playerID
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * Gets the collection of countries present in the continent
     * @return collection of countries
     */
    public HashMap<String, GameCountry> getCountries() {
        return countries;
    }

    /**
     * Sets the countries present in the continent
     * @param inCountry GameCountry to be added to the continent
     */
    public void setCountries(GameCountry inCountry) {
        this.countries.put(inCountry.getCountryName(),inCountry);
    }
}

