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
        int diceNumber = randomNumberGenerator.nextInt(attackingCountry.getNeighbouringCountries().size());
        defendingCountry = attackingCountry.getNeighbouringCountries().get(diceNumber);
        return super.attack(defender, attackingCountry, defendingCountry, redDice, whiteDice);
    }
}
