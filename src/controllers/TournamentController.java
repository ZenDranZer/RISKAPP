package controllers;

import models.*;
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

    public Tournament getTournament() {
        return tournament;
    }

    public void setMaps(int noOfMaps) {

        for(int i=0;i<noOfMaps;i++) {
            GameState g = new GameState();
            MapGenerator mapgen = new MapGenerator(g.getGameMapObject());
            mapgen.readConquestFile(tournament.getMapPaths().get(i));
            tournament.setGamestate(g);
        }
    }

    public void setPlayer(ArrayList<String> player) {

        for(int i=0;i<player.size();i++) {
            if(player.get(i).equals("Cheater")) {
                CheaterPlayer cp = new CheaterPlayer();
                tournament.setBots(cp);
            } else if(player.get(i).equals("Aggressive")) {
                AggresisivePlayer ap = new AggresisivePlayer();
                tournament.setBots(ap);
            } else if (player.get(i).equals("Benevolent")) {
                BenevolentPlayer bp = new BenevolentPlayer();
                tournament.setBots(bp);
            } else if (player.get(i).equals("Random")) {
                RandomPlayer rp = new RandomPlayer();
                tournament.setBots(rp);
            }
        }
    }
}
