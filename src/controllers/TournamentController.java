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

    public void allocateArmiesToEachPlayerCountry(int gameNum, int noOfArmiesToEachCountry) {
        for(int k =0; k<tournament.getGamestate().get(gameNum).getPlayers().size();k++) {
            for(int a =0;a<tournament.getGamestate().get(gameNum).getPlayers().get(k).getCountries().size();a++)
                tournament.getGamestate().get(gameNum).getPlayers().get(k).getCountries().get(a).setArmies(noOfArmiesToEachCountry);
        }
    }

    public void startTournament(){

        int noOfCountries = 12;
        int noOfArmies = 60;
        int noOfArmiesToEachCountry =  noOfArmies/noOfCountries;
        ArrayList<Player> bots = tournament.getBots();
        int turns = 0;

        for(int i=0;i<tournament.getNoOfGames();i++) {

            tournament.getGamestate().get(i).setPlayers(bots);
            TurnController turnController = new TurnController(tournament.getGamestate().get(i));
            ArrayList<GameCountry> countries = tournament.getGamestate().get(i).getGameMapObject().getAllCountries();
            turnController.allocateCountries(bots,countries);

            allocateArmiesToEachPlayerCountry(i,noOfArmiesToEachCountry);

            int player_num=0;
            Player currentPlayer;
            while (turns < tournament.getMaxNoOfTurns()) {
                if(player_num == bots.size()) {
                    player_num = 0;
                }

                currentPlayer = bots.get(player_num);

                currentPlayer.reinforcement();
                currentPlayer.attack();
                currentPlayer.fortify();

            }

        }


    }

}
