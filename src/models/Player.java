package models;
        import java.util.ArrayList;


/**
 *The Player class is a model which contains player related data.
  */
public class Player {

    private String name;
    private String id;
    private int playerArmies;
    private ArrayList<GameCountry> countries;
    private ArrayList<GameContinent> continents;
    private String cardsHeld;
    private String cardExchanged;

    public Player() {
        countries = new ArrayList<>();
    }
    public Player(String name,String id) {
        this.name = name;
        this.id = id;
        playerArmies = 0;
        countries = new ArrayList<>();
        continents = new ArrayList<>();
        cardsHeld = "";
        cardExchanged = "";

    }
    public String getName() {
        return name;
    }

    public void setName(String player_name) {
        this.name = player_name;
    }

    public String getPlayerId() {
        return id;
    }

    public void setPlayerId(String player_id) {
        this.id = player_id;
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

    public void setContinents(ArrayList<GameContinent> continents) {
        this.continents = continents;
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


}