package blackjack;

import java.util.LinkedList;

public class Dealer extends Person{
	public Dealer() {
		super();
	}
	
	/**
	 * Used to see if player can insure
	 * @return rank of visible card
	 */
	public Rank returnShownCard(){
		return current.getCards().getFirst().getRank();
	}
	/**
	 * Used to pass visible card to strategy methods
	 * @return visible card
	 */
	public Card getVisibleCard(){
		return current.getCards().getFirst();
	}
	
	/**
	 * Overrides the same name method in Person in order to hide on card
	 * i - index of card in hand
	 */
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
	
	/**
	 * 
	 * @return string with all cards from dealers hand
	 */
	public String showCurrentHandAll(){
		return super.showCurrentHand();
	}
	/**
	 * 
	 * @return previously hidden card for counting techniques
	 */
	public Card returnHiddenCard(){
		return current.getCards().get(1);
	}

}
