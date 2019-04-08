package models;

import java.util.ArrayList;

public class AggressivePlayer extends Player {
    /**
     * This is a constructor without any parameters for creating object of aggressive Player
     *
     */
    public AggressivePlayer(){
        super();
    }
/*
    *//**The parameterised constructor for aggressivePlayer class instantiation
     *
     * @param name Name of aggressive player
     * @param id the Id of the player
     * @param gameMap
     *//*
    public AggressivePlayer(String name, int id, GameMap gameMap){
        super();
    }*/

    /**
     * This country finds the strongest country from the list of countries passed to it based on number of armies and attack ability
     * @param countriesThatCanAttack the arraylist from which the country is desired
     * @return Returns the strongest country
     */
    public GameCountry findStrongestCountry(ArrayList<GameCountry> countriesThatCanAttack){
        if (countriesThatCanAttack != null ) {
            GameCountry strongest = countriesThatCanAttack.get(0);
            int maxNumberOfArmies = countriesThatCanAttack.get(0).getArmiesStationed();
            for (GameCountry country : countriesThatCanAttack) {
                if (country.getArmiesStationed() > maxNumberOfArmies) {
                    strongest = country;
                }
            }
            return strongest;
        }else {
            return null;
        }
    }
   /* public ArrayList<GameCountry> countriesThatCanAttack(Player player){
        ArrayList<GameCountry> canAttack = new ArrayList<>();
        for(GameCountry country : player.getCountries()){
            for(GameCountry neighbour : country.getNeighbouringCountries().values()){
                if(neighbour.getCurrentPlayer().getId()!=player.getId()){
                    canAttack.add(neighbour);
                }
            }
        }
        return canAttack;
    }*/

    /**
     * This method performs reinforcement for Aggressive player
     * @param armies The total armies to be reinforced
     * @return returns the status of the operation
     */
    public String reinforcement(int armies)
    {
        String resultString="";
        GameCountry country;
       while(armies!=0){
           int ar = findStrongestCountry(super.countriesThatCanAttack(this)).getArmiesStationed()+armies<=12?armies:12-findStrongestCountry(super.countriesThatCanAttack(this)).getArmiesStationed();
           country =  findStrongestCountry(super.countriesThatCanAttack(this));
           super.reinforcement(country.getCountryName(),ar);
            armies-=ar;
            resultString+=this.getName() + " moved " + armies + " number of armies to " + country.getCountryName();

       }
       return resultString;
    }

    /**
     * This method finds the weakest neighbour to be attacked by the Aggressive player
     * @return The weakest neighboring country
     */
    public GameCountry findWeakestNeighbor(){
        GameCountry weakest = new ArrayList<>(this.countries.get(0).getNeighbouringCountries().values()).get(0);
        for (GameCountry playerCountries: this.countries) {
            for (GameCountry neighbor : playerCountries.getNeighbouringCountries().values()) {
                if (neighbor.getArmiesStationed() < weakest.getArmiesStationed()){
                    weakest = neighbor;
                }
            }
        }
        return weakest;
    }

    /**
     * This method implements attack functionality for the Aggressive Player
     * @return Returns the status of the operation
     */
    public String attack() {
        GameCountry attackingCountry = this.findStrongestCountry(super.countriesThatCanAttack(this));
        GameCountry defendingCountry = this.findWeakestNeighbor();
            String status = super.allOutAttack(defendingCountry.getCurrentPlayer(), attackingCountry, defendingCountry);
            return status + ", attack by "+ this.getName()+" to " +  defendingCountry.getCurrentPlayer().getName();
    }

    /**
     * This method implements fortify functionality for the aggressive player
     * @return Returns the status of the operation
     */
    public String fortify() {
        ArrayList<GameCountry> toFortify = bestCountryToFortify();
        super.fortify(toFortify.get(0).getCountryName(),toFortify.get(1).getCountryName(),((toFortify.get(1).getArmiesStationed())-1));
        return this.getName() + " fortified " + ((toFortify.get(1).getArmiesStationed())-1) + " armies from " + toFortify.get(0).getCountryName()
                + " to "+ toFortify.get(1).getCountryName();
    }
    public ArrayList<GameCountry> bestCountryToFortify(){
        ArrayList<GameCountry> toFortify = new ArrayList<>();
        GameCountry bestCountry = null;
        GameCountry bestNeighbour = null;
        int max = 0;
        for(GameCountry country : this.getCountries()){
            for(GameCountry neighbor : country.getNeighbouringCountries().values()){
                if(neighbor.getCurrentPlayer().getId()==this.getId()){
                    if(country.getArmiesStationed()+neighbor.getArmiesStationed()-1 > max){
                        bestCountry = country;
                        bestNeighbour = neighbor;
                    }
                }
            }
        }
        toFortify.clear();
        toFortify.add(bestCountry);
        toFortify.add(bestNeighbour);
        return toFortify;
    }

}