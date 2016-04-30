package blackjack;

import java.util.LinkedList;

public class Dealer extends Person{
	public Dealer() {
		super();
	}
	
	public Rank returnShownCard(){
		return current.getCards().getFirst().getRank();
	}
	
	public String showHands(){
		/*Dealer only has one hand and shows all cards except the first*/
		String game=new String();
		LinkedList<Card> cards=current.getCards();
		int i=0;
		for(Card c:cards){
			if(i!=1){
				if(i!=0)game+=", ";
				game+=c.toString();
				}
			i++;
		}
		
		game+=" ("+current.getPoints()+")";
		
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
