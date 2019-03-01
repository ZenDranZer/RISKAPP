package views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import models.*;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import controllers.*;

public class GamePlay extends JPanel {
	
	JLabel lblPlayerName;
	JLabel lblTurn;
	JList lstPlayerCountries;
	private JTextField txtReinforce;
	JButton btnAdd;
	private JLabel lblRemainingArmies;
	
	GameEngine objGameEngine;
	TurnController objTurnController;
	Player activePlayer;
	/**
	 * Create the panel.
	 */
	public GamePlay(GameEngine engine) {
		objGameEngine =engine;
		objTurnController = objGameEngine.getTurnComtroller();
		activePlayer = objTurnController.getActivePlayer();
		setLayout(null);
		this.setBounds(10, 10, 650, 410);
		lblPlayerName = new JLabel("Player Name");
		lblPlayerName.setBounds(10, 360, 104, 32);
		add(lblPlayerName);
		
		lblTurn = new JLabel("Turn");
		lblTurn.setBounds(10, 326, 55, 23);
		add(lblTurn);
		
		JLabel lblLblreinforce = new JLabel("Select country to reinforce");
		lblLblreinforce.setBounds(64, 33, 134, 14);
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
				reinforceCountry();
			}
		});
		btnAdd.setBounds(340, 59, 89, 23);
		btnAdd.setVisible(false);
		add(btnAdd);
		
		lblRemainingArmies = new JLabel("Remaining Amries :");
		lblRemainingArmies.setBounds(405, 369, 134, 14);
		add(lblRemainingArmies);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(74, 58, 124, 259);
		add(scrollPane);
		
		lstPlayerCountries = new JList(activePlayer.getCountryNames().toArray());
		scrollPane.setViewportView(lstPlayerCountries);

	}
	
	// call function on selected changed of JList
	public void selectCountry()
	{
		txtReinforce.setVisible(true);
		btnAdd.setVisible(true);
	}
	
	
	public void reinforceCountry()
	{	
		//add army to country 
		// update remaining army count 
		//TurnController.placeArmy(activePlayer, GameCountry, armies to add)
		
		txtReinforce.setText("0");
		txtReinforce.setVisible(false);
		btnAdd.setVisible(false);
		
		//update lblRemainingArmies with count
		
		//if remaining Armies == 0 
		// hide controls for reinforcement and update view for fortification
	}
	
	public void setActivePlayerName(Player active)
	{
		lblPlayerName.setText(active.getName());
	}
	
	public void getCountryNames()
	{
		
	}
}
