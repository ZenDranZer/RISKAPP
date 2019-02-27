package controllers;

import models.GameCountry;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import utils.GraphUtil;

import java.util.ArrayList;
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

public boolean isWholeMapConnected(Graph<GameCountry, DefaultEdge> map){

    return true;
}
}
