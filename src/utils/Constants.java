package utils;


import models.GameMap;

import java.awt.*;

import static java.awt.Color.*;

/**Class Constants provides the basic constants values for game like,
 * constant continent value, player colors and initial armies for
 * different game scenarios.*/
public class Constants {

    /**Constant continenet value which is used for allocating armies
     * when a player owns whole continent.*/
    public static final int CONTINENT_VALUE = 7;

    /**This enumeration gives a predefined colors for players.
     * Max player limit is 6.*/
    public enum PLAYER_COLOR {

        /**Player objects with predefined color values.*/
        player1 (RED) ,
        player2 (GREEN),
        player3 (YELLOW),
        player4 (BLUE),
        player5 (CYAN),
        player6 (MAGENTA);

        /**a final awt.Color object which holds color.*/
        private final Color color;

        /**PLAYER_COLOR constructor for initializing the Color object.
         * @param color specifies the color a player will have.*/
        PLAYER_COLOR(Color color){
            this.color = color;
        }

        /**a simple getter method to get Color property.
         * @return Color object of a player.*/
        public Color getColor() {
            return color;
        }
    }


    /**The enumeration gives initial army distribution among the player*/
    public enum ARMY_DISTRIBUTION {

        /**twoPlayerDistribution where both player will have 40 armies and
         * a neutral player which has 40 armies.*/
        twoPlayerDistribution(40),

        /**Each player will get predefined set of armies.
         * All distribution below will act same.*/
        threePlayerDistribution(35),
        fourPlayerDistribution(30),
        fivePlayerDistribution(25),
        sixPlayerDistribution(20);

        /**a final integer specifies initial army distribution.*/
        private final int armyStrength;

        /**ARMY_DISTRIBUTION constructor for specifying initial set
         * of armies each player will get.
         *@param armyStrength specifies the initial strength */
        ARMY_DISTRIBUTION(int armyStrength){
            this.armyStrength = armyStrength;
        }

        /**a getter method for reading armyStrength value.
         * @return integer which provides initial set of armies.*/
        public int getArmyStrength() {
            return armyStrength;
        }
    }

}