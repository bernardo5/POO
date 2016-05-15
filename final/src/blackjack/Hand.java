package blackjack;

import java.util.*;

public class Hand {
	private LinkedList<Card> cards;
	private int points;
	private int bet;
	
	public Hand(){
		this.cards= new LinkedList<Card>();
		this.points=0;
	}
	
	/**
	 * 
	 * @param card1 - first card of the hand
	 * @param card2 - second card of the hand
	 */
	
	public Hand(Card card1, Card card2){
		this();
		this.cards.add(card1);
		this.cards.add(card2);
		updatePoints();
		this.bet=0;
	}
	
	/**
	 * 
	 * @param card1 - first card of the hand
	 * @param card2 - second card of the hand
	 * @param bet - bet associated to the hand
	 */
	public Hand(Card card1, Card card2,int bet){
		this();
		this.cards.add(card1);
		this.cards.add(card2);
		updatePoints();
		this.bet=bet;
	}
	
	/**
	 * 
	 * @return list of cards in hand
	 */
	public LinkedList<Card> getCards(){
		return cards;
	}
	/**
	 * 
	 * @return total hand points
	 */
	public int getPoints(){
		return this.points;
	}
	/**
	 * 
	 * @return bet associated to the hand
	 */
	public int getBet() {
		return bet;
	}
	/**
	 * 
	 * @return number of cards in hand
	 */
	public int getSizeofCards(){
		return cards.size();
	}
	
	/**
	 * 
	 * @param bet - bet associated to the hand
	 */
	public void setBet(int bet) {
		this.bet = bet;
	}

	/**
	 * Updates hand points - counts the hand points and if it exceeds 21 and the hand has aces,
	 * makes them count only one point
	 */
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
	/**
	 * 
	 * @param card - card to add
	 */
	public void addCard(Card card){
		this.cards.add(card);
		this.updatePoints();
	}
	/**
	 * deletes all cards from hand
	 */
	public void emptyHand(){
		this.cards.clear();
	}
	/**
	 * 
	 * @return true if hand has no cards, false otherwise
	 */
	public boolean isEmpty(){
		return cards.isEmpty();
	}
	
	
	@Override
	public String toString() {
		String string=new String();
		for(Card c:cards)string+=c.toString()+" ";
		return string + "(" + points +")";
	}	
/**
 * 
 * @return true if hand busted, false otherwise
 */
	public boolean bust(){
		return this.points>21 ? true:false;
	}
	
	/**
	 * 
	 * @return number of aces
	 */
	public int numberAces(){
		int num=0;
		for(Card c : this.getCards()){
			if(c.getRank() == Rank.ACE) num++;
		}
		return num;
	}
	/**
	 * 
	 * @return true if cards have the same value, false otherwise
	 */
	public boolean sameValues(){
		if(cards.getFirst().equals(cards.getLast()))return true;
		else return false;
	}

}
