package blackjackG21;

public class Card {
	private String name;	
	private char suit;
	
	public Card(String name, char suit){
		this.name=name;
		this.suit=suit;
	}
	
	public String getName(){
		return this.name;
	}

	@Override
	public String toString() {
		return "Card=(" + name + suit + ")";
	}
	
	
}
