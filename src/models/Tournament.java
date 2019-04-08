package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

public class Tournament extends Observable {

    private ArrayList<GameState> gamestate;
    private ArrayList<Player> bots;
    private ArrayList<ArrayList<String>> result;
    private int noOfGames;
    private int maxNoOfTurns;
    private int numberOfMaps;
    private HashMap<Integer,String> mapPaths = new HashMap<>();

    public Tournament() {
        bots = new ArrayList<>();
        gamestate = new ArrayList<>();
        result = new ArrayList<>();
        for (ArrayList<String> list: result) {
            list = new ArrayList<>();
        }
        noOfGames = 1;
        maxNoOfTurns = 1;
        mapPaths.put(0,"/home/jil/IdeaProjects/RISKAPP/src/mapFiles/map1.map");
        mapPaths.put(1,"/home/jil/IdeaProjects/RISKAPP/src/mapFiles/map2.map");
        mapPaths.put(2,"/home/jil/IdeaProjects/RISKAPP/src/mapFiles/map3.map");
        mapPaths.put(3,"/home/jil/IdeaProjects/RISKAPP/src/mapFiles/map4.map");
        mapPaths.put(4,"/home/jil/IdeaProjects/RISKAPP/src/mapFiles/map5.map");
    }


    /**
     * Gets the number of maps on which tournament is played
     * @return number of maps
     */
    public int getNumberOfMaps() {
        return numberOfMaps;
    }

    /**
     * Sets the number of maps on which the tournament is to be played
     * @param numberOfMaps number of maps12q
     */
    public void setNumberOfMaps(int numberOfMaps) {
        this.numberOfMaps = numberOfMaps;
    }

    /**
     * Gets the number of games to be played as a part of the tournament
     * @return Number of games
     */
    public int getNoOfGames() {
        return noOfGames;
    }

    /**
     * Sets the number of games to be played as a part of tournament
     * @param noOfGames Number of games
     */
    public void setNoOfGames(int noOfGames) {
        this.noOfGames = noOfGames;
    }

    /**
     * Gets the max number of turns
     * @return maximum number of turns
     */
    public int getMaxNoOfTurns() {
        return maxNoOfTurns;
    }

    /**
     * Sets the max number of turns
     * @param maxNoOfTurns maximum number of turns
     */
    public void setMaxNoOfTurns(int maxNoOfTurns) {
        this.maxNoOfTurns = maxNoOfTurns;
    }

    /**
     * Hashmap containing map paths
     * @return map path list
     */
    public HashMap<Integer, String> getMapPaths() {
        return mapPaths;
    }

    /**
     * Sets the map paths
     * @param mapPaths sets map paths
     */
    public void setMapPaths(HashMap<Integer, String> mapPaths) {
        this.mapPaths = mapPaths;
    }

    /**
     * Gets the current gamestate
     * @return gamestate
     */
    public ArrayList<GameState> getGamestate() {
        return gamestate;
    }

    /**
     * Sets the current gamestate
     * @return gamestate
     */
    public void setGamestate(GameState gstate) {
        gamestate.add(gstate);
    }

    /**
     * Gets the bots
     * @return ArrayList of bots
     */
    public ArrayList<Player> getBots() {
        return bots;
    }

    /**
     * Sets the bots
     * @return Player object
     */
    public void setBots(Player bot) {
        bots.add(bot);
    }

    /**
     *Gets the result of the tournament
     * @return Result of the tournament
     */
    public ArrayList<ArrayList<String>> getResult() {
        return result;
    }

    /**
     *Declares the result of the current tournament
     * @param map The current map on which tournament is being played
     * @param game Current game number
     * @param winner Winner of the game
     */
    public void addResult(int map,int game,String winner){
        result.get(map).add(game,winner);
        setChanged();
        notifyObservers();
    }


}