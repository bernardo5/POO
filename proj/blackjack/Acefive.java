package blackjack;

public class Acefive implements ActionStrategy,BetStrategy{

	private int count = 0;
	/**
	 * Depending on the card shown refreshes the count variable
	 * @param card - new visible card on game
	 */
	public void cardRevealed(Card card){
		if(card.getValue()==5) count++;
		if(card.getValue()==11) count--;
	}
	/**
	 * At the end of each shuffle the counting is reset
	 */
	public void resetCount(){
		count=0;
	}
	
	
	@Override
	public String advice(Hand player_hand, Card dealer_card) {
		String bet_action=this.advice();
		if (player_hand.getSizeofCards() == 2 && player_hand.getCards().getFirst().getRank()==player_hand.getCards().getLast().getRank()) 
			return bet_action+"-> "+pair_advice[player_hand.getCards().getFirst().getValue()-2][dealer_card.getValue()-2];
		else if (player_hand.numberAces()>0) return bet_action+"-> "+soft_advice[player_hand.getPoints()-13][dealer_card.getValue()-2];
		else return bet_action+"-> "+hard_advice[player_hand.getPoints()-5][dealer_card.getValue()-2];
	}

	@Override
	public String advice() {
		if(count>=2)return "double last bet";
			else return "min_bet";
	}

}
