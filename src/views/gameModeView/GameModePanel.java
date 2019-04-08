package views.gameModeView;

import controllers.GameEngine;
import controllers.TournamentController;
import models.Tournament;
import views.countryView.EditCountryPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameModePanel extends JPanel {

    private GameEngine gameEngine;
    private TournamentController tournamentController;
    private JPanel parent;

    public GameModePanel(GameEngine gameEngine, JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        tournamentController = new TournamentController();
        initComponents();
    }

    private void singleModeButtonMouseClicked(MouseEvent e) {
        SingleGameModePanel singleGameModePanel = new SingleGameModePanel(gameEngine,this);
        singleGameModePanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(singleGameModePanel);
        container.revalidate();
    }

    private void tournamentModeButtonMouseClicked(MouseEvent e) {
        TournamentModePanel tournamentModePanel = new TournamentModePanel(tournamentController,parent);
        tournamentModePanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(tournamentModePanel);
        container.revalidate();
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void loadGameButtonMouseClicked(MouseEvent e) {
        LoadExistingGamePanel loadExistingGamePanel = new LoadExistingGamePanel(gameEngine,this);
        loadExistingGamePanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(loadExistingGamePanel);
        container.revalidate();
    }

    private void initComponents() {
        singleModeButton = new JButton();
        tournamentModeButton = new JButton();
        loadGameButton = new JButton();
        backButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

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

        //---- loadGameButton ----
        loadGameButton.setText("Load Game");
        loadGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadGameButtonMouseClicked(e);
            }
        });
        add(loadGameButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- backButton ----
        backButton.setText("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backButtonMouseClicked(e);
            }
        });
        add(backButton, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
    }

    private JButton singleModeButton;
    private JButton tournamentModeButton;
    private JButton loadGameButton;
    private JButton backButton;
}
