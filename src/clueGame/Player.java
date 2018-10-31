package clueGame;

import java.awt.Color;
import java.util.*;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;
	
	/**
	 * Default constructor for Player
	 */
	public Player() {
		this.myCards = new ArrayList<>();
		this.seenCards = new ArrayList<>();
	}
	
	/**
	 * Constructor will assign player a name
	 * @param playerName String 
	 */
	public Player(String playerName) {
		this();
		this.playerName = playerName; 
	}
	
	/**
	 * Will try to disprove another players suggestion by returning a Card which matches part of the suggestion
	 * @param suggestion
	 * @return Card 
	 */
	public Card disproveSuggestion(Solution suggestion) {
		Card card = new Card();
		return card;
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
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
	
	/**
	 * Setter for the seenCards variable 
	 * @param seenCards
	 */
	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}


}
