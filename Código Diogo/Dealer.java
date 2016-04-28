package blackjackG21;

import java.util.LinkedList;

public class Dealer extends Person{
	private Hand hand;
	
	//Getters
	public Hand getHand(){
		return this.hand;
	}
	//Setters
	public void setHand(Hand hand){
		this.hand=hand;
	}
	//Constructors
	public Dealer() {
		super();
		this.hand= new Hand();
	}
	//Methods
	public void addHand(Hand hand){
		this.hand=hand;
	}
	
	public String showHands(){
		/*Dealer only has one hand and shows all cards except the first*/
		String game=new String();
		LinkedList<Card> cards = this.hand.getCards();
		int i=0;
		for(Card c:cards){
			if(i!=0){
				if(i!=1)game+=", ";
				game+=c.toString();
			}
			i++;
		}
		
		//game+=" with "+hand.getPoints()+" points";
		
		return game;
	}
	
	/*public static void main(String[] args) {
		Card card1 = new Card(Rank.valueOf("ACE"), Suit.valueOf("S"));
		Card card2 = new Card(Rank.valueOf("QUEEN"), Suit.valueOf("C"));
		Card card3 = new Card(Rank.valueOf("TEN"), Suit.valueOf("C"));
		Dealer dealer = new Dealer();
		
		Hand hand1 = new Hand(card1, card2);
		
		dealer.addHand(hand1);

		System.out.println(dealer.showHands());
	}*/

}
