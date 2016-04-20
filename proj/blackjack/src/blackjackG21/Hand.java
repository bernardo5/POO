package blackjackG21;

import java.util.HashMap;
import java.util.LinkedList;

public class Hand {
	LinkedList<Card> hand;
	HashMap<String, Integer> points;
	
	public Hand(){
		hand= new LinkedList<Card>();
		points = new HashMap<String, Integer>();
	}
	
	public Hand(Card card1, Card card2){
		this();
		hand.add(card1);
		hand.add(card2);
		points.put("2", 2);
		points.put("3", 3);
		points.put("4", 4);
		points.put("5", 5);
		points.put("6", 6);
		points.put("7", 7);
		points.put("8", 8);
		points.put("9", 9);
		points.put("10", 10);
		points.put("Q", 10);
		points.put("J", 10);
		points.put("K", 10);
		points.put("A", 11);
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
			total+=points.get(c.getName());
			if((c.getName()).equals("A"))Aces++;
		}
		while((total>21)&&(Aces>0)){
			total-=10;
			Aces--;
		}
		return total;
	}
	
	

	@Override
	public String toString() {
		
		return "Hand [" + hand + "], points=" + this.getTotal() + "\n";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hand hand=new Hand(new Card("Q",'S', null), new Card("A",'S', null));
		
		System.out.println(hand.toString());
		
		//hand.addCard(new Card("A", 'H', null));
		hand.addCard(new Card("K", 'H', null));
		
		System.out.println(hand.toString());

	}

}
