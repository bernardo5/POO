package blackjack;

public class Acefive implements IStrategy{

	private int count = 0;
	
	public void cardRevealed(Card card){
		if(card.getValue()==5) count++;
		if(card.getValue()==11) count--;
	}
	
	
	@Override
	public String advice(Hand hand, Card card) {
		// TODO Auto-generated method stub
		return null;
	}

}
