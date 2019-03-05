package models;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the Country objects used in the Game
 */
public class GameCountry {

    private String countryName;
    private HashMap<String,GameCountry> neighbouringCountries;
    private GameContinent continent;
    private Player currentPlayer;
    private int armiesStationed;
    private int coordinateX;
    private int coordinateY;

    public  GameCountry(){
        continent = new GameContinent();
        neighbouringCountries = new HashMap<>();
    }
    
    /**
     * Creates a country with the specified name 
     * @param countryName String that represents the Country name
     */
    public GameCountry(String countryName){
        this.countryName = countryName;
    }

    /**
     * Gets the name of the country
     * @return String that represents the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the country name
     * @param countryName String that represents the country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets the GameContinent on which the country is present
     * @return GameContinent that represents the continent on which country is present
     */
    public GameContinent getContinent() {
        return continent;
    }

    /**
     * Sets the continent on which the country should be present
     * @param continent GameContinent which represents the continent 
     */
    public void setContinent(GameContinent continent) {
        this.continent = continent;
    }

    /**
     * Gets the player currently owning the country
     * @return Player that owns the country
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the player who owns the country
     * @param currentPlayer Player who should own the country
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    /**
     * Gets the armies stationed on the country
     * @return integer that represents the armies stationed
     */
    public int getArmiesStationed() {
        return armiesStationed;
    }

    /**
     * Sets the armies to be stationed on the country
     * @param armiesStationed Integer that represents the armies to be stationed
     */
    public void setArmies(int armiesStationed) {
        this.armiesStationed = armiesStationed;
    }

    /**
     * Gets the map of neighboring countries
     * @return HashMap that represents the neighboring countries
     */
    public HashMap<String,GameCountry> getNeighbouringCountries() {
        return neighbouringCountries;
    }

    /**
     * Sets the neighboring countries
     * @param neighbouringCountry GameCountry that has to be added to neighboring countries
     */
    public void setNeighbouringCountries(GameCountry neighbouringCountry) {
        this.neighbouringCountries.put(neighbouringCountry.getCountryName(),neighbouringCountry);
    }
    
    /**
     * Adds the neighboring countries
     * @param neighbouringCountry GameCountry that has to be added to neighboring countries
     */
    public void addNeighbouringCountry(GameCountry neighbouringCountry) {
        this.neighbouringCountries.put(neighbouringCountry.getCountryName(),neighbouringCountry);
    }
    
    /**
     * X Coordinate of the country
     * @return x coordinate
     */
    public int getCoordinateX() {
        return coordinateX;
    }

    /**
     * sets the X coordinate of the country
     * @param coordinateX x-coordinate
     */
    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    /**
     * Y coordinate of the country
     * @return Y coordinate 
     */
    public int getCoordinateY() {
        return coordinateY;
    }

    /**
     * Sets the Y coordinate of the country 
     * @param coordinateY Y coordinate
     */
    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
}
