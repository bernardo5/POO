package blackjack;

public class HiLo implements ActionStrategy{
	
	private float runningcount;
	private float nbdeckstotal;
	private int nbcardsrevealed;
	
	/**
	 * 
	 * @param nbDecks - number of decks in shoe
	 */
	public HiLo(int nbDecks){
		this.runningcount=0;
		this.nbdeckstotal=nbDecks;
		this.nbcardsrevealed=0;
	}
	//Methods
	
	/**
	 * Depending on the card revealed, refreshes the count
	 * @param card
	 */
	public void cardRevealed(Card card){
		if(card.getValue()<7 &&card.getValue()>2) runningcount += 1;
		else if(card.getValue()<=11 &&card.getValue()>9) runningcount -= 1;
		nbcardsrevealed++;
	}
	/**
	 * At the end of each shuffle the counting is reset
	 */
	public void restartRunningCount(){
		runningcount=0;
	}

	public float truecount(){
		float truecount=0;
		truecount=runningcount/(nbdeckstotal-(nbcardsrevealed/52));
		return truecount;
	}
	
	@Override
	public String advice(Hand hand, Card card) {
		
		float truecount = truecount();
		//int player_points = hand.getPoints();
		//int dealer_points = card.getValue();
		
		if((truecount>=3)&&(card.getRank()==Rank.ACE)){//insurance
			return "i";
		}else if((hand.getPoints()==16)&&(card.getValue()==10)){//16vt
			if(truecount<=0)return "s";
			else return "h";
		}else if((hand.getPoints()==15)&&(card.getValue()==10)){//15vt
			if((truecount>=0)&&(truecount<=3))return "us";
			else if(truecount>=4) return "s";
			else return "h";
		}else if((hand.getPoints()==20)&&(card.getValue()==5)){//ttv5
			if(truecount>=5)return "ps";
			else return "s";
		}else if((hand.getPoints()==20)&&(card.getValue()==6)){//ttv6
			if(truecount>=4)return "ps";
			else return "s";
		}else if((hand.getPoints()==10)&&(card.getValue()==10)){//10vt
			if(truecount>=4)return "2";
			else return "h";
		}else if((hand.getPoints()==12)&&(card.getValue()==3)){//12v3
			if(truecount>=2)return "s";
			else return "h";
		}else if((hand.getPoints()==12)&&(card.getValue()==2)){//12v2
			if(truecount>=3)return "s";
			else return "h";
		}else if((hand.getPoints()==11)&&(card.getValue()==11)){//11vA
			if(truecount>=1)return "2h";
			else return "h";
		}else if((hand.getPoints()==9)&&(card.getValue()==2)){//9v2
			if(truecount>=1)return "2h";
			else return "h";
		}else if((hand.getPoints()==10)&&(card.getValue()==11)){//10vA
			if(truecount>=4)return "2h";
			else return "h";
		}else if((hand.getPoints()==9)&&(card.getValue()==7)){//9v7
			if(truecount>=3)return "2h";
			else return "h";
		}else if((hand.getPoints()==16)&&(card.getValue()==9)){//16v9
			if(truecount>=5)return "s";
			else return "h";
		}else if((hand.getPoints()==13)&&(card.getValue()==2)){//13v2
			if(truecount>=-1)return "s";
			else return "h";
		}else if((hand.getPoints()==12)&&(card.getValue()==4)){//12v4
			if(truecount>=0)return "s";
			else return "h";
		}else if((hand.getPoints()==12)&&(card.getValue()==5)){//12v5
			if(truecount>=-2)return "s";
			else return "h";
		}else if((hand.getPoints()==12)&&(card.getValue()==6)){//12v6
			if(truecount>=-1)return "s";
			else return "h";
		}else if((hand.getPoints()==13)&&(card.getValue()==3)){//13v3
			if(truecount>=-2)return "s";
			else return "h";
		}else if((hand.getPoints()==14)&&(card.getValue()==10)){//14vt
			if(truecount>=3)return "u";
			else return new Basic().advice(hand, card);
		}else if((hand.getPoints()==15)&&(card.getValue()==9)){//15v9
			if(truecount>=2)return "u";
			else return new Basic().advice(hand, card);
		}else if((hand.getPoints()==15)&&(card.getValue()==9)){//15vA
			if(truecount>=1)return "u";
			else return new Basic().advice(hand, card);
		}
		return "Using basic: "+new Basic().advice(hand, card);
	}

}
