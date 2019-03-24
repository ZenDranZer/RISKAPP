package controllers;

import models.GameCountry;
import models.GameMap;
import models.Player;
import models.RiskCard;
import utils.Constants;
import controllers.MapGenerator;
import java.util.ArrayList;
import java.util.HashMap;

public class RiskCardController {

    private ArrayList<RiskCard> cardDeck;
    private int noOfTrades;
    private GameMap gameMap;
    private final HashMap<Integer,String> armyType;
    private final ArrayList<Integer> army;

    //{"Infantry","Cavalry","Artillery"};
    public RiskCardController() {
        armyType = new HashMap<>();
        cardDeck = new ArrayList<>();
        armyType.put(1,"Infantry");
        armyType.put(5,"Cavalry");
        armyType.put(10,"Artillery");
        army = new ArrayList<>(armyType.keySet());
    }

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
        for (i=0;i<cardDeck.size();i++) {
            System.out.println(cardDeck.get(i));
        }
    }

    //this method is done!
    public void increamentTrade(){
        noOfTrades++;
    }

    //this method is done!
    public int getNoOfTrades() {
        return noOfTrades;
    }


    public ArrayList<Integer> getCombinations(ArrayList<RiskCard> cards,int sum) {

        ArrayList<Integer> set = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++)
            for (int j = i + 1; j < cards.size(); j++)
                for (int k = j+1 ; k<cards.size();k++) {
                    if (cards.get(i).getArmyType() + cards.get(j).getArmyType() + cards.get(k).getArmyType() == sum) {
                        set.add(i);
                        set.add(j);
                        set.add(k);
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
        ArrayList<RiskCard> playerCards = new ArrayList<>();
        ArrayList<Integer> infantry = new ArrayList<>();
        ArrayList<Integer> cavalry = new ArrayList<>();
        ArrayList<Integer> artillery = new ArrayList<>();
        ArrayList<Integer> allDifferent = new ArrayList<>();

        HashMap<String,ArrayList<RiskCard>> possiblSets = new HashMap<>();
        int possSet = 0;
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
                    temp.add(playerCards.get(infantry.get((3*k)+m)));
                }
                possiblSets.put(Integer.toString(possSet), temp);
                possSet++;
            }
        }

        if (cavalry.size() > 0) {
            for (int k=0;k<cavalry.size()/3;k++) {
                ArrayList<RiskCard> temp = new ArrayList<>();
                for (int m=0;m<3;m++) {
                    temp.add(playerCards.get(cavalry.get((3*k)+m)));
                }
                possiblSets.put(Integer.toString(possSet), temp);
                possSet++;
            }
        }

        if (artillery.size() > 0) {
            for (int k=0;k<artillery.size()/3;k++) {
                ArrayList<RiskCard> temp = new ArrayList<>();
                for (int m=0;m<3;m++) {
                    temp.add(playerCards.get(artillery.get((3*k)+m)));
                }
                possiblSets.put(Integer.toString(possSet), temp);
                possSet++;
            }
        }

        if (allDifferent.size() > 0) {
            for (int k=0;k<allDifferent.size()/3;k++) {
                ArrayList<RiskCard> temp = new ArrayList<>();
                for (int m=0;m<3;m++) {
                    temp.add(playerCards.get(allDifferent.get((3*k)+m)));
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
    public String tradeCards(Player activePlayer,ArrayList<String> set){
        return null;
    }

}
