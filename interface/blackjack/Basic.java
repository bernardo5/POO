package blackjack;

public class Basic implements ActionStrategy{
	/**
	 * Depending on the players hand and the dealers visible card gives the better 
	 * command according to the basic strategy
	 */
	@Override
	public String advice(Hand player_hand,Card dealer_card) {
		if (player_hand.getSizeofCards() == 2 && 
				player_hand.getCards().getFirst().getRank()==player_hand.getCards().getLast().getRank()){
			return pair_advice[player_hand.getCards().getFirst().getValue()-2][dealer_card.getValue()-2];
		}
		else if (player_hand.numberAces()>0){
			String ret=new String();
			try{
				ret=soft_advice[player_hand.getPoints()-13][dealer_card.getValue()-2];
			}catch(ArrayIndexOutOfBoundsException e){
				ret=hard_advice[player_hand.getPoints()-5][dealer_card.getValue()-2];
			}
			return ret;
		}
		else{
				return hard_advice[player_hand.getPoints()-5][dealer_card.getValue()-2];		
		}
	}

}

