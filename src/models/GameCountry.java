package models;
import java.util.ArrayList;

public class GameCountry {


    private String countryName;

    /*public GameCountry() {
        this.neighbouringCountries = new ArrayList<>();
    }*/

    private ArrayList<GameCountry> neighbouringCountries;
    /*
        GameContinent continent;
    */
    private GameContinent continent;
    private Player currentPlayer;
    private String armiesStationed;
    private int coordinateX;
    private int coordinateY;

    public  GameCountry(){
        continent = new GameContinent();
    }
    public GameCountry(String countryName){
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getArmiesStationed() {
        return armiesStationed;
    }

    public void setArmies(String armiesStationed) {
        this.armiesStationed = armiesStationed;
    }

    public ArrayList<GameCountry> getNeighbouringCountries() {
        return neighbouringCountries;
    }

    public void setNeighbouringCountries(ArrayList<GameCountry> neighbouringCountries) {
        this.neighbouringCountries = neighbouringCountries;
    }
    public void addNeighbouringCountry(GameCountry neighbouringCountry) {
        this.neighbouringCountries.add(neighbouringCountry);
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
