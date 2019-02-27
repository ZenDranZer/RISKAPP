package utils;

import java.util.Random;

/**
 * Class Dice is used to simulate a dice and roll it.
 * */
public class Dice {

    /**String variable for specifying the color of the dice.*/
    private final String color;

    /**a package private constructor for initializing a dice with desired color.
     * @param  color specifies the color of the dice.*/
    Dice(String color){
        this.color = color;
    }

    /**a method which rolls a dice and provides numbers between 1 to 6.
     * @return an integer value specifying the dice top surface number.*/
    public int rollDice(){
        Random randomNumberGenerator = new Random();
        int diceNumber = randomNumberGenerator.nextInt(6);
        diceNumber = diceNumber + 1;
        return diceNumber;
    }

}
