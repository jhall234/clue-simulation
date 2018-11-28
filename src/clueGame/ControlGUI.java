package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private JTextField player;
	private JTextField rollText;
	private JTextField guessText;
	private JTextField responseText;

	public ControlGUI()
	{
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = CreateButtonRow();
		add(panel);
		panel = createTurnInfoPanel();
		add(panel);

	}
	
	/**
	 * Creates row of the GUI that contains the next player and make accusation buttons
	 * @return
	 */
	private JPanel CreateButtonRow() {
		JPanel parentPanel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		parentPanel.setLayout(new GridLayout(1,2));
		
		//Panel for holding who's turn
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(3,0));
		JLabel turnLabel = new JLabel("Whose turn?", SwingConstants.CENTER);
		
		player = new JTextField(20);
		player.setEditable(false);
		JPanel textTurnPanel = new JPanel();
		textTurnPanel.add(player);
		
		turnPanel.add(turnLabel);
		turnPanel.add(textTurnPanel);
		
		//Create buttons 
		JButton nextPlayer = new JButton("Next Player");
		nextPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guessText.setText(" ");
				responseText.setText(" ");
				// Call the board & give the next player a turn
				Board board = Board.getInstance();					
				board.movePlayer();
				
			}			
		});
		
		JButton makeAccusation = new JButton("Make Accusation");
		makeAccusation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Board board = Board.getInstance();
				board.makeAccusation();
				
				
			}			
		});
		
		//Add all of the elements to the panel
		parentPanel.add(turnPanel);
		parentPanel.add(nextPlayer);
		parentPanel.add(makeAccusation);
		return parentPanel;
	}

	/**
	 * Creates the row of the GUI that displays info about roll, current player, guess, and response
	 * @return
	 */
	private JPanel createTurnInfoPanel() {
		// no layout specified, so this is flow
		JPanel parentPanel = new JPanel();
		
		JPanel rollPanel = new JPanel();
		rollPanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		JLabel roll = new JLabel("Roll");
		rollText = new JTextField(5);
		rollText.setEditable(false);
		rollPanel.add(roll);
		rollPanel.add(rollText);
		
		//Guess Box
		JPanel guessPanel = new JPanel();
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		JLabel guess = new JLabel("Guess");
		guessText = new JTextField(30);
		guessText.setEditable(false);
		guessPanel.add(guess);
		guessPanel.add(guessText);
		
		//Response Box
		JPanel responsePanel = new JPanel();
		responsePanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		JLabel response = new JLabel("Response");
		responseText = new JTextField(20);
		responseText.setEditable(false);
		responsePanel.add(response);
		responsePanel.add(responseText);
		
		
		
		
		
		
		parentPanel.add(rollPanel);
		parentPanel.add(guessPanel);
		parentPanel.add(responsePanel);
		
		return parentPanel;
	}
	
	/**
	 * Getter for player text field
	 * @return
	 */
	public JTextField getPlayer() {
		return player;
	}

	/**
	 * Setter for player text field
	 * @param name
	 */
	public void setPlayer(String name) {
		this.player.setText(name);
	}

	/**
	 * getter for dice roll value
	 * @return
	 */
	public JTextField getRollText() {
		return rollText;
	}

	/**
	 * setter for dice roll value
	 * @param roll
	 */
	public void setRollText(String roll) {
		rollText.setText(roll);
	}

	/**
	 * setter for guess text value 
	 * @param guess
	 */
	public void setGuessText(String guess) {
		guessText.setText(guess);
	}
	
	/**
	 * setter for response text value
	 * @param response
	 */
	public void setResponseText(String response) {
		responseText.setText(response);
	}

	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Control");
		
		// Create the JPanel and add it to the JFrame
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		
		//Make JFrame the size of the JPanels that we added to it
		frame.pack();
		// Now let's view it
		frame.setVisible(true);
	}
}
