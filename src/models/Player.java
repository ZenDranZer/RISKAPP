package models;
        import java.util.ArrayList;


/**
 *The Player class is a model which contains player related data.
  */
public class Player {

    private String name;
    private String id;
    private String player_armies;
    private ArrayList<GameCountry> countries;
    private ArrayList<GameContinent> continents;
    private String cards_held;
    private String cardExchanged;

    public Player() {}
    public Player(String name,String id) {
        this.name = name;
        this.id = id;
        player_armies = "";
        countries = new ArrayList<>();
        continents = new ArrayList<>();
        cards_held = "";
        cardExchanged = "";

    }
    public String getPlayer_name() {
        return name;
    }

    public void setPlayer_name(String player_name) {
        this.name = player_name;
    }

    public String getPlayer_id() {
        return id;
    }

    public void setPlayer_id(String player_id) {
        this.id = player_id;
    }

    public String getPlayer_armies() {
        return player_armies;
    }

    public void setPlayer_armies(String player_armies) {
        this.player_armies = player_armies;
    }

    public ArrayList<GameCountry> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<GameCountry> countries) {
        this.countries = countries;
    }

    public ArrayList<GameContinent> getContinents() {
        return continents;
    }

    public void setContinents(ArrayList<GameContinent> continents) {
        this.continents = continents;
    }

    public String getCards_held() {
        return cards_held;
    }

    public void setCards_held(String cards_held) {
        this.cards_held = cards_held;
    }

    public String getCardExchanged() {
        return cardExchanged;
    }

    public void setCardExchanged(String cardExchanged) {
        this.cardExchanged = cardExchanged;
    }


}