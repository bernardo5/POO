package blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Game {
	
	protected Player player1;
	protected Shoe shoe;
	protected Table table;
	protected Dealer dealer;
	protected Acefive acefive;
	protected Basic basic;
	protected HiLo hilo;
	
	protected int s_number;
	protected int bet;
	private int bet_deal;
	protected int total_hands;
	protected boolean insure_surrender;
	
	/**
	 * Initializes all independent variables
	 * @param minBet
	 * @param maxBet
	 */
	
	public Game(){
		s_number=0;
		acefive=new Acefive();
		basic=new Basic();
		dealer = new Dealer();
		setBet_deal(0);
		total_hands=0;
		insure_surrender=true;
	}
	
	public Game(int minBet, int maxBet){
		this();
		table = new Table(minBet, maxBet);
		bet=minBet;
	}
	/**
	 * Initializes game for interactive mode, which is the common game of Blackjack
	 * @param nshoe - number of shoes
	 * @param shuffle - shuffle percentage
	 * @param numberDecks - number of decks in the shoe
	 * @param balance - initial player balance 
	 * @param minBet
	 * @param maxBet
	 */
	public Game(int shuffle, int numberDecks, int balance, int minBet, int maxBet){
		this(minBet, maxBet);
		shoe = new Shoe(numberDecks, shuffle);
		shoe.populateShoe();
		shoe.shuffleShoe();
		System.out.println("shuffling the shoe...\n");
		player1 = new Player(balance, shoe.nCards()/52);
		hilo=new HiLo(shoe.nCards()/52);
	}
	/**
	 * Calculates the game statistics
	 * @param initialbalance
	 */
	public void statistics(int initialbalance){
		if(total_hands!=0)
			System.out.println("BJ P/D " + ((float)player1.getblackjacks()/total_hands)+"/"+((float)dealer.getblackjacks()/dealer.roundsplayed()));
		else System.out.println("Game has just started!");
		if(player1.roundsplayed()!=0){
			System.out.println("Win " + (float)player1.getwins()/player1.roundsplayed());
			System.out.println("Lose " + (float)player1.getloses()/player1.roundsplayed());
			System.out.println("Push " + (float)player1.getdraws()/player1.roundsplayed());
		}else{
			System.out.println("Win " + player1.getwins());
			System.out.println("Lose " + player1.getloses());
			System.out.println("Push " + player1.getdraws());
		}
		
		System.out.println("Balance " + player1.getBalance() + " ("+ ((100*(float)(player1.getBalance()/initialbalance))-100) +"%)" );
	}
	/**
	 * checks if the mode allows shuffling
	 * @return
	 */
	protected boolean allowedShuffling(){
		if(shoe.getShufflePercentage()!=101)return true;
		else return false;
	}
	/**
	 * Checks if the shoe usage percentage is enough to shuffle again
	 */
	public void checkShuffle(){
		if(shoe.calculateUsagePercentage()>=shoe.getShufflePercentage()){
			acefive.resetCount();
			hilo.restartRunningCount();
			shoe.shuffleShoe();//Shuffle
			System.out.println("shuffling the shoe...\n");
			s_number++;
		}
	}
	
	/**
	 * 
	 * @return - user typed command
	 */
		public String getplayerInput() /*throws IOException*/{
			
			try {
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s;
				
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
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			//return null;
		}
	
	public String getCommandFromPlayer(){
		String command=new String();
		command = getplayerInput();	
		return command;
	}
	/**
	 * Places a bet corresponding to the typed command
	 * @param command
	 */
	public void betAction(String command){
		if(player1.getBalance()>=table.getMinBet() && getBet_deal() == 0){//Se forem dadas as cartas ja nao pode apostar e so pode apostar se tiver maior balance que a aposta minima
			String[] bets = command.split(" ");
			if(bets.length==1){
				player1.subtractBalance(bet);
				setBet_deal(getBet_deal() + 1);
			}else if(bets.length==2){
				//System.out.println(bets[1]+" bets[1]");
				if(Integer.parseInt(bets[1])<=player1.getBalance()){
					bet=Integer.parseInt(bets[1]);
					player1.subtractBalance(bet);
					setBet_deal(getBet_deal() + 1);
				}else System.out.println("b: Illegal command-menor balance");//< balance
			}else System.out.println("b: Illegal command-mais 2 args");//more than 2 arguments
		}else{
			System.out.println("b: Illegal command");/*Can´t use bet*/
		}
	}
	/**
	 * 
	 * @return boolean saying if cards were dealt (true) or not (false)
	 */
	public boolean CardsDealt(){
		if(getBet_deal()<2)return false;
		else return true;
	}
	
	public void dealWithBlackJack(){
		if(dealer.getVisibleCard().getRank()!=Rank.ACE){
			System.out.println("blackjack!!");
			System.out.println("dealer's hand "+dealer.showCurrentHandAll());
			player1.blackjack();
			if(dealer.getCurrentHand().getPoints()==21){//dealer also has blackjack
				player1.setBalance(player1.getBalance()+bet);
				dealer.blackjack();
				player1.SetLast("Draw");
				//draw
				player1.draw();System.out.println("player pushes and his current balance is "+player1.getBalance());
				dealer.draw();
			}else{
				//player won-blackjack pays 3 to 2
				dealer.lost();
				player1.addBalance((float)(1.5*bet+bet));
				player1.SetLast("W");
				System.out.println("player wins and his current balance is "+player1.getBalance());
			}
			//System.out.println("end of turn");
			//collect the cards
			player1.handnumber=0;
			player1.hands.clear();
			player1.setCurrentHand(null);
			dealer.setCurrentHand(null);
		}//else it is possible to insure and do other possibilities
	}
	
	/**
	 * deals cards to dealer and player and if player has blackjack it assigns the result directly
	 */
	public boolean dealAction(){
		if(getBet_deal()==1){
			insure_surrender=true;
			total_hands++;
			player1.handnumber=1;
			//distributeCards();
			Card a=shoe.takeCard();
			acefive.cardRevealed(a);
			hilo.cardRevealed(a);
			Hand d=new Hand(a, shoe.takeCard(),0);
			a=shoe.takeCard();
			Card b=shoe.takeCard();
			acefive.cardRevealed(a);
			acefive.cardRevealed(b);
			hilo.cardRevealed(a);
			hilo.cardRevealed(b);
			Hand p=new Hand(a, b,bet);
			player1.hands.add(p);
			player1.setCurrentHand(p);
			dealer.setCurrentHand(d);
			//printCards();
			System.out.println("dealer's hand "+ dealer.showCurrentHand());
			System.out.println("player's hand "+ player1.showCurrentHand());

			if(player1.hands.getFirst().getPoints()==21){//blackjack
				return true;
			}
			setBet_deal(getBet_deal() + 1);
		}else System.out.println("d: illegal command");
		return false;
	}
	
	public boolean playableHand(){
		if(player1.getCurrentHand()!=null)return true;
		else return false;
	}
	/**
	 * 
	 * @return true if player doubled down in the last play
	 */
	public boolean doubledDown(){
		if(player1.getCurrentHand().getBet()==2*bet)return true;
		else return false;
	}

	public String getPlayCommandFromPlayer(){
		String command=new String();
		command = getplayerInput();
		return command;
	}
	/**
	 * deals another card to the player
	 * @return true if there are no more hands to play, false if there are more hands to play
	 */
	public boolean hitAction(){
		//Se fez double down so pode fazer hit uma vez
		System.out.println("player hits");
		insure_surrender=false;
		Card a=shoe.takeCard();
		acefive.cardRevealed(a);
		hilo.cardRevealed(a);
		if(player1.hit(a)){
			if(player1.handnumber==1&&player1.hands.size()==1)
				System.out.println("player's hand "+ player1.showCurrentHand()+"\nplayer busts");
			else {
				System.out.println("player's hand ["+player1.handnumber+"] "+player1.showCurrentHand());
				System.out.println("player busts ["+player1.handnumber+"]");
				System.out.println("player loses and is current balance is "+player1.getBalance());
			}
			dealer.win();
			player1.SetLast("L");
			
			if(player1.getNextHand()==null){
				System.out.println("dealer's hand "+dealer.showCurrentHandAll());
				player1.lost();System.out.println("player loses and his current balance is "+player1.getBalance());
				player1.hands.remove(player1.getCurrentHand());
				player1.setCurrentHand(null);							
				return true;
			}else{
				player1.loses();
				Hand remove=player1.getCurrentHand();
				player1.setCurrentHand(player1.getNextHand());
				player1.hands.remove(remove);
				System.out.println("playing hand "+(++player1.handnumber)+"...");
				System.out.println("player's hand ["+player1.handnumber+"] "+player1.showCurrentHand());
			}				
			//player1.hands.remove(hand);
		}else {
			if(player1.handnumber==1&&player1.hands.size()==1)
				System.out.println("player's hand "+ player1.showCurrentHand()); 
			else System.out.println("player's hand ["+player1.handnumber+"] "+player1.showCurrentHand());
		}
		return false;
	}
	
	/**
	 * 
	 * @return true if there are no more hands to play, false otherwise
	 */
	public boolean standAction(){
		System.out.println("player stands"); 
		if(player1.getNextHand()!=null){
			player1.setCurrentHand(player1.getNextHand());
			System.out.println("playing hand "+(++player1.handnumber)+"..."); 
			System.out.println("player's hand "+player1.showCurrentHand());
		}else{
			System.out.println("dealer's hand "+dealer.showCurrentHandAll());
			return true;
		}
		return false;
	}
	
	public boolean canUseSideRules(){
		if((player1.hands.peekFirst()!=null)&&(player1.current.getCards().size()==2))return true;
		else return false;
	}
	
	public void surrenderAction(){
		if(canUseSideRules()){
			System.out.println("player surrends...");
			player1.addBalance((float)player1.getCurrentHand().getBet()/2);
			player1.hands.clear();
			player1.setCurrentHand(null);
			dealer.setCurrentHand(null);
		}else System.out.println("You cannot surrender :(");
	}
	
	public void splitAction(){
		if(player1.getBalance()>=table.getMinBet() && player1.getCurrentHand().getSizeofCards()==2 && player1.hands.size()<4){
			//if cards have the same face value
			if(player1.getCurrentHand().getCards().get(0).getRank().getRankValue() == player1.getCurrentHand().getCards().get(1).getRank().getRankValue()){
				total_hands++;
				insure_surrender=false;
				System.out.println("player is splitting");
				Card card1 = player1.getCurrentHand().getCards().get(0);
				Card card2 = player1.getCurrentHand().getCards().get(1);
				Hand hand1=new Hand(card1,shoe.takeCard(),bet);
				player1.hands.add(hand1);
				player1.hands.add(new Hand(card2,shoe.takeCard(),bet));
				player1.hands.remove(player1.getCurrentHand());
				player1.setCurrentHand(hand1);
				player1.subtractBalance(bet);
				System.out.println("player's hand ["+player1.handnumber+"]"+player1.showCurrentHand());
			}else System.out.println("p: illegal command -> You need to have two similar cards to split");
		}else {
			System.out.println("p: illegal command"); 
		}
	}
	
	public boolean DoubleAllowed(){
		if((player1.getCurrentHand().getPoints()==9)
				||(player1.getCurrentHand().getPoints()==10)
					||(player1.getCurrentHand().getPoints()==11))return true;
		else return false;
	}
	
	public void doubleDownAction(){
		if((canUseSideRules())&&(player1.getBalance()>=bet)&&DoubleAllowed()){
			player1.subtractBalance(bet);
			player1.getCurrentHand().setBet(2*bet);
			player1.hands.get(player1.hands.indexOf(player1.current)).setBet(2*bet);
			player1.getCurrentHand().addCard(shoe.takeCard());
			System.out.println("player current hand: "+player1.current.toString());
		}else System.out.println("2: illegal command");
	}
	/**
	 * 
	 * @return true if player still has hands that did not bust
	 */
	public boolean playerDidNotBust(){
		if((dealer.getCurrentHand()!=null)&&(player1.hands.size()>0))return true;
		else return false;
	}
	/**
	 * 
	 * @return true if dealer has Blackjack
	 */
	public boolean DBlackjack(){
		if(dealer.getCurrentHand().getPoints()==21)return true;
		else return false;
	}
	
	public void CheckAndPayIns(){
		if(player1.getInsurance()){
			player1.addBalance(2*bet);
			player1.changeInsurance(false);
		}
	}
	
	public void DHit(){
		while(dealer.getCurrentHand().getPoints()<17){
			System.out.println("dealer hits");
			Card card=shoe.takeCard();
			acefive.cardRevealed(card);
			hilo.cardRevealed(card);
			dealer.hit(card);
			System.out.println("dealer's hand "+ dealer.showCurrentHandAll());
		}
		if(dealer.current.getPoints()<=21)System.out.println("dealer stands");
		else System.out.println("dealer busts");
	}
	/**
	 * Pays the money accordingly to the result
	 */
	public void SetResults(){
		for(Hand h:player1.hands){//check if a players hand beats the dealer's hand
			if(h.getPoints()==21&&h.getCards().size()==2)System.out.println("blackjack!!");
			if((h.getPoints()>dealer.getCurrentHand().getPoints())||(dealer.getCurrentHand().getPoints()>21)){
				player1.addBalance(2*h.getBet());
				player1.win();System.out.println("player wins and his current balance is "+player1.getBalance());
				dealer.lost();
				player1.SetLast("W");
			}else if(h.getPoints()==dealer.getCurrentHand().getPoints()){
				player1.addBalance(h.getBet());
				player1.draw();System.out.println("player pushes and his current balance is "+player1.getBalance());
				dealer.draw();
				player1.SetLast("D");
				//System.out.println("draw");
			}else{
				//System.out.println("dealer wins");
				dealer.win();
				player1.lost();System.out.println("player loses and his current balance is "+player1.getBalance());
				player1.SetLast("L");
			}
		}
		//see what card the dealer had
		Card hidden=dealer.returnHiddenCard();
		acefive.cardRevealed(hidden);
		hilo.cardRevealed(hidden);
		//collectCards();
		player1.hands.clear();
		player1.setCurrentHand(null);
		dealer.setCurrentHand(null);
	}
	
	public Player getPlayer(){
		return player1;
	}
	
	public Dealer getDealer(){
		return dealer;
	}
	
	public String actionAdvices(boolean action){
		if(action)return("According to Basic strategy: " + basic.advice(player1.getCurrentHand(),dealer.getVisibleCard())+"\n"+
		"According to Ace-Five strategy: " + acefive.advice(player1.getCurrentHand(),dealer.getVisibleCard())+"\n"+
		"According to Hi-Low strategy: " + hilo.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
		else return ("According to Ace-Five strategy: " + acefive.advice());
	}
	
	public void insureAction(){
		System.out.println("player is insuring");
		player1.subtractBalance(bet);
		player1.changeInsurance(true);
	}
	
	/**
	 * Interactive mode part
	 * @param initialBalance
	 */
	public void play(String initialBalance){
		String command = " ";
		while(true){
			if(allowedShuffling()) checkShuffle();
					//System.out.println(player1.getCurrentStrategy());
			
			//----------------------Before Bet and Deal----------------------------
			while(!CardsDealt()){
				
				command=getCommandFromPlayer();
				
				if(command.startsWith("b")){//Bet
					betAction(command);
				}else if(command.equals("d")){//Deal
					if(dealAction())dealWithBlackJack();
				}else if(command.equals("$")){//Current balance
					System.out.println("player current balance is "+ player1.getBalance());
				}else if(command.equals("q")){
					System.exit(0);
				}else if(command.equals("ad")){
					actionAdvices(false);
				}else if(command.equals("st")){
					statistics(Integer.parseInt(initialBalance));
				}else System.out.println("Illegal command");
			}
			setBet_deal(0);
			//---------------------After Bet and Deal-------------------------------
			while(playableHand()){
				//Check if player doubled down
				if(doubledDown()) command = "s";
				else{
					command=getPlayCommandFromPlayer();
				}
				
				if(command.equals("$")){//Current balance
					System.out.println("player current balance is "+ player1.getBalance());
				}else if(command.equals("h")){//Hit
					if(hitAction())break;
				}else if(command.equals("s")){//Stand
					if(standAction())break;
				}else if(command.equals("u")){
					surrenderAction();
				}else if(command.equals("p")){//allow resplitting until the player has as many as four hands and doubling a hand after splitting
					splitAction();
				}else if(command.equals("2")){//only on an opening hand worth 9,10,11 and always doubles the bet;take only one more card from the dealer
					doubleDownAction();
				}else if(command.equals("ad")){
					System.out.println(actionAdvices(true));
				}else if(command.equals("st")){
					statistics(Integer.parseInt(initialBalance));
				}else if(command.equals("q")){
					System.exit(0);
				}else if(command.equals("i")&&canUseSideRules()&&player1.getBalance()>=bet){
					insureAction();
				}else System.out.println("Illegal command");			
			}
			//-----------------Dealer-------------------------------
			if(playerDidNotBust()){
				if(DBlackjack()) CheckAndPayIns();
				
			DHit();
			SetResults();
			}
			
		}
	}

	public int getBet_deal() {
		return bet_deal;
	}

	public void setBet_deal(int bet_deal) {
		this.bet_deal = bet_deal;
	}
}
