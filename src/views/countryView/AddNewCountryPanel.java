package views.countryView;

import controllers.GameEngine;
import controllers.MapGenerator;
import models.GameMap;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**This panel is used to add the. country to an existing map.*/
public class AddNewCountryPanel extends JPanel implements Observer {

    /**An arrayList maintaining the current state of the neighbour list of a country.*/
    private ArrayList<String> neighbourList;
    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**A label to display "Add Country:" string.*/
    private JLabel label1;
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
    /**A label to maintain neighbour list.*/
    private JLabel neigbourList;
    /**A button for going to back button.*/
    private JButton backButton;
    private JLabel countryLabel;
    private JScrollPane scrollPane1;
    private JList countryList;
    private JComboBox continentCombobox;
    private JScrollPane scrollPane2;
    private JList countryNeighbourList;
    private JLabel neighbourLabel;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public AddNewCountryPanel(GameEngine gameEngine,JPanel parent) {
        neighbourList = new ArrayList<>();
        this.gameEngine = gameEngine;
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        this.parent = parent;
        initComponents();
        Set<String> continentHashMap = gameEngine.getGameState().getGameMapObject().getContinentHashMap().keySet();
        String[] continentList = continentHashMap.toArray(new String[continentHashMap.size()]);
        continentCombobox.setModel(new DefaultComboBoxModel<>(continentList));
        countryList.setListData(mapGenerator.getListOfCountries().toArray());
    }

    /**A mouse click event on the add neighbour Button for adding a neighbour.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addNeighbourButtonMouseClicked(MouseEvent e) {
        neigbourList.setText(neigbourList.getText() + "\n" + neighbourField.getText());
        neighbourList.add(neighbourField.getText());
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

    private void countryListValueChanged(ListSelectionEvent e) {
        Set<String> neighbours = gameEngine.getGameState().getGameMapObject().getCountryHashMap()
                .get((String)countryList.getSelectedValue()).getNeighbouringCountries().keySet();
        String[] neighList = neighbours.toArray(new String[neighbours.size()]);
        countryNeighbourList.setListData(neighList);
    }

    /**A mouse click event on the back Button used for going back to the previous panel.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        GameMap gameMap = gameEngine.getGameState().getGameMapObject();
        gameMap.deleteObserver(this);
        container.remove(this);
        parent.setVisible(true);
    }

    /**Initialize all the control components with their positions and panel layout.*/
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
        countryLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        countryList = new JList();
        continentCombobox = new JComboBox();
        scrollPane2 = new JScrollPane();
        countryNeighbourList = new JList();
        neighbourLabel = new JLabel();
        backButton = new JButton();

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 139, 0, 143, 0, 113, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
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
                new Insets(0, 0, 0, 5), 0, 0));

        //---- countryLabel ----
        countryLabel.setText("Countries:");
        add(countryLabel, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- neighbourLabel ----
        neighbourLabel.setText("Neighbour:");
        add(neighbourLabel, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {

            //---- countryList ----
            countryList.addListSelectionListener(e -> countryListValueChanged(e));
            scrollPane1.setViewportView(countryList);
        }
        add(scrollPane1, new GridBagConstraints(5, 1, 1, 5, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(countryNeighbourList);
        }
        add(scrollPane2, new GridBagConstraints(7, 1, 1, 5, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        add(continentCombobox, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        }

    @Override
    public void update(Observable observable, Object o) {
        countryList.setListData(gameEngine.getGameState().getGameMapObject().getCountryHashMap().keySet().toArray());
    }
}
