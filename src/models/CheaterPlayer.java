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

    public CheaterPlayer(String name) {
        super();
        this.setName(name);
    }

   public CheaterPlayer(String name, int id, GameMap gameMap){
        super(name,id,gameMap);
    }

    /**
     * This method implements the reinforcement logic of cheater player
     * @return returns the status of the operation
     */
    public String reinforcement() {
        for (GameCountry country: this.countries) {
/*
            country.setArmies((country.getArmiesStationed() * 2)<=12?(country.getArmiesStationed() * 2):12);
*/
            super.reinforcement(country.getCountryName(),(country.getArmiesStationed() * 2)<=12?(country.getArmiesStationed() * 2):12);
        }
       return this.getName() + " Maxed the armies or doubled number of stationed armies on all it's countries in reinforcement phase";
    }

    /**
     * This method implements the attack logic of cheater player
     * @return returns the status of the operation
     */
    public String attack() {
        String status = "";
        for (GameCountry country: countriesThatCanAttack(this)) {

        country.getCurrentPlayer().removeCountry(country);
            System.out.println("Country conquered: "+country.getCountryName());
        this.setCountries(country);

        this.updateContinents();
        status = "success|";
            System.out.println(status);
        if (country.getCurrentPlayer().countries.size() == 0) {
            this.getCardsHeld().addAll(country.getCurrentPlayer().getCardsHeld());
            status += "Player: "+country.getCurrentPlayer().getId()+" eliminated\n";
            eliminate(country.getCurrentPlayer());
            country.setCurrentPlayer(this);
            if (hasPlayerWon(this)) {
                status += "winner";
                System.out.println(status);
                System.out.println(status);
                return status;
            }
        }
        country.setCurrentPlayer(this);
        }
        System.out.println(status);
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
        return this.getName() + " fortified all countries that can be attacked";
    }

}
