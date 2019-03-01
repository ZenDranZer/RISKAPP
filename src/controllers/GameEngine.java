package controllers;
 import java.io.IOException;
import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;
 import models.*;


public class GameEngine {

    ArrayList<Player> listActivePlayers = new ArrayList<Player>();
    ArrayList<Player> listEliminatedPlayers = new ArrayList<Player>();
    MapGenerator mapGenerator = new MapGenerator();

    String mapPath;
    TurnController turn;
    //object initialization
    public GameEngine(){
    	turn = new TurnController();
    }

    public TurnController getTurnComtroller()
    {
    	if(turn == null)
    	{
    		turn = new TurnController();
    	}
    	return turn;
    }
    public MapGenerator getMapGenerator(){
        return mapGenerator;
    }
    public void getPlayerInfo(ArrayList<Player> activePlayers) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter number of players");
        int noOfPlayers = keyboard.nextInt();
        if (noOfPlayers < 2) {
            System.out.println("Minimum number of players is 2");
        } else if (noOfPlayers > 5) {
            System.out.println("Maximum number of players is 5");
        } else {
            for (int i = 1; i <= noOfPlayers; i++) {
                Player p = new Player();
                System.out.println("Enter player name " + i + "\n");
                p.setId(i);
                p.setName(keyboard.next());
                listActivePlayers.add(p);
            }
        }
        keyboard.close();
    }

    /**
     * This method takes in the number of players and their names
     *
     * @return: list of player objects
     */
    public ArrayList<Player> getInitialPlayers() {

        try {
            int i = 0;
            getPlayerInfo(listActivePlayers);
        } catch (Exception ex) {
            System.out.println("Error getting player data");
        }
        return listActivePlayers;
    }

    public void setListActivePlayers(ArrayList<String> listActivePlayers) {
        int i=1;
        for(String name : listActivePlayers){
            Player player = new Player(name,i);
            this.listActivePlayers.add(player);
            i++;
        }
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
    
    public void initialise(ArrayList<String> lstPlayerNames, String mapPath) throws IOException
    {
    	setListActivePlayers(lstPlayerNames);
    	MapGenerator mapGenerator = new MapGenerator();
    	mapGenerator.readConquestFile(mapPath);
    	turn.allocateCountries(listActivePlayers, new ArrayList<GameCountry>(MapGenerator.countryHashMap.values()));
    	
    	//turn.allocateArmies(listActivePlayers);
    	turn.setActivePlayer(listActivePlayers.get(0));
    	for(Player pl : listActivePlayers)
    	{
    		System.out.println(pl.getCountries().size());
    	}
    }

}