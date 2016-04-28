package blackjackG21;

import java.util.*;

public class Hand {
	private LinkedList<Card> cards;
	private int points;
	private int bet;
	
	//Getters
	public LinkedList<Card> getCards(){
		return cards;
	}
	public int getPoints(){
		return this.points;
	}
	public int getBet() {
		return bet;
	}
	public int getSizeofCards(){
		return cards.size();
	}
	
	//Setters
	public void setBet(int bet) {
		this.bet = bet;
	}
	//Constructors
	public Hand(){
		this.cards= new LinkedList<Card>();
		this.points=0;
	}
	public Hand(Card card1, Card card2){
		this();
		this.cards.add(card1);
		this.cards.add(card2);
		updatePoints();
		this.bet=0;
	}
	public Hand(Card card1, Card card2,int bet){
		this();
		this.cards.add(card1);
		this.cards.add(card2);
		updatePoints();
		this.bet=bet;
	}
	
	//Methods
	public void updatePoints(){
		int sum=0;
		int value=0;
		int aces=0;
		ListIterator<Card> listIterator = this.cards.listIterator();
        while (listIterator.hasNext()) {
        	value=listIterator.next().getRank().getRankValue();
        	if(value==11)++aces;
        	sum+=value;
        }
        while((sum>21)&&(aces>0)){
        	--aces;
        	sum-=10;
        }
		this.points=sum;
	}
	
	public void addCard(Card card){
		this.cards.add(card);
		this.updatePoints();
	}
	
	public void emptyHand(){
		this.cards.clear();
	}
	
	public boolean isEmpty(){
		return cards.isEmpty();
	}
	
	
	@Override
	public String toString() {
		
		return "Hand [" + cards + "] with " + points +" points";
	}	

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hand hand=new Hand(new Card("Q",'S', null), new Card("A",'S', null));
		
		System.out.println(hand.toString());
		
		hand.addCard(new Card("A", 'H', null));
		//hand.addCard(new Card("K", 'H', null));
		
		System.out.println(hand.toString());
		hand.bust();
	}*/

}
