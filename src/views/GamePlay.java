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
	JLabel lblError;

	String phase= "initial";
	
	/**
	 * Create the panel.
	 */
	public GamePlay(GameEngine engine) {
		objGameEngine = engine;
		objTurnController = objGameEngine.getTurnComtroller();
		activePlayer = objTurnController.getActivePlayer();
		setLayout(null);
		this.setBounds(10, 10, 650, 410);
		lblPlayerName = new JLabel("Player Name :"+activePlayer.getName());
		lblPlayerName.setBounds(10, 360, 104, 32);
		add(lblPlayerName);

		lblTurn = new JLabel("Turn");
		lblTurn.setBounds(10, 326, 55, 23);
		add(lblTurn);

		JLabel lblLblreinforce = new JLabel("Select country to reinforce");
		lblLblreinforce.setBounds(64, 33, 148, 14);
		add(lblLblreinforce);

		lblError = new JLabel("");
		lblError.setBounds(242,249,246,68);
		add(lblError);

		txtReinforce = new JTextField();
		txtReinforce.setBounds(243, 60, 86, 20);
		add(txtReinforce);
		txtReinforce.setColumns(10);
		txtReinforce.setVisible(false);

		btnAdd = new JButton("Add Army");
		
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(phase.equals("initial"))
				{
					lblError.setText("");

					if (!txtReinforce.getText().isEmpty() && !txtReinforce.getText().equals("0") && txtReinforce.getText().matches("[0-9]+") && lstPlayerCountries.getSelectedValue() != null) {
						initialAllocation();
					}else {
						lblError.setText("Error!\n check selected country and number of armies");
					}
				}
				else
				{
					reinforceCountry();
				}
			}
		});
		
		btnAdd.setBounds(340, 59, 89, 23);
		btnAdd.setVisible(false);
		add(btnAdd);

		dlstPlayerCountries = new DefaultListModel<String>();
		
		lblRemainingArmies = new JLabel("Remaining Amries :");
		lblRemainingArmies.setBounds(405, 369, 134, 14);
		add(lblRemainingArmies);

		JScrollPane scrollPane = new JScrollPane();
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

	public void initialAllocation()
	{
		int army = Integer.parseInt(txtReinforce.getText());
		txtReinforce.setText("0");
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
		
		int allocatedPlayers =0;
		
		do {
			objGameEngine.setNextPlayer(activePlayer);
			activePlayer = objTurnController.getActivePlayer();

			if (activePlayer.getRemainingArmies() == 0) {
				objGameEngine.setNextPlayer(activePlayer);
				activePlayer = objTurnController.getActivePlayer();
				allocatedPlayers++;
			} else {
				break;
			}

		}while(allocatedPlayers < objGameEngine.getNumberOfPlayers());
		
		if(allocatedPlayers == objGameEngine.getNumberOfPlayers())
		{
			updateReinforcementPanel();
		}
		else
		{
			allocatedPlayers=0;
			updateInitialPanel();
		}
	}
	
	public void reinforceCountry() {
		// add army to country
		// update remaining army count
		// TurnController.placeArmy(activePlayer, GameCountry, armies to add)
		int reinforcements = Integer.parseInt(txtReinforce.getText());
		txtReinforce.setText("0");
		txtReinforce.setVisible(false);
		btnAdd.setVisible(false);

		String selectedCountry = lstPlayerCountries.getSelectedValue().toString();
		// update lblRemainingArmies with count

		// if remaining Armies == 0
		// hide controls for reinforcement and update view for fortification
		txtReinforce.setText("0");
		txtReinforce.setVisible(false);
		btnAdd.setVisible(false);
	}
	
	public void updateInitialPanel() {
		updateListElements();
		lblPlayerName.setText("Player Name : " + activePlayer.getName());
		displayRemainingArmies();
		// lstPlayerCountries = new
		// JList(activePlayer.getCountryNames().toArray());
	}
	
	public void updateReinforcementPanel()
	{
		lstPlayerCountries.setVisible(false);
	}
	
	public void updateListElements()
	{
		if(dlstPlayerCountries.size() != 0)
		{
			dlstPlayerCountries.removeAllElements();
		}
		for(String countryName : activePlayer.getCountryNames())
		{
			dlstPlayerCountries.addElement(countryName);
		}	
	}
	
	public void displayRemainingArmies()
	{
		lblRemainingArmies.setText("Remaining Armies "+ " : " +activePlayer.getRemainingArmies());
	}
	
	public void setActivePlayerName(Player active) {
		lblPlayerName.setText(active.getName());
	}

	
}
