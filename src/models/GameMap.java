package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

/**This class is a model which houses the country, continent, and the GUI parameters hashmap.
 * These hashmaps are the only artifacts stored as a part of map reading from the file or the user driven creation of map
 * This class also houses the methods used to manipulate above mentioned data
 *
 */
public class GameMap extends Observable implements Serializable{
    public  HashMap<String,GameCountry> countryHashMap;
    public  HashMap<String,GameContinent> continentHashMap;
    public HashMap <String,String>guiHashMap;

    /**This is a constructor for the class GameMap
     * It initializes the three hashmaps that will be stored in the class
     *
     */
    public GameMap() {
        countryHashMap = new HashMap<>();
        continentHashMap = new HashMap<>();
        guiHashMap = new HashMap<>();
    }

    /**This Method is used to get all the country objects from the CountryHashMap
     *
     * @return returns an arraylist of all the country objects form country hashmap
     */
    public ArrayList<GameCountry> getAllCountries() {
        return new ArrayList<GameCountry>(this.countryHashMap.values());
    }

    /**This method is used to access all the continents from the continent hashmap
     *
     * @return returns an arraylist of all the continent objects from the continent hashmap
     */
    public ArrayList<GameContinent> getAllContinents() {
        return new ArrayList<GameContinent>(this.continentHashMap.values());
    }

    /** This method sets the GUI parameters one by one
     *
     * @param key The title of the parameter
     * @param value The value of the parameter
     */
    public void setGetGuiHashMapParameter(String key, String value){
        guiHashMap.put(key,value);
    }

    /**This method is used to access the whole GUI parameter hashmap that contains the UI parameters for the map
     *
     * @return returns the whole hashmap of parameter name and values
     */
    public HashMap<String,String>getGuiHashMap(){
        return this.guiHashMap;
    }

    /**This method adds the country object passed in the parameter, in the country hashmap for future references.
     *
     * @param newCountry The country object to be added to the country hashmap
     */
    public void addCountry(GameCountry newCountry) {
        this.countryHashMap.put(newCountry.getCountryName(),newCountry);
        setChanged();
        notifyObservers();
    }

    /**This method adds the continent object passed in the parameter, in the country hashmap for future references.
     *
     * @param newContinent The continent object to be added to the continent hashmap
     */
    public void addContinent(GameContinent newContinent) {
        this.continentHashMap.put(newContinent.getContinentName(),newContinent);
        setChanged();
        notifyObservers();
    }

    /**This method removes a country specified by the country name in the method parameter
     *
     * @param countryName Name of the country object to be removed.
     */
    public void removeCountry(String countryName) {
        this.countryHashMap.remove(countryName);
        setChanged();
        notifyObservers();
    }

    /**This method performs the required deletion in order to get a safe deletion of continent in focus.
     *
     * @param continentName name of the continent to be safely removed
     * @return status of the method execcution
     */
    public String removeContinent(String continentName) {
        for(String country: continentHashMap.get(continentName).getCountries().keySet()) {
            for(GameCountry neighbor: countryHashMap.get(country).getNeighbouringCountries().values()) {
                neighbor.getNeighbouringCountries().remove(country);
            }
            countryHashMap.remove(country);
        }
        this.removeContinentFromMap(continentName);
        setChanged();
        notifyObservers();
        return "SUCCESS";
    }

    /**This method removes the continent specified by the continent name in the parameter, from the continent hashmap
     *
     * @param continentName Name of the continent that is to be safely removed
     */
    public void removeContinentFromMap(String continentName){
        this.continentHashMap.remove(continentName);
    }

    /**This method provides a list of neighbour country objects if the country exists, null otherwise
     *
     * @param countryName Name of the country whose neighbours are needed
     * @return a set of neighbor country names
     */
    public Set<String> getNeighbourList(String countryName) {
        if(countryHashMap.containsKey(countryName)) {
            return countryHashMap.get(countryName).getNeighbouringCountries().keySet();
        }
        return null;
    }

    /**This method stores the passed hashmap in the country hashmap locally
     *
     * @param countryHashMap The hashmap to be stored locally
     */
    public void setCountryHashMap(HashMap<String, GameCountry> countryHashMap) {
        this.countryHashMap = countryHashMap;
    }

    /**This method stores the passed hashmap in the continent hashmap locally
     *
     * @param continentHashMap The hashmap to be stored locally
     */
    public void setContinentHashMap(HashMap<String, GameContinent> continentHashMap) {
        this.continentHashMap = continentHashMap;
    }

    /**This method checks if the key is present in the continent hashmap or not.
     *
     * @param key key to be searched in the hashmap
     * @return true if the key exists in the continent hashmap, false otherwise
     */
    public boolean containsKey(String key) {
        if(continentHashMap.containsKey(key)) {
            return true;
        }
        return false;
    }

    /**This method is used to access the continent hashmap.
     *
     * @return returns the hashmap of all the continents in the game till this moment
     */
    public HashMap<String, GameContinent> getContinentHashMap(){
        return continentHashMap;
    }

    /**This method is used to access the country hashmap
     *
     * @return returns the hashmap of all the countries in the game till this moment
     */
    public HashMap<String, GameCountry> getCountryHashMap(){
        return countryHashMap;
    }

    /**This method removes a neighbour as per the player request.
     *
     * @param countryName Country whose neighbour is to be removed
     * @param neighborName Name of country to be removed
     * @return status of the method execcution
     */
    public String removeNeighbor(String countryName , String neighborName) {
        try {

            for (GameCountry neighbor :  countryHashMap.get(countryName).getNeighbouringCountries().values()) {
                if (neighbor.getCountryName().equals(neighborName)) {
                    countryHashMap.get(countryName).getNeighbouringCountries().remove(neighborName);
                }
            }
            return "SUCCESS";
        }catch (NullPointerException e) {
            return "EXCEPTION IN ACCESSING DATA";
        }
    }

    /***
     * This Method checks for all continents owned by a player
     * @param playerID
     * @return ArrayList<GameContinent> continentsOwnedByPlayer
     */
    public ArrayList<GameContinent> checkContinentsOwnedByOnePlayer(int playerID) {
        try {
            ArrayList<GameContinent> continentsOwnedByPlayer = new ArrayList<>(this.getContinentHashMap().values());
            for(String continentName : this.getContinentHashMap().keySet()) {
                for (GameCountry country: this.getContinentHashMap().get(continentName).getCountries().values() ) {
                    if (country.getCurrentPlayer().getId()!=playerID) {
                        continentsOwnedByPlayer.remove(this.getContinentHashMap().get(continentName));
                        break;
                    }
                }
            }
            return continentsOwnedByPlayer;
        }catch (Exception e) {
            return null;
        }

    }
}
