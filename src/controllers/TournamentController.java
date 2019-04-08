package controllers;

import models.*;

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
        tournament.setNumberOfMaps(noOfMaps);
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
                Player cheater = new CheaterPlayer();
                tournament.setBots(cheater);
            } else if(player.get(i).equals("Aggressive")) {
                Player aggressive = new AggressivePlayer();
                tournament.setBots(aggressive);
            } else if (player.get(i).equals("Benevolent")) {
                Player benevolet = new BenevolentPlayer();
                tournament.setBots(benevolet);
            } else if (player.get(i).equals("Random")) {
                Player random = new RandomPlayer();
                tournament.setBots(random);
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
        int maxNumberOfMaps = tournament.getNumberOfMaps();
        int maxNoOfTurns = tournament.getMaxNoOfTurns();
        int noOfArmiesToEachCountry =  noOfArmies/noOfCountries;
        ArrayList<Player> bots = tournament.getBots();
        int noOfGames = tournament.getNoOfGames();
        int turns = 0;
        String message = "";
        for(int j = 0 ; j<maxNumberOfMaps;j++) {


            for (int i = 0; i < noOfGames; i++) {

                tournament.getGamestate().get(i).setPlayers(bots);
                TurnController turnController = new TurnController(tournament.getGamestate().get(i));
                ArrayList<GameCountry> countries = tournament.getGamestate().get(i).getGameMapObject().getAllCountries();
                turnController.allocateCountries(bots, countries);
                allocateArmiesToEachPlayerCountry(i, noOfArmiesToEachCountry);

                int player_num = 0;
                Player currentPlayer;

                while (turns < maxNoOfTurns) {
                    if (player_num == bots.size()) {
                        player_num = 0;
                    }

                    currentPlayer = bots.get(player_num);

                    /*message = */currentPlayer.reinforcement();
                    System.out.println(message);
                    message = currentPlayer.attack();
                    System.out.println(message);
                    if (message.toLowerCase().contains("winner")){
                        tournament.addResult(j,i,currentPlayer.getName());
                        break;
                    }
                    /*message = */currentPlayer.fortify();
                    System.out.println(message);
                    player_num++;
                }
                if(turns == maxNoOfTurns){
                    tournament.addResult(j,i,"draw");
                    break;
                }
            }
        }

    }

}
