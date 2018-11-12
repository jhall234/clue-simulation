package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyCards extends JPanel{
	/**
	 * Default constructor will create panels for the three types of cards 
	 */
	public MyCards() {
		//Create layout with 4 rows
		setLayout(new GridLayout(0,1));
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		
		JPanel people = CreateCardPanel("People");
		JPanel rooms = CreateCardPanel("Rooms");
		JPanel weapons = CreateCardPanel("Weapons");
		
		add(people);
		add(rooms);
		add(weapons);
		
	}
	/**
	 * Creates panel for each card type
	 * @param type
	 * @return
	 */
	private JPanel CreateCardPanel(String type) {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), type));
		panel.add(new JTextField("Test Card"));
		return panel;
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Control");
		
		// Create the JPanel and add it to the JFrame
		MyCards gui = new MyCards();
		frame.add(gui, BorderLayout.CENTER);
		
		//Make JFrame the size of the JPanels that we added to it
		frame.pack();
		// Now let's view it
		frame.setVisible(true);
	}
}
