package blackjackG21;

public class RegularPlayer extends Player{
	private int balance;
	public RegularPlayer(Card card1, Card card2, int balance) {
		super(card1, card2);
		this.setBalance(balance);
	}
	
	/*********************************************************
	 * Implement side rules
	 * 
	 * 
	 * 
	 * 
	 * 
	 * *********************************************************/
	
	public String showHands(){
		/*player may have more than one hand and shows all cards*/
		String game=new String();
		int i=1;
		for(Hand aux:hands){
			game+="Hand "+i+": "+aux.toString()+"\n";
		}
		return game;
	}
	
	/*public void stand(Hand h){
		if((h.getBust())==1){
			//do something
			System.out.println("You will now stand");
		}
	}*/

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public void addBalance(int returnBalance){
		this.balance+=returnBalance;
	}

	public static void main(String[] args) {
		RegularPlayer player=new RegularPlayer(new Card("Q",'S', null), new Card("A",'S', null), 15);
		player.hit(new Card("A",'S', null), 0);
		System.out.println(player.showHands());
	}

}
