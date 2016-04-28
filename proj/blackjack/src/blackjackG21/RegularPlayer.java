package blackjackG21;

import java.util.LinkedList;

public class RegularPlayer extends Player{
	private float balance;
	
	protected LinkedList<Hand> hands;
	
	public RegularPlayer(int balance) {
		super();
		hands= new LinkedList<Hand>();
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
	
	public void addHand(Hand hand){
		hands.add(hand);
	}
	
	public Hand getNextHand(){
		int indexCurrentHand=hands.indexOf(this.getCurrentHand());
		if((indexCurrentHand!=-1)&&((indexCurrentHand+1)<hands.size()))
			return hands.get(indexCurrentHand+1);
		return null;
	}

}
