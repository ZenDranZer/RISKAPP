package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

public class GameMap extends Observable {
    public  HashMap<String,GameCountry> countryHashMap;
    public  HashMap<String,GameContinent> continentHashMap;
    public HashMap <String,String>guiHashMap;

    public GameMap(){
        countryHashMap = new HashMap<>();
        continentHashMap = new HashMap<>();
        guiHashMap = new HashMap<>();
    }
    public ArrayList<GameCountry> getAllCountries(){
        return new ArrayList<GameCountry>(this.countryHashMap.values());
    }
    public ArrayList<GameContinent> getAllContinents(){
        return new ArrayList<GameContinent>(this.continentHashMap.values());
    }
    public void setGetGuiHashMapParameter(String key, String value){
        guiHashMap.put(key,value);
    }
    public HashMap<String,String>getGuiHashMap(){
        return this.guiHashMap;
    }


    public void addCountry(GameCountry newCountry){
        this.countryHashMap.put(newCountry.getCountryName(),newCountry);
        setChanged();
        notifyObservers();
    }
    public void addContinent(GameContinent newContinent){
        this.continentHashMap.put(newContinent.getContinentName(),newContinent);
        setChanged();
        notifyObservers();
    }
    public void removeCountry(String countryName){
        this.countryHashMap.remove(countryName);
        setChanged();
        notifyObservers();
    }
    public String removeContinent(String continentName){
        for(String country: continentHashMap.get(continentName).getCountries().keySet()){
            for(GameCountry neighbor: countryHashMap.get(country).getNeighbouringCountries().values()){
                neighbor.getNeighbouringCountries().remove(country);
            }
            countryHashMap.remove(country);
        }
        this.removeContinentFromMap(continentName);
        setChanged();
        notifyObservers();
        return "SUCCESS";
    }
    public void removeContinentFromMap(String continentName){
        this.continentHashMap.remove(continentName);
    }

    public Set<String> getNeighbourList(String countryName){
        if(countryHashMap.containsKey(countryName)){
            return countryHashMap.get(countryName).getNeighbouringCountries().keySet();
        }
        return null;
    }

    public void setCountryHashMap(HashMap<String, GameCountry> countryHashMap) {
        this.countryHashMap = countryHashMap;
    }

    public void setContinentHashMap(HashMap<String, GameContinent> continentHashMap) {
        this.continentHashMap = continentHashMap;
    }

    public boolean containsKey(String key){
        if(continentHashMap.containsKey(key)){
            return true;
        }
        return false;
    }
    public HashMap<String, GameContinent> getContinentHashMap(){
        return continentHashMap;
    }
    public HashMap<String, GameCountry> getCountryHashMap(){
        return countryHashMap;
    }
    /**This method removes a neighbour as per the player request.
     *
     * @param countryName
     * @param neighborName
     * @return status of the method execcution
     */
    public String removeNeighbor(String countryName , String neighborName) {
        try {

            for (GameCountry neighbor :  countryHashMap.get(countryName).getNeighbouringCountries().values()) {
                if (neighbor.getCountryName().equals(neighborName)) {
                    countryHashMap.get(countryName).getNeighbouringCountries().remove(neighborName);
                }
            }
            //  countryHashMap.get(countryName).setNeighbouringCountries(neighbors); This is not needed.
            return "SUCCESS";
        }catch (NullPointerException e){
            return "EXCEPTION IN ACCESSING DATA";
        }
    }



}
