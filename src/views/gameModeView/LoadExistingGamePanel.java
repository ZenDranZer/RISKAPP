package views.gameModeView;

import controllers.GameEngine;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/*
 * Created by JFormDesigner on Mon Apr 08 16:23:28 EDT 2019
 */



/**
 * @author Jil Mehta
 */
public class LoadExistingGamePanel extends JPanel {

    private GameEngine gameEngine;
    private JPanel parent;

    public LoadExistingGamePanel(GameEngine gameEngine, JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
        loadFilePath.addChoosableFileFilter(new FileNameExtensionFilter("MAP file only", "map"));
    }

    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        loadFilePath = new JFileChooser();
        loadButton = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Load Existing Game");
        label1.setFont(new Font("Ubuntu", Font.PLAIN, 30));
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        label2.setText("Select File:");
        label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 10f));
        add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- loadFilePath ----
        loadFilePath.setControlButtonsAreShown(false);
        add(loadFilePath, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- loadButton ----
        loadButton.setText("Load");
        add(loadButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
    }
    private JLabel label1;
    private JLabel label2;
    private JFileChooser loadFilePath;
    private JButton loadButton;
}
