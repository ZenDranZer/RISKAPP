package views.continentView;

import controllers.GameEngine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditContinentPanel extends JPanel {

    private JPanel parent;
    private GameEngine gameEngine;

    public EditContinentPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    private void addContinentButtonMouseClicked(MouseEvent e) {
        AddContinentPanel addContinent = new AddContinentPanel(gameEngine,this);
        addContinent.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(addContinent);
        container.revalidate();
    }

    private void removeContinentButtonMouseClicked(MouseEvent e) {
        RemoveContinentPanel removeContinentPanel = new RemoveContinentPanel(gameEngine,this);
        removeContinentPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(removeContinentPanel);
        container.revalidate();
    }

    private void editContinentButtonMouseClicked(MouseEvent e) {
        EditContinentValuePanel editContinentValuePanel = new EditContinentValuePanel(gameEngine,this);
        editContinentValuePanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(editContinentValuePanel);
        container.revalidate();
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void initComponents() {
   
        addContinentButton = new JButton();
        removeContinentButton = new JButton();
        editContinentButton = new JButton();
        backButton = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- addContinentButton ----
        addContinentButton.setText("Add Continent");
        addContinentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addContinentButtonMouseClicked(e);
            }
        });
        add(addContinentButton, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- removeContinentButton ----
        removeContinentButton.setText("Remove Continent");
        removeContinentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeContinentButtonMouseClicked(e);
            }
        });
        add(removeContinentButton, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- editContinentButton ----
        editContinentButton.setText("Edit Continent");
        editContinentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editContinentButtonMouseClicked(e);
            }
        });
        add(editContinentButton, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(4, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

    }

    private JButton addContinentButton;
    private JButton removeContinentButton;
    private JButton editContinentButton;
    private JButton backButton;
}
