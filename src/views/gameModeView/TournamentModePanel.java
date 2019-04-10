package views.gameModeView;

import controllers.GameEngine;
import controllers.TournamentController;
import views.GamePlay;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TournamentModePanel extends JPanel {

    private TournamentController tournamentController;
    private JPanel parent;

    public TournamentModePanel(TournamentController gameEngine, JPanel parent) {
        this.tournamentController = gameEngine;
        this.parent = parent;
        initComponents();
    }

    private void numberOfPlayersActionListner(ChangeEvent propertyChangeEvent) {
        int value = (Integer) numberOfPlayers.getValue();
        player1TypeCB.setEnabled(true);
        player2TypeCB.setEnabled(true);
        repaint();
        switch (value) {
            case 2:
                player3TypeCB.setEnabled(false);
                player4TypeCB.setEnabled(false);
                break;
            case 3:
                player3TypeCB.setEnabled(true);
                player4TypeCB.setEnabled(false);
                break;
            case 4:
                player3TypeCB.setEnabled(true);
                player4TypeCB.setEnabled(true);
                break;
        }
        startButton.setEnabled(true);
    }


    private void startButtonMouseClicked(MouseEvent e) {
        int numberOfMapsInt = (Integer)numberOfMaps.getValue();
        ArrayList<String> playersNames = new ArrayList<>();
        int numberOfPlayersInt = (Integer)numberOfPlayers.getValue();
        playersNames.add((String)player1TypeCB.getSelectedItem());
        playersNames.add((String)player2TypeCB.getSelectedItem());
        switch (numberOfPlayersInt){
            case 3:
                playersNames.add((String)player3TypeCB.getSelectedItem());
                break;
            case 4:
                playersNames.add((String)player3TypeCB.getSelectedItem());
                playersNames.add((String)player4TypeCB.getSelectedItem());
                break;
        }
        System.out.println(playersNames);
        int numberOfTurnsInt = (Integer) maximumTerns.getValue();
        int numberOfGamesInt = (Integer) numberOfGames.getValue();
        System.out.println(numberOfGamesInt);
        tournamentController.setMaxNoOfTuruns(numberOfTurnsInt);
        tournamentController.getTournament().setNoOfGames(numberOfGamesInt);
        tournamentController.setMaps(numberOfMapsInt);
        tournamentController.setPlayer(playersNames);
        Container container = this.getParent();
        TournamentResultPanel tournamentResultPanel = new TournamentResultPanel(tournamentController,this);
        tournamentController.getTournament().addObserver(tournamentResultPanel);
        tournamentResultPanel.setVisible(true);
        tournamentResultPanel.playTournament();
        this.setVisible(false);
        container.add(tournamentResultPanel);
        container.revalidate();

    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        numberOfMaps = new JSpinner();
        label3 = new JLabel();
        numberOfPlayers = new JSpinner();
        label4 = new JLabel();
        player1TypeCB = new JComboBox<>();
        label5 = new JLabel();
        player2TypeCB = new JComboBox<>();
        label6 = new JLabel();
        player3TypeCB = new JComboBox<>();
        label7 = new JLabel();
        player4TypeCB = new JComboBox<>();
        label8 = new JLabel();
        numberOfGames = new JSpinner();
        label9 = new JLabel();
        maximumTerns = new JSpinner();
        startButton = new JButton();
        backButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Tournament Mode");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 13f));
        add(label1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label2 ----
        label2.setText("Number Of maps:");
        add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- numberOfMaps ----
        numberOfMaps.setModel(new SpinnerNumberModel(1, 1, 5, 1));
        add(numberOfMaps, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label3 ----
        label3.setText("Number of Players");
        add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- numberOfPlayers ----
        numberOfPlayers.setModel(new SpinnerNumberModel(2, 2, 4, 1));
        numberOfPlayers.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                numberOfPlayersActionListner(changeEvent);
            }

        });
        add(numberOfPlayers, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label4 ----
        label4.setText("Player 1:");
        add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player1TypeCB ----
        player1TypeCB.setModel(new DefaultComboBoxModel<>(new String[] {
            "Aggressive",
            "Benevolent",
            "Random",
            "Cheater"
        }));
        add(player1TypeCB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label5 ----
        label5.setText("Player 2:");
        add(label5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player2TypeCB ----
        player2TypeCB.setModel(new DefaultComboBoxModel<>(new String[] {
            "Aggressive",
            "Benevolent",
            "Random",
            "Cheater"
        }));
        add(player2TypeCB, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label6 ----
        label6.setText("Player 3:");
        add(label6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player3TypeCB ----
        player3TypeCB.setModel(new DefaultComboBoxModel<>(new String[] {
            "Aggressive",
            "Benevolent",
            "Random",
            "Cheater"
        }));
        add(player3TypeCB, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label7 ----
        label7.setText("Player 4:");
        add(label7, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player4TypeCB ----
        player4TypeCB.setModel(new DefaultComboBoxModel<>(new String[] {
            "Aggressive",
            "Benevolent",
            "Random",
            "Cheater"
        }));
        add(player4TypeCB, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label8 ----
        label8.setText("Number of Games:");
        add(label8, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- numberOfGames ----
        numberOfGames.setModel(new SpinnerNumberModel(1, 1, 5, 1));
        add(numberOfGames, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label9 ----
        label9.setText("Maximum terns:");
        add(label9, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- maximumTerns ----
        maximumTerns.setModel(new SpinnerNumberModel(10, 10, 50, 1));
        add(maximumTerns, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- startButton ----
        startButton.setText("Start!");
        startButton.setEnabled(false);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButtonMouseClicked(e);
            }
        });
        add(startButton, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- backButton ----
        backButton.setText("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backButtonMouseClicked(e);
            }
        });
        add(backButton, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
    }

    private JLabel label1;
    private JLabel label2;
    private JSpinner numberOfMaps;
    private JLabel label3;
    private JSpinner numberOfPlayers;
    private JLabel label4;
    private JComboBox<String> player1TypeCB;
    private JLabel label5;
    private JComboBox<String> player2TypeCB;
    private JLabel label6;
    private JComboBox<String> player3TypeCB;
    private JLabel label7;
    private JComboBox<String> player4TypeCB;
    private JLabel label8;
    private JSpinner numberOfGames;
    private JLabel label9;
    private JSpinner maximumTerns;
    private JButton startButton;
    private JButton backButton;
}
