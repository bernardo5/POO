package blackjackG21;

import java.util.LinkedList;

public class Dealer extends Player{

	public Dealer(/*Card card1, Card card2*/) {
		super(/*card1, card2*/);
	}

	/*public void stand(Hand h){
		if((h.getTotal())>=17){
			//do something
			System.out.println("You will now stand");
		}
	}*/
	
	public String showHands(){
		/*Dealer only has one hand and shows all cards except the first*/
		String game=new String();
		Hand h=hands.getFirst();
		LinkedList<Card> cardsss=h.getHand();
		int i=0;
		for(Card c:cardsss){
			if(i!=0){
				if(i!=1)game+=", ";
				game+=c.toString();
				}
			i++;
		}
		
		game+=" with "+hands.getFirst().getTotal()+" points";
		
		return game;
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dealer player=new Dealer(new Card("Q",'S'), new Card("A",'S'));
		player.hit(new Card("10",'S'), 0);
		System.out.println(player.showHands());
	}*/

}
