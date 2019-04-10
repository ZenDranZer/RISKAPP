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

        public AggressivePlayer(String name) {
            super();
            this.setName(name);
        }
        /**The parameterised constructor for aggressivePlayer class instantiation
         *
         * @param name Name of aggressive player
         * @param id the Id of the player
         * @param gameMap
         */
        public AggressivePlayer(String name, int id, GameMap gameMap){
            super(name,id,gameMap);
        }

        /**
         * This country finds the strongest country from the list of countries passed to it based on number of armies and attack ability
         * @param countriesThatCanAttack the arraylist from which the country is desired
         * @return Returns the strongest country
         */
        public GameCountry findStrongestCountry(ArrayList<GameCountry> countriesThatCanAttack) {
            if (countriesThatCanAttack != null && countriesThatCanAttack.size()>0 ) {
                GameCountry strongest = countriesThatCanAttack.get(0);
                int maxNumberOfArmies = countriesThatCanAttack.get(0).getArmiesStationed();
                for (GameCountry country : countriesThatCanAttack) {
                    if (country.getArmiesStationed() > maxNumberOfArmies) {
                        strongest = country;
                    }
                }
                System.out.println("");
                return strongest;
            }else {
                return null;
            }
        }

        /**
         * This method performs reinforcement for Aggressive player
         * @param armies The total armies to be reinforced
         * @return returns the status of the operation
         */
        public String reinforcement(int armies) {
            String resultString="";
            GameCountry country;
            country =  findStrongestCountry(this.countriesThatCanAttack());
            if(country==null) {
                return "The player might have won";
            }
           while(armies!=0 && !this.isAllocationComplete()) {
               int ar = country.getArmiesStationed()+armies<=12?armies:12-country.getArmiesStationed();
               if(ar!=0) {
                   super.reinforcement(country.getCountryName(),ar);
                   armies-=ar;
                   resultString+=this.getName() + " moved " + ar + " number of armies to " + country.getCountryName()+"\n";
                   System.out.println(this.getName() + " moved " + ar + " number of armies to " + country.getCountryName());
               }
                int index = this.getCountries().indexOf(country);
                index++;
                if(index==this.getCountries().size()) {
                    index=0;
                }
                country = this.getCountries().get(index);
           }
           return resultString;
        }

        /**
         * This method finds the weakest neighbour to be attacked by the Aggressive player
         * @return The weakest neighboring country
         */
        public GameCountry findWeakestNeighbor(GameCountry attackingCountry) {
            GameCountry weakest = new GameCountry();
            int ar = 12;
            for(GameCountry neig:attackingCountry.getNeighbouringCountries().values()) {
                if(neig.getCurrentPlayer().getId()!=this.getId()) {
                    if(neig.getArmiesStationed()<=ar) {
                        ar = neig.getArmiesStationed();
                        weakest = neig;
                    }
                }
            }
            return weakest;
        }

        /**
         * Evaluates countries that can be attacked
         * @return List of countries that can be attacked
         */
        public ArrayList<GameCountry> countriesThatCanAttack() {
            ArrayList<GameCountry> canAttack = new ArrayList<>();
            for(GameCountry country : this.getCountries()) {
                for(GameCountry neighbour : country.getNeighbouringCountries().values()) {
                    if(neighbour.getCurrentPlayer().getId()!=this.getId()) {
                        canAttack.add(country);
                    }
                }
            }
            return canAttack;
        }

        /**
         * This method implements attack functionality for the Aggressive Player
         * @return Returns the status of the operation
         */
        public String attack() {
            GameCountry attackingCountry = this.findStrongestCountry(this.countriesThatCanAttack());
            if(attackingCountry==null)
                return "No country to attack(The player might have won)";
            if(attackingCountry.getArmiesStationed()<=1)
                return "No country has more than one army to attack";
            GameCountry defendingCountry = this.findWeakestNeighbor(attackingCountry);
            String defender = defendingCountry.getCurrentPlayer().getName();
            String status = super.allOutAttack(defendingCountry.getCurrentPlayer(), attackingCountry, defendingCountry);
            System.out.println(status);
                return status + "| attack by "+ this.getName()+" to " + defender;
        }

        /**
         * This method implements fortify functionality for the aggressive player
         * @return Returns the status of the operation
         */
        public String fortify() {
            ArrayList<GameCountry> toFortify = bestCountryToFortify();
            if(toFortify==null||toFortify.size()==0)
                return "All the armies are already placed optimally according to the player's strategic behaviour";
            int toAdd = 12-toFortify.get(0).getArmiesStationed();
            if(toAdd==0)
                return "The strongest country is already fortified completely";
            toAdd = toAdd<=(toFortify.get(1).getArmiesStationed()-1)?toAdd:toFortify.get(1).getArmiesStationed()-1;
            super.fortify(toFortify.get(0).getCountryName(),toFortify.get(1).getCountryName(),(toAdd));
            System.out.println(this.getName() + " fortified " + ((toFortify.get(1).getArmiesStationed())-1) + " armies from " + toFortify.get(0).getCountryName()
                    + " to "+ toFortify.get(1).getCountryName());
            return this.getName() + " fortified " + ((toFortify.get(1).getArmiesStationed())-1) + " armies from " + toFortify.get(0).getCountryName()
                    + " to "+ toFortify.get(1).getCountryName();
        }

        /**
         * this method find the best country that aggressive player can attack
         * @return The best country to fortify
         */
        public ArrayList<GameCountry> bestCountryToFortify() {
            ArrayList<GameCountry> toFortify = new ArrayList<>();
            GameCountry bestCountry = null;
            GameCountry bestNeighbour = null;
            ArrayList<GameCountry> playerCountries = new ArrayList<>();
            for(GameCountry country:this.getCountries()){
                for(GameCountry neig : country.getNeighbouringCountries().values()){
                    if(neig.getArmiesStationed()>1 && neig.getCurrentPlayer().getId()==this.getId()){
                        playerCountries.add(country);
                    }
                }
            }
            if(playerCountries.size()==0) {
                return null;
            }
            int max = 0;
            for(GameCountry country : playerCountries) {
                for(GameCountry neighbor : country.getNeighbouringCountries().values()) {
                    if(neighbor.getCurrentPlayer().getId()==this.getId()) {
                        if(country.getArmiesStationed()+neighbor.getArmiesStationed()-1 >= max){
                            max = country.getArmiesStationed()+neighbor.getArmiesStationed();
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