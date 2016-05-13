package blackjack;

import java.io.*;
import java.util.*;


public class Player extends Person{
	protected LinkedList<String> commands;
	protected LinkedList<Hand> hands = new LinkedList<Hand>();
	private float balance;
	private boolean insurance;
	//private int prevBet;
	private String Last;
	int handnumber;
	
	private String followedStretegy;
	
	//Constructors
	public Player(int balance, int ncards) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=null;
		handnumber=0;
	}
	
	public Player(int balance, int ncards, String strategy) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=null;
		followedStretegy=strategy;
		Last="first";
		handnumber=0;
	}
	
	public Player(int balance,String file, int ncards) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=new LinkedList<String>();
		ReadFile(file);
		//System.out.println(commands);
		handnumber=0;
	}
	
	public void SetLast(String l){
		this.Last=l;
	}
	
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
	
	//Methods
	/*public String showHands(){
		//player may have more than one hand and shows all cards
		String game=new String();
		int i=1;
		if(hands.size()==1){
			game = "Hand : "+hands.toString()+"\n";
		}else{
			for(Hand aux:hands){
				game+="Hand "+ i+": "+aux.toString()+"\n";
				i++;
			}
		}
		return game;
	}*/
	
	public void loses(){
		super.lost();
	}
	

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
	    	 e.printStackTrace();
	    }catch(IOException e){
           e.printStackTrace();
	    } 
	}

	public Hand getNextHand(){
		int indexCurrentHand=hands.indexOf(this.getCurrentHand());
		if((indexCurrentHand!=-1)&&((indexCurrentHand+1)<hands.size()))
			return hands.get(indexCurrentHand+1);
		return null;
	}

}
