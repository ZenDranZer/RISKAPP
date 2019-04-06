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

    public void reinforcement(){
        Random randomNumberGenerator = new Random();
        int counteryIndex = randomNumberGenerator.nextInt(this.countries.size());
        int armies = randomNumberGenerator.nextInt(12);
        placeArmy(this.countries.get(counteryIndex).getCountryName() , armies);

    }
}
