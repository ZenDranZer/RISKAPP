package views;

import controllers.GameEngine;
import controllers.MapGenerator;
import controllers.TurnController;
import models.GameCountry;
import models.Player;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View for main game play 
 * includes the reinforcement , attack and fortification phases for the game
 * @author Sidhant Gupta
 *
 */
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
	JLabel lblPhase;

	/**
	 * Create the panel.
	 */
	public GamePlay(GameEngine engine) {
		objGameEngine = engine;
		objTurnController = objGameEngine.getTurmController();
		activePlayer = objTurnController.getActivePlayer();
		setLayout(null);
		this.setBounds(10, 10, 814, 503);
		lblPlayerName = new JLabel("Player Name :" + activePlayer.getName());
		lblPlayerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayerName.setBounds(10, 442, 267, 32);
		add(lblPlayerName);

		lblArmiesPresent = new JLabel("");
		lblArmiesPresent.setBounds(288, 125, 201, 32);
		add(lblArmiesPresent);

		lblactionArmiesPresent = new JLabel("");
		lblactionArmiesPresent.setBounds(288, 182, 201, 32);
		add(lblactionArmiesPresent);

		txtError = new JTextArea();
		txtError.setBackground(SystemColor.control);
		txtError.setWrapStyleWord(true);
		txtError.setLineWrap(true);
		txtError.setBounds(238, 249, 353, 108);
		txtError.setEditable(false);
		txtError.setFocusable(false);
		add(txtError);

		lblTurn = new JLabel("Turn");
		lblTurn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTurn.setBounds(10, 411, 92, 23);
		add(lblTurn);

		lblReinforce = new JLabel("Add army to country");
		lblReinforce.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReinforce.setBounds(64, 11, 254, 36);
		add(lblReinforce);

		txtReinforce = new JTextField();
		txtReinforce.setBounds(268, 58, 113, 20);
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
					if (fortify()) {
						phase = "reinforce";
						objGameEngine.setNextPlayer(activePlayer);
						activePlayer = objTurnController.getActivePlayer();
						updateReinforcementPanel();
					}
				}
			}
		});

		btnAdd.setBounds(417, 58, 104, 41);
		btnAdd.setVisible(false);
		add(btnAdd);

		dlstPlayerCountries = new DefaultListModel<String>();

		lblRemainingArmies = new JLabel("Remaining Armies :");
		lblRemainingArmies.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRemainingArmies.setBounds(578, 411, 211, 23);
		add(lblRemainingArmies);

		lblPhase = new JLabel("Initial Allocation");
		lblPhase.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPhase.setBounds(254, 399, 267, 32);
		add(lblPhase);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 58, 148, 299);
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
		scrollPane_1.setBounds(618, 61, 148, 296);
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
		lblAction.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAction.setBounds(618, 23, 148, 24);
		add(lblAction);

		scrollPane_1.setVisible(false);
		lstActionCountry.setVisible(false);
		displayRemainingArmies();
		lblAction.setVisible(false);
	}

	/**
	 * function for turn based initial allocation of armies for the players
	 */
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
		isAllocationComplete();
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
			lblPhase.setText("Reinforcement");
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
		isAllocationComplete();
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
		lblPhase.setText("Reinforcement");
		lblReinforce.setText("Select country to reinforce");
		txtError.setText("");
		objTurnController.calculateNewArmies(activePlayer);
		updateListElements();
		lblPlayerName.setText("Player Name : " + activePlayer.getName());
		displayRemainingArmies();
	}

	public void attack() {
		phase = "attack";
		flag = 1;
		
		lblPhase.setText("Attack");
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
		lblPhase.setText("Fortify");
		lblReinforce.setText("Select country to forify : ");
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
		GameCountry cntry = MapGenerator.countryHashMap.get(seletedCountry);

		if (dlstActionCountries.size() != 0) {
			dlstActionCountries.removeAllElements();
		}

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
		}else if (activePlayer.getCountries().get(lstPlayerCountries.getSelectedIndex()).getArmiesStationed()
				+ Integer.parseInt(txtReinforce.getText()) > 12) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "Max number of armies allocated to a country is 12");
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
			GameCountry country = MapGenerator.countryHashMap.get(countryName);
			return country.getArmiesStationed() + "";
		}
		return "";
	}
	
	/**
	 * Check whether all countries have maximum possible armies allocated
	 * @return true if allocation is successful
	 * 		   false if allocation fails
	 */
	public boolean isAllocationComplete() {
		for (GameCountry country : activePlayer.getCountries()) {
			if (country.getArmiesStationed() < 12) {
				return false;
			}
		}
		activePlayer.setRemainingArmies(0);
		return true;
	}
}
