package blackjackG21;

public class Game {
	
	int minBet;
	int maxBet;
	int playersBalance;
	int nDecks;
	int shufflePercentage; //% of shoe played begore shuffling 10-100
	Dealer dealer;
	RegularPlayer player;
	
	public Game(int minBet,	int maxBet, int playersBalance,	int nDecks,	int shufflePercentage){
		this.minBet=minBet;
		this.maxBet=maxBet;
		this.playersBalance=playersBalance;
		this.nDecks=nDecks;
		this.shufflePercentage=shufflePercentage;
		
	}

/*	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

}
