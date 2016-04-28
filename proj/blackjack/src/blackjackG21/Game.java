package blackjackG21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import blackjackG21.Card.Rank;

public class Game {
	
	private int minBet;
	private int maxBet;
	private int nDecks;
	private int shufflePercentage; //% of shoe played begore shuffling 10-100
	private Dealer dealer;
	private RegularPlayer player;
	private Shoe shoe;
	
	public Game(int minBet,	int maxBet, int playersBalance,	int nDecks,	int shufflePercentage/*, String file*/){
		this.minBet=minBet;
		this.maxBet=maxBet;
		this.nDecks=nDecks;
		this.shufflePercentage=shufflePercentage;
		shoe=new Shoe(nDecks, shufflePercentage);
		//shoe.populateShoeFromFile(file, nDecks);
		shoe.populateShoe(nDecks);
		player=new RegularPlayer(playersBalance);
		dealer=new Dealer();
	}
	
	public void distributeHands(int bet){
		Hand p=new Hand(shoe.takeCard(), shoe.takeCard(), bet);
		Hand d=new Hand(shoe.takeCard(), shoe.takeCard(), 0);
		player.currentHand(p);
		player.addHand(p);
		dealer.currentHand(d);
	}
	
	public void showCards(){
		System.out.println("Player hand: "+ player.showHands());
		System.out.println("Dealer's hand: "+ dealer.showHands());
		
	}
	
	public int placeFirstBet(int bet){//the player has to do at least a bet
		System.out.println("bet command");
		if((bet==-1)||(bet<minBet)){
			player.setBalance(player.getBalance()-minBet);
			player.hands.getFirst().setCurrentBet(minBet);
			return minBet;
		}else{
			player.setBalance(player.getBalance()-bet);
			player.hands.getFirst().setCurrentBet(bet);
			return bet;
		}
	}
	
	public boolean placeBet(int bet, Hand h){//the player has to do at least a bet
		System.out.println("bet command");
		if(bet>player.getBalance()){
			System.out.println("You cannot afford this bet. Your balance is:"+player.getBalance());
			return false;
		}else{
			player.setBalance(player.getBalance()-bet);
			h.setCurrentBet(h.getCurrentBet()+bet);
			return true;
		}
	}
	
