package blackjackG21;

import java.util.LinkedList;

public class Person {
	
	protected LinkedList<Hand> hands;
	
	public Person(){
		hands = new LinkedList<Hand>();
	}
	
	public void addHand(Hand hand){
		hands.add(hand);
	}
	
	
	//public abstract void stand(Hand h);
	/*
	public void hit(Card card, int index){
		Hand handAux=hands.get(index);//so it wont be necessary to search twice for element
		handAux.addCard(card);
		handAux.bust();
	}
	*/
	
}
