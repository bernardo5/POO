package blackjackG21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Game {
	
	private int minBet;
	private int maxBet;
	private int nDecks;
	private int shufflePercentage; //% of shoe played begore shuffling 10-100
	private Dealer dealer;
	private RegularPlayer player;
	private Shoe shoe;
	
	public Game(int minBet,	int maxBet, int playersBalance,	int nDecks,	int shufflePercentage, String file){
		this.minBet=minBet;
		this.maxBet=maxBet;
		this.nDecks=nDecks;
		this.shufflePercentage=shufflePercentage;
		shoe=new Shoe(nDecks, shufflePercentage);
		shoe.populateShoeFromFile(file, nDecks);
		player=new RegularPlayer(playersBalance);
		dealer=new Dealer();
	}
	
	public void distributeHands(){
		Hand p=new Hand(shoe.takeCard(), shoe.takeCard());
		Hand d=new Hand(shoe.takeCard(), shoe.takeCard());
		player.getHand(p);
		dealer.getHand(d);
	}
	
	public void showCards(){
		System.out.println("Player hand: "+ player.showHands());
		System.out.println("Dealer's hand: "+ dealer.showHands());
		
	}
	
	public int placeFirstBet(int bet){//the player has to do at least a bet
		System.out.println("bet command");
		if((bet==-1)||(bet<minBet)){
			player.setBalance(player.getBalance()-minBet);
			player.setCurrentBet(minBet);
			return minBet;
		}else{
			player.setBalance(player.getBalance()-bet);
			player.setCurrentBet(bet);
			return bet;
		}
	}
	
	public boolean placeBet(int bet){//the player has to do at least a bet
		System.out.println("bet command");
		if(bet>player.getBalance()){
			System.out.println("You cannot afford this bet. Your balance is:"+player.getBalance());
			return false;
		}else{
			player.setBalance(player.getBalance()-bet);
			player.setCurrentBet(player.getCurrentBet()+bet);
			return true;
		}
	}
	
	public String[] getCommand(){
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
	}
	
	public void interactiveMode(){
		boolean tight=false;
		boolean playing=false;
		int bust=0;
		String[] command;
		boolean prevBetFlag=false;
		int prevBet = 0;
		boolean dealFlag=false;
		while((player.getBalance()>=minBet)||(playing)){
			command=this.getCommand();
			switch(command[0]){
				case "b": 
					if(!dealFlag){
						playing=true;
						if(prevBetFlag){
							if(Integer.parseInt(command[1])==-1){
								this.placeBet(prevBet);
							}else{
								if(this.placeBet(Integer.parseInt(command[1]))) 
									prevBet=Integer.parseInt(command[1]);
								
							} 
						}else{
							prevBet=this.placeFirstBet(Integer.parseInt(command[1]));
							prevBetFlag=true;
						}
					}else System.out.println("b: Illegal command");
					break;
				case "$":
					System.out.println("Your current balance is: "+player.getBalance());
					break;
				case "d":
					if(prevBetFlag){
						dealFlag=true;
						this.distributeHands();
						this.showCards();
						if(player.hands.getFirst().getTotal()==21){//blackjack
							if(dealer.hands.getFirst().getTotal()==21){//dealer also has blackjack
								player.setBalance(player.getBalance()+((player.getCurrentBet())));
								System.out.println("nobody won this round!!!");
							}else{
								//player won-blackjack pays 3 to 2
								player.setBalance(player.getBalance()+((3*player.getCurrentBet())/2)+player.getCurrentBet());
								System.out.println("You won this round!!!");
							}
							System.out.println("end of turn");
							player.setCurrentBet(0);
							player.hands.clear();
							dealer.hands.clear();
							dealFlag=false;
							prevBetFlag=false;
						}
					}else System.out.println("You havent placed your bet yet");
					break;
				case "h":
					if(prevBetFlag){
						System.out.println("hit option");
						//get a new card to the hand
						bust=player.hit(shoe.takeCard(), 0);
						this.showCards();
						if(bust==1){
							//dealer won
							player.setCurrentBet(0);
							dealFlag=false;
							prevBetFlag=false;
							bust=0;
							playing=false;
						}
					}else System.out.println("You havent placed your bet yet");
						break;
				case "s":
					playing=false;
					if(prevBetFlag){
						System.out.println("stand option");
						//fazer a parte do dealer
						
						while(dealer.hands.getFirst().getTotal()<=17){
							dealer.hit(shoe.takeCard(), 0);
						}
						this.showCards();
						
						for(Hand h:player.hands){//check if a players hand beats the dealer's hand
							if(h.getTotal()>=dealer.hands.getFirst().getTotal()){
								if(h.getTotal()==dealer.hands.getFirst().getTotal()){
									//check who has less cards
									if(h.getHand().size()<dealer.hands.getFirst().getHand().size()){
										//player has less cards
										player.setBalance(player.getBalance()+(2*player.getCurrentBet()));
										System.out.println("You won this round!!!");
									}else if(h.getHand().size()==dealer.hands.getFirst().getHand().size()){
										//same card number
										tight=true;
									}
								}else{
									//player won
									player.setBalance(player.getBalance()+(2*player.getCurrentBet()));
									System.out.println("You won this round!!!");
								}
								
								break;
							}
						}
						
						if(tight){
							player.setBalance(player.getBalance()+player.getCurrentBet());
							System.out.println("Nobody won this round!!!");
							tight=false;
						}
						
						System.out.println("end of turn");
						/////////////////////////////////
						//player.setBalance(balance);
						player.setCurrentBet(0);
						player.hands.clear();
						dealer.hands.clear();
						dealFlag=false;
						prevBetFlag=false;
					}else System.out.println("You havent placed your bet yet");
					break;
				case "i":
					if(prevBetFlag){
						System.out.println("insurance option");
					}else System.out.println("You havent placed your bet yet");
					break;
				case "u":
					if(prevBetFlag){
						System.out.println("surrender option");
					}else System.out.println("You havent placed your bet yet");
					break;
				case "p":
					if(prevBetFlag){
						System.out.println("splitting option");
					}else System.out.println("You havent placed your bet yet");
					break;
				case "2":
					if(prevBetFlag){
						System.out.println("double option");
					}else System.out.println("You havent placed your bet yet");
					break;
				case "ad":
					if(prevBetFlag){
						System.out.println("advice option");
					}else System.out.println("You havent placed your bet yet");
					break;
				case "st":
					if(prevBetFlag){
						System.out.println("statistics option");
					}else System.out.println("You havent placed your bet yet");
					break;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game= new Game(1,	1000, 50,	4,	58, "src/shoe-file.txt");
		game.interactiveMode();
	}

}
