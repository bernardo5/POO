package blackjack;

public abstract class Person {
	protected Hand current;
	
	//Constructor
	public Person(){
	}
	
	//setter
	public void setCurrentHand(Hand hand){
		current=hand;
	}
		
	//getter
	public Hand getCurrentHand(){
		return current;
	}
		
	//public abstract void stand(Hand h);
		
	public boolean hit(Card card){
		current.addCard(card);
		return current.bust();
	}
	
	//Methods to Override
	public abstract String showHands();
	
}
