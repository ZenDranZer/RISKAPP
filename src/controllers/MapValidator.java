package controllers;

import models.GameContinent;
import models.GameCountry;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import utils.GraphUtil;

import java.util.*;

public class MapValidator {
    public boolean hasDuplicateNeighbours(ArrayList<String> neighbours){
        Set<String> tempSet = new HashSet<>(neighbours);
        if(tempSet.size()==neighbours.size()){
            return false;
        }
        return true;
    }

    public boolean hasSingleCountryContinent(ArrayList<GameContinent> continents){
        for(GameContinent continent : continents){
            if(continent.getCountries().size()==1){
                return true;
            }
        }
        return false;
    }
    public boolean containsloop(ArrayList<GameCountry> countries){
        for(GameCountry country:countries){
            if(country.getNeighbouringCountries().containsKey(country.getCountryName())){
                return true;
            }
        }
        return false;
    }

    public boolean hasNeighbor(GameCountry country) {
        if(country.getNeighbouringCountries().isEmpty()){
            System.out.println(country.getCountryName());
            return false;
        }
        return true;
    }
    public boolean hasValidNumberOfContinents(ArrayList<GameContinent> listOfContinents){
        if(listOfContinents.size()!=0 || listOfContinents.size()<=32 ){
            return true;
        }
        return false;
    }
    public boolean hasValidNumberOfNeighbors(GameCountry country){
        if (country.getNeighbouringCountries().size() > 10){
            return false;
        }
        return true;
    }

    public boolean hasValidNumberOfCountries(ArrayList<GameCountry> countries){
        if (countries.size() > 255){
            return false;
        }
        return true;
    }
    public boolean isFullyLinked() {
        for(String country : MapGenerator.countryHashMap.keySet()){
            for(String neighbor : MapGenerator.countryHashMap.get(country).getNeighbouringCountries().keySet()){
                if(!(MapGenerator.countryHashMap.get(neighbor).getNeighbouringCountries().containsKey(country))){
                    System.out.println(country+"    "+neighbor);
                    return false;}
            }
        }
        System.out.println("something");
        return true;
    }

    public boolean isWholeMapConnected(GraphUtil util){
        return util.isConnected();
    }
}