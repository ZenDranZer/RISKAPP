package views;

import controllers.GameEngine;
import controllers.MapGenerator;
import controllers.TurnController;
import models.GameCountry;
import models.GameState;
import models.Player;
import views.miscellaneous.GraphView;
import views.miscellaneous.TradePanel;
import views.miscellaneous.WorldDominationView;

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
import java.util.*;

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
	private JCheckBox chckbxAllOutAttack;
	private JLabel lblWhiteDice;
	private JLabel lblRedDice;

	private DefaultListModel<String> dlstPlayerCountries;
	private DefaultListModel<String> dlstActionCountries;
	private GameEngine objGameEngine;
	private TurnController objTurnController;
	private Player activePlayer;
	private JScrollPane scrollPane;
	private String phase = "initial";
	private JTextArea txtError;
	private JScrollPane scrollPane_1;
	private JLabel lblPhase;
	private JButton btnSkip;

	private JRadioButton rdbtnRed1;
	private JRadioButton rdbtnRed2;
	private JRadioButton rdbtnRed3;

	private ButtonGroup grpRedDice;

	private JRadioButton rdbtnWhite1;
	private JRadioButton rdbtnWhite2;

	private ButtonGroup grpWhiteDice;
	private JLabel lblDefender;
	
	private JButton btnSwapcards;
	private JButton btnMapview;

	private WorldDominationView worldDominationView;

	private boolean countryWon;
	/**
	 * Renders the initial view of the panel
	 */
	public GamePlay(GameEngine engine) {
		objGameEngine = engine;
		objTurnController = objGameEngine.getTurmController();
		activePlayer = objGameEngine.getGameState().getActivePlayer();
		worldDominationView = new WorldDominationView(objGameEngine.getGameState());
		worldDominationView.setVisible(true);
		add(worldDominationView);
		setLayout(null);
		this.setBounds(10, 10, 883, 556);
		lblPlayerName = new JLabel("Player Name :" + activePlayer.getName());
		lblPlayerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayerName.setBounds(10, 442, 267, 32);
		add(lblPlayerName);

		lblArmiesPresent = new JLabel("");
		lblArmiesPresent.setBounds(288, 139, 300, 32);
		add(lblArmiesPresent);

		lblactionArmiesPresent = new JLabel("");
		lblactionArmiesPresent.setBounds(250, 182, 350, 32);
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
		lblTurn.setFont(new Font("Tahoma", Font.BOLD, 14));
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
					if (attackValidation()) {
						if (!chckbxAllOutAttack.isSelected()) {
							attack();
						} else {
							allOutAttack();
						}
					}
				} else if (phase.equals("fortify") && validateFortification()) {
					fortify();
				}
			}
		});

		btnAdd.setBounds(391, 48, 104, 32);
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
					lblArmiesPresent.setText(lstPlayerCountries.getSelectedValue() + " armies : "
							+ getArmiesPresent(lstPlayerCountries.getSelectedValue().toString()));
				}

				if (phase.equals("fortify") || phase.equals("attack")) {
					txtReinforce.setVisible(false);
					// btnAdd.setVisible(false);
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

		lblDefender = new JLabel("Defender : ");
		lblDefender.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDefender.setBounds(588, 442, 178, 25);
		add(lblDefender);
		lblDefender.setVisible(false);

		dlstActionCountries = new DefaultListModel<String>();

		lstActionCountry = new JList(dlstActionCountries);
		lstActionCountry.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				lblactionArmiesPresent.setVisible(true);
				lblactionArmiesPresent.setText("");
				if (lstActionCountry.getSelectedIndex() != -1) {
					lblactionArmiesPresent.setText(lstActionCountry.getSelectedValue() + " armies : "
							+ getArmiesPresent(lstActionCountry.getSelectedValue().toString()));
				}
				if (phase.equals("fortify")) {
					txtReinforce.setVisible(true);
					txtReinforce.setText("");
				}
				if (phase.equals("attack")) {
					btnAdd.setVisible(true);
					groupRadioSetVisibility(true);
					lblRedDice.setVisible(true);
					lblWhiteDice.setVisible(true);
					chckbxAllOutAttack.setVisible(true);
					if (lstActionCountry.getSelectedIndex() != -1) {
						lblDefender
								.setText("Defender : " + getOwnerName(lstActionCountry.getSelectedValue().toString()));
						lblDefender.setVisible(true);
					}
				}
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
					activePlayer = objGameEngine.getGameState().getActivePlayer();
					updateReinforcementPanel();
					btnSkip.setVisible(false);
				} else {
					if(phase.equals("attack") && countryWon)
					{
						// TODO:  add new RISK card to player
					}
					phase = "fortify";
					updateFortificationPanel();
				}
			}
		});
		btnSkip.setBounds(505, 48, 89, 32);
		add(btnSkip);

		rdbtnRed1 = new JRadioButton("1");
		rdbtnRed1.setBounds(218, 106, 43, 23);
		add(rdbtnRed1);

		rdbtnRed2 = new JRadioButton("2");
		rdbtnRed2.setBounds(268, 106, 43, 23);
		add(rdbtnRed2);

		rdbtnRed3 = new JRadioButton("3");
		rdbtnRed3.setBounds(313, 106, 43, 23);
		add(rdbtnRed3);

		grpRedDice = new ButtonGroup();
		grpRedDice.add(rdbtnRed1);
		grpRedDice.add(rdbtnRed2);
		grpRedDice.add(rdbtnRed3);

		rdbtnWhite2 = new JRadioButton("2");
		rdbtnWhite2.setBounds(569, 106, 43, 23);
		add(rdbtnWhite2);

		rdbtnWhite1 = new JRadioButton("1");
		rdbtnWhite1.setBounds(526, 106, 43, 23);
		add(rdbtnWhite1);

		grpWhiteDice = new ButtonGroup();
		grpWhiteDice.add(rdbtnWhite1);
		grpWhiteDice.add(rdbtnWhite2);

		lblRedDice = new JLabel("Red Dice");
		lblRedDice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRedDice.setBounds(222, 91, 67, 14);
		add(lblRedDice);

		lblWhiteDice = new JLabel("White Dice");
		lblWhiteDice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWhiteDice.setBounds(526, 91, 82, 14);
		add(lblWhiteDice);

		chckbxAllOutAttack = new JCheckBox("All out attack");
		chckbxAllOutAttack.setBounds(300, 87, 200, 23);
		add(chckbxAllOutAttack);
		
		btnSwapcards = new JButton("Swap RISK Cards");

		btnSwapcards.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showTradePanel();
			}
		});
		btnSwapcards.setBounds(64, 368, 148, 23);
		add(btnSwapcards);
		btnSwapcards.setVisible(true);

		add(btnSwapcards);

		btnMapview = new JButton("View Map");
        btnMapview.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                btnMapviewMouseClicked(arg0);
            }
        });
		btnMapview.setBounds(313, 449, 176, 23);
		add(btnMapview);
		btnMapview.setVisible(true);

		btnSkip.setVisible(false);
		lblRedDice.setVisible(false);
		lblWhiteDice.setVisible(false);
		chckbxAllOutAttack.setVisible(false);

		scrollPane_1.setVisible(false);
		lstActionCountry.setVisible(false);
		displayRemainingArmies();
		lblAction.setVisible(false);
		groupRadioSetVisibility(false);
	}


    private void btnMapviewMouseClicked(MouseEvent e){
        GraphView graphView = new GraphView(objGameEngine);
    }

	/**
	 *
	 * This functions is updating Number of Dice radio Button visibility
	 */

	public void groupRadioSetVisibility(boolean value) {
		rdbtnRed1.setVisible(value);
		rdbtnRed2.setVisible(value);
		rdbtnRed3.setVisible(value);
		rdbtnWhite1.setVisible(value);
		rdbtnWhite2.setVisible(value);
	}

	/**
	 * This function checks validations before each attack
	 * 
	 * @return boolean
	 */

	public boolean attackValidation() {

		if (lstPlayerCountries.getSelectedIndex() == -1 || lstPlayerCountries.getSelectedValue() == null) {
			txtError.setText("Please select a country");
			return false;
		}

		if (lstActionCountry.getSelectedIndex() == -1 || lstActionCountry.getSelectedValue() == null) {
			txtError.setText("Please select a country to attack");
			return false;
		}

		String selcetedAttackCountry = (String) lstPlayerCountries.getSelectedValue();
		String selectedActionCountry = (String) lstActionCountry.getSelectedValue();

		GameCountry attackCountry = objGameEngine.getGameState().getGameMapObject().countryHashMap
				.get(selcetedAttackCountry);
		GameCountry actionCountry = objGameEngine.getGameState().getGameMapObject().countryHashMap
				.get(selectedActionCountry);

		if (attackCountry.getArmiesStationed() < 2) {
			txtError.setText("Attacking country should have more than 1 army");
			return false;
		}

		if (!chckbxAllOutAttack.isSelected()) {
			if (grpRedDice.getSelection() != null && grpWhiteDice.getSelection() != null) {
				String selectedRedDice = Collections.list(grpRedDice.getElements()).stream().filter(a -> a.isSelected())
						.findFirst().get().getText();
				String selectedWhiteDice = Collections.list(grpWhiteDice.getElements()).stream()
						.filter(a -> a.isSelected()).findFirst().get().getText();
				if (Integer.parseInt(selectedRedDice) >= attackCountry.getArmiesStationed()
						|| Integer.parseInt(selectedWhiteDice) > actionCountry.getArmiesStationed()) {
					txtError.setText("Select Number of Dices should be less than armies in country");
					return false;
				}
			} else {
				txtError.setText("Select Number of Red and White Dices");
				return false;
			}
		}

		if (chckbxAllOutAttack.isSelected()) {
			txtError.setText("All out attack");
		}
		return true;
	}

	public void allOutAttack() {

		String selcetedAttackCountry = (String) lstPlayerCountries.getSelectedValue();
		String selectedActionCountry = (String) lstActionCountry.getSelectedValue();
		GameCountry attackCountry = objGameEngine.getGameState().getGameMapObject().countryHashMap
				.get(selcetedAttackCountry);
		GameCountry actionCountry = objGameEngine.getGameState().getGameMapObject().countryHashMap
				.get(selectedActionCountry);
		objGameEngine.getGameState().allOutAttack(actionCountry.getCurrentPlayer(), attackCountry, actionCountry);
		Player defender = actionCountry.getCurrentPlayer();
		String message = activePlayer.allOutAttack(defender, attackCountry, actionCountry);
		txtError.setText(message);
		if (activePlayer.getCardsHeld().size()>=5){
			showTradePanel();
		}
	}

	/**
	 * function for turn based initial allocation of armies for the players
	 */
	public void initialAllocation() {

		int army = Integer.parseInt(txtReinforce.getText());
		String selectedCountry = lstPlayerCountries.getSelectedValue().toString();

		btnAdd.setVisible(false);
		displayActions();
		objTurnController.placeArmy(activePlayer, selectedCountry, army);
	}

	/**
	 * Updates the panel for initial allocation of next player
	 */
	public void updateInitialPanel() {
		txtReinforce.setText("");
		txtReinforce.setVisible(false);
		updateListElements();
		lblPlayerName.setText("Player Name : " + activePlayer.getName());
		lblDefender.setVisible(false);
		displayRemainingArmies();
	}

	/**
	 * Adds reinforcement armies to a country based on the values selected in
	 * the UI
	 */
	public void reinforceCountry() {
		if (activePlayer.getCardsHeld().size()>=5){
			showTradePanel();
		}
		int reinforcements = Integer.parseInt(txtReinforce.getText());
		String selectedCountry = lstPlayerCountries.getSelectedValue().toString();

		lstPlayerCountries.setSelectedIndex(-1);
		txtReinforce.setText("");

		objTurnController.placeArmy(activePlayer, selectedCountry, reinforcements);
	}

	private void showTradePanel(){
		this.setVisible(false);
		TradePanel tradePanel = new TradePanel(objGameEngine,this);
		tradePanel.setVisible(true);
		Container container = this.getParent();
		activePlayer.addObserver(tradePanel);
		container.add(tradePanel);
		container.revalidate();
	}

	/**
	 * Updates the panel for reinforcement phase of the intended player
	 */
	public void updateReinforcementPanel() {

		btnAdd.setText("Reinforce");
		btnSkip.setVisible(false);
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
		lblDefender.setVisible(false);
		displayRemainingArmies();
		btnSwapcards.setVisible(true);
		lblactionArmiesPresent.setVisible(false);
		lblRemainingArmies.setVisible(true);
	}

	/**
	 * current player attacks the chosen player based on the value selection in
	 * UI
	 */
	public void attack() {
		phase = "attack";

		String selectedRedDice = Collections.list(grpRedDice.getElements()).stream().filter(a -> a.isSelected())
				.findFirst().get().getText();
		String selectedWhiteDice = Collections.list(grpWhiteDice.getElements()).stream().filter(a -> a.isSelected())
				.findFirst().get().getText();
		String selcetedAttackCountry = (String) lstPlayerCountries.getSelectedValue();
		String selectedActionCountry = (String) lstActionCountry.getSelectedValue();
		GameCountry attackCountry = objGameEngine.getGameState().getGameMapObject().countryHashMap
				.get(selcetedAttackCountry);
		GameCountry actionCountry = objGameEngine.getGameState().getGameMapObject().countryHashMap
				.get(selectedActionCountry);
		objGameEngine.getGameState().attack(actionCountry.getCurrentPlayer(), attackCountry, actionCountry,
				Integer.parseInt(selectedRedDice), Integer.parseInt(selectedWhiteDice));
		if (activePlayer.getCardsHeld().size()>=5){
			showTradePanel();
		}
	}

	/**
	 * This function updates the panel for getting ready for attach phase
	 */

	public void updateAttackPanel() {
		// flag = 1;
		btnSwapcards.setVisible(false);
		phase = "attack";
		lblPhase.setText("Attack");

		scrollPane.setVisible(true);
		lstPlayerCountries.setVisible(true);
		lstPlayerCountries.setSelectedIndex(-1);

		scrollPane_1.setVisible(true);
		lstActionCountry.setVisible(true);

		txtReinforce.setText("");
		txtReinforce.setVisible(false);
		btnAdd.setVisible(true);
		lblReinforce.setVisible(false);
		lblactionArmiesPresent.setText("");
		lblArmiesPresent.setText("");
		btnAdd.setText("Attack");
		lblRemainingArmies.setVisible(false);
		btnSkip.setText("Skip");
		btnSkip.setVisible(true);
		updateListElements();
		lblactionArmiesPresent.setVisible(false);
	}

	/**
	 * Fortifies a given country based on user selection
	 * 
	 * @return true if all values are correct
	 */
	public void fortify() {

		// TODO : validate input here/controller
		int armiesToMove = Integer.parseInt(txtReinforce.getText());
		objGameEngine.getGameState().fortification(lstPlayerCountries.getSelectedValue().toString(),
				lstActionCountry.getSelectedValue().toString(), armiesToMove);
	}

	public boolean validateFortification() {
		if (lstPlayerCountries.getSelectedIndex() == -1 || lstPlayerCountries.getSelectedValue() == null) {
			txtError.setText("Please select a country to fortify");
			return false;
		}

		if (lstActionCountry.getSelectedIndex() == -1 || lstActionCountry.getSelectedValue() == null) {
			txtError.setText("Please select a country to move armies from");
			return false;
		}

		String selcetedAttackCountry = (String) lstPlayerCountries.getSelectedValue();
		String selectedActionCountry = (String) lstActionCountry.getSelectedValue();

		GameCountry attackCountry = objGameEngine.getGameState().getGameMapObject().countryHashMap
				.get(selcetedAttackCountry);
		GameCountry actionCountry = objGameEngine.getGameState().getGameMapObject().countryHashMap
				.get(selectedActionCountry);

		if (actionCountry.getArmiesStationed() < 2) {
			txtError.setText("Fortifying country should have more than one army");
			return false;
		}

		if (txtReinforce.getText().isEmpty() || txtReinforce.getText() == null) {
			txtError.setText("Please enter armies to move");
			return false;
		}

		if (Integer.parseInt(txtReinforce.getText()) > actionCountry.getArmiesStationed() - 1) {
			txtError.setText("Fortifying country should have atleast 1 army left");
			return false;
		}

		return true;
	}

	/**
	 * updates panel for fortification phase of the
	 */
	public void updateFortificationPanel() {
		btnSwapcards.setVisible(false);
		phase = "fortify";
		lblPhase.setText("Fortify");
		lblReinforce.setText("Select country to forify : ");
		lblAction.setVisible(true);
		lblDefender.setVisible(false);
		updateListElements();
		scrollPane.setVisible(true);
		lstPlayerCountries.setVisible(true);
		lstPlayerCountries.setSelectedIndex(-1);
		scrollPane_1.setVisible(true);
		lstActionCountry.setVisible(true);
		btnSkip.setVisible(true);
		btnSkip.setText("skip");
		btnAdd.setText("Fortify");
		lblRedDice.setVisible(false);
		lblWhiteDice.setVisible(false);
		rdbtnRed1.setVisible(false);
		rdbtnRed2.setVisible(false);
		rdbtnRed3.setVisible(false);
		rdbtnWhite1.setVisible(false);
		rdbtnWhite2.setVisible(false);
		chckbxAllOutAttack.setVisible(false);
		txtReinforce.setVisible(true);
		txtError.setText("");
		lblactionArmiesPresent.setVisible(false);
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
		if (lstPlayerCountries.getSelectedIndex() != -1 && lstPlayerCountries.getSelectedValue() != null) {
			String seletedCountry = lstPlayerCountries.getSelectedValue().toString();
			if (!seletedCountry.isEmpty() && seletedCountry != null) {

				GameCountry cntry = objGameEngine.getGameState().getGameMapObject().getCountryHashMap()
						.get(seletedCountry);
				List<GameCountry> lstPlayerNeighbors;
				if (dlstActionCountries.size() != 0) {
					dlstActionCountries.removeAllElements();
				}

				if (phase.equalsIgnoreCase("attack")) {
					lstPlayerNeighbors = cntry.getNeighbouringCountries().values().stream()
							.filter(neighbor -> !neighbor.getCurrentPlayer().equals(activePlayer))
							.collect(Collectors.toList());

				} else {
					lstPlayerNeighbors = cntry.getNeighbouringCountries().values().stream()
							.filter(neighbor -> neighbor.getCurrentPlayer().equals(activePlayer))
							.collect(Collectors.toList());
				}
				for (GameCountry country : lstPlayerNeighbors) {
					dlstActionCountries.addElement(country.getCountryName());
				}
			}
		} else {
			if (dlstActionCountries.size() != 0) {
				dlstActionCountries.removeAllElements();
			}
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

	public String getOwnerName(String countryName) {
		if (countryName != null && countryName != "") {
			GameCountry country = objGameEngine.getGameState().getGameMapObject().getCountryHashMap().get(countryName);
			return country.getCurrentPlayer().getName() + "";
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
			displayActions();
			if (objGameEngine.getGameState().isAllocationComplete()) {
				phase = "reinforce";
				objGameEngine.setNextPlayer(activePlayer, false);
				activePlayer = objGameEngine.getGameState().getActivePlayer();
				updateReinforcementPanel();
			} else {
				objGameEngine.setNextPlayer(activePlayer, true);
				activePlayer = objGameEngine.getGameState().getActivePlayer();
				updateInitialPanel();
			}
			break;

		case "reinforce":
			displayRemainingArmies();
			if (activePlayer.getRemainingArmies() == 0 || activePlayer.isAllocationComplete()) {
				updateAttackPanel();
			}
			break;

		case "attack":
			updateAttackPanel();
			String status = arg1.toString();
			if(status.equals("winner"))
			{
				//TODO :display game win and UI cleared
			}
			else if(status.equals("success"))
			{
				countryWon = true;
			}
			break;
		case "fortify":
			phase = "reinforce";
			objGameEngine.setNextPlayer(activePlayer, false);
			activePlayer = objGameEngine.getGameState().getActivePlayer();
			updateReinforcementPanel();
			break;
		}
	}

	public void displayActions()
	{
		switch(phase)
		{
		case "initial":
			txtError.setText(activePlayer.getName() + " allocated " +txtReinforce.getText() + "armies to " + lstPlayerCountries.getSelectedValue().toString() );
			break;
		case "reinforce":
			txtError.setText(activePlayer.getName() + " reinforced "+lstPlayerCountries.getSelectedValue().toString()+ " with " +txtReinforce.getText() + "armies ");
			//status update for RISK Cards
			break;
		case "attack":
			//update for attacks based on status returned
			String actions = "";

			if(chckbxAllOutAttack.isSelected())
			{
				actions = activePlayer.getName() + " attacked " + lstActionCountry.getSelectedValue().toString() + " from "+ lstPlayerCountries.getSelectedValue().toString() ;
				actions = actions + "\n All out attack";
				if(countryWon)
				{
					actions = actions + "successful";
				}
				else
				{
					actions = actions + "not successful";
				}

			}
			else
			{

			}
			break;
		case "fortify":
			txtError.setText(activePlayer.getName() + " fortified "+lstPlayerCountries.getSelectedValue().toString()+ " from "+ lstActionCountry.getSelectedValue().toString() +"with "+txtReinforce.getText() + "armies ");
			break;
		}

	}
}
