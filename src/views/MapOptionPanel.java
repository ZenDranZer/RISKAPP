package views;

import controllers.GameEngine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MapOptionPanel extends JPanel {

    GameEngine gameEngine;
    JPanel parent;

    public MapOptionPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    private void loadButtonMouseClicked(MouseEvent e) {
        this.setVisible(false);
        LoadMapPanel loadMapPanel = new LoadMapPanel(gameEngine,this);
        loadMapPanel.setVisible(true);
        Container container = getParent();
        container.add(loadMapPanel);
        container.revalidate();
    }

    private void createbuttonMouseClicked(MouseEvent e) {
        this.setVisible(false);
        CreateContinentPanel createContinentPanel = new CreateContinentPanel(gameEngine,this);
        createContinentPanel.setVisible(true);
        Container container = getParent();
        container.add(createContinentPanel);
        container.revalidate();
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void initComponents() {
     
        createbutton = new JButton();
        loadButton = new JButton();
        backButton = new JButton();

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- createbutton ----
        createbutton.setText("Create New Map");
        createbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createbuttonMouseClicked(e);
            }
        });
        add(createbutton, new GridBagConstraints(5, 2, 1, 3, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(5, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //---- loadButton ----
        loadButton.setText("Load Map");
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadButtonMouseClicked(e);
            }
        });
        add(loadButton, new GridBagConstraints(5, 7, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
    }


    private JButton createbutton;
    private JButton loadButton;
    private JButton backButton;
}
