package blackjackG21;

public class Card {
	private String name;	
	private char suit;
	private Deck deck;
	
	public Card(String name, char suit, Deck deck){
		this.name=name;
		this.suit=suit;
		this.deck=deck;
	}

	@Override
	public String toString() {
		return "Card=(" + name + suit + ")";
	}
	
	
}
