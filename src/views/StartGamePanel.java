package views;

import controllers.GameEngine;
import controllers.MapGenerator;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;


public class StartGamePanel extends JPanel {

    private GameEngine gameEngine;
    private JPanel parent;

    public StartGamePanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    private void continueButtonMouseClicked(MouseEvent e) throws IOException {
        ArrayList<String> playerName = new ArrayList<>();
        int value = (Integer)numberOfPlayers.getValue();
        switch (value){
            case 2:
                playerName.add(firstNameField.getText());
                playerName.add(secondNameField.getText());
                break;
            case 3:
                playerName.add(firstNameField.getText());
                playerName.add(secondNameField.getText());
                playerName.add(thirdNameField.getText());
                break;
            case 4:
                playerName.add(firstNameField.getText());
                playerName.add(secondNameField.getText());
                playerName.add(thirdNameField.getText());
                playerName.add(forthNameField.getText());
                break;
            case 5:
                playerName.add(firstNameField.getText());
                playerName.add(secondNameField.getText());
                playerName.add(thirdNameField.getText());
                playerName.add(forthNameField.getText());
                playerName.add(fifthNameField.getText());
                break;
        }

        //gameEngine.initialise(playerName,mapFileChooser.getSelectedFile().getAbsolutePath());
        gameEngine.setListActivePlayers(playerName);
        gameEngine.setMapPath(mapFileChooser.getSelectedFile().getAbsolutePath());
        Container container = this.getParent();
        GamePlay gamePlay = new GamePlay(gameEngine);
        gamePlay.setVisible(true);
        this.setVisible(false);
        container.add(gamePlay);
        container.revalidate();

    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }
    private void doneButtonMouseClicked(MouseEvent e) {
        int value = (Integer)numberOfPlayers.getValue();
        firstNameField.setEditable(true);
        secondNameField.setEditable(true);
        switch (value){
            case 2:
                thirdNameField.setEditable(false);
                forthNameField.setEditable(false);
                fifthNameField.setEditable(false);
                break;
            case 3:
                thirdNameField.setEditable(true);
                forthNameField.setEditable(false);
                fifthNameField.setEditable(false);
                break;
            case 4:
                thirdNameField.setEditable(true);
                forthNameField.setEditable(true);
                fifthNameField.setEditable(false);
                break;
            case 5:
                thirdNameField.setEditable(true);
                forthNameField.setEditable(true);
                fifthNameField.setEditable(true);
                break;
        }
    }

    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        numberOfPlayers = new JSpinner();
        doneButton = new JButton();
        firstNameLabel = new JLabel();
        firstNameField = new JTextField();
        secondNameLabel = new JLabel();
        secondNameField = new JTextField();
        thirdNameLabel = new JLabel();
        thirdNameField = new JTextField();
        forthNameLabel = new JLabel();
        forthNameField = new JTextField();
        fifthNameLabel = new JLabel();
        fifthNameField = new JTextField();
        mapFileChooser = new JFileChooser();
        backButton = new JButton();
        continueButton = new JButton();

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {183, 209, 199, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Enter Player Details:");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("How Many Players :");
        add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- numberOfPlayers ----
        numberOfPlayers.setModel(new SpinnerNumberModel(2, 2, 5, 1));
        add(numberOfPlayers, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- doneButton ----
        doneButton.setText("Done");
        doneButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                doneButtonMouseClicked(e);
            }
        });
        add(doneButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- firstNameLabel ----
        firstNameLabel.setText("Player1 name: ");
        add(firstNameLabel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- firstNameField ----
        firstNameField.setEditable(false);
        add(firstNameField, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- secondNameLabel ----
        secondNameLabel.setText("Player2 Name:");
        add(secondNameLabel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- secondNameField ----
        secondNameField.setEditable(false);
        add(secondNameField, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- thirdNameLabel ----
        thirdNameLabel.setText("Player3 Name:");
        add(thirdNameLabel, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- thirdNameField ----
        thirdNameField.setEditable(false);
        add(thirdNameField, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- forthNameLabel ----
        forthNameLabel.setText("Player4 Name:");
        add(forthNameLabel, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- forthNameField ----
        forthNameField.setEditable(false);
        add(forthNameField, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- fifthNameLabel ----
        fifthNameLabel.setText("Player5 Name:");
        add(fifthNameLabel, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- fifthNameField ----
        fifthNameField.setEditable(false);
        add(fifthNameField, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- mapFileChooser ----
        mapFileChooser.setControlButtonsAreShown(false);
        mapFileChooser.setAcceptAllFileFilterUsed(false);
        add(mapFileChooser, new GridBagConstraints(0, 8, 3, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));



        //---- continueButton ----
        continueButton.setText("Continue");
        continueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
					continueButtonMouseClicked(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        add(continueButton, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
    }

    private JLabel label1;
    private JLabel label2;
    private JSpinner numberOfPlayers;
    private JButton doneButton;
    private JLabel firstNameLabel;
    private JTextField firstNameField;
    private JLabel secondNameLabel;
    private JTextField secondNameField;
    private JLabel thirdNameLabel;
    private JTextField thirdNameField;
    private JLabel forthNameLabel;
    private JTextField forthNameField;
    private JLabel fifthNameLabel;
    private JTextField fifthNameField;
    private JFileChooser mapFileChooser;
    private JButton continueButton;
    private JButton backButton;
}
