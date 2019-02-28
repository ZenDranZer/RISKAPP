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
    public boolean hasDuplicateNeighbors(GameCountry country) {
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
    }
    public boolean hasNeighbor(GameCountry country){ // see if the neighbour list is initialized in the model constructor and if not, do it.
        if(country.getNeighbouringCountries().isEmpty()){
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

public boolean isWholeMapConnected(GraphUtil util, ArrayList<GameCountry> countries){
    if (util.getIteratorSize(util.breadthFirstSearch(countries.get(0))) == countries.size()){
        return true;
    }
    return false;
}

public boolean isWholeContinentConnected(GameContinent continent){
        GraphUtil tempGraph = new GraphUtil();
        tempGraph.setCountryGraph(continent.getCountries());
        if (tempGraph.getIteratorSize(tempGraph.breadthFirstSearch(continent.getCountries().get(0))) == continent.getCountries().size()){
            return true;
        }
        return false;
}

}
