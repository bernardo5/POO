package blackjackG21;

import java.util.LinkedList;

import blackjackG21.Card.Rank;

public class Dealer extends Player{

	public Dealer(/*Card card1, Card card2*/) {
		super(/*card1, card2*/);
	}
	
	public Rank returnShownCard(){
		return this.hands.getFirst().getHand().get(1).getRank();
	}
	
	public String showHands(){
		/*Dealer only has one hand and shows all cards except the first*/
		String game=new String();
		Hand h=hands.getFirst();
		LinkedList<Card> cardsss=h.getHand();
		int i=0;
		for(Card c:cardsss){
			if(i!=0){
				if(i!=1)game+=", ";
				game+=c.toString();
				}
			i++;
		}
		
		game+=" with "+hands.getFirst().getTotal()+" points";
		
		return game;
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dealer player=new Dealer(new Card("Q",'S'), new Card("A",'S'));
		player.hit(new Card("10",'S'), 0);
		System.out.println(player.showHands());
	}*/

}
