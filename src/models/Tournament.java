package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Tournament {
    private ArrayList<GameMap> maps;
    private ArrayList<GameState> gamestate;
    private ArrayList<Player> bots;
    private HashMap<String,ArrayList<String>> result;
    private int noOfGames;
    private int maxNoOfTurns;

    public Tournament() {
        maps = new ArrayList<>();
        bots = new ArrayList<>();
        gamestate = new ArrayList<>();
        result = new HashMap<>();
        noOfGames = 1;
        maxNoOfTurns = 1;
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

}
