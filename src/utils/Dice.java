package utils;

import org.omg.CORBA.INTERNAL;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
    public static int rollDice(){
        Random randomNumberGenerator = new Random();
        int diceNumber = randomNumberGenerator.nextInt(6);
        diceNumber = diceNumber + 1;
        return diceNumber;
    }
    public static ArrayList<HashSet<Integer>> getDiceSets(int redDice, int whiteDice){
        ArrayList<HashSet<Integer>> diceSets = new ArrayList<>();
        int setCount = redDice<=whiteDice?redDice:whiteDice;
       ArrayList<Integer> redRolls = new ArrayList<>();
       ArrayList<Integer> whiteRolls = new ArrayList<>();
        for(int i=0;i<redDice;i++){
            redRolls.add( Dice.rollDice());
        }
        for(int i=0;i<whiteDice;i++){
            whiteRolls.add( Dice.rollDice());
        }
        Collections.sort(redRolls);
        Collections.sort(whiteRolls);
        Collections.reverse(redRolls);
        Collections.reverse(whiteRolls);
        for(int i=0;i<setCount;i++){
            HashSet<Integer> diceSet = new HashSet<>();
            diceSet.add(redRolls.get(i));
            diceSet.add(whiteRolls.get(i));
            diceSets.add(diceSet);
        }
        return diceSets;
    }

}
