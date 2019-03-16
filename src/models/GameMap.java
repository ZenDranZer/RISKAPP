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
    public void removeContinent(String continentName){
        this.countryHashMap.remove(continentName);
        setChanged();
        notifyObservers();
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


}
