package models;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class extends the player class and represents a random player
 */
public class RandomPlayer extends Player {

    /**
     * This is a constructor for the class
     */
    public RandomPlayer(){
        super();
    }

    /**
     * This is a constructor for the class with name param
     */
    public RandomPlayer(String name) {
        super();
        this.name = name;
    }

    /**
     * This is a constructor for the class with name,id, map param
     */
    public RandomPlayer(String name, int id, GameMap gameMap){
        super(name,id,gameMap);
    }


    /**
     * This method implements the attack logic for a random player
     * @return returns the status of the operation
     */
    public String attack() {
        boolean flag = true;
        GameCountry attackingCountry = new GameCountry();
        GameCountry defendingCountry = new GameCountry();
        Random randomNumberGenerator = new Random();
        while (flag) {
            int counteryIndex = randomNumberGenerator.nextInt(this.countries.size());
            attackingCountry = this.countries.get(counteryIndex);
            int defendingIndex = randomNumberGenerator.nextInt(attackingCountry.getNeighbouringCountries().size()-1);
            defendingCountry = (GameCountry) (new ArrayList(attackingCountry.getNeighbouringCountries().values()).get(defendingIndex));
/*
            defendingCountry = attackingCountry.getNeighbouringCountries().get(defendingIndex);
*/
            if(attackingCountry.getNeighbouringCountries().size()==0)
                return "No country to attack";
            if(defendingCountry==null)
                return "No attack for this random player";
            if (!(defendingCountry.getCurrentPlayer().getId()==this.getId())) {
                flag = false;
                return super.allOutAttack(defendingCountry.getCurrentPlayer(), attackingCountry, defendingCountry);
            }
        }
       return "";
    }

    /**
     * This method implements the fortification logic for the random player
     * @return returns the status of the operation
     */
    public String fortify() {
        Random randomNumberGenerator = new Random();
        int counteryIndex = randomNumberGenerator.nextInt(this.countries.size());
        int toCountryIndex = randomNumberGenerator.nextInt(this.countries.get(counteryIndex).getNeighbouringCountries().size()-1);
        int numberOfArmies = randomNumberGenerator.nextInt(this.countries.get(counteryIndex).getArmiesStationed());
        int currentArmiesToCountry = this.countries.get(toCountryIndex).getArmiesStationed();
        int currentArmiesCountry = this.countries.get(counteryIndex).getArmiesStationed();
        int ar = (currentArmiesToCountry + numberOfArmies <= 12) ? numberOfArmies-1 : currentArmiesToCountry+numberOfArmies-12;
        super.fortify(this.getCountries().get(toCountryIndex).getCountryName(),this.getCountries().get(counteryIndex).getCountryName(),ar);
        System.out.println(this.getCountries().get(toCountryIndex).getCountryName()+" gets "+ar+" armies.");
        return this.getCountries().get(toCountryIndex).getCountryName()+" gets "+ar+" armies.";
    }

    /**
     * This method implements the reinforcement logic for a random player
     * @param armies total armies for player to fortify
     * @return returns the status of the operation
     */
    public String reinforcement(int armies) {
        try {
            Random randomNumberGenerator = new Random();
            String returnString = "";
            while (armies != 0 && !this.isAllocationComplete()) {
                int counteryIndex = randomNumberGenerator.nextInt(this.countries.size());
                int armiesStationed = this.getCountries().get(counteryIndex).getArmiesStationed();
                if (armiesStationed < 12) {
                    int rein = (12 - armiesStationed) < armies ? (12 - armiesStationed) : armies;
                    super.reinforcement(this.countries.get(counteryIndex).getCountryName(), rein);
                    returnString += this.getName() + " moved " + rein + " number of armies to " + this.countries.get(counteryIndex).getCountryName() + "\n";
                    armies = armies - rein;

                }
            }
            return returnString;
        } catch (Exception e) {
            return "No attack done this time";
        }
    }

}
