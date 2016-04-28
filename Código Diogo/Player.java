package blackjackG21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;

public class Player extends Person{
	private float balance;
	protected LinkedList<Hand> hands=new LinkedList<Hand>();
	
	//Constructor
	public Player(int balance) {
		super();
		this.setBalance(balance);
	}
	
	//Getters
	public float getBalance() {
		return balance;
	}
	//Setters
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public void addBalance(float f){
		this.balance+=f;
	}
	public void subtractBalance(float f){
		this.balance-=f;
	}
	
	
	//Methods
	public String showHands(){
		/*player may have more than one hand and shows all cards*/
		String game=new String();
		int i=1;
		for(Hand aux:hands){
			game+="Hand "+i+": "+aux.toString()+"\n";
		}
		return game;
	}
	
	public boolean placeBet(int bet){//the player has to do at least a bet
		System.out.println("bet command");
		if(bet>getBalance()){
			System.out.println("You cannot afford this bet. Your balance is:"+getBalance());
			return false;
		}else{
			setBalance(getBalance()-bet);
			return true;
		}
	}
	
	//Input from Stdin
	public String getplayerInput() throws IOException{
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String s;
		s = bufferRead.readLine();
		Scanner scanner = new Scanner (s);
	    String command =	scanner.next ();
	    if(scanner.hasNextInt()){
	        int bet=scanner.nextInt();
	        scanner.close();
	        return command+" "+Integer.toString(bet);
	    }else{
			scanner.close();
			return command;
		}
	}
	
	//Input from File
	public String getplayerCommandsfromFile(){
		return null;
	}

/*
	public static void main(String[] args) {
		Card card1 = new Card(Rank.valueOf("ACE"), Suit.valueOf("SPADES"));
		Card card2 = new Card(Rank.valueOf("QUEEN"), Suit.valueOf("HEARTS"));
		Card card3 = new Card(Rank.valueOf("TEN"), Suit.valueOf("DIAMONDS"));
		Player player1 = new Player(100);
		
		Hand hand1 = new Hand(card1, card2);
		
		player1.addHand(hand1);
		player1.hands.getFirst().addCard(card3);

		System.out.println(player1.showHands());
	}*/

}
