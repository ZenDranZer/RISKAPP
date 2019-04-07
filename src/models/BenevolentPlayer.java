package models;

public class BenevolentPlayer extends Player {
    BenevolentPlayer(){
        super();
    }
    BenevolentPlayer(String name, int id, GameMap gameMap){
        super();
    }

    public GameCountry findWeakCountry(){
        GameCountry weak = this.countries.get(0);
        int leastNumberOfArmies = this.countries.get(0).getArmiesStationed();
        for (GameCountry country: this.countries) {
            if (country.getArmiesStationed() < leastNumberOfArmies){
                weak =country;
            }
        }

        return weak;
    }
}
