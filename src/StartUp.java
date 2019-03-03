import java.awt.EventQueue;
import java.io.IOException;

import controllers.*;
import models.*;
//import Views.*;
import views.GameView;
import views.RiskFrame;

import javax.swing.*;

// MAIN Class
public class StartUp {

	GameEngine objGameEngine;

	/*
	 * Initial setup 1. Render UI (CUI or GUI) and initial options a. Initial
	 * menus b. Game Start button /option
	 */
	public void initialization() {
		// TODO
		objGameEngine = new GameEngine();
	}

	/*
	 * Game Setup 1. Map setup / map editor 2. Initial allocation a. No of
	 * players b. Initial number of armies c. Country and army distribution 3.
	 * Render UI
	 */
	public void gameSetup() throws IOException {
		// TODO
		objGameEngine.initialise();
	}

	/*
	 * Game Loop
	 */
	public void startGame() throws IOException {
		initialization();
		gameSetup();
		// TODO : Start Game Engine
		// while game in play, for each turn :
		// 1. update state
		// 2. Render changes
	}

	public void CUI() throws IOException {
		System.out.println("RISK GAME :");
		startGame();
	}

	public void GUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RiskFrame riskFrame = new RiskFrame();
					riskFrame.setVisible(true);
					riskFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				//	GameView frame = new GameView();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) throws Exception {

		StartUp objRiskGame = new StartUp();
		objRiskGame.GUI();		
	}
}
