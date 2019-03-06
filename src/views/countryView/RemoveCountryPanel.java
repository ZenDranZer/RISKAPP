package views.countryView;

import controllers.GameEngine;
import controllers.MapGenerator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**This panel is used to remove country from an existing map.*/
public class RemoveCountryPanel extends JPanel {

    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A label to display "Remove Country:" string.*/
    private JLabel label1;
    /**A label to display "Enter Country Name :" string.*/
    private JLabel label2;
    /**A scroll pane for JList to make it scrollable*/
    private JScrollPane scrollPane;
    /**A list which maintains the current state of the country list*/
    private JList CountryList;
    /**A button for performing removal of the continent*/
    private JButton backButton;
    /**A button for going to back button.*/
    private JButton removeButton;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public RemoveCountryPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        initComponents();
        CountryList.setSelectedIndex(0);
        CountryList.setListData(mapGenerator.getListOfCountries().toArray());
    }

    /**A mouse click event on the back Button used for going back to the previous panel.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    /**A mouse click event on the remove Button to perform removal operation.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void removeButtonMouseClicked(MouseEvent e) {
        String country = (String)CountryList.getSelectedValue();
        if(country == ""){
            JOptionPane.showMessageDialog(this,"Wrong input");
        }else{
            MapGenerator mapGenerator = gameEngine.getMapGenerator();
            String message = mapGenerator.removeCountry(country);
            JOptionPane.showMessageDialog(this,message);
            CountryList.setListData(mapGenerator.getListOfCountries().toArray());
        }
    }

    /**Initialize all the control components with their positions and panel layout.*/
    private void initComponents() {
       
        label1 = new JLabel();
        label2 = new JLabel();
        scrollPane = new JScrollPane();
        CountryList = new JList();
        backButton = new JButton();
        removeButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 161, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Remove Country");
        add(label1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Enter Country Name:");
        add(label2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane ========
        {
            scrollPane.setViewportView(CountryList);
        }
        add(scrollPane, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //---- removeButton ----
        removeButton.setText("Remove");
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeButtonMouseClicked(e);
            }
        });
        add(removeButton, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
    }

}
