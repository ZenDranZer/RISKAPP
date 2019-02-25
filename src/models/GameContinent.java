package models;
public class GameContinent {

    String continentName;
    int continentValue;
    String playerID;

    public GameContinent() {
        /*this.continentName = continentName;
        this.continentValue = continentValue;
        this.playerID = playerID;*/
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public int getContinentValue() {
        return continentValue;
    }

    public void setContinentValue(int continentValue) {
        this.continentValue = continentValue;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}

