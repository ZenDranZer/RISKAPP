package views;

import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controllers.*;

public class GameView extends JFrame {

	private JPanel contentPane;
	
	JPanel pnlGameStart;
	
	GameEngine objGameEngine;
	
	JLabel lblHeader;
	JButton btnStart;
	GameSetup pnlSetup;
	GamePlay pnlGamePlay;

	int noOfPlayers;
	
	/**
	 * Create the frame.
	 */
	public GameView(GameEngine gameEngine) {
		this.objGameEngine = gameEngine;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pnlGameStart = new JPanel();
		pnlGameStart.setBounds(0, 0, 672, 430);
		contentPane.add(pnlGameStart);
		pnlGameStart.setLayout(null);
		
		lblHeader = new JLabel("RISK Game");
		lblHeader.setBounds(69, 5, 579, 174);
		lblHeader.setFont(new Font("Tahoma", Font.PLAIN, 99));
		pnlGameStart.add(lblHeader);
		
		btnStart = new JButton("Start Game");
	
		btnStart.setBounds(259, 190, 136, 51);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlGameStart.add(btnStart);
		setTitle("RISK Game");
		
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getSetupConfig();
			}
		});
	}

	public void getSetupConfig()
	{
		Container cntSetup = this.getContentPane();
		pnlGameStart.setVisible(false);
		pnlSetup = new GameSetup();
		pnlSetup.setVisible(true);
		cntSetup.remove(pnlGameStart);
		cntSetup.add(pnlSetup);
		cntSetup.revalidate();
		
		revalidate();
	}
	
}
