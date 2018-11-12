package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyCards extends JPanel{
	/**
	 * Default constructor will create panels for the three types of cards 
	 */
	public MyCards(ArrayList<Card> myCards) {
		//Create layout with 4 rows
		setLayout(new GridLayout(0,1));
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		
		JPanel people = CreateCardPanel("People", myCards, CardType.PERSON);
		JPanel rooms = CreateCardPanel("Rooms", myCards, CardType.ROOM);
		JPanel weapons = CreateCardPanel("Weapons", myCards, CardType.WEAPON);
		
		add(people);
		add(rooms);
		add(weapons);
		
	}
	/**
	 * Creates panel for each card type
	 * @param type
	 * @return
	 */
	private JPanel CreateCardPanel(String type, ArrayList<Card> cards, CardType cardType) {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), type));
		for (Card c : cards) {
			if (c.getCardType() == cardType) {
				JTextField text = new JTextField(c.getCardName());
				text.setEditable(false);
				panel.add(text);				
			}
		}
		
		return panel;
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Control");
		String boardConfigFile = "ClueLayout.csv"; 
		String roomConfigFile = "ClueLegend.txt";
		String weaponConfigFile = "ClueWeapons.txt" ; // Weapon file
		String playerConfigFile = "CluePlayers.txt"; // Player file
		Board board = Board.getInstance();
		try {
			board.setConfigFiles(boardConfigFile, roomConfigFile, weaponConfigFile, playerConfigFile);
			board.initialize();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create the JPanel and add it to the JFrame
		MyCards gui = new MyCards(board.getUser().getMyCards());
		frame.add(gui, BorderLayout.CENTER);
		
		//Make JFrame the size of the JPanels that we added to it
		frame.pack();
		// Now let's view it
		frame.setVisible(true);
	}
}
