package models;

import java.util.Random;

public class RandomPlayer extends Player {
    RandomPlayer(){
        super();
    }
    RandomPlayer(String name, int id, GameMap gameMap){
        super();
    }

    @Override
    public String attack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry, int redDice, int whiteDice) {
        Random randomNumberGenerator = new Random();
        int counteryIndex = randomNumberGenerator.nextInt(attackingCountry.getNeighbouringCountries().size());
        defendingCountry = attackingCountry.getNeighbouringCountries().get(counteryIndex);
        //int numberOfAttacks =
        return super.attack(defender, attackingCountry, defendingCountry, redDice, whiteDice);
    }

    public void fortify() {
        Random randomNumberGenerator = new Random();
        int counteryIndex = randomNumberGenerator.nextInt(this.countries.size());
        int toCountryIndex = randomNumberGenerator.nextInt(this.countries.size());
        int numberOfArmies = randomNumberGenerator.nextInt(this.countries.get(counteryIndex).getArmiesStationed());
        int currentArmiesToCountry = this.countries.get(toCountryIndex).getArmiesStationed();
        int currentArmiesCountry = this.countries.get(counteryIndex).getArmiesStationed();
        this.countries.get(toCountryIndex).setArmies(currentArmiesToCountry + numberOfArmies);
        this.countries.get(counteryIndex).setArmies(currentArmiesCountry - numberOfArmies);
    }

    public void reinforcement(int armies){
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


    }
}
