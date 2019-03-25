package controllers;

import models.GameMap;
import models.Player;
import models.RiskCard;
import java.util.ArrayList;
import java.util.HashMap;

public class RiskCardController {

    /**A list of all cards which are not owned by any player.*/
    private ArrayList<RiskCard> cardDeck;
    /**A counter which specifies the number of trade up till now*/
    private int noOfTrades=1;
    /**GameEngine object to preserve the state of the game.*/
    private GameMap gameMap;
    /**A list of possible army types.*/
    private final HashMap<Integer,String> armyType;
    private final ArrayList<Integer> army;

    //{"Infantry","Cavalry","Artillery"};
    /**A public constructor for initializing all the data structures.*/
    public RiskCardController() {
        armyType = new HashMap<>();
        cardDeck = new ArrayList<>();
        armyType.put(1,"Infantry");
        armyType.put(5,"Cavalry");
        armyType.put(10,"Artillery");
        army = new ArrayList<>(armyType.keySet());
    }

    /**The method will generate the risk card deck.
     * @param gamemap is used to get the country names.*/
    public void initRiskCardDeck(GameMap gamemap) {

        ArrayList<String> countryList;
        int i = 0;
        this.gameMap=gamemap;
        countryList = new ArrayList<>(gamemap.getCountryHashMap().keySet());
        int numOfRiskCards = countryList.size();
        for(i=0;i< numOfRiskCards;i++) {
            RiskCard card = new RiskCard();
            card.setCountryName(countryList.get(i));
            card.setArmyType(army.get(i%3));
            cardDeck.add(card);
        }

/*        for(int m=0;m<cardDeck.size();m++) {
            System.out.println(cardDeck.get(m).getCountryName());
        }*/

    }

    public ArrayList<RiskCard> getCardDeck() {
        return cardDeck;
    }

    /**Increment the number of trade when a trade operation is successful.*/
    public void incrementTrade(){
        noOfTrades++;
    }

    /**A simple getter method to get the number of trades.*/
    public int getNoOfTrades() {
        return noOfTrades;
    }

    /**
     * This method generates possible combinations of risk cards that the player can trade for armies
     * @param cards cards held by player
     * @param sum type of armies on cards e.g. Artillery, Cavalry or Infantry
     * @return possible set of the cards that the player can trade
     */
    public ArrayList<RiskCard> getCombinations(ArrayList<RiskCard> cards,int sum) {
        ArrayList<RiskCard> set = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++)
            for (int j = i + 1; j < cards.size(); j++)
                for (int k = j+1 ; k<cards.size();k++) {
                    if (cards.get(i).getArmyType() + cards.get(j).getArmyType() + cards.get(k).getArmyType() == sum) {
                        set.add(cards.get(i));
                        set.add(cards.get(j));
                        set.add(cards.get(k));
                    }
                }
        return set;
    }

    /**
     * This method returns all the possible sets that a player can trade and get armies in exchange.
     * @param activeplayer specifies the active player who wish to get the possible sets from his cards.
     * @return The method returns a HashMap which has Set1,Set2,Set3, etc. as keys and values will be
     * the list of country names(3 country names).*/
    public HashMap<String,ArrayList<RiskCard>> getPossibleSets(Player activeplayer) {
        ArrayList<RiskCard> playerCards;
        ArrayList<RiskCard> infantry;
        ArrayList<RiskCard> cavalry;
        ArrayList<RiskCard> artillery;
        ArrayList<RiskCard> allDifferent;

        HashMap<String,ArrayList<RiskCard>> possiblSets = new HashMap<>();
        int possSet = 1;
        int i=0;
        playerCards = activeplayer.getCardsHeld();
        infantry = getCombinations(playerCards,3);
        cavalry = getCombinations(playerCards,15);
        artillery = getCombinations(playerCards,30);
        allDifferent = getCombinations(playerCards,16);

        if (infantry.size() > 0){

            for (int k=0;k<infantry.size()/3;k++) {
                ArrayList<RiskCard> temp = new ArrayList<>();
                for (int m=0;m<3;m++) {
                    temp.add(infantry.get((3*k)+m));
                }
                possiblSets.put(Integer.toString(possSet), temp);
                possSet++;
            }
        }

        if (cavalry.size() > 0) {
            for (int k=0;k<cavalry.size()/3;k++) {
                ArrayList<RiskCard> temp = new ArrayList<>();
                for (int m=0;m<3;m++) {
                    temp.add(cavalry.get((3*k)+m));
                }
                possiblSets.put(Integer.toString(possSet), temp);
                possSet++;
            }
        }

        if (artillery.size() > 0) {
            for (int k=0;k<artillery.size()/3;k++) {
                ArrayList<RiskCard> temp = new ArrayList<>();
                for (int m=0;m<3;m++) {
                    temp.add(artillery.get((3*k)+m));
                }
                possiblSets.put(Integer.toString(possSet), temp);
                possSet++;
            }
        }

        if (allDifferent.size() > 0) {
            for (int k=0;k<allDifferent.size()/3;k++) {
                ArrayList<RiskCard> temp = new ArrayList<>();
                for (int m=0;m<3;m++) {
                    temp.add(allDifferent.get((3*k)+m));
                }
                possiblSets.put(Integer.toString(possSet), temp);
                possSet++;
            }
        }
        return possiblSets;
    }

    /**
     * This method will perform the trading operation for the active player.
     * @param activePlayer specifies the player who wish to perform trading operation in exchange of card set
     * @param set specifies the set of 3 cards that a player need to exchange and will be allocated the armies
     *            accordingly.
     * @return It returns whether the operation is sucessful or not. if not specify the error.
     * */
    public String tradeCards(Player activePlayer,ArrayList<RiskCard> set){

        ArrayList<RiskCard> playerCards = activePlayer.getCardsHeld();

        for (int i=0;i<3;i++)
            cardDeck.add(set.get(i));

        for (int i=0;i<3;i++)
            playerCards.remove(set.get(i));

        int armies = activePlayer.getRemainingArmies();
        activePlayer.setRemainingArmies(armies+(noOfTrades*5));
        noOfTrades++;
        return "You got :"+ (noOfTrades*5) + " armies by trading your Risk Cards";
    }

    public RiskCard allocateRiskCard() {

        RiskCard card;
        card = cardDeck.get(0);
        cardDeck.remove(0);

        return card;
    }

}
