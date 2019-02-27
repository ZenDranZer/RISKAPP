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

    /**Setter method for creating a new map graph.
     *@param countryList an ArrayList containing all the countries.*/
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


    /**A method which decide whether given countries are connected with an edge
     * @param firstCountry source country
     * @param  secondCountry destination country*/
    public boolean areConnected(GameCountry firstCountry,GameCountry secondCountry){
        return countryGraph.containsEdge(firstCountry, secondCountry);
    }

    /**A method which will return the breadth first search result for a graph from a particular source
     * @param initialCountry the source node
     * @return Iterator<GameCountry> is an iterator with result of BFS of GameCountry type.*/
    public Iterator<GameCountry> breadthFirstSearch(GameCountry initialCountry){
        return new BreadthFirstIterator<>(countryGraph,initialCountry);
    }

    /**A method which will return the breadth first search result for a graph.
     * @return Iterator<GameCountry> is an iterator with result of BFS of GameCountry type.*/
    public Iterator<GameCountry> breadthFirstSearch(){
        return new BreadthFirstIterator<>(countryGraph);
    }

    /**getIteratorSize takes an Iterator and returns the size of the iterator.
     * @param iterator its an Iterator object for which we need to find size
     * @return int which is the count of iterator.*/
    public int getIteratorSize(Iterator<GameCountry> iterator){
        int count = 0;
        while (iterator.hasNext()){
            iterator.next();
            count++;
        }
        return count;
    }

    /**A method which will return the depth first search result for a graph from a particular source
     * @param initialCountry the source node
     * @return Iterator<GameCountry> is an iterator with result of DFS of GameCountry type.*/
    public Iterator<GameCountry> depthFirstSearch(GameCountry initialCountry){
        return new DepthFirstIterator<>(countryGraph);
    }

    /**A method which will return the depth first search result for a graph.
     * @return Iterator<GameCountry> is an iterator with result of DFS of GameCountry type.*/
    public Iterator<GameCountry> depthFirstSearch(){
        return new DepthFirstIterator<>(countryGraph);
    }


}
