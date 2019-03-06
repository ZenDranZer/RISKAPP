package views.continentView;

import controllers.GameEngine;
import controllers.MapGenerator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**This panel shows the removal procedures for the continent.*/
public class RemoveContinentPanel extends JPanel {

    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**A label to display "Remove Continent:" string.*/
    private JLabel label1;
    /**A label to display "Enter Continent Name :" string.*/
    private JLabel label2;
    /**A scroll pane for JList to make it scrollable*/
    private JScrollPane scrollPane;
    /**A list which maintains the current state of the continent list*/
    private JList continentList;
    /**A button for performing removal of the continent*/
    private JButton removeButton;
    /**A button for going to back button.*/
    private JButton backButton;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public RemoveContinentPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        continentList.setListData(mapGenerator.getListOfContinents().toArray());
        continentList.setSelectedIndex(0);
    }

    /**A mouse click event on the remove Button to perform removal operation.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void removeButtonMouseClicked(MouseEvent e) {
        String continentName = (String)continentList.getSelectedValue();
        if(continentName.equals("")){
            JOptionPane.showMessageDialog(this,"Invalid argument");
        }else{
            MapGenerator mapGenerator = gameEngine.getMapGenerator();
            String message = mapGenerator.removeContinent(continentName);
            JOptionPane.showMessageDialog(this,message);
            continentList.setListData(mapGenerator.getListOfContinents().toArray());
        }
    }

    /**A mouse click event on the back Button used for going back to the previous panel.
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
        scrollPane = new JScrollPane();
        continentList = new JList();
        removeButton = new JButton();
        backButton = new JButton();

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 146, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Remove Continent:");
        add(label1, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Enter Continent Name :");
        add(label2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane ========
        {
            scrollPane.setViewportView(continentList);
        }
        add(scrollPane, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- removeButton ----
        removeButton.setText("Remove");
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeButtonMouseClicked(e);
            }
        });
        add(removeButton, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
       }

}
