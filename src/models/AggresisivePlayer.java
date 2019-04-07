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
        placeArmy(findStrongestCountry(countriesThatCanAttack(this)).getCountryName(), armies);
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

            return super.allOutAttack(defendingCountry.getCurrentPlayer(), attackingCountry, defendingCountry);

    }

}