	public String[] getCommand(char mode){
		if(mode=='i'){//interactive mode
			String s=new String();
			String command=new String();
			Scanner scanner;
			int bet=-1;
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	        try {
				s = bufferRead.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        scanner = new Scanner (s);
	        command =	scanner.next ();
	        if(scanner.hasNextInt())bet=scanner.nextInt();
			scanner.close();
			return new String[]{command, Integer.toString(bet)};
		}else if(mode=='d'){//debug mode
			
		}else if(mode=='s'){//simulation mode
			
		}
		return null;
	}
	
	public void decidePlayOutcome(){
		int bDealer=0;
		boolean tight=false;
		while(dealer.getCurrentHand().getTotal()<=17){
			bDealer=dealer.hit(shoe.takeCard());
		}
		this.showCards();
		if(bDealer==0){//dealer didnot bust
			for(Hand ha:player.hands){//check if a players hand beats the dealer's hand
				if(ha.getTotal()>=dealer.getCurrentHand().getTotal()){
					if(ha.getTotal()==dealer.getCurrentHand().getTotal()){
						//check who has less cards
						if(player.getCurrentHand().getHand().size()<dealer.getCurrentHand().getHand().size()){
							//player has less cards
							player.setBalance(player.getBalance()+(2*player.getCurrentHand().getCurrentBet()));
							System.out.println("You won this round!!!");
						}else if(ha.getHand().size()==dealer.getCurrentHand().getHand().size()){
							//same card number
							tight=true;
						}
					}else{
						//player won
						player.setBalance(player.getBalance()+(2*player.getCurrentHand().getCurrentBet()));
						System.out.println("You won this round!!!");
						tight=false;
						break;
					}
					
					
				}
			}
			
			if(tight){
				player.setBalance(player.getBalance()+player.getCurrentHand().getCurrentBet());
				System.out.println("Nobody won this round!!!");
				tight=false;
			}
		}else{//player won
			player.setBalance(player.getBalance()+(2*player.getCurrentHand().getCurrentBet()));
			System.out.println("You won this round!!!");
		}
	}
	
	public void play(char mode){
		
		int bust=0;
		int bDealer=0;
		String[] command=new String[2];
		command[0]=" ";
		command[1]=" ";
		int prevBet = minBet;
		int bet=0;
		while(!command[0].equals("q")){
			command=this.getCommand(mode);
			System.out.println("read command "+command[0]);
			
			switch(command[0]){
				case "b": 
					if((player.getCurrentHand()==null)&&(player.getBalance()>minBet)){//can only bet before deal
							if(Integer.parseInt(command[1])==-1){
								if(prevBet>player.getBalance()){
									System.out.println("You cannot afford this bet. Your balance is:"+player.getBalance());
								}else{
									player.setBalance(player.getBalance()-prevBet);
									bet+=prevBet;
								}
							}else{
								if(Integer.parseInt(command[1])>player.getBalance()){
									System.out.println("You cannot afford this bet. Your balance is:"+player.getBalance());
								}else{
									player.setBalance(player.getBalance()-Integer.parseInt(command[1]));
									bet+=Integer.parseInt(command[1]);
									prevBet=Integer.parseInt(command[1]);
								}
							}
					
					}else System.out.println("b: Illegal command");
					break;
				case "$":
					System.out.println("Your current balance is: "+player.getBalance());
					break;
				case "d":
					if(bet!=0){
						this.distributeHands(bet);
						this.showCards();
						if(player.hands.getFirst().getTotal()==21){//blackjack
							//fzr parte da seguranca
							if(dealer.getCurrentHand().getTotal()==21){//dealer also has blackjack
								player.setBalance(player.getBalance()+((player.hands.getFirst().getCurrentBet())));
								System.out.println("nobody won this round!!!");
							}else{
								//player won-blackjack pays 3 to 2
								player.setBalance(player.getBalance()+((3*(player.hands.getFirst().getCurrentBet()))/2)+player.hands.getFirst().getCurrentBet());
								System.out.println("You won this round!!!");
							}
							System.out.println("end of turn");
							player.hands.clear();
							player.currentHand(null);
							dealer.currentHand(null);
						}
					}else System.out.println("You havent placed your bet yet");
					break;
			}
			
				while(player.getCurrentHand()!=null){
					command=this.getCommand(mode);
					if(shoe.calculateUsagePercentage()>=shoe.getShufflePercentage()){
						System.out.println("Shuffling...");
						shoe.shuffle();
					}
					//
					switch(command[0]){
						
						case "$":
							System.out.println("Your current balance is: "+player.getBalance());
							break;
						
						case "h":
							if((player.getCurrentHand().getCurrentBet()!=0)&&(player.hands.peekFirst()!=null)){
								System.out.println("hit option");
								//get a new card to the hand
								bust=player.hit(shoe.takeCard());
								this.showCards();
								if(bust==1){
									//dealer won
									player.getCurrentHand().setCurrentBet(0);
									bust=0;
									
									if(player.getNextHand()==null){
										this.decidePlayOutcome();
										player.hands.clear();
										player.currentHand(null);
										dealer.currentHand(null);
									}else{//there is another hand to play with
										player.currentHand(player.getNextHand());
									}
									bet=0;
								}
							}else System.out.println("cards not dealed yet");
								break;
						case "s":
							if(player.getCurrentHand().getCurrentBet()!=0){
								System.out.println("stand option");
								//fazer a parte do dealer
								
								if(player.getNextHand()==null){
									//AQUIIIIIIIIIIIIIIIIIIII!!!!!!!!!!!!!!!!!!!!!!!!!
									this.decidePlayOutcome();
									System.out.println("end of turn");
									player.getCurrentHand().setCurrentBet(0);
									
									
									
									player.hands.clear();
									player.currentHand(null);
									dealer.currentHand(null);
									bet=0;
								}else{//there is another hand to play with
									player.currentHand(player.getNextHand());
								}
								
							}else System.out.println("You havent placed your bet yet");
							break;
						case "i":
							if(player.getCurrentHand().getCurrentBet()!=0&&(player.hands.peekFirst()!=null)&&(player.hands.size()!=2)&&((dealer.returnShownCard()).equals(Rank.ACE))){
								System.out.println("insurance option");
								if(dealer.getCurrentHand().getTotal()==21){//dealer has blackjack
									player.addBalance(player.getCurrentHand().getCurrentBet());
								}
							}else System.out.println("You cannot use side rules at the moment");
							break;
						case "u":
							if(player.getCurrentHand().getCurrentBet()!=0&&(player.hands.peekFirst()!=null)&&(player.hands.size()!=2)){
								System.out.println("surrender option");
								player.addBalance((float)player.getCurrentHand().getCurrentBet()/2);
								player.hands.clear();
								player.currentHand(null);
								dealer.currentHand(null);
								bet=0;
							}else System.out.println("You cannot use side rules at the moment");
							break;
						case "p":
							if(player.getCurrentHand().getCurrentBet()!=0&&(player.hands.peekFirst()!=null)&&(player.hands.size()!=2)){
								System.out.println("splitting option");
								
								LinkedList<Hand> copy = (LinkedList<Hand>) player.hands.clone();
								for(Hand ha:copy){
									//if cards have the same face value
									if(ha.getHand().get(0).getRank().getRankPoints()==ha.getHand().get(1).getRank().getRankPoints()){
										System.out.println("split");
										//if it is the first split-ok  //cant split an hand with an Ace from splitted hand
										if((player.hands.size()==1)||(ha.getHand().getFirst().getRank()!=Rank.ACE)){
											
											if(player.hands.get(player.hands.indexOf(ha)).getCurrentBet()<=player.getBalance()){
												player.hands.add(new Hand(player.hands.get(player.hands.indexOf(ha)).getHand().removeLast(), shoe.takeCard(), player.hands.get(player.hands.indexOf(ha)).getCurrentBet()));
												player.hands.get(player.hands.indexOf(ha)).addCard(shoe.takeCard());
												this.showCards();
											}
										}
									}
								}
							}else System.out.println("You cannot use side rules at the moment");
							break;
						case "2":
							if(player.getCurrentHand().getCurrentBet()!=0&&(player.hands.peekFirst()!=null)&&(player.hands.size()!=2)){
								System.out.println("double option");
							}else System.out.println("You cannot use side rules at the moment");
							break;
						case "ad":
							if(player.getCurrentHand().getCurrentBet()!=0){
								System.out.println("advice option");
							}else System.out.println("You havent placed your bet yet");
							break;
						case "st":
							if(player.getCurrentHand().getCurrentBet()!=0){
								System.out.println("statistics option");
							}else System.out.println("You havent placed your bet yet");
							break;
					}
				}
			
		}
	}

	public static void main(String[] args) {
		Game game=null;
		char mode='k';
		if(args.length<6){
			//System.out.println(args.length);
			System.out.println("Wrong command input. Please use one of the following:");
			System.out.println("Usage for interactive mode: -i min-bet max-bet balance shoe shuffle");
			System.out.println("Usage for debug mode: -d min-bet max-bet balance shoe-file cmd-file");
			System.out.println("Usage for simulation mode: -s min-bet max-bet balance shuffle s-number strategy");
			System.exit(1);//minumum arguments of every mode are six
		}
		//System.out.println(args.length);
		
		if(Integer.parseInt(args[1])<1){
			System.out.println("minBet should be at least 1");
			System.exit(3);//minbet>1
		}
		if(Integer.parseInt(args[2])<10*Integer.parseInt(args[1]) ||Integer.parseInt(args[2])>20*Integer.parseInt(args[1])){
			System.out.println("maxBet not in correct parameters");
			System.exit(4);//10*minbet<=maxbet<=20*minbet
		}
		if(Integer.parseInt(args[3])<50*Integer.parseInt(args[1])){
			System.out.println("Balance should be 50 times superior to minBet");
			System.exit(5);//balance>=50*minbet
		}
		
		if(args[0].equals("-i")){
			if(args.length != 6) System.out.println("Usage for interactive mode: -i min-bet max-bet balance shoe shuffle");
			int shoe=Integer.parseInt(args[4]);
			if(shoe<4||shoe>8){
				System.out.println("Not enough shoes");
				System.exit(6);
			}
			int shuffle=Integer.parseInt(args[5]);
			int balance=Integer.parseInt(args[3]);
			int maxBet=Integer.parseInt(args[2]);
			int minBet=Integer.parseInt(args[1]);
			if(shuffle<10||shuffle>100){
				System.out.println("Wrong shuffling percentage");
				System.exit(7);
			}
			//(int minBet,	int maxBet, int playersBalance,	int nDecks,	int shufflePercentage
			System.out.println("Entering interactive mode");
			game= new Game(minBet,	maxBet, balance,	shoe,	shuffle);
			mode='i';
		}
		//debug mode
		else if(args[0].equals("-d")){
			if(args.length != 6) System.out.println("Usage for debug mode: -d min-bet max-bet balance shoe-file cmd-file");
			//inicializar o game e fazer load do barulho de um file
			
			game= new Game(1,	1000, 50,	4,	58);
			mode='d';
		}
		//simulation mode
		else if(args[0].equals("-s")){
			if(args.length != 8) System.out.println("Usage for simulation mode: -s min-bet max-bet balance shuffle s-number strategy");
			//
			mode='s';
		}else{
			System.out.println("Wrong command input. Please use one of the following:");
			System.out.println("Usage for interactive mode: -i min-bet max-bet balance shoe shuffle");
			System.out.println("Usage for debug mode: -d min-bet max-bet balance shoe-file cmd-file");
			System.out.println("Usage for simulation mode: -s min-bet max-bet balance shuffle s-number strategy");
			System.exit(2);
		}
		game= new Game(1,	1000, 50,	4,	58);
		game.play(mode);
		//System.out.println("Error");
	}

}
