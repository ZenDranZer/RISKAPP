package models;
import java.util.ArrayList;


/**
 *The Player class is a model which contains player related data.
  */
public class Player {

    private String name;
    private int id;
    private int playerArmies;
    private ArrayList<GameCountry> countries;
    private ArrayList<GameContinent> continents;
    private String cardsHeld;
    private String cardExchanged;
    private int remainingArmies;

    public Player() {
        countries = new ArrayList<>();
        continents = new ArrayList<>();
    }
    public Player(String name,int id) {
        this.name = name;
        this.id = id;
        playerArmies = 0;
        countries = new ArrayList<>();
        continents = new ArrayList<>();
        cardsHeld = "";
        cardExchanged = "";

    }

    public int getRemainingArmies()
    {
    	return remainingArmies;
    }
    
    public void setRemainingArmies(int armies)
    {
    	this.remainingArmies = armies;
    }
    
    public void updateRemainingArmies(int armies)
    {
    	this.remainingArmies -=armies;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String player_name) {
        this.name = player_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerArmies() {
        return playerArmies;
    }

    public void setPlayerArmies(int player_armies) {
        this.playerArmies = player_armies;
    }

    public ArrayList<GameCountry> getCountries() {
        return countries;
    }

    public void setCountries(GameCountry country) {
        this.countries.add(country);
    }

    public ArrayList<GameContinent> getContinents() {
        return continents;
    }

    public void setContinents(GameContinent continent) {
        this.continents.add(continent);
    }

    public String getCardsHeld() {
        return cardsHeld;
    }

    public void setCardsHeld(String cards_held) {
        this.cardsHeld = cards_held;
    }

    public String getCardExchanged() {
        return cardExchanged;
    }

    public void setCardExchanged(String cardExchanged) {
        this.cardExchanged = cardExchanged;
    }
    
    public ArrayList<String> getCountryNames()
    {
    	ArrayList<String> names = new ArrayList<String>();
    	for(GameCountry country : countries)
    	{
    		names.add(country.getCountryName());
    	}
    	return names;
    }
    
    public void addPlayerArmy(int armies)
    {
    	this.playerArmies += armies;
    }
}