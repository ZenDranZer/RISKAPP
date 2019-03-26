package views.miscellaneous;


import models.GameContinent;
import models.GameState;
import models.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Sarvesh Vora
 */
public class WorldDominationView extends JPanel implements Observer {

    private int numberOfCountry;
    private int numberOfPlayer;
    private ArrayList<Player> players;
    private JLabel label1;
    private JLabel label5;
    private JLabel label4;
    private JLabel label3;
    private JLabel player1Label;
    private JTextField player1Ratio;
    private JTextField player1Army;
    private JScrollPane scrollPane1;
    private JTextArea player1Continents;
    private JLabel player2Label;
    private JTextField player2Ratio;
    private JTextField player2Army;
    private JScrollPane scrollPane2;
    private JTextArea player2Continents;
    private JLabel player3Label;
    private JTextField player3Ratio;
    private JTextField player3Army;
    private JScrollPane scrollPane3;
    private JTextArea player3Continents;
    private JLabel player4Label;
    private JTextField player4Ratio;
    private JTextField player4Army;
    private JScrollPane scrollPane4;
    private JTextArea player4Continents;
    private JLabel player5Label;
    private JTextField player5Ratio;
    private JTextField player5Army;
    private JScrollPane scrollPane5;
    private JTextArea player5Continents;

    public WorldDominationView(GameState gameState) {
        initComponents();
        numberOfCountry = gameState.getGameMapObject().getAllCountries().size();
        numberOfPlayer = gameState.getPlayers().size();
        players = gameState.getPlayers();
        switch (numberOfPlayer){
            case 2:
                hidePlayer3();
                hidePlayer4();
                hidePlayer5();
                break;
            case 3:
                hidePlayer4();
                hidePlayer5();
                break;
            case 4:
                hidePlayer5();
                break;
        }
        for (Player p: players) {
            int index = players.indexOf(p);
            setPlayerValue(p,index+1);
        }
    }

    private void hidePlayer3(){
        player3Army.setEnabled(false);
        player3Continents.setEnabled(false);
        player3Label.setEnabled(false);
        player3Ratio.setEnabled(false);
    }
    private void hidePlayer4(){
        player4Army.setEnabled(false);
        player4Continents.setEnabled(false);
        player4Label.setEnabled(false);
        player4Ratio.setEnabled(false);
    }
    private void hidePlayer5(){
        player5Army.setEnabled(false);
        player5Continents.setEnabled(false);
        player5Label.setEnabled(false);
        player5Ratio.setEnabled(false);
    }

