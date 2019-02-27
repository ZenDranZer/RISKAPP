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
    private String playerId;
    private String armiesStationed;
    private int coordinateX;
    private int coordinateY;
    GameContinent continent;


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /*public Continent getContinent() {
        return continent;
    }

    public void setContinent(GameContinent continent) {
        this.continent = continent;
    }
*/
    public GameCountry() {
        this.neighbouringCountries = new ArrayList<>();
    }
    public GameContinent getContinent() {
        return continent;
    }
    public String getPlayerId() {
        return playerId;
    }
    public void setContinent(GameContinent continent) {
        this.continent = continent;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
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
