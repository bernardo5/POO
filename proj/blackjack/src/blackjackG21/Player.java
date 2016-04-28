package blackjackG21;

public abstract class Player {
	private Hand current;
	
	/*public Player(){
		hands= new LinkedList<Hand>();
		//hands.add(new Hand(card1, card2));
	}*/
	
	public void currentHand(Hand hand){
		current=hand;
	}
	
	public abstract String showHands();
	
	public Hand getCurrentHand(){
		return current;
	}
	
	//public abstract void stand(Hand h);
	
	public int hit(Card card){
		current.addCard(card);
		return current.bust();
	}
	
	
	
	
}
