package blackjack;

import java.io.*;
import java.util.*;

public class Game {

	public static void main(String[] args) throws IOException {
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
		}
		//debug mode
		else if(args[0].equals("-d")){
			if(args.length != 6) System.out.println("Usage for debug mode: -d min-bet max-bet balance shoe-file cmd-file");
			//Carregar ficheiros
		}
		//simulation mode
		else if(args[0].equals("-s")){
			if(args.length != 8) System.out.println("Usage for simulation mode: -s min-bet max-bet balance shuffle s-number strategy");
			//Carregar estrategia
		}else{
			System.exit(2);
		}
		
		//Instanciate objects
		Table table = new Table(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		Player player1 = new Player(Integer.parseInt(args[3]));
		Dealer dealer = new Dealer();
		Shoe shoe = new Shoe(Integer.parseInt(args[4]),Integer.parseInt(args[5]));
		//Populateshoe depende dos argumentos
		shoe.populateShoe();
		shoe.shuffleShoe();
		
		//-----------------------Begining of the game------------------------------//
		int bet = table.getMinBet();
		String command = " ";
		int b_d = 0;
		
		while(true){
			
			if(shoe.calculateUsagePercentage()>=shoe.getShufflePercentage())shoe.shuffleShoe();//Shuffle
			
			//----------------------Before Bet and Deal----------------------------
			while(b_d<2){
				if(args[0].equals("-i")){//interactive mode
					command = player1.getplayerInput();
				}
				
				//if(args[0].equals("-d"))command -  ir buscar ao ficheiro
				//if(args[0].equals("-s"))command -  pedir a estrategia
				
				if(command.startsWith("b")){//Bet
					if(player1.getBalance()>=table.getMinBet() && b_d == 0){//Se forem dadas as cartas ja nao pode apostar e so pode apostar se tiver maior balance que a aposta minima
						String[] bets = command.split(" ");
						if(bets.length==1){
							player1.subtractBalance(bet);
							b_d++;
						}else if(bets.length==2){
							if(Integer.parseInt(bets[1])>=player1.getBalance()){
								bet=Integer.parseInt(bets[1]);
								player1.subtractBalance(bet);
								b_d++;
							}else System.out.println("b: Illegal command");//< balance
						}else System.out.println("b: Illegal command");//more than 2 arguments
					}else System.out.println("b: Illegal command");//Can´t use bet
				}else if(command.equals("d")){//Deal
					if(b_d==1){
						//distributeCards();
						Hand p=new Hand(shoe.takeCard(), shoe.takeCard(),bet);
						Hand d=new Hand(shoe.takeCard(), shoe.takeCard(),0);
						player1.hands.add(p);
						player1.setCurrentHand(p);
						dealer.setCurrentHand(d);
						//printCards();
						System.out.println("Player hand: "+ player1.showHands());
						System.out.println("Dealer's hand: "+ dealer.showHands());	
						
						/*if(dealer.getCurrentHand().getCards().getFirst().getRank().equals(Rank.ACE)){
							//pergunta se quer fazer insurance
							if(player1.getBalance()>= bet){
								while(!command.equals("y") || !command.equals("n")){
									System.out.println("Want to make insurance?(y/n)");
									command = player1.getplayerInput();
									if(command.equals("y")){
										player1.changeInsurance(true);
										player1.subtractBalance(bet);
									}
									if(command.equals("n"))player1.changeInsurance(false);
								}
							}else System.out.println("no money for insurance");
						}*/

						if(player1.hands.getFirst().getPoints()==21){//blackjack
							if(dealer.getCurrentHand().getPoints()==21){//dealer also has blackjack
								player1.setBalance(player1.getBalance()+bet);
								System.out.println("draw");
							}else{
								//player won-blackjack pays 3 to 2
								player1.addBalance((float)(1.5*bet+bet));
								System.out.println("player wins");
							}
							System.out.println("end of turn");
							//collectCards();
							player1.hands.clear();
							player1.setCurrentHand(null);
							dealer.setCurrentHand(null);
						}
						b_d++;
					}else System.out.println("d: illegal command");
					
				}else if(command.equals("$")){//Current balance
					System.out.println("Your current balance is: "+ player1.getBalance());
					
				}else if(command.equals("q")){
					System.exit(0);
				}else System.out.println("Illegal command");
			}
			b_d=0;
			//---------------------After Bet and Deal-------------------------------
			while(player1.getCurrentHand()!=null){
				
				
					
					if(args[0].equals("-i")){//interactive mode
						if(player1.getCurrentHand().getBet()==2*bet) command = "s";//fez double down
						else command = player1.getplayerInput();
					}
					//if(args[0].equals("-d"))command -  ir buscar ao ficheiro
					//if(args[0].equals("-s"))command -  pedir a estrategia
				  
					if(command.equals("$")){//Current balance
						System.out.println("Your current balance is: "+ player1.getBalance());
					
					}else if(command.equals("h")){//Hit
						//Se fez double down so pode fazer hit uma vez
						System.out.println("player hits");
						if(player1.hit(shoe.takeCard())){
							System.out.println("dealer wins");
							System.out.println(player1.showHands());
							if(player1.getNextHand()==null){
								player1.hands.remove(player1.getCurrentHand());
								break;
							}else {
								Hand remove=player1.getCurrentHand();
								player1.setCurrentHand(player1.getNextHand());
								player1.hands.remove(remove);
							}
							
							//player1.hands.remove(hand);
						}else System.out.println(player1.showHands());
					}else if(command.equals("s")){//Stand
						System.out.println("player stands");
						if(player1.getNextHand()==null)break;
						else player1.setCurrentHand(player1.getNextHand());
						//se for a ultima mão fazer a parte do dealer
						/*if(hand == player1.hands.getLast()){
							if(dealer.getCurrentHand().getPoints()==21){//Sem fazer hit ver se tem blackjack
								if(player1.getInsurance()){
									player1.addBalance(2*bet);
									player1.changeInsurance(false);
								}
							}
							while(dealer.getCurrentHand().getPoints()<=17){
								System.out.println("dealer hits");
								dealer.getCurrentHand().addCard(shoe.takeCard());
								dealer.showHands();
							}
			
							for(Hand h:player1.hands){//check if a players hand beats the dealer's hand
								if(h.getPoints()>dealer.getCurrentHand().getPoints()){
									player1.addBalance(2*bet);
									System.out.println("player wins and his current balance is "+player1.getBalance());
								}else if(h.getPoints()==dealer.getCurrentHand().getPoints()){
									player1.addBalance(bet);
									System.out.println("draw");
								}else{
									System.out.println("dealer wins");
								}
							}
							//collectCards();
							player1.hands.clear();
							player1.setCurrentHand(null);
							dealer.setCurrentHand(null);
						}*/
						
						
					}else if(command.equals("u")){
						
					}else if(command.equals("p")){//allow resplitting until the player has as many as four hands and doubling a hand after splitting
						if(player1.getBalance()>=table.getMinBet() && player1.getCurrentHand().getSizeofCards()==2 && player1.hands.size()<4){
							//if cards have the same face value
							if(player1.getCurrentHand().getCards().get(0).getRank().getRankValue() == player1.getCurrentHand().getCards().get(1).getRank().getRankValue()){
								System.out.println("player splits");
								Card card1 = player1.getCurrentHand().getCards().get(0);
								Card card2 = player1.getCurrentHand().getCards().get(1);
								Hand hand1=new Hand(card1,shoe.takeCard(),bet);
								player1.hands.add(hand1);
								player1.hands.add(new Hand(card2,shoe.takeCard(),bet));
								player1.hands.remove(player1.getCurrentHand());
								player1.setCurrentHand(hand1);
								player1.subtractBalance(bet);
								System.out.println(player1.showHands());
							}
						}else System.out.println("u: illegal command");
					}else if(command.equals("2")){//only on an opening hand worth 9,10,11 and always doubles the bet;take only one more card from the dealer
						if(player1.getBalance()>=bet){
							player1.subtractBalance(bet);
							player1.getCurrentHand().setBet(2*bet);
							player1.getCurrentHand().addCard(shoe.takeCard());
						}else System.out.println("u: illegal command");
					}else if(command.equals("ad")){
					}else if(command.equals("st")){
					}else if(command.equals("q")){
						System.exit(0);
					}else System.out.println("Illegal command");	
				
			}
			//-----------------dealer part-------------------------------
			if((dealer.getCurrentHand()!=null)&&(player1.getCurrentHand()!=null)){
				if(dealer.getCurrentHand().getPoints()==21){//Sem fazer hit ver se tem blackjack
					if(player1.getInsurance()){
						player1.addBalance(2*bet);
						player1.changeInsurance(false);
					}
				}
				while(dealer.getCurrentHand().getPoints()<=17){
					System.out.println("dealer hits");
					dealer.hit(shoe.takeCard());
					System.out.println(dealer.showHands());
				}
				for(Hand h:player1.hands){//check if a players hand beats the dealer's hand
					if((h.getPoints()>dealer.getCurrentHand().getPoints())||(dealer.getCurrentHand().getPoints()<=21)){
						player1.addBalance(2*bet);
						System.out.println("player wins and his current balance is "+player1.getBalance());
					}else if(h.getPoints()==dealer.getCurrentHand().getPoints()){
						player1.addBalance(bet);
						System.out.println("draw");
					}else{
						System.out.println("dealer wins");
					}
				}
				//collectCards();
				player1.hands.clear();
				player1.setCurrentHand(null);
				dealer.setCurrentHand(null);
			}
		}
	}
}
