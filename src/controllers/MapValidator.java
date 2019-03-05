package controllers;

import models.GameContinent;
import models.GameCountry;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import utils.GraphUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MapValidator {
 /*   public boolean hasDuplicateNeighbors(GameCountry country) {
        for (int i = 0; i < country.getNeighbouringCountries().size(); i++) {
            for (int j = 0; j < country.getNeighbouringCountries().size(); j++) {
                if (j != i) {
                    if (country.getNeighbouringCountries().get(i).getCountryName().equals(country.getNeighbouringCountries().get(j).getCountryName())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }*/
    public boolean hasNeighbor(GameCountry country){ // see if the neighbour list is initialized in the model constructor and if not, do it.
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

public boolean isWholeMapConnected(GraphUtil util, Collection<GameCountry> countries){
        return util.isConnected();
}


/*public boolean isWholeContinentConnected(GameContinent continent){
        GraphUtil tempGraph = new GraphUtil();
        tempGraph.setCountryGraph(continent.getCountries());
        return tempGraph.isConnected();
}*/

}
