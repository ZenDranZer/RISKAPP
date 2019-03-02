package views;

import controllers.GameEngine;
import controllers.MapGenerator;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadMapPanel extends JPanel {
    private GameEngine gameEngine;
    private JPanel parent;
    public LoadMapPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
        mapFileChooser.setCurrentDirectory(new File("/home/sarvesh/APP_RISK_6441/src/mapFiles/"));
        mapFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MAP file only", "map"));
    }

    private void loadButtonMouseClicked(MouseEvent e) {
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        String mapFilePath = mapFileChooser.getSelectedFile().getAbsolutePath();
        try {
            String message = mapGenerator.readConquestFile(mapFilePath);
            gameEngine.setMapPath(mapFilePath);
            JOptionPane.showMessageDialog(this,message);
            if(message.equals("SUCCESS"))
                editMapButton.setEnabled(true);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this,"IO Exception");
        }
    }

    private void editMapButtonMouseClicked(MouseEvent e) {
        EditOptionPanel editOptionPanel = new EditOptionPanel(gameEngine,this);
        editOptionPanel.setVisible(true);
        setVisible(false);
        Container container = this.getParent();
        container.add(editOptionPanel);
        container.revalidate();
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void initComponents() {
        label1 = new JLabel();
        mapFileChooser = new JFileChooser();
        loadButton = new JButton();
        editMapButton = new JButton();
        backButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Load Map:");
        add(label1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- mapFileChooser ----
        mapFileChooser.setAcceptAllFileFilterUsed(false);
        mapFileChooser.setControlButtonsAreShown(false);
        add(mapFileChooser, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- loadButton ----
        loadButton.setText("Load");
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadButtonMouseClicked(e);
            }
        });
        add(loadButton, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- editMapButton ----
        editMapButton.setText("Edit Map");
        editMapButton.setEnabled(false);
        editMapButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editMapButtonMouseClicked(e);
            }
        });
        add(editMapButton, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
      }

   private JLabel label1;
    private JFileChooser mapFileChooser;
    private JButton loadButton;
    private JButton editMapButton;
    private JButton backButton;
   }
