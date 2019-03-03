package views.continentView;

import controllers.GameEngine;
import controllers.MapGenerator;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class EditContinentValuePanel extends JPanel {
    private ArrayList<String> continentsName;
    private GameEngine gameEngine;
    private JPanel parent;
    private MapGenerator mapGenerator;

    public EditContinentValuePanel(GameEngine gameEngine, JPanel parent) {
        this.gameEngine = gameEngine;
        this.parent = parent;
        mapGenerator = gameEngine.getMapGenerator();
        continentsName = mapGenerator.getListOfContinents();
        initComponents();
        continentList.setListData(continentsName.toArray());
        valueField.setText("1");
    }

    private void featureBoxActionPerformed(ActionEvent e) {
        int index = featureBox.getSelectedIndex();
        switch (index){
            case 0:
                nameField.setEditable(true);
                valueField.setEditable(false);
                break;
            case 1:
                nameField.setEditable(false);
                valueField.setEditable(true);
                break;
            case 2:
                nameField.setEditable(true);
                valueField.setEditable(true);
                break;
        }
    }

    private void continentListValueChanged(ListSelectionEvent e) {
        fixedNameField.setText((String)continentList.getSelectedValue());
    }

    private void backButtonMouseClicked(MouseEvent e) {
        Container container = this.getParent();
        container.remove(this);
        parent.setVisible(true);
    }

    private void editButtonMouseClicked(MouseEvent e) {
        String continentName = nameField.getText();
        int value = Integer.parseInt(valueField.getText());
        MapGenerator mapGenerator = gameEngine.getMapGenerator();
        String message = "";
        int index = featureBox.getSelectedIndex();
        switch (index){
            case 0:
                message += mapGenerator.changeContinentName((String)continentList.getSelectedValue(),continentName);
                break;
            case 1:
                message += mapGenerator.changeContinentValue((String)continentList.getSelectedValue(),value);
                break;
            case 2:
                message += mapGenerator.changeContinentName((String)continentList.getSelectedValue(),continentName);
                message += mapGenerator.changeContinentValue((String)continentList.getSelectedValue(),value);
                break;
        }
        JOptionPane.showMessageDialog(this,message);
        nameField.setText("");
        valueField.setText("1");
        continentsName = mapGenerator.getListOfContinents();
        continentList.setListData(continentsName.toArray());
        continentList.revalidate();
    }



    private void initComponents() {
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        continentList = new JList();
        label2 = new JLabel();
        fixedNameField = new JTextField();
        label3 = new JLabel();
        featureBox = new JComboBox<>();
        label4 = new JLabel();
        nameField = new JTextField();
        label5 = new JLabel();
        valueField = new JTextField();
        backButton = new JButton();
        editButton = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 176, 0, 139, 156, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 35, 32, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Edit Continent Values:");
        add(label1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {
            continentList.addListSelectionListener(e -> continentListValueChanged(e));
            scrollPane1.setViewportView(continentList);
        }
        add(scrollPane1, new GridBagConstraints(1, 1, 1, 7, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- label2 ----
        label2.setText("Name :");
        add(label2, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- fixedNameField ----
        fixedNameField.setEditable(false);
        add(fixedNameField, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label3 ----
        label3.setText("Field to Change :");
        add(label3, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- featureBox ----
        featureBox.setModel(new DefaultComboBoxModel<>(new String[] {
            "Name",
            "Value",
            "Name & value"
        }));
        featureBox.addActionListener(e -> featureBoxActionPerformed(e));
        add(featureBox, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label4 ----
        label4.setText("Name :");
        add(label4, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- nameField ----
        nameField.setEditable(false);
        add(nameField, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- label5 ----
        label5.setText("Value :");
        add(label5, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- valueField ----
        valueField.setEditable(false);
        add(valueField, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
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
        add(backButton, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- editButton ----
        editButton.setText("Edit");
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editButtonMouseClicked(e);
            }
        });
        add(editButton, new GridBagConstraints(4, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
    }

    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList continentList;
    private JLabel label2;
    private JTextField fixedNameField;
    private JLabel label3;
    private JComboBox<String> featureBox;
    private JLabel label4;
    private JTextField nameField;
    private JLabel label5;
    private JTextField valueField;
    private JButton backButton;
    private JButton editButton;
}