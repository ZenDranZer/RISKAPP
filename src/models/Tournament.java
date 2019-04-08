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


    public int getNumberOfMaps() {
        return numberOfMaps;
    }

    public void setNumberOfMaps(int numberOfMaps) {
        this.numberOfMaps = numberOfMaps;
    }

    public int getNoOfGames() {
        return noOfGames;
    }

    public void setNoOfGames(int noOfGames) {
        this.noOfGames = noOfGames;
    }

    public int getMaxNoOfTurns() {
        return maxNoOfTurns;
    }

    public void setMaxNoOfTurns(int maxNoOfTurns) {
        this.maxNoOfTurns = maxNoOfTurns;
    }

    public HashMap<Integer, String> getMapPaths() {
        return mapPaths;
    }

    public void setMapPaths(HashMap<Integer, String> mapPaths) {
        this.mapPaths = mapPaths;
    }

    public ArrayList<GameState> getGamestate() {
        return gamestate;
    }

    public void setGamestate(GameState gstate) {
        gamestate.add(gstate);
    }

    public ArrayList<Player> getBots() {
        return bots;
    }

    public void setBots(Player bot) {
        bots.add(bot);
    }

    public ArrayList<ArrayList<String>> getResult() {
        return result;
    }

    public void addResult(int map,int game,String winner){
        result.get(map).add(game,winner);
        setChanged();
        notifyObservers();
    }


}
