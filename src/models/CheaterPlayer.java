package models;

/**
 * This class extends the Player class and represents the CheaterPlayer
 */
public class CheaterPlayer extends Player {
    /**
     * This is the constructor for CheaterPlayer
     */
    public CheaterPlayer(){
        super();

    }
   /* public CheaterPlayer(String name, int id, GameMap gameMap){
        super(name,id,gameMap);
    }*/

    /**
     * This method implements the reinforcement logic of cheater player
     * @return returns the status of the operation
     */
    public String reinforcement() {
        for (GameCountry country: this.countries) {
            country.setArmies(country.getArmiesStationed() * 2);
        }
       return this.getName() + " doubled number of stationed armies in reinforcement phase";
    }

    /**
     * This method implements the attack logic of cheater player
     * @return returns the status of the operation
     */
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

    /**
     * This method implements the fortification logic for cheater player
     * @return returns the  status of the operation
     */
    public String  fortify() {
        for (GameCountry country: countriesThatCanAttack(this)) {
            country.setArmies(country.getArmiesStationed() *2<=12?country.getArmiesStationed() *2:12);
        }
        return this.getName() + " fortified all countries";
    }
}
