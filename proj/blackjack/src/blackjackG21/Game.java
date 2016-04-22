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
	
	public void waitBet(){//the player has to do at least a bet
		String s=new String();
		Scanner scanner;		
		String command=new String();
		int bet=-1;
		System.out.println("Please commit your bet: ");
		while(!(command.equals("b"))){
			try{
		        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		        s = bufferRead.readLine();
		        scanner = new Scanner (s);
		        command =	scanner.next ();
		        if(scanner.hasNextInt()) bet=scanner.nextInt ();
		        System.out.println("Input command was "+command+" with bet amount of "+bet);
		        if(player.getBalance()<bet){
		        	command="not valid";
		        	System.out.println("Please commit other amount to bet. You cant afford that bet amount");
		        }
			}
		    catch(IOException e)
		    {
		        e.printStackTrace();
		    }
		}
		if((bet==-1)||(bet<minBet)){
			player.setBalance(player.getBalance()-minBet);
			player.setCurrentBet(minBet);
		}else{
			player.setBalance(player.getBalance()-bet);
			player.setCurrentBet(bet);
		}
	}
	
	public void interactiveMode(){
		while(player.getBalance()>=minBet){
			//give player and dealer the hand
			this.waitBet();
			this.distributeHands();
			this.showCards();
			break;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game= new Game(1,	1000, 50,	4,	58, "src/shoe-file.txt");
		game.interactiveMode();
	}

}
