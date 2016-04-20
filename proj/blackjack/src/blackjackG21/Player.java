package blackjackG21;

import java.util.LinkedList;

public abstract class Player {
	protected LinkedList<Hand> hands;
	
	public Player(Card card1, Card card2){
		hands= new LinkedList<Hand>();
		hands.add(new Hand(card1, card2));
	}
	
	public abstract String showHands();
	
	//public abstract void stand(Hand h);
	
	public void hit(Card card, int index){
		Hand handAux=hands.get(index);//so it wont be necessary to search twice for element
		handAux.addCard(card);
		handAux.bust();
	}
	
	
}
