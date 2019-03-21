package views;

import controllers.GameEngine;
import controllers.MapGenerator;
import controllers.TurnController;
import models.GameCountry;
import models.GameState;
import models.Player;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

/**
 * View for main game play includes the reinforcement , attack and fortification
 * phases for the game
 *
 */
public class GamePlay extends JPanel implements Observer {

	private JLabel lblPlayerName;
	private JLabel lblTurn;
	private JLabel lblAction;
	private JList lstPlayerCountries;
	private JList lstActionCountry;
	private JLabel lblReinforce;
	private JLabel lblArmiesPresent;
	private JLabel lblactionArmiesPresent;
	private JTextField txtReinforce;
	private JButton btnAdd;
	private JLabel lblRemainingArmies;

	private DefaultListModel<String> dlstPlayerCountries;
	private DefaultListModel<String> dlstActionCountries;
	private GameEngine objGameEngine;
	private TurnController objTurnController;
	private Player activePlayer;

	private JScrollPane scrollPane;
	private String phase = "initial";
	private JTextArea txtError;
	private int flag = 0;
	private JScrollPane scrollPane_1;
	private JLabel lblPhase;
	private JButton btnSkip;

	/**
	 * Renders the initial view of the panel
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
		lblReinforce.setBounds(64, 11, 211, 36);
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
							reinforceCountry();
						}
					}
				} else if (phase.equals("attack")) {
					updateFortificationPanel();

				} else if (phase.equals("fortify")) {
					fortify();
				}
			}
		});

		btnAdd.setBounds(391, 48, 104, 41);
		btnAdd.setVisible(false);
		add(btnAdd);

		dlstPlayerCountries = new DefaultListModel<String>();

		lblRemainingArmies = new JLabel("Remaining Armies :");
		lblRemainingArmies.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRemainingArmies.setBounds(578, 411, 211, 23);
		add(lblRemainingArmies);

		lblPhase = new JLabel("Initial Allocation");
		lblPhase.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPhase.setBounds(254, 15, 267, 32);
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
                            lstPlayerCountries.getSelectedValue()+ " armies : " + getArmiesPresent(lstPlayerCountries.getSelectedValue().toString()));
				}

				if (phase.equals("fortify") || phase.equals("attack")) {
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
					lblactionArmiesPresent.setText( lstActionCountry.getSelectedValue() + " armies : "
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

		btnSkip = new JButton("Skip");
		btnSkip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (phase.equals("fortify")) {
					phase = "reinforce";
					objGameEngine.setNextPlayer(activePlayer, false);
					activePlayer = objTurnController.getActivePlayer();
					updateReinforcementPanel();
					btnSkip.setVisible(false);
				} else {
					phase = "fortify";
					updateFortificationPanel();
				}
			}
		});
		btnSkip.setBounds(505, 48, 89, 41);
		add(btnSkip);
		btnSkip.setVisible(false);

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
		String selectedCountry = lstPlayerCountries.getSelectedValue().toString();

		txtReinforce.setText("");
		txtReinforce.setVisible(false);
		btnAdd.setVisible(false);

		objTurnController.placeArmy(activePlayer, selectedCountry, army);
	}

	/**
	 * Updates the panel for initial allocation of next player
	 */
	public void updateInitialPanel() {
		updateListElements();
		lblPlayerName.setText("Player Name : " + activePlayer.getName());
		displayRemainingArmies();
	}

	/**
	 * Adds reinforcement armies to a country based on the values selected in
	 * the UI
	 */
	public void reinforceCountry() {

		int reinforcements = Integer.parseInt(txtReinforce.getText());
		String selectedCountry = lstPlayerCountries.getSelectedValue().toString();

		lstPlayerCountries.setSelectedIndex(-1);
		txtReinforce.setText("");

		objTurnController.placeArmy(activePlayer, selectedCountry, reinforcements);
	}

	/**
	 * Updates the panel for reinforcement phase of the intended player
	 */
	public void updateReinforcementPanel() {

		scrollPane.setVisible(true);
		lstPlayerCountries.setVisible(true);
		scrollPane_1.setVisible(false);
		lstActionCountry.setVisible(false);
		lblAction.setVisible(false);
		lblPhase.setText("Reinforcement");
		lblReinforce.setText("Select country to reinforce");
		txtError.setText("");
		objTurnController.calculateNewArmies(activePlayer, objGameEngine.getMapGenerator());
		updateListElements();
		lblPlayerName.setText("Player Name : " + activePlayer.getName());
		displayRemainingArmies();
	}

	/**
	 * current player attacks the chosen player based on the value selection in
	 * UI
	 */
	public void attack() {
		phase = "attack";

	}

	public void updateAttackPanel(){
        // flag = 1;
        phase = "attack";
        lblPhase.setText("Attack");
        scrollPane.setVisible(true);
        lstPlayerCountries.setVisible(true);
        lstPlayerCountries.setSelectedIndex(-1);
        scrollPane_1.setVisible(true);
        lstActionCountry.setVisible(true);
        txtError.setText("attack phase");
        txtReinforce.setText("");
        txtReinforce.setVisible(false);
        btnAdd.setVisible(true);
        lblReinforce.setVisible(false);
        lblactionArmiesPresent.setText("");
        lblArmiesPresent.setText("");
        btnAdd.setText("Attack");

        btnSkip.setText("No Attack");
        btnSkip.setVisible(true);
    }

