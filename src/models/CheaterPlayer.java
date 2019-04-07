package models;

import java.util.Observable;

public class CheaterPlayer extends Player {
    CheaterPlayer(){
        super();

    }
    CheaterPlayer(String name, int id, GameMap gameMap){
        super(name,id,gameMap);
    }


    public String reinforcement() {
        for (GameCountry country: this.countries) {
            country.setArmies(country.getArmiesStationed() * 2);
        }
       return this.getName() + " doubled number of stationed armies";
    }

    public String attack() {
        String status = "";
        for (GameCountry country: countriesThatCanAttack(this)) {

        country.getCurrentPlayer().removeCountry(country);
        this.setCountries(country);

        this.updateContinents();
        status = "success";
        if (country.getCurrentPlayer().countries.size() == 0) {
            this.getCardsHeld().addAll(country.getCurrentPlayer().getCardsHeld());
            eliminate(country.getCurrentPlayer());
            status = "eliminated";
            if (hasPlayerWon(this)) {
                status = "winner";
            }
        }
        country.setCurrentPlayer(this);
        }
        return status ;
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
