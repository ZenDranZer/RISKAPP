package views.continentView;

import controllers.GameEngine;
import controllers.MapGenerator;
import views.countryView.AddCountry;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**CreateContinentPanel is a panel which is used to add a new continent in the map in creating new map.*/
public class CreateContinentPanel extends JPanel {

    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**A label to display "Create Continent" string.*/
    private JLabel label1;
    /**A label to display "Enter Continent Name:" string.*/
    private JLabel label2;
    /**A text field used to get the user input for continent name.*/
    private JTextField nameField;
    /**A label to display "Enter Value:" string.*/
    private JLabel label3;
    /**A text field for asking user for value of a continent */
    private JTextField valueField;
    /**A button to perform addition of a continent*/
    private JButton addContinent;
    /**A list which maintains the current state of the continent list*/
    private JList continentList;
    /**A button for finidhing the adding continent process.*/
    private JButton finishButton;
    /**A scroll pane for providing the list a scrollable*/
    private JScrollPane scrollPane;
    /**A button for going to back button.*/
    private JButton backButton;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public CreateContinentPanel(GameEngine gameEngine , JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    /**An mouse click event on the finish Button for going to next panel.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void finishButtonMouseClicked(MouseEvent e) {
        if(finishButton.isEnabled()){
        AddCountry addCountry = new AddCountry(gameEngine , this);
        addCountry.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(addCountry);
        container.revalidate();
        }
    }

    /**An mouse click event on the addContinent button used for performing addition operation of continent.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addContinentMouseClicked(MouseEvent e) {
        String continentName = nameField.getText();
        int value = Integer.parseInt(valueField.getText());
        if(continentName.equals("")){
            JOptionPane.showMessageDialog(this,"Wrong Input.");
        }else {
            MapGenerator mapGenerator = gameEngine.getMapGenerator();
            String message = mapGenerator.addContinent(continentName, value);
            JOptionPane.showMessageDialog(this.getParent().getParent(), message);
            continentList.setListData(mapGenerator.getListOfContinents().toArray());
            finishButton.setEnabled(true);
        }
        nameField.setText("");
        valueField.setText("1");
    }

    /**An mouse click event on the back Button used for going back to the previous panel.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    /**Initialize all the control components with their positions and panel layout.*/
    private void initComponents() {
    
        label1 = new JLabel();
        label2 = new JLabel();
        nameField = new JTextField();
        label3 = new JLabel();
        valueField = new JTextField();
        scrollPane = new JScrollPane();

        addContinent = new JButton();
        continentList = new JList();
        finishButton = new JButton();
        backButton = new JButton();
        valueField.setText("1");
        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 187, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Create Continent");
        add(label1, new GridBagConstraints(2, 0, 4, 2, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Enter Continent Name:");
        add(label2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        add(nameField, new GridBagConstraints(2, 3, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane ========
        {
            scrollPane.setViewportView(continentList);
        }
        add(scrollPane, new GridBagConstraints(7, 1, 1, 9, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //---- label3 ----
        label3.setText("Enter Value:");
        add(label3, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- valueField ----
        valueField.setText("1");
        add(valueField, new GridBagConstraints(2, 5, 4, 1, 0.0, 0.0,
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
        add(addContinent, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- finishButton ----
        finishButton.setText("Next");
        finishButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                finishButtonMouseClicked(e);
            }
        });
        add(finishButton, new GridBagConstraints(5, 8, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
    }
}
