package views.gameModeView;

import controllers.GameEngine;
import controllers.MapGenerator;
import views.GamePlay;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SingleGameModePanel extends JPanel {

    private GameEngine gameEngine;
    private JPanel parent;

    public SingleGameModePanel(GameEngine gameEngine, JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
        filePath.addChoosableFileFilter(new FileNameExtensionFilter("MAP file only", "map"));
    }

    private void numberOfPlayersActionListner(ChangeEvent propertyChangeEvent) {
        int value = (Integer) numberOfPlayers.getValue();
        player1Type.setEnabled(true);
        player2Type.setEnabled(true);
        repaint();
        switch (value) {
            case 2:
                player3Type.setEnabled(false);
                player4Type.setEnabled(false);
                player5Type.setEnabled(false);
                break;
            case 3:
                player3Type.setEnabled(true);
                player4Type.setEnabled(false);
                player5Type.setEnabled(false);
                break;
            case 4:
                player3Type.setEnabled(true);
                player4Type.setEnabled(true);
                player5Type.setEnabled(false);
                break;
            case 5:
                player3Type.setEnabled(true);
                player4Type.setEnabled(true);
                player5Type.setEnabled(true);
                break;
        }
        startButton.setEnabled(true);
    }

    private void resetButtonMouseClicked(MouseEvent e) {
        player1Name.setText("");
        player2Name.setText("");
        player3Name.setText("");
        player4Name.setText("");
        player5Name.setText("");
    }

    private void startButtonMouseClicked(MouseEvent e) {
        try{
        if(startButton.isEnabled() && !filePath.getSelectedFile().getAbsolutePath().isEmpty()){
            ArrayList<String> playerNames = new ArrayList<>();
            int value = (Integer) numberOfPlayers.getValue();
            if(isValidNames()){
                playerNames.add(player1Name.getText());
                playerNames.add(player2Name.getText());
                switch (value){
                    case 3:
                        playerNames.add(player3Name.getText());
                        break;
                    case 4:
                        playerNames.add(player3Name.getText());
                        playerNames.add(player4Name.getText());
                        break;
                    case 5:
                        playerNames.add(player3Name.getText());
                        playerNames.add(player4Name.getText());
                        playerNames.add(player5Name.getText());
                        break;
                }
                gameEngine.setListActivePlayers(playerNames);
                gameEngine.setMapPath(filePath.getSelectedFile().getAbsolutePath());
                MapGenerator mapGenerator = gameEngine.getMapGenerator();
                String message = mapGenerator.readConquestFile(gameEngine.getMapPath());
                if (message.equals("SUCCESS")) {
                    message = mapGenerator.validateMap();
                    if(message.equals("SUCCESS")&& gameEngine.getGameState().getGameMapObject().
                            getCountryHashMap().values().size() < playerNames.size()) {
                        message = "Number of countries in the map should be more than the players";
                        JOptionPane.showMessageDialog(this, message);
                    }
                    if (message.equals("SUCCESS")) {
                        gameEngine.initialiseEngine();
                        JOptionPane.showMessageDialog(this, message);
                        Container container = this.getParent();
                        GamePlay gamePlay = new GamePlay(gameEngine,this);
                        gameEngine.getGameState().addObserver(gamePlay);
                        gamePlay.setVisible(true);
                        this.setVisible(false);
                        container.add(gamePlay);
                        container.revalidate();
                    } else {
                        mapGenerator.reSetAllocations();
                        JOptionPane.showMessageDialog(this, message);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, message);
                    mapGenerator.reSetAllocations();
                }
            }
            else{
                JOptionPane.showMessageDialog(this,"Please select file !!");
            }
        }else{
            JOptionPane.showMessageDialog(this,"Please select file !!");
        }
        }catch(Exception a){

        }
    }

    private boolean isValidNames(){
        if(player1Name.isEnabled() && player1Name.getText().equals(""))
            return false;
        else if(player2Name.isEnabled() && player2Name.getText().equals(""))
            return false;
        else if(player3Name.isEnabled() && player3Name.getText().equals(""))
            return false;
        else if(player4Name.isEnabled() && player4Name.getText().equals(""))
            return false;
        else if(player5Name.isEnabled() && player5Name.getText().equals(""))
            return false;

        return true;
    }
    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void player1TypeMouseClicked(ActionEvent e) {
        setNameField(player1Type, player1Name);
    }

    private void player2TypeMouseClicked(ActionEvent e) {
        setNameField(player2Type, player2Name);
    }

    private void player3TypeMouseClicked(ActionEvent e) {
        setNameField(player3Type, player3Name);
    }

    private void player4TypeMouseClicked(ActionEvent e) {
        setNameField(player4Type, player4Name);
    }

    private void player5TypeMouseClicked(ActionEvent e) {
        setNameField(player5Type, player5Name);
    }

    private void setNameField(JComboBox<String> playerType, JTextField playerName) {
        switch((Integer)playerType.getSelectedIndex()){
            case 0:
                playerName.setEnabled(true);
                playerName.setText("");
                playerName.setEditable(true);
                break;
            case 1:
                playerName.setEditable(false);
                playerName.setEnabled(true);
                playerName.setText("Aggressive");
                break;
            case 2:
                playerName.setEditable(false);
                playerName.setEnabled(true);
                playerName.setText("Benevolent");
                break;
            case 3:
                playerName.setEditable(false);
                playerName.setEnabled(true);
                playerName.setText("Random");
                break;
            case 4:
                playerName.setEditable(false);
                playerName.setEnabled(true);
                playerName.setText("Cheater");
                break;
        }
    }

    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        numberOfPlayers = new JSpinner();
        label3 = new JLabel();
        player1Type = new JComboBox<>();
        player1Name = new JTextField();
        label4 = new JLabel();
        player2Type = new JComboBox<>();
        player2Name = new JTextField();
        label5 = new JLabel();
        player3Type = new JComboBox<>();
        player3Name = new JTextField();
        label6 = new JLabel();
        player4Type = new JComboBox<>();
        player4Name = new JTextField();
        label7 = new JLabel();
        player5Type = new JComboBox<>();
        player5Name = new JTextField();
        filePath = new JFileChooser();
        resetButton = new JButton();
        startButton = new JButton();
        backButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 187, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Single Game Mode");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 15f));
        add(label1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Enter Number of players:");
        add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- numberOfPlayers ----
        numberOfPlayers.setModel(new SpinnerNumberModel(2, 2, 5, 1));
        numberOfPlayers.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                numberOfPlayersActionListner(changeEvent);
            }

        });
        add(numberOfPlayers, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("Player 1:");
        add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player1Type ----
        player1Type.setModel(new DefaultComboBoxModel<>(new String[] {
            "Human",
            "Aggressive",
            "Benevolent",
            "Random",
            "Cheater"
        }));
        player1Type.setEnabled(false);
        player1Type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                player1TypeMouseClicked(actionEvent);
            }
        });
        add(player1Type, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player1Name ----
        player1Name.setEnabled(false);
        add(player1Name, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setText("Player 2:");
        add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player2Type ----
        player2Type.setModel(new DefaultComboBoxModel<>(new String[] {
            "Human",
            "Aggressive",
            "Benevolent",
            "Random",
            "Cheater"
        }));
        player2Type.setEnabled(false);
        player2Type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                player2TypeMouseClicked(actionEvent);
            }
        });
        add(player2Type, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player2Name ----
        player2Name.setEnabled(false);
        add(player2Name, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setText("Player 3:");
        add(label5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player3Type ----
        player3Type.setModel(new DefaultComboBoxModel<>(new String[] {
            "Human",
            "Aggressive",
            "Benevolent",
            "Random",
            "Cheater"
        }));
        player3Type.setEnabled(false);
        player3Type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                player3TypeMouseClicked(actionEvent);
            }
        });
        add(player3Type, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player3Name ----
        player3Name.setEnabled(false);
        add(player3Name, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label6 ----
        label6.setText("Player 4:");
        add(label6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player4Type ----
        player4Type.setModel(new DefaultComboBoxModel<>(new String[] {
            "Human",
            "Aggressive",
            "Benevolent",
            "Random",
            "Cheater"
        }));
        player4Type.setEnabled(false);
        player4Type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                player4TypeMouseClicked(actionEvent);
            }
        });
        add(player4Type, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player4Name ----
        player4Name.setEnabled(false);
        add(player4Name, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label7 ----
        label7.setText("Player 5:");
        add(label7, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player5Type ----
        player5Type.setModel(new DefaultComboBoxModel<>(new String[] {
            "Human",
            "Aggressive",
            "Benevolent",
            "Random",
            "Cheater"
        }));
        player5Type.setEnabled(false);
        player5Type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                player5TypeMouseClicked(actionEvent);
            }
        });
        add(player5Type, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- player5Name ----
        player5Name.setEnabled(false);
        add(player5Name, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- filePath ----
        filePath.setAcceptAllFileFilterUsed(false);
        filePath.setControlButtonsAreShown(false);
        filePath.setDialogTitle("MAP files only");
        add(filePath, new GridBagConstraints(0, 7, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- resetButton ----
        resetButton.setText("Reset");
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetButtonMouseClicked(e);
            }
        });
        add(resetButton, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- startButton ----
        startButton.setText("Start!");
        startButton.setEnabled(false);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButtonMouseClicked(e);
            }
        });
        add(startButton, new GridBagConstraints(4, 8, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
    }

    private JLabel label1;
    private JLabel label2;
    private JSpinner numberOfPlayers;
    private JLabel label3;
    private JComboBox<String> player1Type;
    private JTextField player1Name;
    private JLabel label4;
    private JComboBox<String> player2Type;
    private JTextField player2Name;
    private JLabel label5;
    private JComboBox<String> player3Type;
    private JTextField player3Name;
    private JLabel label6;
    private JComboBox<String> player4Type;
    private JTextField player4Name;
    private JLabel label7;
    private JComboBox<String> player5Type;
    private JTextField player5Name;
    private JFileChooser filePath;
    private JButton resetButton;
    private JButton startButton;
    private JButton backButton;
}
