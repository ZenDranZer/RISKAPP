package views.optionView;

import controllers.GameEngine;
import controllers.MapGenerator;
import views.StartGamePanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class LoadMapPanel extends JPanel {

    /**GameEngine object to preserve the state of the game.*/
    private GameEngine gameEngine;
    /**A JPanel object for tracking the parent panel.*/
    private JPanel parent;
    /**A lable to show "Load Map:" string*/
    private JLabel label1;
    /**A file chooser for loading a new map file*/
    private JFileChooser mapFileChooser;
    /**A button for reading a file.*/
    private JButton readButton;
    /**A button for redirecting the flow to edit the map details.*/
    private JButton editMapButton;
    /**A button to reset all the data sets.*/
    private JButton resetButton;
    /**A button to validate and create map file.*/
    private JButton loadButton;
    /**A button to start the game after loading the file.*/
    private JButton startGameButton;

    private JButton backButton;


    public LoadMapPanel(GameEngine gameEngine,JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
        mapFileChooser.setCurrentDirectory(new File("/home/sarvesh/APP_RISK_6441/src/mapFiles/"));
        mapFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MAP file only", "map"));
    }

    private void readButtonMouseClicked(MouseEvent e) {
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        String mapFilePath = mapFileChooser.getSelectedFile().getAbsolutePath();
        try {
            String message = mapGenerator.readConquestFile(mapFilePath);
            gameEngine.setMapPath(mapFilePath);
            resetButton.setEnabled(true);
            JOptionPane.showMessageDialog(this,message);

            if(message.equals("SUCCESS")){
                editMapButton.setEnabled(true);
                loadButton.setEnabled(true);
            }
            else{

                if(mapGenerator.reSetAllocations().equals("SUCCESS")){
                    JOptionPane.showMessageDialog(this,"Map is being reset.");
                }
                else{
                    JOptionPane.showMessageDialog(this,"Some error in resetting map.");
                }

            }
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this,"IO Exception");
        }
    }

    private void editMapButtonMouseClicked(MouseEvent e) {
        if(editMapButton.isEnabled()){
            EditOptionPanel editOptionPanel = new EditOptionPanel(gameEngine,this);
            editOptionPanel.setVisible(true);
            setVisible(false);
            Container container = this.getParent();
            container.add(editOptionPanel);
            container.revalidate();
        }
    }

    private void loadButtonMouseClicked(MouseEvent e) {
        if(loadButton.isEnabled()){
            MapGenerator mapGenerator = gameEngine.getMapGenerator();
            String message = mapGenerator.validateMap();
            if(message.equals("SUCCESS")){
                message = mapGenerator.writeConquestFile();
                resetButton.setEnabled(true);
                startGameButton.setEnabled(true);
                if(message.equals("SUCCESS")) {
                    message = "Map loaded and written to file correctly";
                    JOptionPane.showMessageDialog(this,message);
                }
                else{
                    message="Map loaded correctly. Problems faced in writing the map";
                    JOptionPane.showMessageDialog(this,message);
                }
            }else
                JOptionPane.showMessageDialog(this,message);
        }
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void resetButtonMouseClicked(MouseEvent e) {
        if(resetButton.isEnabled()){
            MapGenerator mapGenerator = gameEngine.getMapGenerator();
            if(mapGenerator.reSetAllocations().equals("SUCCESS")){
                JOptionPane.showMessageDialog(this,"Map is being reset.");
            }
            else{
                JOptionPane.showMessageDialog(this,"Some error in resetting map.");
            }
            startGameButton.setEnabled(false);
        }
    }

    private void startGameButtonMouseClicked(MouseEvent e) {
        if(startGameButton.isEnabled()){
            Container container = this.getParent();
            StartGamePanel gamePanel = new StartGamePanel(gameEngine,this,true);
            gamePanel.setVisible(true);
            this.setVisible(false);
            container.add(gamePanel);
            container.revalidate();
        }
    }

    private void initComponents() {
        label1 = new JLabel();
        mapFileChooser = new JFileChooser();
        readButton = new JButton();
        editMapButton = new JButton();
        loadButton = new JButton();
        resetButton = new JButton();
        startGameButton = new JButton();
        backButton = new JButton();

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
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

        //---- readButton ----
        readButton.setText("Read");
        readButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                readButtonMouseClicked(e);
            }
        });
        add(readButton, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

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
                new Insets(0, 0, 5, 5), 0, 0));

        //---- resetButton ----
        resetButton.setText("Reset");
        resetButton.setEnabled(false);
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetButtonMouseClicked(e);
            }
        });
        add(resetButton, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- loadButton ----
        loadButton.setText("Load");
        loadButton.setEnabled(false);
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadButtonMouseClicked(e);
            }
        });
        add(loadButton, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

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

        //---- startGameButton ----
        startGameButton.setText("Start Game!");
        startGameButton.setEnabled(false);
        startGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startGameButtonMouseClicked(e);
            }
        });
        add(startGameButton, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

      }


}
