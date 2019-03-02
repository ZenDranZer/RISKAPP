package views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import models.*;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import controllers.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;

public class GamePlay extends JPanel {

	JLabel lblPlayerName;
	JLabel lblTurn;
	JList lstPlayerCountries;
	private JTextField txtReinforce;
	JButton btnAdd;
	private JLabel lblRemainingArmies;

	DefaultListModel<String> dlstPlayerCountries;
	GameEngine objGameEngine;
	TurnController objTurnController;
	Player activePlayer;

	JScrollPane scrollPane;
	String phase = "initial";
	JTextArea txtError;
	int flag = 0;

	/**
	 * Create the panel.
	 */
	public GamePlay(GameEngine engine) {
		objGameEngine = engine;
		objTurnController = objGameEngine.getTurnComtroller();
		activePlayer = objTurnController.getActivePlayer();
		setLayout(null);
		this.setBounds(10, 10, 650, 410);
		lblPlayerName = new JLabel("Player Name :" + activePlayer.getName());
		lblPlayerName.setBounds(10, 360, 188, 32);
		add(lblPlayerName);


		txtError = new JTextArea();
		txtError.setBackground(SystemColor.control);
		txtError.setWrapStyleWord(true);
		txtError.setLineWrap(true);
		txtError.setBounds(222, 234, 267, 83);
		txtError.setEditable(false);
		txtError.setFocusable(false);
		add(txtError);
		
		lblTurn = new JLabel("Turn");
		lblTurn.setBounds(10, 326, 55, 23);
		add(lblTurn);

		JLabel lblLblreinforce = new JLabel("Select country to reinforce");
		lblLblreinforce.setBounds(64, 33, 198, 14);
		add(lblLblreinforce);

		txtReinforce = new JTextField();
		txtReinforce.setBounds(243, 60, 86, 20);
		add(txtReinforce);
		txtReinforce.setColumns(10);
		txtReinforce.setVisible(false);

		btnAdd = new JButton("Add Army");

		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				txtError.setText("");
			
				if (validateInput()) {
					if (phase.equals("initial")) {
						initialAllocation();
					} else if (phase.equalsIgnoreCase("reinforce")) {
						if (flag == 0) {
							flag = 1;
							objGameEngine.setNextPlayer(activePlayer);
							activePlayer = objTurnController.getActivePlayer();
							objTurnController.calculateNewArmies(activePlayer);
							updateReinforcementPanel();
						}
						reinforceCountry();
					}
				}
			}

		});

		btnAdd.setBounds(340, 60, 104, 41);
		btnAdd.setVisible(false);
		add(btnAdd);

		dlstPlayerCountries = new DefaultListModel<String>();

		lblRemainingArmies = new JLabel("Remaining Amries :");
		lblRemainingArmies.setBounds(405, 369, 188, 14);
		add(lblRemainingArmies);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(74, 58, 124, 259);
		add(scrollPane);

		updateListElements();
		lstPlayerCountries = new JList(dlstPlayerCountries);

		lstPlayerCountries.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				// branch between initialization and reinforcement
				txtReinforce.setVisible(true);
				btnAdd.setVisible(true);
			}
		});

		scrollPane.setViewportView(lstPlayerCountries);

		JLabel lblError = new JLabel("");
		lblError.setBounds(243, 249, 246, 68);
		add(lblError);
		
		displayRemainingArmies();
	}

	// call function on selected changed of JList
	public void selectCountry() {
		txtReinforce.setVisible(true);
		btnAdd.setVisible(true);
	}

	public void initialAllocation() {
		int army = Integer.parseInt(txtReinforce.getText());
		txtReinforce.setText("");
		txtReinforce.setVisible(false);
		btnAdd.setVisible(false);
		String selectedCountry = lstPlayerCountries.getSelectedValue().toString();

		txtReinforce.setText("0");
		txtReinforce.setVisible(false);
		btnAdd.setVisible(false);
		objTurnController.placeArmy(activePlayer, selectedCountry, army);
		activePlayer.addPlayerArmy(army);
		activePlayer.updateRemainingArmies(army);
		displayRemainingArmies();

		int allocatedPlayers = 0;

		do {
			objGameEngine.setNextPlayer(activePlayer);
			activePlayer = objTurnController.getActivePlayer();
			if (activePlayer.getRemainingArmies() == 0) {
				allocatedPlayers++;
			} else {
				break;
			}

		} while (allocatedPlayers < objGameEngine.getNumberOfPlayers());

		if (allocatedPlayers == objGameEngine.getNumberOfPlayers()) {
			phase = "reinforce";
			objGameEngine.setNextPlayer(activePlayer);
			activePlayer = objTurnController.getActivePlayer();
			objTurnController.calculateNewArmies(activePlayer);
			updateReinforcementPanel();
			flag = 1;
			System.out.println("Reinforcement");
		} else {
			allocatedPlayers = 0;
			updateInitialPanel();
		}
	}

	public void reinforceCountry() {

		int reinforcements = Integer.parseInt(txtReinforce.getText());
		String selectedCountry = lstPlayerCountries.getSelectedValue().toString();
		txtReinforce.setText("");
		objTurnController.placeArmy(activePlayer, selectedCountry, reinforcements);
		activePlayer.addPlayerArmy(reinforcements);
		activePlayer.updateRemainingArmies(reinforcements);
		displayRemainingArmies();
		if (activePlayer.getRemainingArmies() == 0) {
			flag = 1;
			objGameEngine.setNextPlayer(activePlayer);
			activePlayer = objTurnController.getActivePlayer();
			objTurnController.calculateNewArmies(activePlayer);
			updateReinforcementPanel();
		}
	}

	public void updateInitialPanel() {
		updateListElements();
		lblPlayerName.setText("Player Name : " + activePlayer.getName());
		displayRemainingArmies();
		// lstPlayerCountries = new
		// JList(activePlayer.getCountryNames().toArray());
	}

	public void updateReinforcementPanel() {
		updateListElements();
		lblPlayerName.setText("Player Name : " + activePlayer.getName());
		displayRemainingArmies();
	}

	public void updateListElements() {
		if (dlstPlayerCountries.size() != 0) {
			dlstPlayerCountries.removeAllElements();
		}
		for (String countryName : activePlayer.getCountryNames()) {
			dlstPlayerCountries.addElement(countryName);
		}
	}

	public void displayRemainingArmies() {
		lblRemainingArmies.setText("Remaining Armies " + " : " + activePlayer.getRemainingArmies());
	}

	public void setActivePlayerName(Player active) {
		lblPlayerName.setText(active.getName());
	}

	public boolean validateInput() {

		boolean isValid = true;

		if (txtReinforce.getText().isEmpty() && txtReinforce.getText().equals("0")) {
			isValid = false;

			txtError.setText(txtError.getText() + "\n" + "Enter some value");
		}

		if (!txtReinforce.getText().matches("[0-9]+")) {
			isValid = false;
	
			txtError.setText(txtError.getText() + "\n" + "Inavlid number");
		}
		if (lstPlayerCountries.getSelectedValue() == null) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "No country selected");
		}
		
		if (  Integer.parseInt(txtReinforce.getText()) > activePlayer.getRemainingArmies()) {
			isValid = false;
			
			txtError.setText(txtError.getText() + "\n" + "select number less than or equal to remaining armies");
		}

		return isValid;
	}
}
