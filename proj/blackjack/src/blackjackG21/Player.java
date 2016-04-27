package blackjackG21;

import java.util.LinkedList;

public abstract class Player {
	protected LinkedList<Hand> hands;
	
	public Player(/*Card card1, Card card2*/){
		hands= new LinkedList<Hand>();
		//hands.add(new Hand(card1, card2));
	}
	
	public void getHand(Hand hand){
		hands.add(hand);
	}
	
	public abstract String showHands();
	
	//public abstract void stand(Hand h);
	
	public int hit(Card card, Hand h){
		Hand handAux=hands.get(hands.indexOf(h));//so it wont be necessary to search twice for element
		handAux.addCard(card);
		return handAux.bust();
	}
	
	
}
