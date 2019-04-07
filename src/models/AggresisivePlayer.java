package models;

import java.util.ArrayList;

public class AggresisivePlayer extends Player {
    AggresisivePlayer(){
        super();
    }

    AggresisivePlayer(String name, int id, GameMap gameMap){
        super();
    }

    public GameCountry findStrongestCountry(ArrayList<GameCountry> countriesThatCanAttack){
        if (countriesThatCanAttack != null ) {
            GameCountry strongest = countriesThatCanAttack.get(0);
            int maxNumberOfArmies = countriesThatCanAttack.get(0).getArmiesStationed();
            for (GameCountry country : countriesThatCanAttack) {
                if (country.getArmiesStationed() > maxNumberOfArmies) {
                    strongest = country;
                }
            }
            return strongest;
        }else {
            return null;
        }
    }

    @Override
    public String attack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry, int redDice, int whiteDice) {
        //attackingCountry = this.findStrongestCountry();

            return super.attack(defender, attackingCountry, defendingCountry, redDice, whiteDice);

    }
}
