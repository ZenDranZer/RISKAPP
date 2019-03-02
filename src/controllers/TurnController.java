package controllers;

import models.*;
import utils.TestUtil;

import java.util.*;

import static utils.Constants.ARMY_DISTRIBUTION.*;

public class TurnController {

    Player activePlayer;
    private int availableArmies;
    public TurnController() {
        //TODO allocate armies
        availableArmies = 3;
    }

    public void setAvailableArmies(int armies)
    {
    	this.availableArmies = armies;
    }
    
    public int getAvailableArmies()
    {
    	return this.availableArmies;
    }
    
    public Player getActivePlayer()
    {
    	return activePlayer;
    }
    
    public void setActivePlayer(Player objPlayer)
    {
    	activePlayer = objPlayer;
    }
    
    public void nextPlayer()
    {
    	
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
        activePlayer.setRemainingArmies(availableArmies);
        return availableArmies;
    }

    //add army to a country
    public void placeArmy(Player activePlayer, String country, int armies) {

        ArrayList<GameCountry> lstPlayerArmies = activePlayer.getCountries();

        //get object from list where name matches the given object
        GameCountry matchedCountry = lstPlayerArmies.stream().filter(cntry -> cntry.getCountryName().equals(country)).findFirst().get();
        matchedCountry.setArmies(matchedCountry.getArmiesStationed() + armies);

        this.availableArmies = this.availableArmies - armies;

    }

    //reinforcement phase
    public void reinforcement() {
        // 1. number of new armies available
        // 2. while (newArmies !=0){ 1. ask user to select the country 2. place army }
    }

    /**
     * This function implements the fortification phase moving player arnies from one country to another
     * @param Countries List of game countries
     * @return returns message showing success or failure of fortification
     */
    public String fortification(List<GameCountry> Countries) {
            String moveTo = "";
            String moveFrom = "";
            int numOfArmies = 0;
            String returnMessage = "";

            GameCountry moveToCountry = MapGenerator.countryHashMap.get(moveTo);
            GameCountry moveFromCountry = MapGenerator.countryHashMap.get(moveFrom);

            if ((moveFromCountry.getArmiesStationed() - numOfArmies) > 1) {
                moveFromCountry.setArmies(moveFromCountry.getArmiesStationed() - numOfArmies);
                moveToCountry.setArmies(moveToCountry.getArmiesStationed() + numOfArmies);
                returnMessage = "Armies Moved !!!";
            } else {
                returnMessage = "Atleast one army should remain in the country !!!";
            }
            return returnMessage;
    }

    /**
     * This method allocates the initial number of armies to each player based upon the number of players in the game
     * @param activePlayers Player arraylist containing player data
     * @return
     */
    public int getEachPlayerArmy(ArrayList<Player> activePlayers) {
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
     * Assigns 1 army to each country possessed by the players
     * @param activePlayers Arraylist containing player data
     * @param remainingArmies Arraylist containing the remaining number of armies with player after initial allocation of armies
     * @param armiesForeachPlayer Number of armies assigned to the player
     */
    public ArrayList<Integer> allocateInitialArmy(ArrayList<Player> activePlayers, int armiesForeachPlayer) {
        
    	ArrayList<Integer> remainingArmies = new ArrayList<Integer>();
    	
    	for (int i = 0; i < activePlayers.size(); i++) {
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
        return remainingArmies;
    }

    public ArrayList<Integer> allocateArmiesToCountry(ArrayList<Player> activePlayers, int moveToCountry, int moveArmies, int x, int tempArmy) {

        Scanner keyboard = new Scanner(System.in);
        ArrayList<Integer> moveVariables = new ArrayList<>();
        System.out.println("Which country would you like to move your army to ? Please enter the index");
        for (int k = 0; k < activePlayers.get(x).getCountries().size(); k++) {
            System.out.println(activePlayers.get(x).getCountries().indexOf(activePlayers.get(x).getCountries().get(k)) +
                    " " + activePlayers.get(x).getCountries().get(k).getCountryName());
        }
        moveToCountry = keyboard.nextInt();

        if (moveToCountry >= activePlayers.get(x).getCountries().size()) {
            System.out.println("Enter country index between 0 and " + (activePlayers.get(x).getCountries().size()-1));
            moveToCountry = keyboard.nextInt();
        }

        System.out.println("You have " + tempArmy + " armies. How many armies do u want to move?");
        moveArmies = keyboard.nextInt();

        if (moveArmies > tempArmy) {
            System.out.println("You dont have that many armies. Enter a valid no of armies");
            moveArmies = keyboard.nextInt();
        }

        moveVariables.add(moveToCountry);
        moveVariables.add(moveArmies);
   
        return moveVariables;

       // keyboard.close();
    }

    /**
     * Allocates armies to countries. Initially 1 army is assigned to each country and then in a round robin fashion,
     * the player is asked to assign remaining armies to countries that he possesses.
     *
     * @param activePlayers List containing the Player object
     */
    public void allocateArmies(ArrayList<Player> activePlayers) {

        int i = 0;
        int x = 0;
        int tempArmy = 0;
        int moveToCountry = 0;
        int moveArmies = 0;
        ArrayList<Integer> moveVariables = new ArrayList<>();
        int armiesForeachPlayer = getEachPlayerArmy(activePlayers);

        ArrayList<Integer> remainingArmies = allocateInitialArmy(activePlayers, armiesForeachPlayer);
        int unAllocated = activePlayers.size();
        while (unAllocated >0) {

            tempArmy = remainingArmies.get(x);
            if (tempArmy > 0) {
                moveVariables = allocateArmiesToCountry(activePlayers, moveToCountry, moveArmies, x, tempArmy);
                moveToCountry = moveVariables.get(0);
                moveArmies = moveVariables.get(1);

                activePlayers.get(x).getCountries().get(moveToCountry).setArmies(moveArmies);
                tempArmy = tempArmy - moveArmies;
                remainingArmies.set(x, tempArmy);
                if(tempArmy == 0 )
                {
                	unAllocated --;
                }
            }
            x++;
            if(x >=activePlayers.size() )
            {
            	x= 0;
            }
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
    }

    public void gameLoop() {

    }

    public static void main(String args[]) {

        TurnController m = new TurnController();

        ArrayList<Player> p = TestUtil.getPlayerArrayList();
        ArrayList<GameCountry> c = TestUtil.getGameCountryList();

        m.allocateCountries(p, c);
        m.allocateArmies(p);
    }
}
