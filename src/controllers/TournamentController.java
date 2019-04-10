package controllers;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controls the tournament play
 */
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
        int id = 0 ;
        for (String s : player) {

            if (s.equals("Cheater")) {
                Player cheater = new CheaterPlayer(s + "_" + id);
                cheater.setId(id);
                tournament.setBots(cheater);
            } else if (s.equals("Aggressive")) {
                Player aggressive = new AggressivePlayer(s+ "_" + id);
                aggressive.setId(id);
                tournament.setBots(aggressive);
            } else if (s.equals("Benevolent")) {
                Player benevolent = new BenevolentPlayer(s+ "_" + id);
                benevolent.setId(id);
                tournament.setBots(benevolent);
            } else if (s.equals("Random")) {
                Player random = new RandomPlayer(s+ "_" + id);
                random.setId(id);
                tournament.setBots(random);
            }

            id++;
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
     * Allocates the initial number of armies to each player in the tournament
     * @param turn turn controller
     * @param gameState game state for getting map object
     * @param countries countries in the maps
     */
    public void allocateInitialArmies(TurnController turn,GameState gameState, ArrayList<GameCountry> countries) {
        int initialArmies = turn.getEachPlayerArmy(gameState.getPlayers());

        for (GameCountry country : countries) {
            country.setArmies(1);
        }
        for (Player player : gameState.getPlayers()) {
            int countryCount = player.getCountries().size();
            player.setPlayerArmies(countryCount);
            player.setRemainingArmies(initialArmies - countryCount);
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
        ArrayList<Player> bots = tournament.getBots();
        int noOfGames = tournament.getNoOfGames();

        int turns = 0;
        String message = "";
        for(int j = 0 ; j<maxNumberOfMaps;j++) {
            for (int i = 0; i < noOfGames; i++) {
                System.out.println("MAP :" + j);
                System.out.println("GAME :" + i);
                tournament.getGamestate().get(j).setPlayers(bots);
                TurnController turnController = new TurnController(tournament.getGamestate().get(j));
                ArrayList<GameCountry> countries = tournament.getGamestate().get(j).getGameMapObject().getAllCountries();
                turnController.allocateCountries(bots, countries);
                allocateInitialArmies(turnController,tournament.getGamestate().get(j),countries);
                RiskCardController riskCardController = tournament.getGamestate().get(j).getRiskController();
                riskCardController.initRiskCardDeck(tournament.getGamestate().get(j).getGameMapObject());
                int player_num = 0;
                tournament.setGameMapForPlayers(tournament.getGamestate().get(j).getGameMapObject());
                Player currentPlayer;
                MapGenerator mapGenerator = new MapGenerator(tournament.getGamestate().get(j).getGameMapObject());
                turns = 0;
                while (turns < maxNoOfTurns) {
                    if (player_num == bots.size()) {
                        player_num = 0;
                    }
                    currentPlayer = bots.get(player_num);
                    turnController.calculateNewArmies(currentPlayer,mapGenerator);
                    tradeRiskCard(currentPlayer,riskCardController);
                    message = currentPlayer.reinforcement(currentPlayer.getRemainingArmies());
                    System.out.println("Reinforcement by : "+currentPlayer.getName()+" : " + message);
                    message = currentPlayer.attack();
                    System.out.println("Attack by : "+currentPlayer.getName()+" : " + message);
                    if(message.toLowerCase().contains("success")){
                        RiskCard riskCard = riskCardController.allocateRiskCard();
                        currentPlayer.addRiskCard(riskCard);
                        tradeRiskCard(currentPlayer,riskCardController);
                    }
                    if(message.toLowerCase().contains("eliminated")){
                        tradeRiskCard(currentPlayer,riskCardController);
                    }
                    if (message.toLowerCase().contains("winner")){
                        System.out.println("RESULT : WINNER : " + currentPlayer.getName());
                        tournament.addResult(j,i,currentPlayer.getName());
                        break;
                    }
                    message = currentPlayer.fortify();
                    System.out.println("Fortify by : "+currentPlayer.getName()+" : " + message);
                    player_num++;
                    turns++;
                }
                if(turns == maxNoOfTurns){
                    tournament.addResult(j,i,"draw");
                    System.out.println("RESULT : DRAW ");
                }
            }
        }

    }

}
