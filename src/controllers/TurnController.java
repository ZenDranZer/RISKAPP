package controllers;
import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurnController {

	Player activePlayer;
	int availableArmies;
	
	public TurnController(){
		//TODO allocate armies
		availableArmies =3;
	}

	//calculate armies to add at the beginning of each turn
	public int calculateNewArmies(Player activePlayer){	
		//3 is the minimum of number of armies each turn
		
		int countryCount = activePlayer.getCountries().size();
		
		availableArmies = 3;
		if(countryCount/3 >3)
			availableArmies = countryCount/3;
		
		//continent logic
		for(GameContinent continent : activePlayer.getContinents())
		{
			availableArmies = continent.getContinentValue();
		}
		return availableArmies;
	}

	//add army to a country
	public void placeArmy(Player activePlayer, GameCountry country, int armies){
		
		ArrayList<GameCountry> lstPlayerArmies = activePlayer.getCountries();
		
		//get object from list where name matches the given object 
		GameCountry res = lstPlayerArmies.stream().filter(cntry -> cntry.getCountryName().equals(country.getCountryName())).findFirst().get();
		res.setArmies(res.getArmiesStationed() + armies);
		
		this.availableArmies = this.availableArmies - armies;
		
	}

	//reinforcement phase
	public void reinforcement(){
		// 1. number of new armies available
		// 2. while (newArmies !=0){ 1. ask user to select the country 2. place army }
	}

	public void fortification(List<GameCountry> lstCountries){  // TODO: Graph object representing the entire map
		// 1. select player country
		// 2. check for connected player countries
		// 3. if available, move as many armies you want
		// all countries should have atleast one army left
	}


	//allocate initial set of armies
	public void allocateArmies(ArrayList<Player> activePlayers){
		//Allocate atleast one army to each country
		// user allocates the remaining armies
	}

	  //initial allocation of countries
    // preferably in round robin fashion
    public void allocateCountries(ArrayList<Player> activePlayers, ArrayList<GameCountry> countries){

    }
    
	public void gameLoop(){

	}
}
