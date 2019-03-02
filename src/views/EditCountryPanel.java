package views;

import controllers.GameEngine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditCountryPanel extends JPanel {
    private JPanel parent;
    private GameEngine gameEngine;
    public EditCountryPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    private void addBoutonMouseClicked(MouseEvent e) {
        AddNewCountryPanel addNewCountryPanel = new AddNewCountryPanel(gameEngine,this);
        addNewCountryPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(addNewCountryPanel);
        container.revalidate();
    }

    private void removeButtonMouseClicked(MouseEvent e) {
        RemoveCountryPanel removeCountryPanel = new RemoveCountryPanel(gameEngine,this);
        removeCountryPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(removeCountryPanel);
        container.revalidate();
    }

    private void editButtonMouseClicked(MouseEvent e) {
        EditCountryValuePanel editCountryValuePanel = new EditCountryValuePanel(gameEngine,this);
        editCountryValuePanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(editCountryValuePanel);
        container.revalidate();
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void initComponents() {
       
        addBouton = new JButton();
        removeButton = new JButton();
        editButton = new JButton();
        backButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- addBouton ----
        addBouton.setText("Add New Country");
        addBouton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addBoutonMouseClicked(e);
            }
        });
        add(addBouton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- removeButton ----
        removeButton.setText("Remove Country");
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeButtonMouseClicked(e);
            }
        });
        add(removeButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- editButton ----
        editButton.setText("Edit Country Value");
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editButtonMouseClicked(e);
            }
        });
        add(editButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
    }

    private JButton addBouton;
    private JButton removeButton;
    private JButton editButton;
    private JButton backButton;
}
