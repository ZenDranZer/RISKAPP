package views.countryView;

import controllers.GameEngine;
import models.GameMap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**This class is used to  show all the options for manipulating country information.*/
public class EditCountryPanel extends JPanel {

    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A button to redirect to new panel for adding new country*/
    private JButton addBouton;
    /**A button to redirect to new panel for removing a country*/
    private JButton removeButton;
    /**A button to redirect to new panel for editing an existing country*/
    private JButton editButton;
    /**A button for going to back button.*/
    private JButton backButton;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public EditCountryPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    /**A mouse click event on the addContinent Button for invoking new panel for adding new country.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void addBoutonMouseClicked(MouseEvent e) {
        AddNewCountryPanel addNewCountryPanel = new AddNewCountryPanel(gameEngine,this);
        addNewCountryPanel.setVisible(true);
        setVisible(false);
        GameMap gameMap = gameEngine.getGameState().getGameMapObject();
        gameMap.addObserver(addNewCountryPanel);
        Container container = this.getParent();
        container.add(addNewCountryPanel);
        container.revalidate();
    }

    /**A mouse click event on the addContinent Button for invoking new panel for removing country.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void removeButtonMouseClicked(MouseEvent e) {
        RemoveCountryPanel removeCountryPanel = new RemoveCountryPanel(gameEngine,this);
        removeCountryPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        GameMap gameMap = gameEngine.getGameState().getGameMapObject();
        gameMap.addObserver(removeCountryPanel);
        container.add(removeCountryPanel);
        container.revalidate();
    }

    /**A mouse click event on the addContinent Button for invoking new panel for editing existing country.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void editButtonMouseClicked(MouseEvent e) {
        EditCountryValuePanel editCountryValuePanel = new EditCountryValuePanel(gameEngine,this);
        editCountryValuePanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        GameMap gameMap = gameEngine.getGameState().getGameMapObject();
        gameMap.addObserver(editCountryValuePanel);
        container.add(editCountryValuePanel);
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
       
        addBouton = new JButton();
        removeButton = new JButton();
        editButton = new JButton();
        backButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- addBouton ----
        addBouton.setText("Add New Country");
        addBouton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addBoutonMouseClicked(e);
            }
        });
        add(addBouton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- removeButton ----
        removeButton.setText("Remove Country");
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeButtonMouseClicked(e);
            }
        });
        add(removeButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- editButton ----
        editButton.setText("Edit Country Value");
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editButtonMouseClicked(e);
            }
        });
        add(editButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
    }
}
