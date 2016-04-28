package blackjackG21;

import java.util.LinkedList;

public class Hand {
	private LinkedList<Card> hand;
	private int bust;
	private int currentBet;
	
	public LinkedList<Card> getHand(){
		return hand;
	}
	
	public Hand(){
		hand= new LinkedList<Card>();
		this.bust=0;
	}
	
	public int getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}
	
	public Hand(Card card1, Card card2, int bet){
		this();
		hand.add(card1);
		hand.add(card2);
		this.setCurrentBet(bet);
	}
	
	public void addCard(Card card){
		hand.add(card);
	}
	
	public void emptyHand(){
		hand.clear();
	}
	
	public int getTotal(){
		int total=0;
		int Aces=0;
		for(Card c:hand){
			total+=c.rank.getRankPoints();
			if(c.rank.getRankPoints()==11)Aces++;
		}
		while((total>21)&&(Aces>0)){
			total-=10;
			Aces--;
		}
		return total;
	}
	
	@Override
	public String toString() {
		
		return "Hand [" + hand + "] with " + this.getTotal()+" points";
	}

	public int getBust() {
		return bust;
	}
	
	public int bust(){
		if(this.getTotal()>21){
			this.bust=1;
			System.out.println("Hand"+this.toString()+", busted");
		}
		return this.bust;
	}
}
