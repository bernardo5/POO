package blackjack;

import java.io.*;
import java.util.*;


public class Player extends Person{
	protected LinkedList<String> commands;
	protected LinkedList<Hand> hands = new LinkedList<Hand>();
	private float balance;
	private boolean insurance;
	//private int prevBet;
	Acefive acefive;
	Basic basic;
	HiLo hilo;
	private String Last;
	
	String followedStretegy;
	
	//Constructors
	public Player(int balance, int ncards) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=null;
		basic=new Basic();
		acefive=new Acefive();
		hilo=new HiLo(ncards);
	}
	
	public Player(int balance, int ncards, String strategy) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=null;
		basic=new Basic();
		acefive=new Acefive();
		hilo=new HiLo(ncards);
		followedStretegy=strategy;
		Last="first";
	}
	
	public Player(int balance,String file, int ncards) {
		super();
		this.setBalance(balance);
		this.insurance=false;
		this.commands=new LinkedList<String>();
		ReadFile(file);
		basic=new Basic();
		acefive=new Acefive();
		hilo=new HiLo(ncards);
	}
	
	public void SetLast(String l){
		this.Last=l;
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
	public String getplayerInput(int bet_deal, String mode, boolean bet_flag, int minBet, int lastBet, Hand hand, Card card) /*throws IOException*/{
		if(mode.equals("-i")){
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String s;
			try {
				s = bufferRead.readLine();
			
				Scanner scanner = new Scanner (s);
				String command = scanner.next ();
				if(scanner.hasNextInt()){
		        int bet=scanner.nextInt();
		        scanner.close();
		        return command+" "+Integer.toString(bet);
		    	}else{
					scanner.close();
					return command;
		    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}else if(mode.equals("-d")){
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
		}else{//strategy mode
			if(bet_flag){//want to know the amount of money to bet
				if(bet_deal==0){
					if(followedStretegy.equals("BS")||followedStretegy.equals("HL")){//follow normal bet strategy
						System.out.println("ok");
						int plus=lastBet+minBet;
						int minus=lastBet-minBet;
						if(Last.equals("first"))return "b "+minBet;
						else if(Last.equals("W"))return "b "+(plus);
						else if(Last.equals("L"))return "b "+(minus);
						else /*draw*/return "b "+lastBet;
					}else{//follow ace-five bet strategy
						if(acefive.advice().equals("min_bet"))return "b "+minBet;
						else if(acefive.advice().equals("double last bet"))return "b "+2*lastBet;
						else return acefive.advice();
					}
				}else return "d";
			}else{//want to know what action to perform
				if(followedStretegy.equals("BS")){
					return basic.advice(hand, card);
				}else if(followedStretegy.equals("BS-AF")){
					return basic.advice(hand, card);//at this point the action taken is the same as the basic
				}else if(followedStretegy.equals("HL")){
					if(hilo.advice(hand, card).contains("Using basic:")) return basic.advice(hand, card);
					else return hilo.advice(hand, card);
				}else{//HL-AF
					if(hilo.advice(hand, card).contains("Using basic:")) return basic.advice(hand, card);
					else return hilo.advice(hand, card);
				}
			}
		}
		//return null;
	}
	
	@Override
	public void win() {
		// TODO Auto-generated method stub
		this.wins++;
		System.out.println("player wins");
	}

	public Hand getNextHand(){
		int indexCurrentHand=hands.indexOf(this.getCurrentHand());
		if((indexCurrentHand!=-1)&&((indexCurrentHand+1)<hands.size()))
			return hands.get(indexCurrentHand+1);
		return null;
	}
	
	
	public class Basic implements ActionStrategy{
		@Override
		public String advice(Hand player_hand,Card dealer_card) {
			if (player_hand.getSizeofCards() == 2 && player_hand.getCards().getFirst().getRank()==player_hand.getCards().getLast().getRank()) 
			return pair_advice[player_hand.getCards().getFirst().getValue()-2][dealer_card.getValue()-2];
			else if (player_hand.numberAces()>0) return soft_advice[player_hand.getPoints()-13][dealer_card.getValue()-2];
			else return hard_advice[player_hand.getPoints()-5][dealer_card.getValue()-2];
		}

	}
	
	public class HiLo implements ActionStrategy{
		
		private float runningcount;
		private float nbdeckstotal;
		private int nbcardsrevealed;
		
		//Constructor
		public HiLo(int nbDecks){
			this.runningcount=0;
			this.nbdeckstotal=nbDecks;
			this.nbcardsrevealed=0;
		}
		//Methods
		public void cardRevealed(Card card){
			if(card.getValue()<7 &&card.getValue()>2) runningcount += 1;
			else if(card.getValue()<=11 &&card.getValue()>9) runningcount -= 1;
			nbcardsrevealed++;
		}
		
		public void restartRunningCount(){
			runningcount=0;
		}

		public float truecount(){
			float truecount=0;
			truecount=runningcount/(nbdeckstotal-(nbcardsrevealed/52));
			return truecount;
		}
		
		@Override
		public String advice(Hand hand, Card card) {
			
			float truecount = truecount();
			int player_points = hand.getPoints();
			int dealer_points = card.getValue();
			
			if((truecount>=3)&&(card.getRank()==Rank.ACE)){//insurance
				return "i";
			}else if((hand.getPoints()==16)&&(card.getValue()==10)){//16vt
				if(truecount<=0)return "s";
				else return "h";
			}else if((hand.getPoints()==15)&&(card.getValue()==10)){//15vt
				if((truecount>=0)&&(truecount<=3))return "u";
				else if(truecount>=4) return "s";
				else return "h";
			}else if((hand.getPoints()==20)&&(card.getValue()==5)){//ttv5
				if(truecount>=5)return "p";
				else return "s";
			}else if((hand.getPoints()==20)&&(card.getValue()==6)){//ttv6
				if(truecount>=4)return "p";
				else return "s";
			}else if((hand.getPoints()==10)&&(card.getValue()==10)){//10vt
				if(truecount>=4)return "2";
				else return "h";
			}else if((hand.getPoints()==12)&&(card.getValue()==3)){//12v3
				if(truecount>=2)return "s";
				else return "h";
			}else if((hand.getPoints()==12)&&(card.getValue()==2)){//12v2
				if(truecount>=3)return "s";
				else return "h";
			}else if((hand.getPoints()==11)&&(card.getValue()==11)){//11vA
				if(truecount>=1)return "2";
				else return "h";
			}else if((hand.getPoints()==9)&&(card.getValue()==2)){//9v2
				if(truecount>=1)return "2";
				else return "h";
			}else if((hand.getPoints()==10)&&(card.getValue()==11)){//10vA
				if(truecount>=4)return "2";
				else return "h";
			}else if((hand.getPoints()==9)&&(card.getValue()==7)){//9v7
				if(truecount>=3)return "2";
				else return "h";
			}else if((hand.getPoints()==16)&&(card.getValue()==9)){//16v9
				if(truecount>=5)return "s";
				else return "h";
			}else if((hand.getPoints()==13)&&(card.getValue()==2)){//13v2
				if(truecount>=-1)return "s";
				else return "h";
			}else if((hand.getPoints()==12)&&(card.getValue()==4)){//12v4
				if(truecount>=0)return "s";
				else return "h";
			}else if((hand.getPoints()==12)&&(card.getValue()==5)){//12v5
				if(truecount>=-2)return "s";
				else return "h";
			}else if((hand.getPoints()==12)&&(card.getValue()==6)){//12v6
				if(truecount>=-1)return "s";
				else return "h";
			}else if((hand.getPoints()==13)&&(card.getValue()==3)){//13v3
				if(truecount>=-2)return "s";
				else return "h";
			}else if((hand.getPoints()==14)&&(card.getValue()==10)){//14vt
				if(truecount>=3)return "u";
				else return new Basic().advice(hand, card);
			}else if((hand.getPoints()==15)&&(card.getValue()==9)){//15v9
				if(truecount>=2)return "u";
				else return new Basic().advice(hand, card);
			}else if((hand.getPoints()==15)&&(card.getValue()==9)){//15vA
				if(truecount>=1)return "u";
				else return new Basic().advice(hand, card);
			}
			return "Using basic: "+new Basic().advice(hand, card);
		}

	}
	
	public class Acefive implements ActionStrategy,BetStrategy{

		private int count = 0;
		
		public void cardRevealed(Card card){
			if(card.getValue()==5) count++;
			if(card.getValue()==11) count--;
		}
		
		public void resetCount(){
			count=0;
		}
		
		@Override
		public String advice(Hand player_hand, Card dealer_card) {
			String bet_action=this.advice();
			if (player_hand.getSizeofCards() == 2 && player_hand.getCards().getFirst().getRank()==player_hand.getCards().getLast().getRank()) 
				return bet_action+"-> "+pair_advice[player_hand.getCards().getFirst().getValue()-2][dealer_card.getValue()-2];
			else if (player_hand.numberAces()>0) return bet_action+"-> "+soft_advice[player_hand.getPoints()-13][dealer_card.getValue()-2];
			else return bet_action+"-> "+hard_advice[player_hand.getPoints()-5][dealer_card.getValue()-2];
		}

		@Override
		public String advice() {
			if(count>=2)return "2";
				else if(count<=1) return "min_bet";
			return "double last bet";
		}

	}
	

}
