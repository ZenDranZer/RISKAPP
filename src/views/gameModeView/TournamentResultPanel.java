package views.gameModeView;

import controllers.TournamentController;
import models.Tournament;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class TournamentResultPanel extends JPanel implements Observer {

    TournamentController tournamentController;
    JPanel parents;

    public TournamentResultPanel(TournamentController tournamentController,JPanel parents) {
        this.tournamentController = tournamentController;
        this.parents = parents;
        initComponents();
    }

    private void backButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        mapsField = new JLabel();
        label3 = new JLabel();
        playersField = new JLabel();
        label4 = new JLabel();
        numberOfGames = new JLabel();
        label5 = new JLabel();
        numberOfTurns = new JLabel();
        label11 = new JLabel();
        label12 = new JLabel();
        label13 = new JLabel();
        label14 = new JLabel();
        label15 = new JLabel();
        label6 = new JLabel();
        map1Game1 = new JTextField();
        map1Game2 = new JTextField();
        map1Game3 = new JTextField();
        map1Game4 = new JTextField();
        map1Game5 = new JTextField();
        label7 = new JLabel();
        map2Game1 = new JTextField();
        map2Game2 = new JTextField();
        map2Game3 = new JTextField();
        map2Game4 = new JTextField();
        map2Game5 = new JTextField();
        label8 = new JLabel();
        map3Game1 = new JTextField();
        map3Game2 = new JTextField();
        map3Game3 = new JTextField();
        map3Game4 = new JTextField();
        map3Game5 = new JTextField();
        label9 = new JLabel();
        map4Game1 = new JTextField();
        map4Game2 = new JTextField();
        map4Game3 = new JTextField();
        map4Game4 = new JTextField();
        map4Game5 = new JTextField();
        label10 = new JLabel();
        map5Game1 = new JTextField();
        map5Game2 = new JTextField();
        map5Game3 = new JTextField();
        map5Game4 = new JTextField();
        map5Game5 = new JTextField();
        backButton = new JButton();
        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 127, 128, 134, 139, 136, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Results:");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 15f));
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Maps : ");
        add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- mapsField ----
        mapsField.setText("text");
        add(mapsField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("Players :");
        add(label3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- playersField ----
        playersField.setText("text");
        add(playersField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setText("Games:");
        add(label4, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- numberOfGames ----
        numberOfGames.setText("text");
        add(numberOfGames, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setText("Number of Turn:");
        add(label5, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- numberOfTurns ----
        numberOfTurns.setText("text");
        add(numberOfTurns, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label11 ----
        label11.setText("Game 1");
        add(label11, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label12 ----
        label12.setText("Game 2");
        add(label12, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label13 ----
        label13.setText("Game 3");
        add(label13, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label14 ----
        label14.setText("Game 4");
        add(label14, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label15 ----
        label15.setText("Game 5");
        add(label15, new GridBagConstraints(5, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label6 ----
        label6.setText("Map 1");
        add(label6, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map1Game1, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map1Game2, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map1Game3, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map1Game4, new GridBagConstraints(4, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map1Game5, new GridBagConstraints(5, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label7 ----
        label7.setText("Map 2");
        add(label7, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map2Game1, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map2Game2, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map2Game3, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map2Game4, new GridBagConstraints(4, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map2Game5, new GridBagConstraints(5, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label8 ----
        label8.setText("Map 3");
        add(label8, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map3Game1, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map3Game2, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map3Game3, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map3Game4, new GridBagConstraints(4, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map3Game5, new GridBagConstraints(5, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label9 ----
        label9.setText("Map 4");
        add(label9, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map4Game1, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map4Game2, new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map4Game3, new GridBagConstraints(3, 10, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map4Game4, new GridBagConstraints(4, 10, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(map4Game5, new GridBagConstraints(5, 10, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label10 ----
        label10.setText("Map 5");
        add(label10, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        add(map5Game1, new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        add(map5Game2, new GridBagConstraints(2, 11, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        add(map5Game3, new GridBagConstraints(3, 11, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        add(map5Game4, new GridBagConstraints(4, 11, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        add(map5Game5, new GridBagConstraints(5, 11, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- backButton ----
        backButton.setText("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backButtonMouseClicked(e);
            }
        });
        add(backButton, new GridBagConstraints(5, 13, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        setTextNonEditableField();
    }

    private void setTextNonEditableField(){
        map1Game1.setEditable(false);
        map1Game2.setEditable(false);
        map1Game3.setEditable(false);
        map1Game4.setEditable(false);
        map1Game5.setEditable(false);
        map2Game1.setEditable(false);
        map2Game2.setEditable(false);
        map2Game3.setEditable(false);
        map2Game4.setEditable(false);
        map2Game5.setEditable(false);
        map3Game1.setEditable(false);
        map3Game2.setEditable(false);
        map3Game3.setEditable(false);
        map3Game4.setEditable(false);
        map3Game5.setEditable(false);
        map4Game1.setEditable(false);
        map4Game2.setEditable(false);
        map4Game3.setEditable(false);
        map4Game4.setEditable(false);
        map4Game5.setEditable(false);
        map5Game1.setEditable(false);
        map5Game2.setEditable(false);
        map5Game3.setEditable(false);
        map5Game4.setEditable(false);
        map5Game5.setEditable(false);
    }

    private JLabel label1;
    private JLabel label2;
    private JLabel mapsField;
    private JLabel label3;
    private JLabel playersField;
    private JLabel label4;
    private JLabel numberOfGames;
    private JLabel label5;
    private JLabel numberOfTurns;
    private JLabel label11;
    private JLabel label12;
    private JLabel label13;
    private JLabel label14;
    private JLabel label15;
    private JLabel label6;
    private JTextField map1Game1;
    private JTextField map1Game2;
    private JTextField map1Game3;
    private JTextField map1Game4;
    private JTextField map1Game5;
    private JLabel label7;
    private JTextField map2Game1;
    private JTextField map2Game2;
    private JTextField map2Game3;
    private JTextField map2Game4;
    private JTextField map2Game5;
    private JLabel label8;
    private JTextField map3Game1;
    private JTextField map3Game2;
    private JTextField map3Game3;
    private JTextField map3Game4;
    private JTextField map3Game5;
    private JLabel label9;
    private JTextField map4Game1;
    private JTextField map4Game2;
    private JTextField map4Game3;
    private JTextField map4Game4;
    private JTextField map4Game5;
    private JLabel label10;
    private JTextField map5Game1;
    private JTextField map5Game2;
    private JTextField map5Game3;
    private JTextField map5Game4;
    private JTextField map5Game5;
    private JButton backButton;

    private void displayResults(ArrayList<ArrayList<String>> result){
        int map = 1;
        for (ArrayList<String> gameResult: result) {
            int game = 1;
            for (String s: gameResult) {
                setTextField(map,game,s);
                game++;
            }
            map++;
        }
    }

    private void setTextField(int map,int game,String s){
        switch (map){
            case 1:
                setGameField(game, s, map1Game1, map1Game2, map1Game3, map1Game4, map1Game5);
                break;
            case 2:
                setGameField(game, s, map2Game1, map2Game2, map2Game3, map2Game4, map2Game5);
                break;
            case 3:
                setGameField(game, s, map3Game1, map3Game2, map3Game3, map3Game4, map3Game5);
                break;
            case 4:
                setGameField(game, s, map4Game1, map4Game2, map4Game3, map4Game4, map4Game5);
                break;
            case 5:
                setGameField(game, s, map5Game1, map5Game2, map5Game3, map5Game4, map5Game5);
                break;
        }

    }

    private void setGameField(int game, String s, JTextField mapGame1, JTextField mapGame2, JTextField mapGame3, JTextField mapGame4, JTextField mapGame5) {
        switch (game) {
            case 1:
                mapGame1.setText(s);
                break;
            case 2:
                mapGame2.setText(s);
                break;
            case 3:
                mapGame3.setText(s);
                break;
            case 4:
                mapGame4.setText(s);
                break;
            case 5:
                mapGame5.setText(s);
                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Tournament tournament = (Tournament) observable;
        ArrayList<ArrayList<String>> result = tournament.getResult();
        displayResults(result);
    }
}
