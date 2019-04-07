package models;

import java.util.ArrayList;

public class BenevolentPlayer extends Player {
    public BenevolentPlayer(){
        super();
    }
    public BenevolentPlayer(String name, int id, GameMap gameMap){
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
        String returnString = "";
        GameCountry country;
        while(armies!=0){
            country = this.findWeakCountry();
            int ar = country.getArmiesStationed()+armies<=12?armies:(12-country.getArmiesStationed());
            super.reinforcement(country.getCountryName(),ar);
            armies-=ar;
            returnString+=this.getName() + " moved " + ar + " number of armies to " + country.getCountryName();
    }
        return returnString;
    }
    public void attack(){

    }
    public String fortify(){
        ArrayList<GameCountry> canBeFortified = super.countriesThatCanBeFortified(this);
        GameCountry countryToBeFortified = null;
        GameCountry anotherCountry = null;
        int min = 50;
        for(GameCountry country1 : canBeFortified){
            if(country1.getArmiesStationed()<=min){
                countryToBeFortified = country1;
                for(GameCountry c : countryToBeFortified.getNeighbouringCountries().values()){
                    if(c.getArmiesStationed()>1){
                        anotherCountry = c;
                        break;
                    }
                }
            }
        }
        super.fortify(countryToBeFortified.getCountryName(),anotherCountry.getCountryName(),anotherCountry.getArmiesStationed()-1);

        return null;
    }
}
