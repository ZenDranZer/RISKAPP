package views.optionView;

import controllers.GameEngine;
import models.GameMap;
import views.continentView.CreateContinentPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**This panel is used to redirect the flow to either create a map file or load an
 * existing one.*/
public class MapOptionPanel extends JPanel {

    GameEngine gameEngine;
    JPanel parent;
    private JButton createbutton;
    private JButton loadButton;
    private JButton backButton;


    /**A public constructor to initialize the whole panel with different controls
     * @param gameEngine a GameEngine object which is used for maintaining the current state of the game.
     * @param parent a previous panel which is being used to redirect back to the previous Panel.
     * */
    public MapOptionPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
    }

    /**A mouse click event on the editContinent Button for invoking new panel for redirecting to load map panel.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void loadButtonMouseClicked(MouseEvent e) {
        this.setVisible(false);
        LoadMapPanel loadMapPanel = new LoadMapPanel(gameEngine,this);
        loadMapPanel.setVisible(true);
        Container container = getParent();
        container.add(loadMapPanel);
        container.revalidate();
    }

    /**A mouse click event on the editContinent Button for invoking new panel for redirecting to create new map button.
     * @param e is a MouseEvent object to get all the details regarding the event.*/
    private void createbuttonMouseClicked(MouseEvent e) {
        this.setVisible(false);
        CreateContinentPanel createContinentPanel = new CreateContinentPanel(gameEngine,this);
        createContinentPanel.setVisible(true);
        Container container = getParent();
        GameMap gameMap = gameEngine.getGameState().getGameMapObject();
        gameMap.addObserver(createContinentPanel);
        container.add(createContinentPanel);
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
     
        createbutton = new JButton();
        loadButton = new JButton();
        backButton = new JButton();

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- createbutton ----
        createbutton.setText("Create New Map");
        createbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createbuttonMouseClicked(e);
            }
        });
        add(createbutton, new GridBagConstraints(5, 2, 1, 3, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(5, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //---- loadButton ----
        loadButton.setText("Load Map");
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadButtonMouseClicked(e);
            }
        });
        add(loadButton, new GridBagConstraints(5, 7, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
    }
}
