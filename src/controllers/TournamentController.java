package controllers;

import models.GameState;
import models.Player;
import models.Tournament;
import utils.Constants;

import java.util.ArrayList;

public class TournamentController {

    Tournament tournament;

    public TournamentController() {
        tournament = new Tournament();
    }

    public void setMaxNoOfTuruns(int maxTurns) {
        tournament.setMaxNoOfTurns(maxTurns);
    }

    public void setNoOfGames(int noOfGames) {
        tournament.setNoOfGames(noOfGames);
    }

    public void setMaps(int noOfMaps) {

        for(int i=0;i<noOfMaps;i++) {
            GameState g = new GameState();
            MapGenerator mapgen = new MapGenerator(g.getGameMapObject());
            mapgen.readConquestFile(tournament.getMapPaths().get(i));
            tournament.setGamestate(g);
        }
    }
}
