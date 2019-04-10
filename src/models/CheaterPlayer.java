package models;

/**
 * This class extends the Player class and represents the CheaterPlayer
 */
public class CheaterPlayer extends Player {
    /**
     * This is the constructor for CheaterPlayer
     */
    public CheaterPlayer() {
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
    public String reinforcement(int armies) {
        for (GameCountry country: this.countries) {
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
        for (GameCountry country1: countriesThatCanAttack(this)) {
            for(GameCountry country:country1.getNeighbouringCountries().values()){
                if(country.getCurrentPlayer().getId()==this.getId())
                    continue;
            status = "success|";
        country.getCurrentPlayer().removeCountry(country);
            System.out.println("Country conquered: "+country.getCountryName());
            status+="Country conquered: "+country.getCountryName();
        this.setCountries(country);

        this.updateContinents();

            System.out.println(status);
        if (country.getCurrentPlayer().countries.size() == 0) {
            this.getCardsHeld().addAll(country.getCurrentPlayer().getCardsHeld());
            status += "Player: "+country.getCurrentPlayer().getId()+" eliminated\n";
            eliminate(country.getCurrentPlayer());
            country.setCurrentPlayer(this);
            if (hasPlayerWon(this)) {
                String status1 = "winner"+status;
                System.out.println(status);
                System.out.println(status);
                return status;
            }
        }
        country.setCurrentPlayer(this);
        }
        }
        System.out.println(status);
        return status ;
    }

    /**
     * This method implements the fortification logic for cheater player
     * @return returns the  status of the operation
     */
    public String fortify() {
        for (GameCountry country: countriesThatCanAttack(this)) {
            country.setArmies(country.getArmiesStationed() *2<=12?country.getArmiesStationed() *2:12);
        }
        return this.getName() + " fortified all countries that can be attacked";
    }

}
