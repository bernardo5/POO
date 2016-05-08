package blackjack;

public class HiLo implements ActionStrategy{
	
	private float runningcount;
	private float nbdeckstotal;
	private float nbcardsrevealed;
	
	//Constructor
	public HiLo(int nbDecks){
		this.runningcount=0;
		this.nbdeckstotal=nbDecks;
		this.nbcardsrevealed=0;
	}
	//Methods
	public void cardRevealed(Card card){
		if(card.getValue()<7 &&card.getValue()>2) runningcount += 1;
		else if(card.getValue()<=11 &&card.getValue()>9) runningcount -= 1;
		nbcardsrevealed++;
	}

	public float truecount(){
		float truecount=0;
		truecount=runningcount/(nbdeckstotal-(nbcardsrevealed/52));
		return truecount;
	}
	
	@Override
	public String advice(Hand hand, Card card) {
		
		float truecount = truecount();
		int player_points = hand.getPoints();
		int dealer_points = card.getValue();
		
		//insurance: insure at +3 or higher
		return null;
	}

}
