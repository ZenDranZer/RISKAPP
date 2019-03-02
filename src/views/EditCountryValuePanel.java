package views;

import controllers.GameEngine;
import controllers.MapGenerator;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class EditCountryValuePanel extends JPanel {

    private ArrayList<String> countryArrayList;
    private GameEngine gameEngine;
    private JPanel parent;
    private MapGenerator mapGenerator;

    public EditCountryValuePanel(GameEngine gameEngine, JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        mapGenerator = gameEngine.getMapGenerator();
        countryArrayList = mapGenerator.getListOfCountry();
        initComponents();
        countryList.setListData(countryArrayList.toArray());
    }

    private void countryListMouseClicked(MouseEvent e) {
        fixedCountryName.setText((String)countryList.getSelectedValue());
    }

    private void featureBoxMouseClicked(MouseEvent e) {
        int index = featureBox.getSelectedIndex();
        switch (index){
            case 0:
                countryName.setEditable(true);
                editButton.setEnabled(true);
                continentName.setEditable(false);
                neighboutName.setEditable(false);
                addNeighbourButton.setEnabled(false);
                removeNegihbourButton.setEnabled(false);
                break;
            case 1:
                countryName.setEditable(false);
                editButton.setEnabled(true);
                continentName.setEditable(true);
                neighboutName.setEditable(false);
                addNeighbourButton.setEnabled(false);
                removeNegihbourButton.setEnabled(false);
                break;
            case 2:
                neighboutName.setEditable(true);
                addNeighbourButton.setEnabled(true);
                countryName.setEditable(false);
                editButton.setEnabled(false);
                continentName.setEditable(false);
                removeNegihbourButton.setEnabled(false);
                break;
            case 3:
                neighboutName.setEditable(true);
                removeNegihbourButton.setEnabled(true);
                countryName.setEditable(false);
                editButton.setEnabled(false);
                continentName.setEditable(false);
                addNeighbourButton.setEnabled(false);
                break;
        }
    }

    private void addNeighbourButtonMouseClicked(MouseEvent e) {
        String neighbourName = neighboutName.getText();
        if(neighbourName == ""){
            JOptionPane.showMessageDialog(this,"Invalid argument");
        }else{
            String message = mapGenerator.addNeighbor((String) countryList.getSelectedValue(),neighbourName);
            JOptionPane.showMessageDialog(this,message);
        }
    }

    private void removeNegihbourButtonMouseClicked(MouseEvent e) {
        String neighbourName = neighboutName.getText();
        if(neighbourName == ""){
            JOptionPane.showMessageDialog(this,"Invalid argument");
        }else{
            String message = mapGenerator.removeNeighbor((String) countryList.getSelectedValue(),neighbourName);
            JOptionPane.showMessageDialog(this,message);
        }
    }

    private void editButtonMouseClicked(MouseEvent e) {
        int index = featureBox.getSelectedIndex();
        String message = "";
        switch (index){
            case 0:
                String name = countryName.getText();
                if(name == "")
                    message = "Invalid name";
                else
                    message = mapGenerator.changeCountryName((String) countryList.getSelectedValue(),name);
                break;
            case 1:
                String newContinentName = continentName.getText();
                if(newContinentName == "")
                    message = "Invalid name";
                else
                    message = mapGenerator.changeCountryContinent((String) countryList.getSelectedValue(),newContinentName);
                break;
        }
        JOptionPane.showMessageDialog(this,message);
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void initComponents() {
        
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        countryList = new JList();
        label2 = new JLabel();
        fixedCountryName = new JTextField();
        label6 = new JLabel();
        featureBox = new JComboBox<>();
        label3 = new JLabel();
        countryName = new JTextField();
        label4 = new JLabel();
        continentName = new JTextField();
        label5 = new JLabel();
        neighboutName = new JTextField();
        addNeighbourButton = new JButton();
        removeNegihbourButton = new JButton();
        editButton = new JButton();
        backButton = new JButton();

        //======== this ========

    
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 149, 0, 128, 156, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Edit Country:");
        add(label1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {

            //---- countryList ----
            countryList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    countryListMouseClicked(e);
                }
            });
            scrollPane1.setViewportView(countryList);
        }
        add(scrollPane1, new GridBagConstraints(1, 2, 1, 8, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- label2 ----
        label2.setText("Country Name");
        add(label2, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- textField1 ----
        fixedCountryName.setEditable(false);
        add(fixedCountryName, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label6 ----
        label6.setText("Select Option:");
        add(label6, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- featureBox ----
        featureBox.setModel(new DefaultComboBoxModel<>(new String[] {
            "Name",
            "Continent",
            "Add Neighbour",
            "Remove Neighbour"
        }));
        featureBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                featureBoxMouseClicked(e);
            }
        });
        add(featureBox, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("Name:");
        add(label3, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- textField2 ----
        countryName.setEditable(false);
        add(countryName, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setText("Continent:");
        add(label4, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- textField3 ----
        continentName.setEditable(false);
        add(continentName, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setText("Neighbour:");
        add(label5, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- textField4 ----
        neighboutName.setEditable(false);
        add(neighboutName, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- addNeighbourButton ----
        addNeighbourButton.setText("Add Neighbour");
        addNeighbourButton.setEnabled(false);
        addNeighbourButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addNeighbourButtonMouseClicked(e);
            }
        });
        add(addNeighbourButton, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- removeNegihbourButton ----
        removeNegihbourButton.setText("Remove Neighbour");
        removeNegihbourButton.setEnabled(false);
        removeNegihbourButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeNegihbourButtonMouseClicked(e);
            }
        });
        add(removeNegihbourButton, new GridBagConstraints(4, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- editButton ----
        editButton.setText("Edit");
        editButton.setEnabled(false);
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editButtonMouseClicked(e);
            }
        });
        add(editButton, new GridBagConstraints(4, 8, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(4, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
    }

    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList countryList;
    private JLabel label2;
    private JTextField fixedCountryName;
    private JLabel label6;
    private JComboBox<String> featureBox;
    private JLabel label3;
    private JTextField countryName;
    private JLabel label4;
    private JTextField continentName;
    private JLabel label5;
    private JTextField neighboutName;
    private JButton addNeighbourButton;
    private JButton removeNegihbourButton;
    private JButton editButton;
    private JButton backButton;
}
