package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String cardName) {
		this.cardName = cardName;
	}
	
	public Card() {
		this.cardName = "default card name";
	}
	
	public boolean equals(Card card) {
		return this.cardName.equals(card.getCardName()) && this.cardType.equals(card.getCardType());
	}
	
	public String getCardName() {
		return this.cardName;
	}
	
	public CardType getCardType() {
		return this.getCardType();
	}
}
