package models;

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
       return this.getName() + " doubled number of stationed armies in reinforcement phase";
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

    public String  fortify() {
        for (GameCountry country: countriesThatCanAttack(this)) {
            country.setArmies(country.getArmiesStationed() *2<=12?country.getArmiesStationed() *2:12);
        }
        return this.getName() + " fortified all countries";
    }
}
