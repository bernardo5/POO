package blackjack;

import java.util.*;

public class Hand {
	private LinkedList<Card> cards;
	private int points;
	private int bet;
	
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
		
		return cards + " (" + points +")";
	}	

	public boolean bust(){
		return this.points>21 ? true:false;
	}
	
	public int numberAces(){
		int num=0;
		for(Card c : this.getCards()){
			if(c.getRank() == Rank.ACE) num++;
		}
		return num;
	}

}