	/**
	 * Fortifies a given country based on user selection
	 * 
	 * @return true if all values are correct
	 */
	public void fortify() {

		// TODO : validate input here/controller
		int armiesToMove = Integer.parseInt(txtReinforce.getText());
		objTurnController.fortification(lstPlayerCountries.getSelectedValue().toString(),
				lstActionCountry.getSelectedValue().toString(), armiesToMove);
	}

	/**
	 * updates panel for fortification phase of the
	 */
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
		btnSkip.setVisible(true);
	}

	/**
	 * Update UI list for active player
	 */
	public void updateListElements() {
		if (dlstPlayerCountries.size() != 0) {
			dlstPlayerCountries.removeAllElements();
		}
		for (String countryName : activePlayer.getCountryNames()) {
			dlstPlayerCountries.addElement(countryName);
		}
	}

	/**
	 * Updates list of action countries
	 */
	public void updateActionCountries() {
		String seletedCountry = lstPlayerCountries.getSelectedValue().toString();
		GameCountry cntry = objGameEngine.getGameState().getGameMapObject().getCountryHashMap().get(seletedCountry);
		List<GameCountry> lstPlayerNeighbors;
		if (dlstActionCountries.size() != 0) {
			dlstActionCountries.removeAllElements();
		}

		if (phase.equalsIgnoreCase("attack")) {
			lstPlayerNeighbors = cntry.getNeighbouringCountries().values().stream()
					.filter(neighbor -> !neighbor.getCurrentPlayer().equals(activePlayer)).collect(Collectors.toList());

		} else {
			lstPlayerNeighbors = cntry.getNeighbouringCountries().values().stream()
					.filter(neighbor -> neighbor.getCurrentPlayer().equals(activePlayer)).collect(Collectors.toList());
		}
		for (GameCountry country : lstPlayerNeighbors) {
			dlstActionCountries.addElement(country.getCountryName());
		}
	}

	/**
	 * Updates the view to display the remaining armies of the current player
	 */
	public void displayRemainingArmies() {
		lblRemainingArmies.setText("Remaining Armies " + " : " + activePlayer.getRemainingArmies());
	}

	/**
	 * updates the view to display the name of the current player
	 * 
	 * @param activePlayer
	 *            Player which represents the current active player
	 */
	public void setActivePlayerName(Player activePlayer) {
		lblPlayerName.setText(activePlayer.getName());
	}

	/**
	 * Validate user inputs for army allocation and country selection
	 * 
	 * @return true is all inputs are correct otherwise false
	 */
	public boolean validateInput() {

		boolean isValid = true;

		if (txtReinforce.getText().isEmpty() || txtReinforce.getText().equals("0")) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "Enter some value");
		} else if (Integer.parseInt(txtReinforce.getText()) > activePlayer.getRemainingArmies()) {
			isValid = false;
			System.out.println("rem :: " + activePlayer.getRemainingArmies());
			txtError.setText(txtError.getText() + "\n" + "select number less than or equal to remaining armies");
		}

		if (!txtReinforce.getText().matches("[0-9]+")) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "Inavlid number");
		}
		if (lstPlayerCountries.getSelectedValue() == null) {
			isValid = false;
			txtError.setText(txtError.getText() + "\n" + "No country selected");
		} else if (activePlayer.getCountries().get(lstPlayerCountries.getSelectedIndex()).getArmiesStationed()
				+ Integer.parseInt(txtReinforce.getText()) > 12) {
			isValid = false;
			System.out.println("rem 2 :: " + activePlayer.getRemainingArmies());
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
			GameCountry country = objGameEngine.getGameState().getGameMapObject().getCountryHashMap().get(countryName);
			return country.getArmiesStationed() + "";
		}
		return "";
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		// if (arg0 instanceof GameState) {
		// System.out.println("Works!!");
		// }

		switch (phase) {
		case "initial":
			if (objGameEngine.getGameState().isAllocationComplete()) {
				phase = "reinforce";
				objGameEngine.setNextPlayer(activePlayer, false);
				activePlayer = objTurnController.getActivePlayer();
				updateReinforcementPanel();
			} else {
				objGameEngine.setNextPlayer(activePlayer, true);
				activePlayer = objTurnController.getActivePlayer();
				updateInitialPanel();
			}
			break;

		case "reinforce":
			displayRemainingArmies();
			if (activePlayer.getRemainingArmies() == 0 || activePlayer.isAllocationComplete()) {
				//attack();
                updateAttackPanel();
			}
			break;

		case "attack":
			break;
		case "fortify":
			phase = "reinforce";
			objGameEngine.setNextPlayer(activePlayer, false);
			activePlayer = objTurnController.getActivePlayer();
			updateReinforcementPanel();
			break;
		}
	}
}
