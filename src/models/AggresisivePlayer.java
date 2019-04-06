package models;

public class AggresisivePlayer extends Player {
    AggresisivePlayer(){
        super();
    }

    AggresisivePlayer(String name, int id, GameMap gameMap){
        super();
    }

    public GameCountry findStrongestCountry(){
        if (this.countries != null ) {
            GameCountry strongest = this.countries.get(0);
            int maxNumberOfArmies = this.countries.get(0).getArmiesStationed();
            for (GameCountry country : this.countries) {
                if (country.getArmiesStationed() > maxNumberOfArmies) {
                    strongest = country;
                }
            }
            return strongest;
        }else {
            return null;
        }
    }
}
