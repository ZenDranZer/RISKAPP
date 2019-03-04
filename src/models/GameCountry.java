package models;
import java.util.ArrayList;
import java.util.HashMap;

public class GameCountry {


    private String countryName;

    /*public GameCountry() {
        this.neighbouringCountries = new ArrayList<>();
    }*/

    private HashMap<String,GameCountry> neighbouringCountries;
    /*
        GameContinent continent;
    */
    private GameContinent continent;
    private Player currentPlayer;
    private int armiesStationed;
    private int coordinateX;
    private int coordinateY;

    public  GameCountry(){
        continent = new GameContinent();
        neighbouringCountries = new HashMap<>();
    }
    public GameCountry(String countryName){
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName.toUpperCase();
    }

    public GameContinent getContinent() {
        return continent;
    }

    public void setContinent(GameContinent continent) {
        this.continent = continent;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getArmiesStationed() {
        return armiesStationed;
    }

    public void setArmies(int armiesStationed) {
        this.armiesStationed = armiesStationed;
    }

    public HashMap<String,GameCountry> getNeighbouringCountries() {
        return neighbouringCountries;
    }

    public void setNeighbouringCountries(GameCountry neighbouringCountry) {
        this.neighbouringCountries.put(neighbouringCountry.getCountryName(),neighbouringCountry);
    }
    public void addNeighbouringCountry(GameCountry neighbouringCountry) {
        this.neighbouringCountries.put(neighbouringCountry.getCountryName(),neighbouringCountry);
    }
    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
}
