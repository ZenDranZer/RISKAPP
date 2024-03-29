import views.RiskFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Drive for the project
 * Responsible for the initializing the Main view
 *
 */
public class StartUp {

	public void GUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RiskFrame riskFrame = new RiskFrame();
					riskFrame.setVisible(true);
					riskFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
