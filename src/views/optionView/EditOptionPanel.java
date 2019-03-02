package views.optionView;

import controllers.GameEngine;
import controllers.MapGenerator;
import views.continentView.EditContinentPanel;
import views.countryView.EditCountryPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class EditOptionPanel extends JPanel {

    private JPanel parent;
    private GameEngine gameEngine;

    public EditOptionPanel(GameEngine gameEngine,JPanel parent)
    {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    private void editContinentButtonMouseClicked(MouseEvent e) {
        EditContinentPanel editContinentPanel = new EditContinentPanel(gameEngine,this);
        editContinentPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(editContinentPanel);
        container.revalidate();
    }

    private void editCountryButtonMouseClicked(MouseEvent e) {
        EditCountryPanel editCountryPanel = new EditCountryPanel(gameEngine,this);
        editCountryPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(editCountryPanel);
        container.revalidate();
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }
    private void finishButtonMouseClicked(MouseEvent e) {
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        String message = mapGenerator.validateMap();
        JOptionPane.showMessageDialog(this,message);
    }

    private void initComponents() {
        editContinentButton = new JButton();
        editCountryButton = new JButton();
        backButton = new JButton();
        finishButton = new JButton();

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- editContinentButton ----
        editContinentButton.setText("Edit Continents");
        editContinentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editContinentButtonMouseClicked(e);
            }
        });
        add(editContinentButton, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- editCountryButton ----
        editCountryButton.setText("Edit Countries");
        editCountryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editCountryButtonMouseClicked(e);
            }
        });
        add(editCountryButton, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- finishButton ----
        finishButton.setText("Finish");
        finishButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                finishButtonMouseClicked(e);
            }
        });
        add(finishButton, new GridBagConstraints(4, 7, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(4, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
    }

    private JButton editContinentButton;
    private JButton editCountryButton;
    private JButton backButton;
    private JButton finishButton;
}
