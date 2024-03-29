package views.miscellaneous;

import controllers.GameEngine;
import controllers.RiskCardController;
import models.Player;
import models.RiskCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class TradePanel extends JPanel implements Observer {

    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    private HashMap<String,ArrayList<RiskCard>> validSet;
    private JLabel label1;
    private JLabel label2;
    private JLabel playerNameLabel;
    private JLabel cardLabel;
    private JLabel label3;
    private JLabel armyLabel;
    private JLabel label4;
    private JComboBox tradeSetList;
    private JButton tradeButton;
    private JButton continueButton;
    private RiskCardController riskCardController;
    private JLabel label5;
    private JLabel numberOfCardsLabel;
    private Player activePlayer;
    private JScrollPane scrollPane1;
    private JList cardList;

    public TradePanel(GameEngine gameEngine,JPanel panel) {
        initComponents();
        this.gameEngine = gameEngine;
        this.parent = panel;
        this.riskCardController = gameEngine.getGameState().getRiskController();
        activePlayer = gameEngine.getGameState().getActivePlayer();
        validSet = riskCardController.getPossibleSets(activePlayer);
        tradeSetList.setModel(new DefaultComboBoxModel<>(validSet.keySet().toArray()));
        playerNameLabel.setText(activePlayer.getName());
        armyLabel.setText(Integer.toString(activePlayer.getRemainingArmies()));
        numberOfCardsLabel.setText(Integer.toString(activePlayer.getCardsHeld().size()));
        ArrayList<String> cardToString = new ArrayList<>();
        for (RiskCard r: activePlayer.getCardsHeld()) {
            cardToString.add(r.getCountryName()+" " + r.getArmyType());
        }
        cardList.setListData(cardToString.toArray());
    }

    private void tradeButtonMouseClicked(MouseEvent e) {
        ArrayList<RiskCard> cardSet = validSet.get((String)tradeSetList.getSelectedItem());
        riskCardController.tradeCards(activePlayer,cardSet);
    }

    private void continueButtonMouseClicked(MouseEvent e) {
        if(activePlayer.getCardsHeld().size()<=4){
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
        Player player = gameEngine.getGameState().getActivePlayer();
        player.deleteObserver(this);
        }else{
            JOptionPane.showMessageDialog(this,"You can not hold more than 4 more cards.");
        }
    }

    private void tradeSetListActionPerformed(ActionEvent e) {
        ArrayList<RiskCard> setValue = validSet.get(tradeSetList.getSelectedItem());
        ArrayList<String> stringSet = new ArrayList<>();
        for (RiskCard r: setValue) {
            stringSet.add(r.getCountryName());
        }
        String stringCardSet = "";
        for (String s : stringSet) {
            stringCardSet = stringCardSet + s + "\n";
        }
        cardLabel.setText(stringCardSet);
    }

    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        playerNameLabel = new JLabel();
        cardLabel = new JLabel();
        label3 = new JLabel();
        armyLabel = new JLabel();
        label4 = new JLabel();
        tradeSetList = new JComboBox();
        tradeButton = new JButton();
        continueButton = new JButton();
        label5 = new JLabel();
        numberOfCardsLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        cardList = new JList();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {112, 117, 105, 149, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {30, 0, 0, 0, 0, 34, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Trade Cards:");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Player Name:");
        add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        add(playerNameLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        add(cardLabel, new GridBagConstraints(2, 2, 1, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(cardList);
        }
        add(scrollPane1, new GridBagConstraints(3, 2, 1, 4, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- label3 ----
        label3.setText("Total Armies:");
        add(label3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        add(armyLabel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setText("# of Cards:");
        add(label5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- numberOfCardsLabel ----
        numberOfCardsLabel.setText("text");
        add(numberOfCardsLabel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setText("Trade Sets:");
        add(label4, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- tradeSetList ----
        tradeSetList.addActionListener(e -> tradeSetListActionPerformed(e));
        add(tradeSetList, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- tradeButton ----
        tradeButton.setText("Trade");
        tradeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tradeButtonMouseClicked(e);
            }
        });
        add(tradeButton, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //---- continueButton ----
        continueButton.setText("Continue");
        continueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                continueButtonMouseClicked(e);
            }
        });
        add(continueButton, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
    }

    @Override
    public void update(Observable observable, Object o) {
        Player player = (Player) observable;
        playerNameLabel.setText(player.getName());
        armyLabel.setText(Integer.toString(player.getRemainingArmies()));
        validSet = riskCardController.getPossibleSets(player);
        numberOfCardsLabel.setText(Integer.toString(player.getCardsHeld().size()));
        tradeSetList.setModel(new DefaultComboBoxModel<>(validSet.keySet().toArray()));
        ArrayList<String> cardToString = new ArrayList<>();
        for (RiskCard r: activePlayer.getCardsHeld()) {
            cardToString.add(r.getCountryName()+" " + r.getArmyType());
        }
        cardList.setListData(cardToString.toArray());
    }
}
