package models;

import java.util.ArrayList;

public class BenevolentPlayer extends Player {
    BenevolentPlayer(){
        super();
    }
    BenevolentPlayer(String name, int id, GameMap gameMap){
        super();
    }

    public GameCountry findWeakCountry(){
        /*GameCountry weak = this.countries.get(0);
        int leastNumberOfArmies = this.countries.get(0).getArmiesStationed();
        for (GameCountry country: this.countries) {
            if (country.getArmiesStationed() < leastNumberOfArmies){
                weak =country;
            }
        }

        return weak;*/
        ArrayList<GameCountry> canBeAttack ;
        int armies=0;
        GameCountry weak = new GameCountry();
        canBeAttack = super.countriesThatCanAttack(this);
        for(GameCountry country : canBeAttack){
            for(GameCountry neighbor : country.getNeighbouringCountries().values()){
                if(neighbor.getCurrentPlayer().getId()!=this.getId()&&neighbor.getArmiesStationed()>1){
                    if(country.getArmiesStationed()<armies) {
                        armies = country.getArmiesStationed();
                        weak = country;
                    }
                }
                if(weak.getCountryName().equals(country.getCountryName()))
                    break;
            }
        }
        return weak;
    }

    public String reinforcement(int armies)
    {
        super.reinforcement(this.findWeakCountry().getCountryName(),armies);
        return this.getName() + " moved " + armies + " number of armies to " + findWeakCountry().getCountryName();
    }
}
