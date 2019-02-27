package controllers;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;
 import models.*;


public class GameEngine {

    ArrayList<Player> listActivePlayers = new ArrayList<Player>();
    ArrayList<Player> listEliminatedPlayers = new ArrayList<Player>();

    //object initialization
    public GameEngine(){
    }

    /**
     * This method takes in the number of players and their names
     *
     * @return: list of player objects
     */
    public List<Player> getInitialPlayers() {

        try {
            int i = 0;
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter number of players");
            int noOfPlayers = keyboard.nextInt();

            if (noOfPlayers < 2) {
                System.out.println("Minimum number of players is 2");
            } else if (noOfPlayers > 5) {
                System.out.println("Maximum number of players is 5");
            } else {
                for (i = 1; i <= noOfPlayers; i++) {
                    Player p = new Player();
                    System.out.println("Enter player name " + i + "\n");
                    p.setPlayerId(Integer.toString(i));
                    p.setName(keyboard.next());
                    listActivePlayers.add(p);
                }
            }
            keyboard.close();
        } catch (Exception ex) {
            System.out.println("Error getting player data");
        }
        return listActivePlayers;
    }


    //should return list of player objects
    //GUI
    public List<Player> getInitialPlayers(int numberOfplayers){
        return new ArrayList<Player>();
    }

    //initial allocation of countries
    // preferably in round robin fashion
    public void allocateCountries(ArrayList<Player> activePlayers, ArrayList<GameCountry> countries){

    }


    public void selectMap()	{

    }

    public void initialise() {
        getInitialPlayers();
    }
}