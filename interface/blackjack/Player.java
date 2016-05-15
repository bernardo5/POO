package blackjack;

import java.io.*;
import java.util.*;


public class Player extends Person{
	protected LinkedList<String> commands;
	protected LinkedList<Hand> hands = new LinkedList<Hand>();
	private float balance;
	private boolean insurance;
	private String Last;
	int handnumber;
	private String followedStretegy;
	
	/**
	 * Constructor for interactive mode
	 * @param balance
	 * @param ncards
	 */
	public Player(int balance, int ncards) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=null;
		handnumber=0;
		Last="first";
	}
	
	/**
	 * Constructor for simulation mode
	 * @param balance
	 * @param ncards
	 * @param strategy
	 */
	public Player(int balance, int ncards, String strategy) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=null;
		followedStretegy=strategy;
		Last="first";
		handnumber=0;
	}
	/**
	 * Constructor for debug mode
	 * @param balance
	 * @param file
	 * @param ncards
	 */
	public Player(int balance,String file, int ncards) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=new LinkedList<String>();
		ReadFile(file);
		//System.out.println(commands);
		handnumber=0;
		Last="first";
	}
	
	/**
	 * 
	 * @param l - last game result
	 */
	public void SetLast(String l){
		this.Last=l;
	}
	
	/**
	 * 
	 * @return - last game result
	 */
	public String getLast(){
		return this.Last;
	}
	
	public String getCurrentStrategy(){
		return this.followedStretegy;
	}
	
	//Getters
	public float getBalance() {
		return balance;
	}
	
	/**
	 * 
	 * @return if the player has insured
	 */
	public boolean getInsurance(){
		return insurance;
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
	public void changeInsurance(boolean insurance){
		this.insurance=insurance;
	}
	
	
	public void loses(){
		super.lost();
	}
	
/**
 * gets user commands for debug mode and places them in a linked list (commands)
 * @param file
 */
	public void ReadFile(String file){
		String line;
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
		    while ((line = br.readLine()) != null) {
		    	String[] arr = line.split(" ");
		    	for(String c:arr){
		    		commands.addLast(c);
		    	}
		    }
		    br.close();
		}catch(FileNotFoundException e){
	    	System.out.println("File not found");
	    	System.exit(1);
	    }catch(IOException e){
	    	System.out.println("Cannot open file");
	    	System.exit(1);
	    } 
	}
/**
 * 
 * @return player next hand, in case of previous split, or null if he has no more hands to play
 */
	public Hand getNextHand(){
		int indexCurrentHand=hands.indexOf(this.getCurrentHand());
		if((indexCurrentHand!=-1)&&((indexCurrentHand+1)<hands.size()))
			return hands.get(indexCurrentHand+1);
		return null;
	}

}
