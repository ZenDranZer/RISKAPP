package views.continentView;

import controllers.GameEngine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**This is a  option panel for redirection to create, remove or edit the continent.*/
public class EditContinentPanel extends JPanel {

    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A button to redirect to new panel for adding new continent*/
    private JButton addContinentButton;
    /**A button to redirect to new panel for removing a continent*/
    private JButton removeContinentButton;
    /**A button to redirect to new panel for editing an existing continent*/
    private JButton editContinentButton;
    /**A button for going to back button.*/
    private JButton backButton;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public EditContinentPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    /**A mouse click event on the addContinent Button for invoking new panel for adding new continent.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addContinentButtonMouseClicked(MouseEvent e) {
        AddContinentPanel addContinent = new AddContinentPanel(gameEngine,this);
        addContinent.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(addContinent);
        container.revalidate();
    }

    /**A mouse click event on the addContinent Button for invoking new panel for removing continent.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void removeContinentButtonMouseClicked(MouseEvent e) {
        RemoveContinentPanel removeContinentPanel = new RemoveContinentPanel(gameEngine,this);
        removeContinentPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(removeContinentPanel);
        container.revalidate();
    }

    /**A mouse click event on the addContinent Button for invoking new panel for editing existing continent.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void editContinentButtonMouseClicked(MouseEvent e) {
        EditContinentValuePanel editContinentValuePanel = new EditContinentValuePanel(gameEngine,this);
        editContinentValuePanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(editContinentValuePanel);
        container.revalidate();
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
   
        addContinentButton = new JButton();
        removeContinentButton = new JButton();
        editContinentButton = new JButton();
        backButton = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- addContinentButton ----
        addContinentButton.setText("Add Continent");
        addContinentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addContinentButtonMouseClicked(e);
            }
        });
        add(addContinentButton, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- removeContinentButton ----
        removeContinentButton.setText("Remove Continent");
        removeContinentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeContinentButtonMouseClicked(e);
            }
        });
        add(removeContinentButton, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- editContinentButton ----
        editContinentButton.setText("Edit Continent");
        editContinentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editContinentButtonMouseClicked(e);
            }
        });
        add(editContinentButton, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(4, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

    }

}
