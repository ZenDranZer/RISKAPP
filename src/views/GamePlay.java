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
import java.util.stream.Collector;
import java.util.stream.Collectors;

import controllers.*;
import javax.swing.event.ListSelectionListener;

import java.util.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamePlay extends JPanel {

	JLabel lblPlayerName;
	JLabel lblTurn;
	JLabel lblAction;
	JList lstPlayerCountries;
	JList lstActionCountry;
	JLabel lblReinforce;
	JLabel lblArmiesPresent;
	JLabel lblactionArmiesPresent;
	private JTextField txtReinforce;
	JButton btnAdd;
	private JLabel lblRemainingArmies;

	DefaultListModel<String> dlstPlayerCountries;
	DefaultListModel<String> dlstActionCountries;
	GameEngine objGameEngine;
	TurnController objTurnController;
	Player activePlayer;

	JScrollPane scrollPane;
	String phase = "initial";
	JTextArea txtError;
	int flag = 0;
	private JScrollPane scrollPane_1;

	/**
	 * Create the panel.
	 */
	public GamePlay(GameEngine engine) {
		objGameEngine = engine;
		objTurnController = objGameEngine.getTurnComtroller();
		activePlayer = objTurnController.getActivePlayer();
		setLayout(null);
		this.setBounds(10, 10, 671, 431);
		lblPlayerName = new JLabel("Player Name :" + activePlayer.getName());
		lblPlayerName.setBounds(10, 360, 188, 32);
		add(lblPlayerName);

		lblArmiesPresent = new JLabel("");
		lblArmiesPresent.setBounds(243, 121, 201, 32);
		add(lblArmiesPresent);

		lblactionArmiesPresent = new JLabel("");
		lblactionArmiesPresent.setBounds(243, 164, 201, 32);
		add(lblactionArmiesPresent);

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

		lblReinforce = new JLabel("Select country to reinforce");
		lblReinforce.setBounds(64, 33, 198, 14);
		add(lblReinforce);

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

				if (phase.equals("initial") || phase.equals("reinforce")) {
					if (validateInput()) {
						if (phase.equals("initial")) {
							initialAllocation();
						} else if (phase.equalsIgnoreCase("reinforce")) {
							if (flag == 0) {
								System.out.println(" button event listener condition called ");
								flag = 1;
								objGameEngine.setNextPlayer(activePlayer);
								activePlayer = objTurnController.getActivePlayer();
								objTurnController.calculateNewArmies(activePlayer);
								updateReinforcementPanel();
							}
							reinforceCountry();
						}
					}
				} else if (phase.equals("attack")) {
					updateFortificationPanel();

				} else if (phase.equals("fortify")) {

					// update army allocation
					if (fortify()) {

						// set next player and phase
						phase = "reinforce";
						objGameEngine.setNextPlayer(activePlayer);
						activePlayer = objTurnController.getActivePlayer();
						// objTurnController.calculateNewArmies(activePlayer);
						updateReinforcementPanel();
						// update panels
					}
				}
			}
		});

		btnAdd.setBounds(340, 60, 104, 41);
		btnAdd.setVisible(false);
		add(btnAdd);

		dlstPlayerCountries = new DefaultListModel<String>();

		lblRemainingArmies = new JLabel("Remaining Armies :");
		lblRemainingArmies.setBounds(405, 360, 188, 23);
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
				lblArmiesPresent.setText("");
				if (lstPlayerCountries.getSelectedIndex() != -1) {
					lblArmiesPresent.setText(
							"Armies present : " + getArmiesPresent(lstPlayerCountries.getSelectedValue().toString()));
				}
				
					
				if (phase.equals("fortify")) {
					txtReinforce.setVisible(false);
					btnAdd.setVisible(false);
					updateActionCountries();
				}
			}
		});

		scrollPane.setViewportView(lstPlayerCountries);

		JLabel lblError = new JLabel("");
		lblError.setBounds(243, 249, 246, 68);
		add(lblError);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(499, 61, 124, 259);
		add(scrollPane_1);

		dlstActionCountries = new DefaultListModel<String>();

		lstActionCountry = new JList(dlstActionCountries);
		lstActionCountry.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				lblactionArmiesPresent.setText("");
				if (lstActionCountry.getSelectedIndex() != -1) {
					lblactionArmiesPresent.setText("Fortifying Country armies  : "
							+ getArmiesPresent(lstActionCountry.getSelectedValue().toString()));
				}
				txtReinforce.setVisible(true);
				txtReinforce.setText("");
				btnAdd.setVisible(true);
			}
		});
		lstActionCountry.setBounds(490, 59, 124, 258);

		scrollPane_1.setViewportView(lstActionCountry);

		lblAction = new JLabel("Action");
		lblAction.setBounds(499, 33, 66, 17);
		add(lblAction);

		scrollPane_1.setVisible(false);
		lstActionCountry.setVisible(false);
		displayRemainingArmies();
		lblAction.setVisible(false);
	}

	// call function on selected changed of JList
	// public void selectCountry() {
	// txtReinforce.setVisible(true);
	// btnAdd.setVisible(true);
	// }

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

	public void updateInitialPanel() {
		updateListElements();
		lblPlayerName.setText("Player Name : " + activePlayer.getName());
		displayRemainingArmies();
	}

	public void reinforceCountry() {

		int reinforcements = Integer.parseInt(txtReinforce.getText());
		String selectedCountry = lstPlayerCountries.getSelectedValue().toString();
		lstPlayerCountries.setSelectedIndex(-1);

		txtReinforce.setText("");

		objTurnController.placeArmy(activePlayer, selectedCountry, reinforcements);
		activePlayer.addPlayerArmy(reinforcements);
		activePlayer.updateRemainingArmies(reinforcements);
		displayRemainingArmies();

		if (activePlayer.getRemainingArmies() == 0) {
			// TODO on complete initiate attack phase
			attack();
		}
	}

	public void updateReinforcementPanel() {
		scrollPane.setVisible(true);
		lstPlayerCountries.setVisible(true);

		scrollPane_1.setVisible(false);
		lstActionCountry.setVisible(false);
		lblAction.setVisible(false);

		txtError.setText("");
		objTurnController.calculateNewArmies(activePlayer);
		updateListElements();
		lblPlayerName.setText("Player Name : " + activePlayer.getName());
		displayRemainingArmies();
	}

	public void attack() {
		phase = "attack";
		flag = 1;

		scrollPane.setVisible(false);
		lstPlayerCountries.setVisible(false);
		txtError.setText("attack phase");
		txtReinforce.setText("");
		txtReinforce.setVisible(false);
		btnAdd.setVisible(true);
		lblReinforce.setVisible(false);
		lblactionArmiesPresent.setText("");
		lblArmiesPresent.setText("");
	}

	public boolean fortify() {

		GameCountry countryToFortify = MapGenerator.countryHashMap
				.get(lstPlayerCountries.getSelectedValue().toString());
		GameCountry fortifyFrom = MapGenerator.countryHashMap.get(lstActionCountry.getSelectedValue().toString());

		int armiesToMove = Integer.parseInt(txtReinforce.getText());

		if (armiesToMove > fortifyFrom.getArmiesStationed() - 1) {
			txtError.setText("Selected country should have atleast one army");
			return false;
		}
		countryToFortify.setArmies(countryToFortify.getArmiesStationed() + armiesToMove);
		fortifyFrom.setArmies(fortifyFrom.getArmiesStationed() - armiesToMove);

		txtError.setText("Fortification : " + countryToFortify.getCountryName() + " fortified by "
				+ fortifyFrom.getCountryName() + "\n Armies moved : " + armiesToMove);
		return true;
	}

	public void updateFortificationPanel() {
		phase = "fortify";

		lblAction.setVisible(true);
		scrollPane.setVisible(true);
		lstPlayerCountries.setVisible(true);
		lstPlayerCountries.setSelectedIndex(-1);

		scrollPane_1.setVisible(true);
		lstActionCountry.setVisible(true);
		lstPlayerCountries.setSelectedIndex(-1);
	}

	public void updateListElements() {
		if (dlstPlayerCountries.size() != 0) {
			dlstPlayerCountries.removeAllElements();
		}
		for (String countryName : activePlayer.getCountryNames()) {
			dlstPlayerCountries.addElement(countryName);
		}
	}

	public void updateActionCountries() {
		String seletedCountry = lstPlayerCountries.getSelectedValue().toString();

		if (dlstActionCountries.size() != 0) {
			dlstActionCountries.removeAllElements();
		}

		GameCountry cntry = MapGenerator.countryHashMap.get(seletedCountry);

		List<GameCountry> lstPlayerNeighbors = (List) cntry.getNeighbouringCountries().values().stream()
				.filter(neighbor -> neighbor.getCurrentPlayer().equals(activePlayer)).collect(Collectors.toList());

		for (GameCountry country : lstPlayerNeighbors) {
			dlstActionCountries.addElement(country.getCountryName());
		}
	}

	public void displayRemainingArmies() {
		lblRemainingArmies.setText("Remaining Armies " + " : " + activePlayer.getRemainingArmies());
	}

	public void setActivePlayerName(Player active) {
		lblPlayerName.setText(active.getName());
	}

	/**
	 * Validate user inputs for army allocation and country selection
	 * 
	 * @return true is all inputs are correct otherwise false
	 */
	public boolean validateInput() {

		boolean isValid = true;

		if (txtReinforce.getText().isEmpty() && txtReinforce.getText().equals("0")) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "Enter some value");
		} else if (Integer.parseInt(txtReinforce.getText()) > activePlayer.getRemainingArmies()) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "select number less than or equal to remaining armies");
		}

		if (!txtReinforce.getText().matches("[0-9]+")) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "Inavlid number");
		}
		if (lstPlayerCountries.getSelectedValue() == null) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "No country selected");
		}

		if (activePlayer.getCountries().get(lstPlayerCountries.getSelectedIndex()).getArmiesStationed()
				+ Integer.parseInt(txtReinforce.getText()) > 12) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "Max number of armies allocated to a country is 12");
		}

		if (Integer.parseInt(txtReinforce.getText()) > activePlayer.getRemainingArmies()) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "select number less than or equal to remaining armies");
		}
		return isValid;
	}

	/**
	 * Get armies present in a country based on country name
	 * 
	 * @param countryName
	 *            String name of the country
	 * @return number of armies present
	 */
	public String getArmiesPresent(String countryName) {

		if (countryName != null && countryName != "") {
			System.out.println("***" + countryName);
			GameCountry country = MapGenerator.countryHashMap.get(countryName);
			return country.getArmiesStationed() + "";
		}
		return "";
	}
}
