package blackjack;

public class Basic implements ActionStrategy{
											// 2   3   4   5   6   7   8   9   10  A
	
	
	@Override
	public String advice(Hand player_hand,Card dealer_card) {
		if (player_hand.getSizeofCards() == 2 && player_hand.getCards().getFirst().getRank()==player_hand.getCards().getLast().getRank()) 
			return pair_advice[player_hand.getCards().getFirst().getValue()-2][dealer_card.getValue()-2];
		else if (player_hand.numberAces()>0) return soft_advice[player_hand.getPoints()-13][dealer_card.getValue()-2];
		else return hard_advice[player_hand.getPoints()-5][dealer_card.getValue()-2];
		
	}

}