    private void initComponents() {
        label1 = new JLabel();
        label5 = new JLabel();
        label4 = new JLabel();
        label3 = new JLabel();
        player1Label = new JLabel();
        player1Ratio = new JTextField();
        player1Army = new JTextField();
        scrollPane1 = new JScrollPane();
        player1Continents = new JTextArea();
        player2Label = new JLabel();
        player2Ratio = new JTextField();
        player2Army = new JTextField();
        scrollPane2 = new JScrollPane();
        player2Continents = new JTextArea();
        player3Label = new JLabel();
        player3Ratio = new JTextField();
        player3Army = new JTextField();
        scrollPane3 = new JScrollPane();
        player3Continents = new JTextArea();
        player4Label = new JLabel();
        player4Ratio = new JTextField();
        player4Army = new JTextField();
        scrollPane4 = new JScrollPane();
        player4Continents = new JTextArea();
        player5Label = new JLabel();
        player5Ratio = new JTextField();
        player5Army = new JTextField();
        scrollPane5 = new JScrollPane();
        player5Continents = new JTextArea();

        //======== this ========
        setAlignmentX(650.0F);
        setAlignmentY(60.0F);
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {75, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("World Domination View:");
        add(label1, new GridBagConstraints(0, 0, 6, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setText("Country Ratio");
        add(label5, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setText("Armies");
        add(label4, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("Continents Owned");
        add(label3, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player1Label ----
        player1Label.setText("Player 1:");
        add(player1Label, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player1Ratio ----
        player1Ratio.setEditable(false);
        add(player1Ratio, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player1Army ----
        player1Army.setEditable(false);
        add(player1Army, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {

            //---- player1Continents ----
            player1Continents.setEditable(false);
            scrollPane1.setViewportView(player1Continents);
        }
        add(scrollPane1, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player2Label ----
        player2Label.setText("Player 2:");
        add(player2Label, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player2Ratio ----
        player2Ratio.setEditable(false);
        add(player2Ratio, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player2Army ----
        player2Army.setEditable(false);
        add(player2Army, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane2 ========
        {

            //---- player2Continents ----
            player2Continents.setEditable(false);
            scrollPane2.setViewportView(player2Continents);
        }
        add(scrollPane2, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player3Label ----
        player3Label.setText("Player 3:");
        add(player3Label, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player3Ratio ----
        player3Ratio.setEditable(false);
        add(player3Ratio, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player3Army ----
        player3Army.setEditable(false);
        add(player3Army, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane3 ========
        {

            //---- player3Continents ----
            player3Continents.setEditable(false);
            scrollPane3.setViewportView(player3Continents);
        }
        add(scrollPane3, new GridBagConstraints(5, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player4Label ----
        player4Label.setText("Player 4:");
        add(player4Label, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player4Ratio ----
        player4Ratio.setEditable(false);
        add(player4Ratio, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player4Army ----
        player4Army.setEditable(false);
        add(player4Army, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane4 ========
        {

            //---- player4Continents ----
            player4Continents.setEditable(false);
            scrollPane4.setViewportView(player4Continents);
        }
        add(scrollPane4, new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player5Label ----
        player5Label.setText("Player 5:");
        add(player5Label, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- player5Ratio ----
        player5Ratio.setEditable(false);
        add(player5Ratio, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- player5Army ----
        player5Army.setEditable(false);
        add(player5Army, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //======== scrollPane5 ========
        {

            //---- player5Continents ----
            player5Continents.setEditable(false);
            scrollPane5.setViewportView(player5Continents);
        }
        add(scrollPane5, new GridBagConstraints(5, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
    }

    private String arrayToString(ArrayList<GameContinent> continents){
            String continentString = "";
            for (GameContinent continent: continents) {
                continentString += continent.getContinentName()+"\n";
            }
            return continentString;
    }

    private void setPlayerValue(Player player,int index){
        switch (index){
            case 1 :
                player1Army.setText(Integer.toString(player.getPlayerArmies()));
                float numberOfPlayerCountry = player.getCountries().size();
                float ratio = numberOfPlayerCountry/numberOfCountry;
                player1Ratio.setText(Float.toString((ratio)*100));
                String continents = arrayToString(player.getContinents());
                player1Continents.setText(continents);
                player1Label.setText(player.getName());
                break;
            case 2:
                player2Army.setText(Integer.toString(player.getPlayerArmies()));
                numberOfPlayerCountry = player.getCountries().size();
                ratio = numberOfPlayerCountry/numberOfCountry;
                player2Ratio.setText(Float.toString((ratio)*100));
                continents = arrayToString(player.getContinents());
                player2Continents.setText(continents);
                player2Label.setText(player.getName());
                break;
            case 3:
                player3Army.setText(Integer.toString(player.getPlayerArmies()));
                numberOfPlayerCountry = player.getCountries().size();
                ratio = numberOfPlayerCountry/numberOfCountry;
                player3Ratio.setText(Float.toString((ratio)*100));
                continents = arrayToString(player.getContinents());
                player3Continents.setText(continents);
                player3Label.setText(player.getName());
                break;
            case 4:
                player4Army.setText(Integer.toString(player.getPlayerArmies()));
                numberOfPlayerCountry = player.getCountries().size();
                ratio = numberOfPlayerCountry/numberOfCountry;
                player4Ratio.setText(Float.toString((ratio)*100));
                continents = arrayToString(player.getContinents());
                player4Continents.setText(continents);
                player4Label.setText(player.getName());
                break;
            case 5:
                player5Army.setText(Integer.toString(player.getPlayerArmies()));
                numberOfPlayerCountry = player.getCountries().size();
                ratio = numberOfPlayerCountry/numberOfCountry;
                player5Ratio.setText(Float.toString((ratio)*100));
                continents = arrayToString(player.getContinents());
                player5Continents.setText(continents);
                player5Label.setText(player.getName());
                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        int index = players.indexOf(observable);
        setPlayerValue((Player)observable,index+1);
    }
}
