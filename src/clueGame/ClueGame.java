package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	private Board board;
	private ControlGUI control;
	private MyCards cards;
	private String boardConfigFile = "ClueLayout.csv"; 
	private String roomConfigFile = "ClueLegend.txt";
	private String weaponConfigFile = "ClueWeapons.txt" ; // Weapon file
	private String playerConfigFile = "CluePlayers.txt"; // Player file
	public ClueGame() {
		board = Board.getInstance();
	    board.setConfigFiles(boardConfigFile, roomConfigFile, weaponConfigFile, playerConfigFile);
	    try {
	      board.initialize();
	    } catch (Exception e) {
	      System.out.println("Invalid config files, try again.");
	    }
	    control = new ControlGUI();
	    cards = new MyCards();
	    add(board, BorderLayout.CENTER);	
	    add(control, BorderLayout.SOUTH);
	    add(cards, BorderLayout.EAST);
	    //set size of the window
	  	setSize(800,800);
	    
	}
	public static void main(String[] args) {
		ClueGame window = new ClueGame();
		window.setVisible(true);
	}
}
