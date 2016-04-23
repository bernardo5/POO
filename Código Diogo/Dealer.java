package blackjackG21;

import java.util.LinkedList;

public class Dealer extends Person{

	public Dealer() {
		super();
	}
	
	public String showHands(){
		/*Dealer only has one hand and shows all cards except the first*/
		String game=new String();
		Hand h=hands.getFirst();
		LinkedList<Card> cardsss=h.getCards();
		int i=0;
		for(Card c:cardsss){
			if(i!=0)game+=c.toString();
			i++;
		}
		
		game+=" with "+hands.getFirst().getPoints()+" points";
		
		return game;
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dealer player=new Dealer(new Card("Q",'S'), new Card("A",'S'));
		player.hit(new Card("10",'S'), 0);
		System.out.println(player.showHands());
	}*/

}
