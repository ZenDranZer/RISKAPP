package views.gameModeView;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameModePanel extends JPanel {
    public GameModePanel() {
        initComponents();
    }

    private void singleModeButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void tournamentModeButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        singleModeButton = new JButton();
        tournamentModeButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout) getLayout()).columnWidths = new int[]{0, 0, 0};
        ((GridBagLayout) getLayout()).rowHeights = new int[]{0, 0, 0, 0};
        ((GridBagLayout) getLayout()).columnWeights = new double[]{0.0, 0.0, 1.0E-4};
        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};

        //---- singleModeButton ----
        singleModeButton.setText("Single Game Mode");
        singleModeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                singleModeButtonMouseClicked(e);
            }
        });
        add(singleModeButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- tournamentModeButton ----
        tournamentModeButton.setText("Tournament Mode");
        tournamentModeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tournamentModeButtonMouseClicked(e);
            }
        });
        add(tournamentModeButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
    }
    private JButton singleModeButton;
    private JButton tournamentModeButton;
}
