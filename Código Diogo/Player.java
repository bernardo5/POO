package blackjackG21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Player extends Person{
	private float balance;
	
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
	
	public void insurance(){
	}
	
	public void surrender(){
	}
	
	public void splitting(){
	}
	
	public void doubledown(){
	}
	

	/*public static void main(String[] args) {
		RegularPlayer player=new RegularPlayer(new Card("Q",'S'), new Card("A",'S'), 15);
		player.hit(new Card("A",'S'), 0);
		System.out.println(player.showHands());
	}*/

}
