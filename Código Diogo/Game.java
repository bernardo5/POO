package blackjackG21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Game {

	private Dealer dealer;
	private Player player;
	private Shoe shoe;
	private Table table;
		
	//Constructor for interactive mode
	public Game(int minBet,	int maxBet, int playersBalance,	int shoe, int shufflePercentage){
		this.table.setMinBet(minBet);
		this.table.setMaxBet(maxBet);
		this.shoe = new Shoe(shoe,shufflePercentage);
		this.shoe.populateShoe(shoe);
		this.player = new Player(playersBalance);
		this.dealer = new Dealer();
	}
	
	//Methods
	public void distributeHands(){
		Hand p=new Hand(shoe.takeCard(), shoe.takeCard());
		Hand d=new Hand(shoe.takeCard(), shoe.takeCard());
		player.addHand(p);
		dealer.addHand(d);
	}
	
	public void printCards(){
		System.out.println("Player hand: "+ player.showHands());
		System.out.println("Dealer's hand: "+ dealer.showHands());	
	}
	
	//Devia estar no player pois é o seu comando
	public String[] getplayerInput(){
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
	/*
	//isto chama-se de main
	public void interactiveMode(){
		String[] command;
		boolean prevBetFlag=false;
		int prevBet = 0;
		boolean dealFlag=false;
		while(player.getBalance()>=minBet){
			command=this.getCommand();
			switch(command[0]){
				case "b": 
					if(!dealFlag){
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
					}else System.out.println("You havent placed your bet yet");
					break;
				case "h":
					if(prevBetFlag){
						System.out.println("hit option");
						//get a new card to the hand
						player.hit(shoe.takeCard(), 0);
						this.showCards();
					}else System.out.println("You havent placed your bet yet");
						break;
				case "s":
					if(prevBetFlag){
						System.out.println("stand option");
						//fazer a parte do dealer
						
						while(dealer.hands.getFirst().getTotal()<=17){
							dealer.hit(shoe.takeCard(), 0);
						}
						this.showCards();
						System.out.println("end of turn");
						/////////////////////////////////
						//player.setBalance(balance);
						player.setCurrentBet(0);
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
*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game= new Game(1,	1000, 50,	4,	58);
		//game.interactiveMode();
	}

}
