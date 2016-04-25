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
	
}
