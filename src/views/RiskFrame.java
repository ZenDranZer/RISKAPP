package views;

import controllers.GameEngine;
import views.gameModeView.GameModePanel;
import views.optionView.MapOptionPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *RiskFrame class for initial view
 */
public class RiskFrame extends JFrame {
    public RiskFrame() {
        gameEngine = new GameEngine();
        initComponents();
    }

    private GameEngine gameEngine;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JButton startButton;
    private JButton mapEditorButton;
    private JButton cancelButton;

    /**A listener function for mouse click event on cancelButton
     * @param e is a mouse event object for event details.*/
    private void cancelButtonMouseClicked(MouseEvent e) {
        System.exit(0);
    }

    /**A listener function for mouse click event on startButton
     * @param e is a mouse event object for event details.*/
    private void startButtonMouseClicked(MouseEvent e) {
        Container container = getContentPane();
        GameModePanel gamePanel = new GameModePanel(gameEngine,dialogPane);
        gamePanel.setVisible(true);
        dialogPane.setVisible(false);
        container.add(gamePanel);
        container.revalidate();
        revalidate();
    }

    /**A listener function for mouse click event on mapEditorButton
     * @param e is a mouse event object for event details.*/
    private void mapEditorButtonMouseClicked(MouseEvent e) {
        Container container = getContentPane();
        MapOptionPanel mapOptionPanel = new MapOptionPanel(gameEngine,dialogPane);
        mapOptionPanel.setVisible(true);
        dialogPane.setVisible(false);
        container.add(mapOptionPanel);
        container.revalidate();
        revalidate();
    }

    /**this method will initialize all the components on the frame*/
    private void initComponents() {
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        startButton = new JButton();
        mapEditorButton = new JButton();
        cancelButton = new JButton();
        
        setResizable(false);
        setTitle("RISK!");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 108, 0, 14, 0, 0, 0, 0, 80, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 75, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("Welcome to Risk!");
                contentPanel.add(label1, new GridBagConstraints(5, 2, 6, 3, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- startButton ----
                startButton.setText("Start");
                startButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        startButtonMouseClicked(e);
                    }
                });
                contentPanel.add(startButton, new GridBagConstraints(5, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- mapEditorButton ----
                mapEditorButton.setText("Map Editor");
                mapEditorButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mapEditorButtonMouseClicked(e);
                    }
                });
                contentPanel.add(mapEditorButton, new GridBagConstraints(10, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked(e);
                    }
                });
                contentPanel.add(cancelButton, new GridBagConstraints(5, 11, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(1300, 700);
        setLocationRelativeTo(getOwner());
    }
}
