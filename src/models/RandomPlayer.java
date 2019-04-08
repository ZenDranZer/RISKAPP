package models;

import java.util.Random;

public class RandomPlayer extends Player {
    public RandomPlayer(){
        super();
    }
    public RandomPlayer(String name, int id, GameMap gameMap){
        super();
    }


    public String attack() {
        boolean flag = true;
        GameCountry attackingCountry = new GameCountry();
        GameCountry defendingCountry = new GameCountry();
        Random randomNumberGenerator = new Random();
        while (flag) {
            int counteryIndex = randomNumberGenerator.nextInt(this.countries.size());
            attackingCountry = this.countries.get(counteryIndex);
            int defendingIndex = randomNumberGenerator.nextInt(attackingCountry.getNeighbouringCountries().size());
            defendingCountry = attackingCountry.getNeighbouringCountries().get(defendingIndex);
            if (defendingCountry.getCurrentPlayer() != this) {
                flag = false;
                return super.allOutAttack(defendingCountry.getCurrentPlayer(), attackingCountry, defendingCountry);
            }
        }
       return "";
    }

    public String fortify() {
        Random randomNumberGenerator = new Random();
        int counteryIndex = randomNumberGenerator.nextInt(this.countries.size());
        int toCountryIndex = randomNumberGenerator.nextInt(this.countries.get(counteryIndex).getNeighbouringCountries().size());
        int numberOfArmies = randomNumberGenerator.nextInt(this.countries.get(counteryIndex).getArmiesStationed());
        int currentArmiesToCountry = this.countries.get(toCountryIndex).getArmiesStationed();
        int currentArmiesCountry = this.countries.get(counteryIndex).getArmiesStationed();
        int ar = (currentArmiesToCountry + numberOfArmies <= 12) ? numberOfArmies-1 : currentArmiesToCountry+numberOfArmies-12;
        this.countries.get(toCountryIndex).setArmies(currentArmiesToCountry + ar);
        this.countries.get(counteryIndex).setArmies(currentArmiesCountry - ar);
        return null;
    }

    public String reinforcement(int armies){
        Random randomNumberGenerator = new Random();
        while(armies!=0){
        int counteryIndex = randomNumberGenerator.nextInt(this.countries.size());
        int armiesStationed = this.getCountries().get(counteryIndex).getArmiesStationed();
        if(armiesStationed<12){
            int rein =  (12-armiesStationed)<armies?(12-armiesStationed):armies;
            placeArmy(this.countries.get(counteryIndex).getCountryName() ,rein);
            armies = armies-rein;

        }
        }
        return null;
    }
}
