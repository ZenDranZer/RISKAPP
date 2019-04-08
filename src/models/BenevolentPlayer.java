package models;

import java.util.ArrayList;

/**
 * This class extends the Player class and implements the operation for the benevolent player
 */
public class BenevolentPlayer extends Player {
    /**
     * COnstructor for the class
     */
    public BenevolentPlayer(){
        super();
    }
    /*
    public BenevolentPlayer(String name, int id, GameMap gameMap){
        super();
    }*/

    /**
     * Finds the weak country for the player to reinforce based on number of armies and if it can be attacked or not
     * @return returns the weakest country
     */
    public GameCountry findWeakCountry(){
        /*GameCountry weak = this.countries.get(0);
        int leastNumberOfArmies = this.countries.get(0).getArmiesStationed();
        for (GameCountry country: this.countries) {
            if (country.getArmiesStationed() < leastNumberOfArmies){
                weak =country;
            }
        }

        return weak;*/
        ArrayList<GameCountry> canBeAttack ;
        int armies=0;
        GameCountry weak = new GameCountry();
        canBeAttack = super.countriesThatCanAttack(this);
        for(GameCountry country : canBeAttack){
            for(GameCountry neighbor : country.getNeighbouringCountries().values()){
                if(neighbor.getCurrentPlayer().getId()!=this.getId()&&neighbor.getArmiesStationed()>1){
                    if(country.getArmiesStationed()<armies) {
                        armies = country.getArmiesStationed();
                        weak = country;
                    }
                }
                if(weak.getCountryName().equals(country.getCountryName()))
                    break;
            }
        }
        return weak;
    }

    /**
     * This method implements the reinforcement logic for Benevolent Player
     * @param armies Total armies for the player to reinforce
     * @return returns the status of the operation
     */
    public String reinforcement(int armies)
    {
        String returnString = "";
        GameCountry country;
        while(armies!=0){
            country = this.findWeakCountry();
            int ar = country.getArmiesStationed()+armies<=12?armies:(12-country.getArmiesStationed());
            super.reinforcement(country.getCountryName(),ar);
            armies-=ar;
            returnString+=this.getName() + " moved " + ar + " number of armies to " + country.getCountryName();
    }
        return returnString;
    }

    /**
     * The attack methodology for attack of Benevolent Player
     * @return status of the operation
     */
    public String attack(){
        return null;
    }

    /**
     * This method implements the fortification logic for Benevolent Player
     * @return returns the status of the operation
     */
    public String fortify(){
        ArrayList<GameCountry> canBeFortified = super.countriesThatCanBeFortified(this);
        GameCountry countryToBeFortified = null;
        GameCountry anotherCountry = null;
        int min = 50;
        for(GameCountry country1 : canBeFortified){
            if(country1.getArmiesStationed()<=min){
                countryToBeFortified = country1;
                for(GameCountry c : countryToBeFortified.getNeighbouringCountries().values()){
                    if(c.getArmiesStationed()>1){
                        anotherCountry = c;
                        break;
                    }
                }
            }
        }
        super.fortify(countryToBeFortified.getCountryName(),anotherCountry.getCountryName(),anotherCountry.getArmiesStationed()-1);

        return null;
    }
}
