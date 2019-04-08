package controllers;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TournamentController {

    Tournament tournament;

    public TournamentController() {
        tournament = new Tournament();
    }

    /**
     * Setting maximum number of players for the tournament
     * @param maxTurns max num of turns
     */
    public void setMaxNoOfTuruns(int maxTurns) {
        tournament.setMaxNoOfTurns(maxTurns);
    }

    /**
     * Setting maximum number of games for the tournament
     * @param noOfGames max num of games
     */
    public void setNoOfGames(int noOfGames) {
        tournament.setNoOfGames(noOfGames);
    }

    /**
     * Gets the tournament object
     * @return tournament tournament object
     */
    public Tournament getTournament() {
        return tournament;
    }

    /**
     * Sets the Maps for the tournament
     * @param noOfMaps num of maps to be set
     */
    public void setMaps(int noOfMaps) {
        tournament.setNumberOfMaps(noOfMaps);
        for(int i=0;i<noOfMaps;i++) {
            GameState g = new GameState();
            MapGenerator mapgen = new MapGenerator(g.getGameMapObject());
            mapgen.readConquestFile(tournament.getMapPaths().get(i));
            tournament.setGamestate(g);
        }
    }

    /**
     * Sets the player profile in the bots
     * @param player Player List containing various types of player objects
     */
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

    /**
     * Allocates armies to each country possessed by each player
     * @param gameNum Num of the game being played currently
     * @param noOfArmiesToEachCountry Num of armies allocated to each player
     */
    public void allocateArmiesToEachPlayerCountry(int gameNum, int noOfArmiesToEachCountry) {
        for(int k =0; k<tournament.getGamestate().get(gameNum).getPlayers().size();k++) {
            for(int a =0;a<tournament.getGamestate().get(gameNum).getPlayers().get(k).getCountries().size();a++)
                tournament.getGamestate().get(gameNum).getPlayers().get(k).getCountries().get(a).setArmies(noOfArmiesToEachCountry);
        }
    }

    /**
     * Trading risk cards for armies
     * @param currentPlayer Current player
     * @param riskCardController Risk card controller object
     */
    private void tradeRiskCard(Player currentPlayer,RiskCardController riskCardController){
        if(currentPlayer.getCardsHeld().size()>=5) {
            HashMap<String, ArrayList<RiskCard>> possibleSet = riskCardController.getPossibleSets(currentPlayer);
            for (ArrayList<RiskCard> set:possibleSet.values()) {
                riskCardController.tradeCards(currentPlayer,set);
            }
        }
    }

    /**
     * This function executes the whole tournament flow from allocating armies to declaring winners for each tournament
     */
    public void startTournament() {

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

                tournament.getGamestate().get(j).setPlayers(bots);
                TurnController turnController = new TurnController(tournament.getGamestate().get(j));
                ArrayList<GameCountry> countries = tournament.getGamestate().get(j).getGameMapObject().getAllCountries();
                turnController.allocateCountries(bots, countries);
                allocateArmiesToEachPlayerCountry(i, noOfArmiesToEachCountry);
                RiskCardController riskCardController = tournament.getGamestate().get(j).getRiskController();
                riskCardController.initRiskCardDeck(tournament.getGamestate().get(j).getGameMapObject());
                int player_num = 0;
                Player currentPlayer;

                while (turns < maxNoOfTurns) {
                    if (player_num == bots.size()) {
                        player_num = 0;
                    }

                    currentPlayer = bots.get(player_num);
                    tradeRiskCard(currentPlayer,riskCardController);
                    message = currentPlayer.reinforcement();
                    System.out.println(message);
                    message = currentPlayer.attack();
                    System.out.println(message);
                    if(message.toLowerCase().contains("success")){
                        RiskCard riskCard = riskCardController.allocateRiskCard();
                        currentPlayer.addRiskCard(riskCard);
                        tradeRiskCard(currentPlayer,riskCardController);
                    }
                    if(message.toLowerCase().contains("eliminated")){
                        tradeRiskCard(currentPlayer,riskCardController);
                    }
                    if (message.toLowerCase().contains("winner")){
                        tournament.addResult(j,i,currentPlayer.getName());
                        break;
                    }
                    message = currentPlayer.fortify();
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
