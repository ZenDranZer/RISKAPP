package controllers;

import models.GameCountry;
import models.GameMap;
import models.RiskCard;
import utils.Constants;
import controllers.MapGenerator;
import java.util.ArrayList;

public class RiskCardController {

    private ArrayList<RiskCard> cardDeck;
    private int noOfTrades;
    private GameMap gameMap;
    private ArrayList<String> armyType;

    //{"Infantry","Cavalry","Artillery"};

    public RiskCardController() {
        armyType = new ArrayList<>();
        cardDeck = new ArrayList<>();
        armyType.add("Infantry");
        armyType.add("Cavalry");
        armyType.add("Artillery");
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
            card.setArmyType(armyType.get(i%3));
            cardDeck.add(card);
        }

        for (i=0;i<cardDeck.size();i++) {
            System.out.println(cardDeck.get(i));
        }
    }
}
