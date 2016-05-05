package blackjack;

import java.io.*;
import java.util.*;


public class Player extends Person{
	protected LinkedList<String> commands;
	protected LinkedList<Hand> hands=new LinkedList<Hand>();
	private float balance;
	private boolean insurance;
	//private int prevBet;
	
	//Constructors
	public Player(int balance) {
		super();
		this.setBalance(balance);
		this.insurance=false;
	}
	
	public Player(int balance,String file) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=new LinkedList<String>();
		ReadFile(file);
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
	public String showHands(){
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
	}
	
	/*public boolean placeBet(int bet){
		System.out.println("bet command");
		if(bet>getBalance()){
			System.out.println("You cannot afford this bet. Your balance is:"+getBalance());
			return false;
		}else{
			setBalance(getBalance()-bet);
			return true;
		}
	}*/
	
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
	
	//Input from Stdin
	public String getplayerInput(String mode) throws IOException{
		if(mode.equals("-i")){
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
		}else /*if(mode.equals("-d"))*/{
			if(commands.isEmpty()) return "q"; //no more commands to read
			else{
				String s=this.commands.removeFirst();
				if(s.equals("b")){
					try{
						System.out.println("top:"+Integer.parseInt(commands.getFirst()));
						return s+=" "+commands.removeFirst();
					}catch(NumberFormatException e){
						return s;
					}
				}else return s;					
			}
		}
	}
	
	
	public Hand getNextHand(){
		int indexCurrentHand=hands.indexOf(this.getCurrentHand());
		if((indexCurrentHand!=-1)&&((indexCurrentHand+1)<hands.size()))
			return hands.get(indexCurrentHand+1);
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
