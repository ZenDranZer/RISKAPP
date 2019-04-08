package views.gameModeView;

import controllers.GameEngine;
import views.GamePlay;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class LoadExistingGamePanel extends JPanel {

    private GameEngine gameEngine;
    private JPanel parent;

    public LoadExistingGamePanel(GameEngine gameEngine, JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        initComponents();
        loadFilePath.addChoosableFileFilter(new FileNameExtensionFilter("SAVE file only", "save"));
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void loadButtonMouseClicked(MouseEvent e) {
        try{
        if(!loadFilePath.getSelectedFile().getAbsolutePath().isEmpty()){
            String filePath = loadFilePath.getSelectedFile().getAbsolutePath();
            gameEngine.loadGame(filePath);
            Container container = this.getParent();
            GamePlay gamePlay = new GamePlay(gameEngine,this);
            gameEngine.getGameState().addObserver(gamePlay);
            gamePlay.setVisible(true);
            this.setVisible(false);
            container.add(gamePlay);
            container.revalidate();
        }
        }catch(Exception f){}
    }


    private void initComponents() {
        label1 = new JLabel();
        label2 = new JLabel();
        loadFilePath = new JFileChooser();
        loadButton = new JButton();
        backButton = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

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
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadButtonMouseClicked(e);
            }
        });
        add(loadButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- backButton ----
        backButton.setText("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backButtonMouseClicked(e);
            }
        });
        add(backButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
    }
    private JLabel label1;
    private JLabel label2;
    private JFileChooser loadFilePath;
    private JButton loadButton;
    private JButton backButton;
}
