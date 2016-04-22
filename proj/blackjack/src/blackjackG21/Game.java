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
		shoe=new Shoe(nDecks);
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
	
	public void placeBet(int bet){//the player has to do at least a bet
		System.out.println("bet command");
		if((bet==-1)||(bet<minBet)){
			player.setBalance(player.getBalance()-minBet);
			player.setCurrentBet(minBet);
		}else{
			player.setBalance(player.getBalance()-bet);
			player.setCurrentBet(bet);
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
		String[] command;
		while(player.getBalance()>=minBet){
			command=this.getCommand();
			switch(command[0]){
				case "b": 
					this.placeBet(Integer.parseInt(command[1]));
					break;
				case "$":
					System.out.println("Your current balance is: "+player.getBalance());
					break;
				case "d":
					this.distributeHands();
					this.showCards();
					break;
				case "h":
					System.out.println("hit option");
					break;
				case "s":
					System.out.println("stand option");
					break;
				case "i":
					System.out.println("insurance option");
					break;
				case "u":
					System.out.println("surrender option");
					break;
				case "p":
					System.out.println("splitting option");
					break;
				case "2":
					System.out.println("double option");
					break;
				case "ad":
					System.out.println("advice option");
					break;
				case "st":
					System.out.println("statistics option");
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
