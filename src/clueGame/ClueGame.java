package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame {
	private Board board;
	private ControlGUI control;
	private MyCards cards;
	private String boardConfigFile = "ClueLayout.csv"; 
	private String roomConfigFile = "ClueLegend.txt";
	private String weaponConfigFile = "ClueWeapons.txt" ; // Weapon file
	private String playerConfigFile = "CluePlayers.txt"; // Player file
	private String userName;
	
	/**
	 * Default constructor for the ClueGame class. Creates all necessary functionality for the game
	 */
	public ClueGame() {
		board = Board.getInstance();
	    board.setConfigFiles(boardConfigFile, roomConfigFile);
	    try {
	      board.initialize();
	    } catch (Exception e) {
	      System.out.println("Invalid config files, try again.");
	    }
	    userName = board.getUser().getPlayerName();
	    control = new ControlGUI();
	    board.setControlGUI(control);
	    cards = new MyCards(board.getUser().getMyCards());
	    
	    add(board, BorderLayout.CENTER);	
	    add(control, BorderLayout.SOUTH);
	    add(cards, BorderLayout.EAST);
	    //set size of the window
	  	setSize(850,850);
	  	
	  	//Display splash screen
	  	
	  	//initialize the first players turn
	  	board.movePlayer();
			    
	}
	
	/**
	 * Will return the human players name
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Will create a checkbox for the DetectiveNotes dialog
	 * @param cardType
	 * @return
	 */
	private JPanel makeDetectiveCheckBox(CardType cardType) {
		JPanel panel = new JPanel();
//		panel.setSize(256,256);
		panel.setLayout(new GridLayout(4,1));
		String title;
		ArrayList<String> list;
		
		switch (cardType) {
		case PERSON:
			title = "Person";
			list = board.getPlayerNames();
			break;
		case ROOM:
			title = "Room";
			list = board.getRooms();
			break;
		case WEAPON:
			title = "Weapon";
			list = board.getWeapons();
			break;
		default:
			title = "Error";
			list = new ArrayList<String>();
			break;
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), title));
		for (String card : list){
		    JCheckBox box = new JCheckBox(card);
		    panel.add(box);
		}
		
		return panel;
	}
	
	/**
	 * Will create a dropdown for the DetectiveNotes dialog
	 * @param cardType
	 * @return
	 */
	private JPanel makeDetectiveDropDown(CardType cardType) {
		JPanel panel = new JPanel();
//		panel.setSize(256,256);
		panel.setLayout(new GridLayout(4,1));
		
		String title;
		ArrayList<String> bufferList;
		String[] list;
		
		switch (cardType) {
		case PERSON:
			title = "Person Guess";
			bufferList = new ArrayList<String>(board.getPlayerNames());
			bufferList.add(0, "Unknown");
			list = bufferList.toArray(new String[bufferList.size()]);
			break;
		case ROOM:
			title = "Room Guess";
			bufferList = new ArrayList<String>(board.getRooms());
			bufferList.add(0, "Unknown");
			list = bufferList.toArray(new String[bufferList.size()]);
			break;
		case WEAPON:
			title = "Weapon Guess";
			bufferList = new ArrayList<String>(board.getWeapons());
			bufferList.add(0, "Unknown");
			list = bufferList.toArray(new String[bufferList.size()]);
			break;
		default:
			title = "Error";
			list = new String[0];
			break;
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), title));
		JComboBox<String> dropDown = new JComboBox<String>(list);
		panel.add(dropDown);
		
		return panel;
	}
	
	/**
	 * Function that will create the entire detective notes dialog
	 */
	private void makeDetectiveNotes() {
		JDialog panel = new JDialog();
//		panel.setSize(512,1024);
		panel.setLayout(new GridLayout(3,2));
		
		panel.add(makeDetectiveCheckBox(CardType.PERSON));
		panel.add(makeDetectiveDropDown(CardType.PERSON));
		
		panel.add(makeDetectiveCheckBox(CardType.ROOM));
		panel.add(makeDetectiveDropDown(CardType.ROOM));
		
		panel.add(makeDetectiveCheckBox(CardType.WEAPON));
		panel.add(makeDetectiveDropDown(CardType.WEAPON));
		
		panel.pack();
		panel.setVisible(true);
		
//		JOptionPane.showOptionDialog(null, panel, "Detective Notes", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
	}

	public static void main(String[] args) {
		ClueGame window = new ClueGame();
		window.setTitle("Clue Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem detectiveMenu = new JMenuItem(new AbstractAction("Detective Notes") {
			public void actionPerformed(ActionEvent ae) {
				window.makeDetectiveNotes();
			}
		});
		JMenuItem exit = new JMenuItem(new AbstractAction("Exit") {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		file.add(detectiveMenu);
		file.add(exit);
		menuBar.add(file);
		window.setJMenuBar(menuBar);
		
		window.setVisible(true);
		
		JOptionPane.showMessageDialog(window,"You are " + window.getUserName() + ". Press ok to continue", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	
}
