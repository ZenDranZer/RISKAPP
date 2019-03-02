package views.continentView;

import controllers.GameEngine;
import controllers.MapGenerator;
import views.countryView.AddCountry;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CreateContinentPanel extends JPanel {

    private GameEngine gameEngine;
    private JPanel parent;
    public CreateContinentPanel(GameEngine gameEngine , JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    private void finishButtonMouseClicked(MouseEvent e) {
        AddCountry addCountry = new AddCountry(gameEngine , this);
        addCountry.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(addCountry);
        container.revalidate();
    }

    private void addContinentMouseClicked(MouseEvent e) {
        String continentName = nameField.getText();
        int value = Integer.parseInt(valueField.getText());
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        String message = mapGenerator.addContinent(continentName,value);
        JOptionPane.showMessageDialog(this.getParent().getParent(),message);
        nameField.setText("");
        valueField.setText("1");
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void initComponents() {
    
        label1 = new JLabel();
        label2 = new JLabel();
        nameField = new JTextField();
        label3 = new JLabel();
        valueField = new JTextField();
        addContinent = new JButton();
        finishButton = new JButton();
        backButton = new JButton();
        valueField.setText("1");
        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Create Continent");
        add(label1, new GridBagConstraints(2, 0, 4, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label2 ----
        label2.setText("Enter Continent Name:");
        add(label2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(nameField, new GridBagConstraints(2, 3, 4, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label3 ----
        label3.setText("Enter Value:");
        add(label3, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(valueField, new GridBagConstraints(2, 5, 4, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- addContinent ----
        addContinent.setText("Add Continent");
        addContinent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addContinentMouseClicked(e);
            }
        });
        add(addContinent, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- finishButton ----
        finishButton.setText("Finish");
        finishButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                finishButtonMouseClicked(e);
            }
        });
        add(finishButton, new GridBagConstraints(5, 8, 1, 1, 0.0, 0.0,
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
    private JTextField nameField;
    private JLabel label3;
    private JTextField valueField;
    private JButton addContinent;
    private JButton finishButton;
    private JButton backButton;
}
