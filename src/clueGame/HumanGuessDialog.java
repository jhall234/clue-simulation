package clueGame;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HumanGuessDialog extends JDialog {
	private JTextField roomName;
	private boolean submitted;
	private Solution selectedSolution;
	private JComboBox<String> roomSelection;
	private JComboBox<String> personSelection;
	private JComboBox<String> weaponSelection;
	public HumanGuessDialog(String currentRoom) {
		Board board = Board.getInstance();
				
		setTitle("Make a guess");
		setSize(400, 300);
		
		setLayout(new GridLayout(4,2));
		setModal(true);
		JLabel roomLabel = new JLabel("Your room");
		add(roomLabel);
		
		if (currentRoom.equals("accusation")) {
			roomSelection = personDropdown(board.getRooms());
			add(roomSelection);
		}
		
		else {
			roomName = new JTextField(currentRoom);
			roomName.setFont(roomName.getFont().deriveFont(Font.BOLD, 12f));
			roomName.setEditable(false);
			add(roomName);
		}
		
		
		
		JLabel personLabel = new JLabel("Person");
		add(personLabel);
		
		personSelection = personDropdown(board.getPlayerNames());
		add(personSelection);
		
		JLabel weaponLabel = new JLabel("Weapon");
		add(weaponLabel);
		
		weaponSelection = weaponDropdown(board.getWeapons());
		add(weaponSelection);
		
		
	    
	    JButton submit = submitButton();
		submit.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e) {
		          submitted = true;
		          String room;
		          if (currentRoom.equals("accusation")) {
		        	  room = roomSelection.getSelectedItem().toString();
		          }
		          else {
		        	  room = currentRoom;
		          }
		          String person = personSelection.getSelectedItem().toString();
		          String weapon = weaponSelection.getSelectedItem().toString();
		          selectedSolution = new Solution(person, room, weapon);
		          setVisible(false);
		      }		      
		});
	    add(submit);
		
		JButton cancel = cancelButton();
		cancel.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {		          
		    	  submitted = false;		        
		          setVisible(false);
		      }		      
		});
		add(cancel);			    
	}
	
	/**
	 * Function creates a dropdown menu of possible people 
	 * @param people
	 * @return
	 */
	private JComboBox<String> personDropdown(ArrayList<String> people) {
		JComboBox<String> comboBox = new JComboBox<String>();
		for (String s : people) {
			comboBox.addItem(s);
		}
		return comboBox;
	}
	
	/**
	 * Function creates a dropdown menu of possible weapons
	 * @param weapons
	 * @return
	 */
	private JComboBox<String> weaponDropdown(ArrayList<String> weapons) {
		JComboBox<String> comboBox = new JComboBox<String>();
		for (String s : weapons) {
			comboBox.addItem(s);
		}
		return comboBox;
	}
	
	/**
	 * Function creates a button to submit the suggestion/accusation
	 * @return
	 */
	private JButton submitButton() {
		JButton submitButton = new JButton();		
		submitButton.setText("Submit");
		return submitButton;
	}
	
	/**
	 * Function creates a cancel button to cancel suggestion submission
	 * @return
	 */
	private JButton cancelButton() {
		JButton cancelButton = new JButton();		
		cancelButton.setText("Cancel");
		return cancelButton;
	}
	
	/**
	 * Returns if dialouge has been sumbitted
	 * @return
	 */
	public boolean isSubmitted() {
		return submitted;
	}

	/**
	 * Getter for the generated solution
	 * @return
	 */
	public Solution getSelectedSolution() {
		return selectedSolution;
	}

	public static void main(String args[]){
		HumanGuessDialog guessPanel = new HumanGuessDialog("Observatory");

		
		
	}
}