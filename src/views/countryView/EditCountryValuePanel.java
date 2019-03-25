package views.countryView;

import controllers.GameEngine;
import controllers.MapGenerator;
import models.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**This panel is used to edit an existing country in an existing map*/
public class EditCountryValuePanel extends JPanel implements Observer {

    /**An arrayList for keeping the list of the country a particular point*/
    private ArrayList<String> countryArrayList;
    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    private GameMap gameMap;
    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**A MapGenerator object for using various map control event.*/
    private MapGenerator mapGenerator;
    /**A label to display "Edit Country:" string.*/
    private JLabel label1;
    /**A scroll pane for JList to make it scrollable.*/
    private JScrollPane scrollPane1;
    /**A list containing all the country names.*/
    private JList countryList;
    /**A label to display "Country Name" string.*/
    private JLabel label2;
    /**A fisex text field for current name of the continent*/
    private JTextField fixedCountryName;
    /**A label to display "Select Option:" string.*/
    private JLabel label6;
    /**A comboBox for having different edit options*/
    private JComboBox<String> featureBox;
    /**A label to display "Name:" string.*/
    private JLabel label3;
    /**A text field used to get the user input for country name.*/
    private JTextField countryName;
    /**A label to display "Continent:" string.*/
    private JLabel label4;
    /**A text field used to get the user input for continent name.*/
    private JTextField continentName;
    /**A label to display "Neighbour:" string.*/
    private JLabel label5;
    /**A text field used to get the user input for neighbour name.*/
    private JTextField neighboutName;
    /**A button to add a new Neighbour.*/
    private JButton addNeighbourButton;
    /**A button to remove Neighbour.*/
    private JButton removeNegihbourButton;
    /**A button to perform any name change.*/
    private JButton editButton;
    /**A button for going to back button.*/
    private JButton backButton;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public EditCountryValuePanel(GameEngine gameEngine, JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        mapGenerator = gameEngine.getMapGenerator();
        gameMap = gameEngine.getGameState().getGameMapObject();
        countryArrayList = mapGenerator.getListOfCountries();
        initComponents();
        countryList.setListData(countryArrayList.toArray());
    }

    /**A list selection event on the countryList to display the selected name
     * @param e is a ListSelectionEvent object to get all the details regarding the event.*/
    private void countryListMouseClicked(MouseEvent e) {
        fixedCountryName.setText((String)countryList.getSelectedValue());
    }

    /**An Action performed event on the featureBox for enabling the name text fields.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void featureBoxActionPerformed(ActionEvent e) {
        int index = featureBox.getSelectedIndex();
        switch (index){
            case 0:
                //--True--
                countryName.setEditable(true);
                editButton.setEnabled(true);
                //--false--
                continentName.setEditable(false);
                neighboutName.setEditable(false);
                addNeighbourButton.setEnabled(false);
                removeNegihbourButton.setEnabled(false);
                break;
            case 1:
                //--True--
                editButton.setEnabled(true);
                continentName.setEditable(true);
                //--false--
                countryName.setEditable(false);
                neighboutName.setEditable(false);
                addNeighbourButton.setEnabled(false);
                removeNegihbourButton.setEnabled(false);
                break;
            case 2:
                //--True--
                neighboutName.setEditable(true);
                addNeighbourButton.setEnabled(true);
                //--False--
                countryName.setEditable(false);
                editButton.setEnabled(false);
                continentName.setEditable(false);
                removeNegihbourButton.setEnabled(false);
                break;
            case 3:
                //--True--
                neighboutName.setEditable(true);
                removeNegihbourButton.setEnabled(true);
                //--False--
                countryName.setEditable(false);
                editButton.setEnabled(false);
                continentName.setEditable(false);
                addNeighbourButton.setEnabled(false);
                break;
        }
    }

    /**A mouse click event on the add neighbour Button used for add new neighbours.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addNeighbourButtonMouseClicked(MouseEvent e) {
        String neighbourName = neighboutName.getText();
        if(neighbourName.equals("")){
            JOptionPane.showMessageDialog(this,"Invalid argument");
        }else{
            String message = mapGenerator.addNeighbor((String) countryList.getSelectedValue(),neighbourName);
            JOptionPane.showMessageDialog(this,message);
        }
    }

    /**A mouse click event on the add neighbour Button used for removing neighbours.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void removeNegihbourButtonMouseClicked(MouseEvent e) {
        String neighbourName = neighboutName.getText();
        if(neighbourName.equals("")){
            JOptionPane.showMessageDialog(this,"Invalid argument");
        }else{
            String message = gameMap.removeNeighbor((String) countryList.getSelectedValue(),neighbourName);
            JOptionPane.showMessageDialog(this,message);
        }
    }

    /**A mouse click event on the add neighbour Button used for editing an existing neighbours.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void editButtonMouseClicked(MouseEvent e) {
        int index = featureBox.getSelectedIndex();
        String message = "";
        switch (index){
            case 0:
                String name = countryName.getText();
                if(name.equals(""))
                    message = "Invalid name";
                else
                    message = mapGenerator.changeCountryName((String) countryList.getSelectedValue(),name);
                break;
            case 1:
                String newContinentName = continentName.getText();
                if(newContinentName.equals(""))
                    message = "Invalid name";
                else
                    message = mapGenerator.changeCountryContinent((String) countryList.getSelectedValue(),newContinentName);
                break;
        }
        JOptionPane.showMessageDialog(this,message);
    }

    /**A mouse click event on the back Button used for going back to the previous panel.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        GameMap gameMap = gameEngine.getGameState().getGameMapObject();
        gameMap.deleteObserver(this);
        parent.setVisible(true);
    }

    /**Initialize all the control components with their positions and panel layout.*/
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
        featureBox.addActionListener(e -> featureBoxActionPerformed(e));
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

    @Override
    public void update(Observable observable, Object o) {
        countryArrayList = mapGenerator.getListOfCountries();
        initComponents();
        countryList.setListData(countryArrayList.toArray());
    }
}
