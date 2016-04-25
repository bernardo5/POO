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
	public void distributeCards(){
		Hand p=new Hand(shoe.takeCard(), shoe.takeCard());
		Hand d=new Hand(shoe.takeCard(), shoe.takeCard());
		this.player.addHand(p);
		this.dealer.addHand(d);
	}
	public void collectCards(){
		this.player.hands.clear();
		this.dealer.hands.clear();
	}
	public void printCards(){
		System.out.println("Player hand: "+ this.player.showHands());
		System.out.println("Dealer's hand: "+ this.dealer.showHands());	
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length<1)System.exit(1);
		

		if(Integer.parseInt(args[1])<1)System.exit(3);//minbet>1
		if(Integer.parseInt(args[2])<10*Integer.parseInt(args[1]) ||Integer.parseInt(args[2])>20*Integer.parseInt(args[1])) System.exit(4);//10*minbet<=maxbet<=20*minbet
		if(Integer.parseInt(args[3])<50*Integer.parseInt(args[1]))System.exit(5);//balance>=50*minbet
		
		//interactive mode
		if(args[0].equals("-i")){
			if(args.length != 6) System.out.println("Usage for interactive mode: -i min-bet max-bet balance shoe shuffle");
			int shoe=Integer.parseInt(args[4]);
			if(shoe<4||shoe>8)System.exit(6);
			int shuffle=Integer.parseInt(args[5]);
			if(shuffle<10||shuffle>100)System.exit(7);
			
			//Game game = new Game(minbet,maxbet,balance,shoe,shuffle);
			//inicializar o game e fazer shuffle do baralho
		}
		//debug mode
		else if(args[0].equals("-d")){
			if(args.length != 6) System.out.println("Usage for debug mode: -d min-bet max-bet balance shoe-file cmd-file");
			//inicializar o game e fazer load do barulho de um file
		}
		//simulation mode
		else if(args[0].equals("-s")){
			if(args.length != 8) System.out.println("Usage for simulation mode: -s min-bet max-bet balance shuffle s-number strategy");
			//
		}else{
			System.exit(2);
		}
		
		Game game = new Game(Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),shoe,shuffle);
		
		//game.interactiveMode();
		int bet = 0;
		String command;
		while(!command.equals("q")){
			
			if(args[0].equals("-i"))command = game.player.getplayerInput();
			//if(args[0].equals("-d"))command -  ir buscar ao ficheiro
			//if(args[0].equals("-s"))command -  pedir a estrategia
			
			if(command.startsWith("b")){//Bet
				/*
				if(game.player.hands.getFirst().getPoints()>=0){//Se forem dadas as cartas ja nao pode apostar
					String[] bets=command.split(" ");
					if(bet!=0){//existiu bet anterior
						if(Integer.parseInt(bets[1])>){
							this.placeBet(prevBet);
						}else{
							if(this.placeBet(Integer.parseInt(command[1]))) 
								prevBet=Integer.parseInt(command[1]);
							
						} 
					}else{
						prevBet=this.placeFirstBet(Integer.parseInt(command[1]));
						prevBetFlag=true;
					}
				}else System.out.println("b: Illegal command");*/
				bet=5;
			}else if(command.equals("$")){//Current balance
				System.out.println("Your current balance is: "+ game.player.getBalance());
			}else if(command.equals("d")){//Deal
				if(bet>=0){
					game.distributeCards();
					game.printCards();
	
					if(game.player.hands.getFirst().getPoints()==21){//blackjack
						if(game.dealer.hands.getFirst().getPoints()==21){//dealer also has blackjack
							game.player.setBalance(game.player.getBalance()+bet);
							System.out.println("nobody won this round!!!");
						}else{
							//player won-blackjack pays 3 to 2
							game.player.addBalance((float)(1.5*bet+bet));
							System.out.println("you won this round!!!");
						}
						System.out.println("end of turn");
						bet=0;
						game.collectCards();
					}
				}else System.out.println("d: illegal command");
			}else if(command.equals("h")){//Hit
				if(bet>=0 /*&& dar as cartas*/){
					System.out.println("player hits");
					//get a new card to the hand
					game.player.hands.getFirst().addCard(game.shoe.takeCard());
					game.player.showHands();
					if(game.player.hands.getFirst().getPoints()>21){
						//dealer won
						bet=0;
					}
				}else System.out.println("h: illegal command");
			}else if(command.equals("s")){//Stand
				System.out.println("player stands");
				//fazer a parte do dealer
					
				while(game.dealer.hands.getFirst().getPoints()<=17){
					System.out.println("dealer hits");
					game.dealer.hands.getFirst().addCard(game.shoe.takeCard());
					game.dealer.showHands();
				}
					
				for(Hand h:game.player.hands){//check if a players hand beats the dealer's hand
					if(h.getPoints()>game.dealer.hands.getFirst().getPoints()){
						game.player.addBalance(2*bet);
						System.out.println("player wins and his current balance is "+game.player.getBalance());
					}else if(h.getPoints()==game.dealer.hands.getFirst().getPoints()){
						game.player.addBalance(bet);
						//systemprint empate
					}else{
						System.out.println("dealer wins");
					}
				}
				game.collectCards();
				bet=0;
			}else if(command.equals("i")){
			}else if(command.equals("u")){
			}else if(command.equals("p")){
			}else if(command.equals("2")){
			}else if(command.equals("ad")){
			}else if(command.equals("st")){
			}
		}
	}
}
