package models;

import java.util.ArrayList;

public class AggresisivePlayer extends Player {
    AggresisivePlayer(){
        super();
    }

    AggresisivePlayer(String name, int id, GameMap gameMap){
        super();
    }

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
    public ArrayList<GameCountry> countriesThatCanAttack(Player player){
        ArrayList<GameCountry> canAttack = new ArrayList<>();
        for(GameCountry country : player.getCountries()){
            for(GameCountry neighbour : country.getNeighbouringCountries().values()){
                if(neighbour.getCurrentPlayer().getId()!=player.getId()){
                    canAttack.add(neighbour);
                }
            }
        }
        return canAttack;
    }
    public void reinforcement(int armies)
    {
        super.reinforcement(findStrongestCountry(countriesThatCanAttack(this)).getCountryName(),armies);
    }

    public GameCountry findWeakestNeighbor(){
        GameCountry weakest = new ArrayList<GameCountry>(this.countries.get(0).getNeighbouringCountries().values()).get(0);
        for (GameCountry playerCountries: this.countries) {
            for (GameCountry neighbor : playerCountries.getNeighbouringCountries().values()) {
                if (neighbor.getArmiesStationed() < weakest.getArmiesStationed()){
                    weakest = neighbor;
                }
            }
        }
        return weakest;
    }

    public String attack() {
        GameCountry attackingCountry = this.findStrongestCountry(countriesThatCanAttack(this));
        GameCountry defendingCountry = this.findWeakestNeighbor();
            String status = super.allOutAttack(defendingCountry.getCurrentPlayer(), attackingCountry, defendingCountry);
            return status + " attack by "+ this.getName()+" to " +  defendingCountry.getCurrentPlayer().getName();


    }
    public String fortify(){
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