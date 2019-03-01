package views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MapOptionPanel extends JPanel {
    public MapOptionPanel() {
        initComponents();
    }

    private void createbuttonMouseClicked(MouseEvent e) {
        this.setVisible(false);
        CreateContinentPanel createContinentPanel = new CreateContinentPanel();
        createContinentPanel.setVisible(true);
        Container container = getParent();
        container.add(createContinentPanel);
        container.revalidate();
    }

    private void initComponents() {
     
        createbutton = new JButton();
        loadButton = new JButton();

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

        //---- loadButton ----
        loadButton.setText("Load Map");
        add(loadButton, new GridBagConstraints(5, 7, 1, 3, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
    }

    private JButton createbutton;
    private JButton loadButton;
}
