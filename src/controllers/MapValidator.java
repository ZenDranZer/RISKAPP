package controllers;

import models.GameContinent;
import models.GameCountry;
import models.GameMap;
import utils.GraphUtil;

import java.util.*;

/**
 * Class containing various validations for the generated map
 */
public class MapValidator {
    HashMap<String, GameCountry>countryHashMap;
    HashMap<String, GameContinent>continentHashMap;
    HashMap<String, String>guiHashMap;
    GameMap gameMap;

    public MapValidator(GameMap map) {
        gameMap = map;
        countryHashMap = gameMap.getCountryHashMap();
        continentHashMap = gameMap.getContinentHashMap();
        guiHashMap = gameMap.getGuiHashMap();
    }

    /**
	 * Check whether a country has duplicate neighbors
	 * @param neighbours List of neighboring countries 
	 * @return true 
	 */
    public boolean hasDuplicateNeighbours(ArrayList<String> neighbours) {
        Set<String> tempSet = new HashSet<>(neighbours);
        if(tempSet.size()==neighbours.size()) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a continent has only one country
     * @param continents List of GameContinents
     * @return true in only one country present 
     */
    public boolean hasSingleCountryContinent(ArrayList<GameContinent> continents) {
        for(GameContinent continent : continents) {
            if(continent.getCountries().size()<=1) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks whether a country has itself as neighbor
     * @param countries list Game countries
     * @return true is loop is present
     */
    public boolean containsLoop(ArrayList<GameCountry> countries) {
        for(GameCountry country:countries) {
            if(country.getNeighbouringCountries().containsKey(country.getCountryName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether a country has neighbors or not
     * @param country GameCountry representing a country
     * @return true if neighbors are present
     */
    public boolean hasNeighbor(GameCountry country) {
        if(country.getNeighbouringCountries().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if the map contains valid number of continents
     * @param listOfContinents List of Game Continents
     * @return true if the continent count is valid
     */
    public boolean hasValidNumberOfContinents(ArrayList<GameContinent> listOfContinents) {
        if(listOfContinents.size()>1 && listOfContinents.size()<=32 ) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks whether a country has valid number of neighbors
     * @param country Game Country object
     * @return true is number of neighbors is valid
     */
    public boolean hasValidNumberOfNeighbors(GameCountry country) {
        if (country.getNeighbouringCountries().size() > 10) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether the map has valid number of countries
     * @param countries List of GameCountry objects
     * @return true is number of countries is valid
     */
    public boolean hasValidNumberOfCountries(ArrayList<GameCountry> countries) {
        if (countries.size() > 255) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if map is fully linked 
     * @return true is map is linked
     */
    public boolean isFullyLinked() {
        for(String country : countryHashMap.keySet()) {
            for(String neighbor : countryHashMap.get(country).getNeighbouringCountries().keySet()) {
                if(!(countryHashMap.get(neighbor).getNeighbouringCountries().containsKey(country))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Function to check if the continent is fully linked with other continents in the map
     * @param continent Continent to be checked
     * @param graphUtilObject GraphUtilObject
     * @return Boolean value indicating linking of the continent
     */
    public boolean isContinentFullyLinked(GameContinent continent, GraphUtil graphUtilObject) {
        ArrayList<String> countries = new ArrayList<>();
        for(GameCountry country : continent.getCountries().values()) {
            for(GameCountry neighbor : continent.getCountries().values()) {
                if(graphUtilObject.getCountryGraph().containsEdge(country,neighbor)) {
                    countries.add(neighbor.getCountryName());
                }
            }
        }
        LinkedHashSet<String> noDuplicates = new LinkedHashSet<>();
        noDuplicates.addAll(countries);
        countries.clear();
        countries.addAll(noDuplicates);
        if(countries.size()==continent.getCountries().size()) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the whole map is connected
     * @param util GraphUtility
     * @return true is the map is connected
     */
    public boolean isWholeMapConnected(GraphUtil util){
        return util.isConnected();
    }
}