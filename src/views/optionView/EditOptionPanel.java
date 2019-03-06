package views.optionView;

import controllers.GameEngine;
import views.continentView.EditContinentPanel;
import views.countryView.EditCountryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**This panel is to redirect the flow to either editing country or continent.*/
public class EditOptionPanel extends JPanel {

    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A button to redirect to new panel for editing a continent*/
    private JButton editContinentButton;
    /**A button to redirect to new panel for editing a country*/
    private JButton editCountryButton;
    /**A button to go back to previous panel*/
    private JButton backButton;

    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public EditOptionPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    /**A mouse click event on the editContinent Button for invoking new panel for editing continent.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void editContinentButtonMouseClicked(MouseEvent e) {
        EditContinentPanel editContinentPanel = new EditContinentPanel(gameEngine,this);
        editContinentPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(editContinentPanel);
        container.revalidate();
    }

    /**A mouse click event on the editContinent Button for invoking new panel for editing country.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void editCountryButtonMouseClicked(MouseEvent e) {
        EditCountryPanel editCountryPanel = new EditCountryPanel(gameEngine,this);
        editCountryPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(editCountryPanel);
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
        editContinentButton = new JButton();
        editCountryButton = new JButton();
        backButton = new JButton();

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- editContinentButton ----
        editContinentButton.setText("Edit Continents");
        editContinentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editContinentButtonMouseClicked(e);
            }
        });
        add(editContinentButton, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- editCountryButton ----
        editCountryButton.setText("Edit Countries");
        editCountryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editCountryButtonMouseClicked(e);
            }
        });
        add(editCountryButton, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(4, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
    }


}
