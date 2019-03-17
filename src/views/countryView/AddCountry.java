package views.countryView;

import controllers.GameEngine;
import controllers.MapGenerator;
import models.GameContinent;
import models.GameMap;
import views.StartGamePanel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;

/**This panel is used to show the interface for adding a new country in creating new map.*/
public class AddCountry extends JPanel implements Observer {

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
    /**A label to display "Enter Neighbour:" string.*/
    private JLabel label3;
    /**A text field used to get the user input for neighbour name.*/
    private JTextField neighbourField;
    /**A button to perform addition of a neighbour*/
    private JButton addNeighbourButton;
    /**A button to perform addition of a country*/
    private JButton addCountryButton;
    /**A button to validate the map and write it to the .map file.*/
    private JButton saveButton;
    /**A scroll pane for Jlist*/
    private JScrollPane scrollPaneList;
    /** a list to maintain the current state of the country list*/
    private JList countryListLabel;
    /**a label to display "Countries:" string*/
    private JLabel label5;
    /**a back button to go to previous panel*/
    private JButton backButton;
    private JScrollPane scrollPane1;
    private JList<String> countryNeighbourList;
    private JLabel neighbourLabel;
    private JButton startGameButton;

    private JComboBox continentCombobox;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public AddCountry(GameEngine gameEngine,JPanel parent) {
        neighbourList = new ArrayList<>();
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        countryListLabel.setListData(mapGenerator.getListOfCountries().toArray());
        Set<String> continentHashMap = gameEngine.getGameState().getGameMapObject().getContinentHashMap().keySet();
        String[] continentList = continentHashMap.toArray(new String[continentHashMap.size()]);
        continentCombobox.setModel(new DefaultComboBoxModel<>(continentList));
    }

    /**A mouse click event on the add neighbour Button for adding a neighbour.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addNeighbourButtonMouseClicked(MouseEvent e) {
        neigbourList.setText(neigbourList.getText() + "\n" + neighbourField.getText() + " ");
        neighbourList.add(neighbourField.getText());
    }

    /**A mouse click event on the add country Button for adding a country.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addCountryButtonMouseClicked(MouseEvent e) {
        String countryName = nameField.getText();
        String continentName = (String)continentCombobox.getSelectedItem();
        if(countryName.equals("") || continentName.equals("")){
            JOptionPane.showMessageDialog(this,"Value not added properly.");
        } else {
            MapGenerator mapGenerator = gameEngine.getMapGenerator();
            String message = mapGenerator.addCountry(continentName,countryName,neighbourList);
            JOptionPane.showMessageDialog(this.getParent(),message);
            nameField.setText("");
            neigbourList.setText("Neighbours:");
            neighbourField.setText("");
            neighbourList.clear();

        }
    }

    /**A mouse click event on the add finish Button for writing and validating the map file.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void saveButtonMouseClicked(MouseEvent e) {
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        String message;
        String fileName = (String)JOptionPane.showInputDialog(this,"Enter File Name :","File name",JOptionPane.YES_OPTION,null,null,null);
        message = mapGenerator.writeConquestFile(fileName);
        JOptionPane.showMessageDialog(this.getParent(),message);


    }

    private void countryListLabelValueChanged(ListSelectionEvent e) {
        Set<String> neighbours = gameEngine.getGameState().getGameMapObject().getCountryHashMap()
                                    .get((String)countryListLabel.getSelectedValue()).getNeighbouringCountries().keySet();
        String[] neighList = neighbours.toArray(new String[neighbours.size()]);
        countryNeighbourList.setListData(neighList);
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
        GameMap gameMap = gameEngine.getGameState().getGameMapObject();
        gameMap.deleteObserver(this);
    }

    private void startGameButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        String message  = mapGenerator.validateMap();
        if(message.equals("SUCCESS")) {
            this.setVisible(false);
            GameMap gameMap = gameEngine.getGameState().getGameMapObject();
            gameMap.deleteObserver(this);
            Container container = this.getParent();
            container.remove(this);
            StartGamePanel startGamePanel = new StartGamePanel(gameEngine,this,true);
            startGamePanel.setVisible(true);
            container.add(startGamePanel);
        }else {
            JOptionPane.showMessageDialog(this.getParent(), message);
        }
    }

    /**Initialize all the control components with their positions and panel layout.*/
    private void initComponents() {

        label1 = new JLabel();
        neigbourList = new JLabel();
        label2 = new JLabel();
        nameField = new JTextField();
        label4 = new JLabel();
        scrollPane1 = new JScrollPane();
        countryNeighbourList = new JList<>();
        label3 = new JLabel();
        label5 = new JLabel();
        neighbourLabel = new JLabel();
        continentCombobox = new JComboBox();
        neighbourField = new JTextField();
        scrollPaneList = new JScrollPane();
        countryListLabel = new JList();
        addNeighbourButton = new JButton();
        addCountryButton = new JButton();
        saveButton = new JButton();
        backButton = new JButton();
        startGameButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 169, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 157, 0, 116, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Add Country:");
        add(label1, new GridBagConstraints(2, 0, 4, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setText("Countries:");
        add(label5, new GridBagConstraints(13, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- neighbourLabel ----
        neighbourLabel.setText("Country Neighbour:");
        add(neighbourLabel, new GridBagConstraints(15, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- neigbourList ----
        neigbourList.setText("Neighbour:");
        add(neigbourList, new GridBagConstraints(6, 1, 7, 12, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //======== scrollPaneList ========
        {

            //---- countryListLabel ----
            countryListLabel.setModel(new AbstractListModel<String>() {
                String[] values = {
                        "Countries:"
                };
                @Override
                public int getSize() { return values.length; }
                @Override
                public String getElementAt(int i) { return values[i]; }
            });
            countryListLabel.addListSelectionListener(e -> countryListLabelValueChanged(e));
            scrollPaneList.setViewportView(countryListLabel);
        }
        add(scrollPaneList, new GridBagConstraints(13, 1, 1, 12, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //======== scrollPane1 ========
        {

            //---- countryNeighbourList ----
            countryNeighbourList.setModel(new AbstractListModel<String>() {
                String[] values = {
                        "Neighbour:"
                };
                @Override
                public int getSize() { return values.length; }
                @Override
                public String getElementAt(int i) { return values[i]; }
            });
            scrollPane1.setViewportView(countryNeighbourList);
        }
        add(scrollPane1, new GridBagConstraints(15, 1, 1, 12, 0.0, 0.0,
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

        //---- continentCombobox ----
        continentCombobox.setModel(new DefaultComboBoxModel<>(new String[] {
                "Continents:"
        }));
        add(continentCombobox, new GridBagConstraints(3, 4, 2, 1, 0.0, 0.0,
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

        //---- startGameButton ----
        startGameButton.setText("Start Game!");
        startGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startGameButtonMouseClicked(e);
            }
        });
        add(startGameButton, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(3, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- saveButton ----
        saveButton.setText("Save File");
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveButtonMouseClicked(e);
            }
        });
        add(saveButton, new GridBagConstraints(4, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

    }

    @Override
    public void update(Observable observable, Object o) {
        countryListLabel.setListData(((GameMap) observable).getCountryHashMap().keySet().toArray());
    }
}
