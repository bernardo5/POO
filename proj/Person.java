package blackjack;

public abstract class Person {
	protected Hand current;
	protected int wins, draws, loses, blackjacks;

	//Constructor
	public Person(){
		this.wins=0;
		this.draws=0;
		this.loses=0;
		this.blackjacks=0;
	}
	
	//Setters
	public void setCurrentHand(Hand hand){
		current=hand;
	}
	public abstract void win();
	public void draw(){
		this.draws++;
		//System.out.println("draw");
	}
	public void lost(){
		this.loses++;
		//System.out.println("loses");
	}
	public void blackjack(){
		this.blackjacks++;
		//System.out.println("wins");
	}
		
	//Getters
	public Hand getCurrentHand(){
		return current;
	}
	public int getwins(){
		return this.wins;
	}
	public int getdraws(){
		return this.draws;
	}
	public int getloses(){
		return this.loses;
	}
	public int getblackjacks(){
		return this.blackjacks;
	}
	
	//Methods
	public int roundsplayed(){
		return this.blackjacks+this.draws+this.loses+this.wins;
	}
		
	public boolean hit(Card card){
		current.addCard(card);
		return current.bust();
	}
	
	//Methods to Override
	public abstract String showCurrentHand();
	
}
