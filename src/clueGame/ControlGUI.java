package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
	private JTextField name;
	private JTextField rollText;
	private JTextField guessText;
	private JTextField responseText;

	public ControlGUI()
	{
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = createButtonRow();
		add(panel);
		panel = createTurnInfoPanel();
		add(panel);
	}

	private JPanel createButtonRow() {
		JPanel parentPanel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		parentPanel.setLayout(new GridLayout(1,2));
		
		//Panel for holding who's turn
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(3,0));
		JLabel turnLabel = new JLabel("Whose turn?", SwingConstants.CENTER);
		
		name = new JTextField(20);
		name.setEditable(false);
		JPanel textTurnPanel = new JPanel();
		textTurnPanel.add(name);
		
		turnPanel.add(turnLabel);
		turnPanel.add(textTurnPanel);
		
		//Create buttons 
		JButton nextPlayer = new JButton("Next Player");
		JButton makeAccusation = new JButton("Make Accusation");
		
		//Add all of the elements to the panel
		parentPanel.add(turnPanel);
		parentPanel.add(nextPlayer);
		parentPanel.add(makeAccusation);
		return parentPanel;
	}

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

	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Control");
		frame.setSize(800, 225);	
		// Create the JPanel and add it to the JFrame
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}
}
