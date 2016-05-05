package blackjack;

public abstract class Person {
	protected Hand current;
	protected int wins;
	protected int draws;
	protected int loses;
	
	//Constructor
	public Person(){
		this.wins=0;
		this.draws=0;
		this.loses=0;
	}
	
	//Setters
	public void setCurrentHand(Hand hand){
		current=hand;
	}
	public void win(){
		this.wins++;
		System.out.println("wins");
	}
	public void draw(){
		this.draws++;
		System.out.println("loses");
	}
	public void lost(){
		this.loses++;
		System.out.println("loses");
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
		
	//public abstract void stand(Hand h);
		
	public boolean hit(Card card){
		current.addCard(card);
		return current.bust();
	}
	
	//Methods to Override
	public abstract String showHands();
	
}
