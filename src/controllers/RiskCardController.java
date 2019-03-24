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
        armyType = new ArrayList<>();
        cardDeck = new ArrayList<>();
        armyType.put(1,"Infantry");
        armyType.put(5,"Cavalry");
        armyType.put(10,"Artillery");
        army = new ArrayList<>(armyType.keySet().toArray());
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


    public void getCombinations(int sum) {

        int count = 0;// Initialize result

        // Consider all possible pairs and check their sums
        for (int i = 0; i < army.size(); i++)
            for (int j = i + 1; j < army.size(); j++)
                for (int k = j+1 ; k<army.size();k++) {
                    if (army.get(i) + army.get(j) + army.get(k) == sum) {

                    }
                }
    }

    /**
     * This method returns all the possible sets that a player can trade and get armies in exchange.
     * @param activeplayer specifies the active player who wish to get the possible sets from his cards.
     * @return The method returns a HashMap which has Set1,Set2,Set3, etc. as keys and values will be
     * the list of country names(3 country names).*/
    public HashMap<String,ArrayList<RiskCard>> getPossibleSets(Player activeplayer) {
        ArrayList<RiskCard> playerCards = new ArrayList<>();
        HashMap<String,ArrayList<RiskCard>> possiblSets = new HashMap<>();
        int possSet = 0;
        int i=0;
        playerCards = activeplayer.getCardsHeld();

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
