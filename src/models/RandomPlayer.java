package models;

public class RandomPlayer extends Player {
    RandomPlayer(){
        super();
    }
    RandomPlayer(String name, int id, GameMap gameMap){
        super();
    }

    @Override
    public String attack(Player defender, GameCountry attackingCountry, GameCountry defendingCountry, int redDice, int whiteDice) {
        int randomIndex = (int) Math.random();
        
        return super.attack(defender, attackingCountry, defendingCountry, redDice, whiteDice);
    }
}
