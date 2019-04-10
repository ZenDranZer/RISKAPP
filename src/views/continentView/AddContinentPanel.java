package views.continentView;

import controllers.GameEngine;
import controllers.MapGenerator;
import models.GameMap;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;


/**AddContinentPanel is a panel which is used to add a new continent into the map after reading map.*/
public class AddContinentPanel extends JPanel implements Observer {

    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**A label to display "Create Continent" string.*/
    private JLabel label1;
    /**A label to display "Enter Continent Name:" string*/
    private JLabel label2;
    /**A text field used to get the user input for continent name.*/
    private JTextField nameField;
    /**A lable to display "Enter Value:" string*/
    private JLabel label3;
    /**A text field for asking user for value of a continent */
    private JTextField valueField;
    /**A button to perform addition of a continent*/
    private JButton addContinent;
    /**A button for going to back button.*/
    private JButton backButton;
    private JLabel continentLabel;
    private JScrollPane scrollPane1;
    private JList continentList;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public AddContinentPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        initComponents();
        continentList.setListData(mapGenerator.getListOfContinents().toArray());
    }

    /**An mouse click event on the addContinent button used for performing addition operation of continent.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addContinentMouseClicked(MouseEvent e) {
        String continentName = nameField.getText();
        int noOfArmies = Integer.parseInt(valueField.getText());
        if(continentName.equals("")){
            JOptionPane.showMessageDialog(this,"Invalid argument");
        }else{
            MapGenerator mapGenerator = gameEngine.getMapGenerator();
            String message = mapGenerator.addContinent(continentName,noOfArmies);
            JOptionPane.showMessageDialog(this,message);
        }
    }

    /**An mouse click event on the back Button used for going back to the previous panel.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
        GameMap gameMap = gameEngine.getGameState().getGameMapObject();
        gameMap.deleteObserver(this);
    }

    /**Initialize all the control components with their positions and panel layout.*/
    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        nameField = new JTextField();
        label3 = new JLabel();
        valueField = new JTextField();
        addContinent = new JButton();
        backButton = new JButton();
        scrollPane1 = new JScrollPane();
        continentList = new JList();
        continentLabel = new JLabel();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Create Continent");
        add(label1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- continentLabel ----
        continentLabel.setText("Continents:");
        add(continentLabel, new GridBagConstraints(4, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Enter Continent Name:");
        add(label2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        add(nameField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(continentList);
        }
        add(scrollPane1, new GridBagConstraints(4, 2, 3, 4, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("Enter Value:");
        add(label3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- valueField ----
        valueField.setText("1");
        add(valueField, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- addContinent ----
        addContinent.setText("Add Continent");
        addContinent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addContinentMouseClicked(e);
            }
        });
        add(addContinent, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
    }

    /**
     * updade observable modules
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        continentList.setListData(((GameMap) observable).getContinentHashMap().keySet().toArray());
    }
}
