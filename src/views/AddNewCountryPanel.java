package views;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class AddNewCountryPanel extends JPanel {
    private ArrayList<String> neighbourList;
    private GameEngine gameEngine;
    private JPanel parent;
    public AddNewCountryPanel(GameEngine gameEngine,JPanel parent) {
        neighbourList = new ArrayList<>();
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    private void addNeighbourButtonMouseClicked(MouseEvent e) {
        neigbourList.setText(neigbourList.getText() + "\n" + neighbourField.getText());
        neighbourList.add(neighbourField.getText());
    }

    private void addCountryButtonMouseClicked(MouseEvent e) {
        String countryName = nameField.getText();
        String continentName = continentField.getText();
        if(countryName == "" || continentName == ""){
            JOptionPane.showMessageDialog(this,"Value not added properly.");
        } else {
            MapGenerator mapGenerator = gameEngine.getMapGenerator();
            String message = mapGenerator.addCountry(continentName,countryName,neighbourList);
            JOptionPane.showMessageDialog(this.getParent(),message);
            nameField.setText("");
            continentField.setText("");
            neigbourList.setText("Neighbours:");
            neighbourField.setText("");
            neighbourList.clear();
        }
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
        label4 = new JLabel();
        continentField = new JTextField();
        label3 = new JLabel();
        neighbourField = new JTextField();
        addNeighbourButton = new JButton();
        addCountryButton = new JButton();
        neigbourList = new JLabel();
        backButton = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 134, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Add Country:");
        add(label1, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Enter Name :");
        add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(nameField, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setText("Enter Continent Name:");
        add(label4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(continentField, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("Enter Neighbour:");
        add(label3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(neighbourField, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- addNeighbourButton ----
        addNeighbourButton.setText("Add Neighbour");
        addNeighbourButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addNeighbourButtonMouseClicked(e);
            }
        });
        add(addNeighbourButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- addCountryButton ----
        addCountryButton.setText("Add Country");
        addCountryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addCountryButtonMouseClicked(e);
            }
        });
        add(addCountryButton, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- neigbourList ----
        neigbourList.setText("Neighbour:");
        add(neigbourList, new GridBagConstraints(3, 0, 1, 6, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- backButton ----
        backButton.setText("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backButtonMouseClicked(e);
            }
        });
        add(backButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        }

    private JLabel label1;
    private JLabel label2;
    private JTextField nameField;
    private JLabel label4;
    private JTextField continentField;
    private JLabel label3;
    private JTextField neighbourField;
    private JButton addNeighbourButton;
    private JButton addCountryButton;
    private JLabel neigbourList;
    private JButton backButton;
  
}
