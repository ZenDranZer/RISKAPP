package models;

import java.util.Observable;

public class CheaterPlayer extends Player {
    CheaterPlayer(){
        super();

    }
    CheaterPlayer(String name, int id, GameMap gameMap){
        super(name,id,gameMap);
    }

    @Override
    public void reinforcement(String countryName, int armies) {
        for (GameCountry country: this.countries) {
            country.setArmies(country.getArmiesStationed() * 2);
        }

    }

    @Override
    public String attack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry, int redDice, int whiteDice) {
        String status = "";
        defender.removeCountry(defendingCountry);
        this.setCountries(defendingCountry);
        this.updateContinents();
        defendingCountry.setArmies(attackingCountry.getArmiesStationed() - 1);
        attackingCountry.removeArmies(attackingCountry.getArmiesStationed() - 1);
        status = "success";
        if (defender.countries.size() == 0) {
            this.getCardsHeld().addAll(defender.getCardsHeld());
            eliminate(defender);
            status = "eliminated";
            if (hasPlayerWon(this)) {
                status = "winner";
            }
        }
        return status;
    }

    @Override
    public void fortify(String countryToFortify, String fortifyFrom, int armies) {

        GameCountry toCountry = countries.stream().filter(cntry -> cntry.getCountryName().equals(countryToFortify))
                .findFirst().get();
        GameCountry fortifyingCountry = countries.stream().filter(cntry -> cntry.getCountryName().equals(fortifyFrom))
                .findFirst().get();

        toCountry.setArmies(toCountry.getArmiesStationed() + fortifyingCountry.getArmiesStationed()*2);
        //fortifyingCountry.setArmies(fortifyingCountry.getArmiesStationed() - armies);
    }
}
