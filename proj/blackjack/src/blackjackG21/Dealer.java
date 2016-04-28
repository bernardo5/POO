package blackjackG21;

import java.util.LinkedList;

import blackjackG21.Card.Rank;

public class Dealer extends Player{

	public Dealer() {
		super();
	}
	
	public Rank returnShownCard(){
		return this.getCurrentHand().getHand().get(1).getRank();
	}
	
	public String showHands(){
		/*Dealer only has one hand and shows all cards except the first*/
		String game=new String();
		LinkedList<Card> cardsss=this.getCurrentHand().getHand();
		int i=0;
		for(Card c:cardsss){
			if(i!=0){
				if(i!=1)game+=", ";
				game+=c.toString();
				}
			i++;
		}
		
		game+=" with "+this.getCurrentHand().getTotal()+" points";
		
		return game;
	}
}
