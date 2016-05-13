package blackjack;

import java.util.LinkedList;

public class Dealer extends Person{
	public Dealer() {
		super();
	}
	
	
	
	public Rank returnShownCard(){
		return current.getCards().getFirst().getRank();
	}
	
	public Card getVisibleCard(){
		return current.getCards().getFirst();
	}
	
	/*public String showHands(){
		//Dealer only has one hand and shows all cards except the first
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
	}*/
	
	@Override
	public String showCurrentHand(){
		String hand=new String();
		LinkedList<Card> cards=current.getCards();
		int i=0;
		for(Card c:cards){
			if(i!=1){
				if(i!=0)hand+=" ";
				hand+=c.toString();
			}else hand+=" X";
			i++;
		}		
		return hand;
	}
	
	public String showCurrentHandAll(){
		return super.showCurrentHand();
	}
	
	public Card returnHiddenCard(){
		return current.getCards().get(1);
	}

}
