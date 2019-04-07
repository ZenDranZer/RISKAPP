package views.gameModeView;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SingleGameModePanel extends JPanel {
    public SingleGameModePanel() {
        initComponents();
    }

    private void numberOfPlayersMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void resetButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void startButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void backButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void Player1TypeMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void player1TypeCBMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void player1TypeCB5MouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void player1TypeCB6MouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void player1TypeCB7MouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void player1TypeMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void player2TypeMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void player3TypeMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void player4TypeMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void player5TypeMouseClicked(MouseEvent e) {
        // TODO add your code here
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
        numberOfPlayers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                numberOfPlayersMouseClicked(e);
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
        player1Type.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player1TypeMouseClicked(e);
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
        player2Type.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player2TypeMouseClicked(e);
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
        player3Type.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player3TypeMouseClicked(e);
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
        player4Type.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player4TypeMouseClicked(e);
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
        player5Type.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player5TypeMouseClicked(e);
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
