package models;
import java.util.ArrayList;

public class GameCountry {


    String countryName;

    /*public GameCountry() {
        this.neighbouringCountries = new ArrayList<>();
    }*/

    ArrayList<GameCountry> neighbouringCountries;
    /*
        GameContinent continent;
    */
    String playerId;
    String armiesStationed;
    String coordinateX;
    String coordinateY;

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
    public String getPlayerId() {
        return playerId;
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
    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }
}
