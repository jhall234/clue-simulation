package clueGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private PlayerType playerType;
	private ArrayList<Card> myCards;
	private ArrayList<String> seenCards;
	private static final int PIECE_DIAMETER = 26; 
	
	/**
	 * Default constructor for Player
	 */
	public Player() {
		this.myCards = new ArrayList<>();
		this.seenCards = new ArrayList<>();
	}
	
	/**
	 * Constructor will initialize all variables with the given paramaters 
	 * @param playerName name of player
	 * @param red Red color value 
	 * @param green Green color value
	 * @param blue Blue color value
	 * @param is_human either "human" or "computer"
	 * @param row starting row of player
	 * @param column starting column of player 
	 */
	public Player(String playerName, int red, int green, int blue, PlayerType playerType, int row, int column) {
		this();
		this.playerName = playerName;
		this.color = new Color(red, green, blue);
		this.playerType = playerType;
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Will try to disprove another players suggestion by returning a Card which matches part of the suggestion
	 * @param suggestion
	 * @return Card 
	 */
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> cardsThatMatch = new ArrayList<Card>(); 
		for (Card c : myCards) {
			if (c.getCardName().equals(suggestion.person)) {
				cardsThatMatch.add(c);	
			}
			else if (c.getCardName().equals(suggestion.room)) {
				cardsThatMatch.add(c);
			}
			else if (c.getCardName().equals(suggestion.weapon)) {
				cardsThatMatch.add(c);
			}
		}
		
		if (cardsThatMatch.isEmpty()) {
			return null;
		}
		else {
			Random rand = new Random();
			int ranom_num = rand.nextInt(cardsThatMatch.size());
			return cardsThatMatch.get(ranom_num);
		}
	}
	
	/**
	 * Will move the player to the specified target
	 * @param target
	 */
	public abstract void makeMove(BoardCell target);
	
	/**
	 * This method will handle drawing all of the players on the game boards as circles
	 * @param g
	 */
	public void draw(Graphics2D g) {
		int x = BoardCell.getWidth() * column;
		int y = BoardCell.getHeight() * row;
		g.setColor(color);
		g.fillOval(x, y, PIECE_DIAMETER, PIECE_DIAMETER);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, PIECE_DIAMETER, PIECE_DIAMETER);
	}
	
	/**
	 * Getter for row
	 * @return
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Setter for row
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Getter for column
	 * @return
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Setter for column
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 * Getter for playerName
	 * @return
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * Getter for player's color
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Getter for the list of the players own cards
	 * @return ArrayList
	 */
	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	/**
	 * Will set the myCards variable to the ArrayList that is passed
	 * @param myCards
	 */
	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}
	
	/**
	 * Getter for the list of cards seen by the player
	 * @return
	 */
	public ArrayList<String> getSeenCards() {
		return seenCards;
	}
	
	/**
	 * Setter for the seenCards variable 
	 * @param seenCards
	 */
	public void setSeenCards(ArrayList<String> seenCards) {
		this.seenCards = seenCards;
	}
	
}	
