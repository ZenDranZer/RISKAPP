package utils;


import models.GameCountry;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.ArrayList;
import java.util.Iterator;


/**GraphUtil class is used for representing the map in a graph formation
 * and perform graph operations like graph traversal, connectivity, etc.
 * */
public class GraphUtil {

    /**countryGraph is a Graph object for maintaining the map graph.*/
    private Graph<GameCountry, DefaultEdge> countryGraph;

    /**Public constructor for initializing the countryGraph*/
    public GraphUtil(){
        countryGraph = new SimpleGraph<>(DefaultEdge.class);
    }

    /**Getter method to access the countryGraph
     * @return Graph<GameCountry> an object which */
    public Graph<GameCountry, DefaultEdge> getCountryGraph() {
        return countryGraph;
    }

    public void setCountryGraph(ArrayList<GameCountry> countryList) {

        for (GameCountry gameCountry : countryList) {
            countryGraph.addVertex(gameCountry);
        }

        for (GameCountry gameCountry : countryList) {
            ArrayList<GameCountry> neighbours = gameCountry.getNeighbouringCountries();
            for (GameCountry neighbour : neighbours){
                countryGraph.addEdge(gameCountry,neighbour);
            }
        }
    }

    public boolean areConnected(GameCountry firstCountry,GameCountry secondCountry){
        return countryGraph.containsEdge(firstCountry, secondCountry);
    }

    public Iterator<GameCountry> breadthFirstSearch(GameCountry initialCountry){
        return new BreadthFirstIterator<>(countryGraph);
    }

    public Iterator<GameCountry> depthFirstSearch(GameCountry initialCountry){
        return new DepthFirstIterator<>(countryGraph);
    }

}
