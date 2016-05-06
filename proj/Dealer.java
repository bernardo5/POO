package blackjack;

import java.util.LinkedList;

public class Dealer extends Person{
	public Dealer() {
		super();
	}
	
	public Rank returnShownCard(){
		return current.getCards().getFirst().getRank();
	}
	
	public String showHands(){
		/*Dealer only has one hand and shows all cards except the first*/
		String game=new String();
		LinkedList<Card> cards=current.getCards();
		int i=0;
		for(Card c:cards){
			if(i!=1){
				if(i!=0)game+=", ";
				game+=c.toString();
				}
			i++;
		}
		
		game+=" ("+current.getPoints()+")";
		
		return game;
	}

	@Override
	public void win() {
		// TODO Auto-generated method stub
		super.win();
		System.out.println("dealer wins");
	}

}
