package views.countryView;

import controllers.GameEngine;
import controllers.MapGenerator;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class AddCountry extends JPanel {

    /**An arrayList maintaining the current state of the neighbour list of a country.*/
    private ArrayList<String> neighbourList;
    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**A label to display "Add Country:" string.*/
    private JLabel label1;
    /**A label to maintain neighbour list.*/
    private JLabel neigbourList;
    /**A label to display "Enter Name :" string.*/
    private JLabel label2;
    /**A text field used to get the user input for country name.*/
    private JTextField nameField;
    /**A label to display "Enter Continent Name:" string.*/
    private JLabel label4;
    /**A text field used to get the user input for continent name.*/
    private JTextField continentField;
    /**A label to display "Enter Neighbour:" string.*/
    private JLabel label3;
    /**A text field used to get the user input for neighbour name.*/
    private JTextField neighbourField;
    /**A button to perform addition of a neighbour*/
    private JButton addNeighbourButton;
    /**A button to perform addition of a country*/
    private JButton addCountryButton;
    /**A button to validate the map and write it to the .map file.*/
    private JButton finishButton;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public AddCountry(GameEngine gameEngine,JPanel parent) {
        neighbourList = new ArrayList<>();
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    /**A mouse click event on the add neighbour Button for adding a neighbour.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addNeighbourButtonMouseClicked(MouseEvent e) {
        neigbourList.setText(neigbourList.getText() + "\n" + neighbourField.getText() + " ");
        neighbourList.add(neighbourField.getText() + " ");
    }

    /**A mouse click event on the add country Button for adding a country.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addCountryButtonMouseClicked(MouseEvent e) {
        String countryName = nameField.getText();
        String continentName = continentField.getText();
        if(countryName.equals("") || continentName.equals("")){
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

    /**A mouse click event on the add finish Button for writing and validating the map file.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void finishButtonMouseClicked(MouseEvent e) {
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        String message  = mapGenerator.validateMap();
        JOptionPane.showMessageDialog(this.getParent(),message);
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    /**Initialize all the control components with their positions and panel layout.*/
    private void initComponents() {

        label1 = new JLabel();
        neigbourList = new JLabel();
        label2 = new JLabel();
        nameField = new JTextField();
        label4 = new JLabel();
        continentField = new JTextField();
        label3 = new JLabel();
        neighbourField = new JTextField();
        addNeighbourButton = new JButton();
        addCountryButton = new JButton();
        finishButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 169, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Add Country:");
        add(label1, new GridBagConstraints(2, 0, 4, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- neigbourList ----
        neigbourList.setText("Neighbour:");
        add(neigbourList, new GridBagConstraints(6, 1, 7, 12, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- label2 ----
        label2.setText("Enter Name :");
        add(label2, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(nameField, new GridBagConstraints(3, 3, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setText("Enter Continent Name:");
        add(label4, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(continentField, new GridBagConstraints(3, 4, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("Enter Neighbour:");
        add(label3, new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(neighbourField, new GridBagConstraints(3, 5, 2, 1, 0.0, 0.0,
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
        add(addNeighbourButton, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
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
        add(addCountryButton, new GridBagConstraints(4, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- finishButton ----
        finishButton.setText("Finish");
        finishButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                finishButtonMouseClicked(e);
            }
        });
        add(finishButton, new GridBagConstraints(4, 10, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));


    }

}
