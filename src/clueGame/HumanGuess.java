package guess;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HumanGuess extends JDialog {

	public HumanGuess() {
		super();
		setLayout(new GridLayout(2,1));
		setTitle("Make a guess");
		//add(row1());
		//add(row2());
		JPanel panel = row3();
//		add(panel);
		panel = buttons();
		add(panel);
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	    
	}

	private JPanel buttons() {
		JPanel myPanel = new JPanel();
		setLayout(new GridLayout(1, 0));
		add(submit());
		add(cancel());
		return myPanel;
	}
	private JButton cancel() {
		JButton cancelButton = new JButton();		
		cancelButton.setText("Cancel");
		return cancelButton;
	}

	private JPanel row3() {
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new GridLayout(1,0));
		JLabel weaponLabel = new JLabel("Weapon");
		myPanel.add(weaponLabel);
		JComboBox<String> weaponDropDown = new JComboBox<String>();
		myPanel.add(weaponDropDown);
		return myPanel;
	}

	private JPanel row2() {
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new GridLayout(1,0));
		JLabel personLabel = new JLabel("Person");
		myPanel.add(personLabel);
		JComboBox<String> personDropDown = new JComboBox<String>();
		myPanel.add(personDropDown);
		return myPanel;
	}
	private JPanel row1() {
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new GridLayout(1,0));
		JLabel roomLabel = new JLabel("Your room");
		myPanel.add(roomLabel);
		JLabel room = new JLabel("room");
		myPanel.add(room);
		return myPanel;
	}
	public JButton submit() {
		JButton submitButton = new JButton();		
		submitButton.setText("Submit");
		return submitButton;
	}
	
	public static void main(String args[]){
		HumanGuess guessPanel = new HumanGuess();

		
		
	}
}