package controllers;

import models.*;
import utils.TestUtil;

import java.util.*;

import static utils.Constants.ARMY_DISTRIBUTION.*;

public class TurnController {

    Player activePlayer;
    int availableArmies;

    public TurnController() {
        //TODO allocate armies
        availableArmies = 3;
    }

    public int getRandomCountryIndex(ArrayList<GameCountry> countries) {

        Random rand = new Random();
        int n = rand.nextInt(countries.size());
        //System.out.println(n);
        return n;
    }

    //calculate armies to add at the beginning of each turn
    public int calculateNewArmies(Player activePlayer) {
        //3 is the minimum of number of armies each turn

        int countryCount = activePlayer.getCountries().size();

        availableArmies = 3;
        if (countryCount / 3 > 3)
            availableArmies = countryCount / 3;

        //continent logic
        for (GameContinent continent : activePlayer.getContinents()) {
            availableArmies = continent.getContinentValue();
        }
        return availableArmies;
    }

    //add army to a country
    public void placeArmy(Player activePlayer, GameCountry country, int armies) {

        ArrayList<GameCountry> lstPlayerArmies = activePlayer.getCountries();

        //get object from list where name matches the given object
        GameCountry res = lstPlayerArmies.stream().filter(cntry -> cntry.getCountryName().equals(country.getCountryName())).findFirst().get();
        res.setArmies(res.getArmiesStationed() + armies);

        this.availableArmies = this.availableArmies - armies;

    }

    //reinforcement phase
    public void reinforcement() {
        // 1. number of new armies available
        // 2. while (newArmies !=0){ 1. ask user to select the country 2. place army }
    }

    public void fortification(List<GameCountry> lstCountries) {  // TODO: Graph object representing the entire map
        // 1. select player country
        // 2. check for connected player countries
        // 3. if available, move as many armies you want
        // all countries should have atleast one army left
    }


    public int getNumberEachPlayerArmy(ArrayList<Player> activePlayers) {
        int numOfPlayers = activePlayers.size();
        int armiesForeachPlayer = 0;
        switch (numOfPlayers) {
            case 2:
                armiesForeachPlayer = twoPlayerDistribution.getArmyStrength();
                break;
            case 3:
                armiesForeachPlayer = threePlayerDistribution.getArmyStrength();
                break;
            case 4:
                armiesForeachPlayer = fourPlayerDistribution.getArmyStrength();
                break;
            case 5:
                armiesForeachPlayer = fivePlayerDistribution.getArmyStrength();
                break;
        }
        return armiesForeachPlayer;

    }
    /**
     * Allocates armies to countries. Initially 1 army is assigned to each country and then in a round robin fashion,
     * the player is asked to assign remaining armies to countries that he possesses.
     *
     * @param activePlayers List containing the Player object
     */
    public void allocateArmies(ArrayList<Player> activePlayers) {


        ArrayList<Integer> remainingArmies = new ArrayList<Integer>();
        int i = 0;
        int x = 0;
        int tempArmy = 0;
        Scanner keyboard = new Scanner(System.in);
        int armiesForeachPlayer = getNumberEachPlayerArmy(activePlayers);

        for (i = 0; i < activePlayers.size(); i++) {
            activePlayers.get(i).setPlayerArmies(armiesForeachPlayer);
            remainingArmies.add(i, armiesForeachPlayer);
            int tempRemainingArmies = remainingArmies.get(i);
            for (int j = 0; j < activePlayers.get(i).getCountries().size(); j++) {
                if (tempRemainingArmies > 0) {
                    activePlayers.get(i).getCountries().get(j).setArmies(1);
                    tempRemainingArmies--;
                }
            }
            remainingArmies.set(i, tempRemainingArmies);
        }

        while (x < activePlayers.size()) {
            if (remainingArmies.get(x) == 0) {
                x++;
                if (x == (activePlayers.size()-1)) {
                    break;
                } else {
                    continue;
                }
            }
            if (x == activePlayers.size()-1) {
                x=0;
            }
            tempArmy = remainingArmies.get(x);
            if (tempArmy > 0) {
                System.out.println("Which country would you like to move your army to ? Please enter the index");
                for (int k = 0; k < activePlayers.get(x).getCountries().size(); k++) {
                    System.out.println(activePlayers.get(x).getCountries().indexOf(activePlayers.get(x).getCountries().get(k)) +
                            " " + activePlayers.get(x).getCountries().get(k).getCountryName());
                }
                int moveToCountry = keyboard.nextInt();

                if (moveToCountry >= activePlayers.get(x).getCountries().size()) {
                    System.out.println("Enter country index between 0 and " + (activePlayers.get(x).getCountries().size()-1));
                    moveToCountry = keyboard.nextInt();
                }

                System.out.println("You have " + tempArmy + "armies. How many armies do u want to move?");
                int moveArmies = keyboard.nextInt();

                if (moveArmies > tempArmy) {
                    System.out.println("You dont have that many armies. Enter a valid no of armies");
                    moveArmies = keyboard.nextInt();
                }

                activePlayers.get(x).getCountries().get(moveToCountry).setArmies(moveArmies);
                tempArmy = tempArmy - moveArmies;
            }
            remainingArmies.set(x, tempArmy);
            x++;
        }
    }

    /**
     * Initial allocation of countries to the players at the beginning of the game.
     * The country allocation happens in a round robin manner
     *
     * @param activePlayers List of players
     * @param countries     List of countries in the map
     */
    public void allocateCountries(ArrayList<Player> activePlayers, ArrayList<GameCountry> countries) {
        int i = 0;
        Collections.shuffle(countries);

        for (int k = 0; k < countries.size(); k++) {
            if (i == (activePlayers.size())) {
                i = 0;
            }
            countries.get(k).setCurrentPlayer(activePlayers.get(i));
            activePlayers.get(i).setCountries(countries.get(k));
            i++;
        }

/*		for(int j=0;j<activePlayers.size();j++) {
			for(int k=0;k<activePlayers.get(j).getCountries().size();k++)
				System.out.println(activePlayers.get(j).getName() + " " + activePlayers.get(j).getCountries().get(k).getCountryName());
		}*/
    }

    public void gameLoop() {

    }

/*    public static void main(String args[]) {

        TurnController m = new TurnController();

        ArrayList<Player> p = TestUtil.getPlayerArrayList();
        ArrayList<GameCountry> c = TestUtil.getGameCountryList();

        m.allocateCountries(p, c);
        m.allocateArmies(p);

    }*/
}
