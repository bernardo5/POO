package blackjackG21;


public class RegularPlayer extends Player{
	private float balance;
	
	
	public RegularPlayer(int balance) {
		super();
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
	
	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public void addBalance(float returnBalance){
		this.balance+=returnBalance;
	}

}
