package blackjackG21;

public class Game {
	
	private int minBet;
	private int maxBet;
	private int nDecks;
	private int shufflePercentage; //% of shoe played begore shuffling 10-100
	private Dealer dealer;
	private RegularPlayer player;
	private Shoe shoe;
	
	public Game(int minBet,	int maxBet, int playersBalance,	int nDecks,	int shufflePercentage, String file){
		this.minBet=minBet;
		this.maxBet=maxBet;
		this.nDecks=nDecks;
		this.shufflePercentage=shufflePercentage;
		shoe=new Shoe(nDecks);
		shoe.populateShoeFromFile(file, nDecks);
		player=new RegularPlayer(playersBalance);
		dealer=new Dealer();
	}
	
	public void distributeHands(){
		Hand p=new Hand(shoe.takeCard(), shoe.takeCard());
		Hand d=new Hand(shoe.takeCard(), shoe.takeCard());
		player.getHand(p);
		dealer.getHand(d);
	}
	
	public void showCards(){
		System.out.println("Player hand: "+ player.showHands());
		System.out.println("Dealer's hand: "+ dealer.showHands());
		
	}
	
	public void interactiveMode(){
		while(player.getBalance()>=minBet){
			//give player and dealer the hand
			this.distributeHands();
			this.showCards();
			break;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game= new Game(1,	1000, 50,	4,	58, "src/shoe-file.txt");
		game.interactiveMode();
	}

}
