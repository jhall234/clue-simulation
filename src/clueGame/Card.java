package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	/**
	 * Constructor will assign the string provided as the cardName
	 * @param cardName
	 */
	public Card(String cardName) {
		this.cardName = cardName;
	}
	
	/**
	 * Default constructor for Card
	 */
	public Card() {
		
	}
	
	/**
	 * Will return true if both the name and the cardtype are equivalent
	 * @param card
	 * @return
	 */
	public boolean equals(Card card) {
		return this.cardName.equals(card.getCardName()) && this.cardType.equals(card.getCardType());
	}
	
	/**
	 * getter for cardName
	 * @return this objects cardName variable
	 */
	public String getCardName() {
		return this.cardName;
	}
	
	/**
	 * getter for cardType
	 * @return enumerated cardType 
	 */
	public CardType getCardType() {
		return this.getCardType();
	}
}
