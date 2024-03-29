package models;

import java.util.ArrayList;

/**
 * This class extends the Player class and implements the operation for the benevolent player
 */
public class BenevolentPlayer extends Player {
    /**
     * Constructor for the class
     */
    public BenevolentPlayer(){
        super();
    }

    /**
     * Constructor for the class with name param
     */
    public BenevolentPlayer(String name) {
        super();
        this.setName(name);
    }

    /**
     * Constructor for the class with name,id, and game map param
     */
    public BenevolentPlayer(String name, int id, GameMap gameMap){
        super(name,id,gameMap);
    }

    /**
     * Finds the weak country for the player to reinforce based on number of armies and if it can be attacked or not
     * @return returns the weakest country
     */
    public GameCountry findWeakCountry() {

        ArrayList<GameCountry> canBeAttack ;
        int armies=12;
        GameCountry weak = new GameCountry();
        canBeAttack = super.countriesThatCanAttack(this);
        if(canBeAttack.size()==0) {
            System.out.println("No country has any neighbour owned by opposition(player might have won)");
            return null;
        }
        for(GameCountry country : canBeAttack) {
            if(country.getArmiesStationed()<=armies) {
                armies = country.getArmiesStationed();
                weak = country;
            }
        }
        return weak;
    }

    /**
     * This method implements the reinforcement logic for Benevolent Player
     * @param armies Total armies for the player to reinforce
     * @return returns the status of the operation
     */
    public String reinforcement(int armies) {
        String returnString = "";
        GameCountry country;
        while(armies!=0&&!this.isAllocationComplete()) {
            country = this.findWeakCountry();
            if(country==null){
                System.out.println("There is no weak country(got null from countries that can attack)(check if the player has won.)");
                return "There is no weak country(got null from countries that can attack)(check if the player has won.)";
            }
            int ar = country.getArmiesStationed()+armies<=12?armies:(12-country.getArmiesStationed());
            if(ar!=0) {
                super.reinforcement(country.getCountryName(), ar);
                armies -= ar;
                returnString += this.getName() + " moved " + ar + " number of armies to " + country.getCountryName() + "\n";
                System.out.println(this.getName() + " moved " + ar + " number of armies to " + country.getCountryName());
            }
        }
        return returnString;
    }

    /**
     * The attack methodology for attack of Benevolent Player
     * @return status of the operation
     */
    public String attack() {
        System.out.println("This attack does nothing");
        return "This attack does nothing";
    }

    /**
     * This method implements the fortification logic for Benevolent Player
     * @return returns the status of the operation
     */
    public String fortify() {
        ArrayList<GameCountry> canBeFortified = super.countriesThatCanBeFortified(this);
        if(canBeFortified.size()==0) {
            System.out.println("No country have any friendly neighbor to help in fortification");
            return "No country have any friendly neighbor to help in fortification";
        }
        GameCountry countryToBeFortified = null;
        GameCountry anotherCountry = null;
        int min = 12;
        for(GameCountry country1 : canBeFortified) {
            if(country1.getArmiesStationed()<=min) {
                countryToBeFortified = country1;
                for(GameCountry c : countryToBeFortified.getNeighbouringCountries().values()) {
                    if(c.getCurrentPlayer().getId()==this.getId()&&c.getArmiesStationed()>1) {
                        anotherCountry = c;
                        break;
                    }
                }
            }
        }
        if(countryToBeFortified==null) {
            return "No country can be fortified";
        }
        int toAdd = 12-countryToBeFortified.getArmiesStationed();
        toAdd = toAdd<=anotherCountry.getArmiesStationed()-1?toAdd:anotherCountry.getArmiesStationed()-1;
        super.fortify(countryToBeFortified.getCountryName(),anotherCountry.getCountryName(),toAdd);
        System.out.println(countryToBeFortified.getCountryName()+" gets "+toAdd+" armies from country: "+anotherCountry.getCountryName());
        return countryToBeFortified.getCountryName()+" gets "+toAdd+" armies from country: "+anotherCountry.getCountryName();
    }
}
