package blackjack;

public class Game {

	public static void main(String[] args){
		String strategy=new String();
		Player player1 = null;
		Shoe shoe = null;
		int s_number=0;
		int nshoe=0;
		if(args.length<6)System.exit(1);
		if(Integer.parseInt(args[1])<1)System.exit(3);//minbet>1
		if(Integer.parseInt(args[2])<10*Integer.parseInt(args[1]) ||Integer.parseInt(args[2])>20*Integer.parseInt(args[1])) System.exit(4);//10*minbet<=maxbet<=20*minbet
		if(Integer.parseInt(args[3])<50*Integer.parseInt(args[1]))System.exit(5);//balance>=50*minbet

		//interactive mode
		if(args[0].equals("-i")){
			if(args.length != 6){
				System.out.println("Usage for interactive mode: -i min-bet max-bet balance shoe shuffle");
				System.exit(0);
			}
			strategy="doesnt matter";
			nshoe=Integer.parseInt(args[4]);
			if(nshoe<4||nshoe>8)System.exit(6);
			int shuffle=Integer.parseInt(args[5]);
			if(shuffle<10||shuffle>100)System.exit(7);
			//populate shoe
			shoe = new Shoe(Integer.parseInt(args[4]),Integer.parseInt(args[5]));
			shoe.populateShoe();
			shoe.shuffleShoe();
			player1 = new Player(Integer.parseInt(args[3]), shoe.nCards()/52);
		}
		
		//debug mode
		else if(args[0].equals("-d")){
			if(args.length != 6){
				System.out.println("Usage for debug mode: -d min-bet max-bet balance shoe-file cmd-file");
				System.exit(0);
			}
			strategy="doesnt matter";
			shoe = new Shoe();
			shoe.populateShoeFromFile(args[4]);
			player1 = new Player(Integer.parseInt(args[3]),args[5], shoe.nCards()/52);
		}
		
		//simulation mode
		else if(args[0].equals("-s")){
			if(args.length != 8){
				System.out.println("Usage for simulation mode: -s min-bet max-bet balance shuffle s-number strategy");
				System.exit(0);
			}
			strategy=args[6];
			shoe = new Shoe(Integer.parseInt(args[3]),Integer.parseInt(args[4]));
			player1 = new Player(Integer.parseInt(args[3]), shoe.nCards()/52);						
		}else{
			strategy="doesnt matter";
			/*shoe= new Shoe();
			player1 = new Player(Integer.parseInt(args[3]));*/
			System.out.println("Bad input parameters");
			System.exit(2);
		}
		
		//Instanciate generic objects
		Table table = new Table(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		Dealer dealer = new Dealer();
		
		//-----------------------Begining of the game------------------------------//
		int bet = table.getMinBet();
		String command = " ";
		int bet_deal = 0;
		
		while(true){
			if((s_number==Integer.parseInt(args[5]))&&(args[0].equals("-s"))){//end simulation mode
				System.out.println("Your final balance is: "+ player1.getBalance());
				System.exit(0);
			}
			if(shoe.getShufflePercentage()!=100)
				if(shoe.calculateUsagePercentage()>=shoe.getShufflePercentage()){
					player1.acefive.resetCount();
					player1.hilo.restartRunningCount();
					shoe.shuffleShoe();//Shuffle
					s_number++;
				}
					
			
			//----------------------Before Bet and Deal----------------------------
			while(bet_deal<2){
				
				command = player1.getplayerInput(args[0]);
				System.out.println(command);
				
				//if(args[0].equals("-s"))command -  pedir a estrategia
				
				if(command.startsWith("b")){//Bet
					if(player1.getBalance()>=table.getMinBet() && bet_deal == 0){//Se forem dadas as cartas ja nao pode apostar e so pode apostar se tiver maior balance que a aposta minima
						String[] bets = command.split(" ");
						if(bets.length==1){
							player1.subtractBalance(bet);
							bet_deal++;
						}else if(bets.length==2){
							//System.out.println(bets[1]+" bets[1]");
							if(Integer.parseInt(bets[1])<=player1.getBalance()){
								bet=Integer.parseInt(bets[1]);
								player1.subtractBalance(bet);
								bet_deal++;
							}else System.out.println("b: Illegal command");//< balance
						}else System.out.println("b: Illegal command");//more than 2 arguments
					}else System.out.println("b: Illegal command");//Can´t use bet
				}else if(command.equals("d")){//Deal
					if(bet_deal==1){
						//distributeCards();
						Card a=shoe.takeCard();
						Card b=shoe.takeCard();
						player1.acefive.cardRevealed(a);
						player1.acefive.cardRevealed(b);
						player1.hilo.cardRevealed(a);
						player1.hilo.cardRevealed(b);
						Hand p=new Hand(a, b,bet);
						a=shoe.takeCard();
						player1.acefive.cardRevealed(a);
						player1.hilo.cardRevealed(a);
						Hand d=new Hand(a, shoe.takeCard(),0);
						player1.hands.add(p);
						player1.setCurrentHand(p);
						dealer.setCurrentHand(d);
						//printCards();
						System.out.println("Player's hand: "+ player1.showHands());
						System.out.println("Dealer's hand: "+ dealer.showHands());	

						if(player1.hands.getFirst().getPoints()==21){//blackjack
							if(dealer.getCurrentHand().getPoints()==21){//dealer also has blackjack
								player1.setBalance(player1.getBalance()+bet);
								System.out.println("draw");
							}else{
								//player won-blackjack pays 3 to 2
								player1.addBalance((float)(1.5*bet+bet));
								player1.blackjack();
							}
							System.out.println("end of turn");
							//collect the cards
							player1.hands.clear();
							player1.setCurrentHand(null);
							dealer.setCurrentHand(null);
						}
						bet_deal++;
					}else System.out.println("d: illegal command");
					
				}else if(command.equals("$")){//Current balance
					System.out.println("Your current balance is: "+ player1.getBalance());
					
				}else if(command.equals("q")){
					System.exit(0);
				}else if(command.equals("ad")){
						System.out.println("According to Ace-Five strategy: " + player1.acefive.advice());
				}else if(command.equals("st")){
					if(dealer.getblackjacks()!=0)
						System.out.println("BJ P/D" + player1.getblackjacks()/dealer.getblackjacks());
					else System.out.println("Dealer has no Blackjacks yet");
					if(player1.roundsplayed()!=0){
						System.out.println("Win " + (float)player1.getwins()/player1.roundsplayed());
						System.out.println("Lose " + (float)player1.getloses()/player1.roundsplayed());
						System.out.println("Push " + (float)player1.getdraws()/player1.roundsplayed());
					}else{
						System.out.println("Win " + player1.getwins());
						System.out.println("Lose " + player1.getloses());
						System.out.println("Push " + player1.getdraws());
					}
					
					System.out.println("Balance" + player1.getBalance() + "("+ 100*(float)(player1.getBalance()/Integer.parseInt(args[3])) +"%)" );
				}else System.out.println("Illegal command");
			}
			bet_deal=0;
			//---------------------After Bet and Deal-------------------------------
			while(player1.getCurrentHand()!=null){
				
				
				//Check if player doubled down
					
				if(player1.getCurrentHand().getBet()==2*bet) command = "s";
				else command = player1.getplayerInput(args[0]);
					
				//if(args[0].equals("-d"))command -  ir buscar ao ficheiro
				//if(args[0].equals("-s"))command -  pedir a estrategia
				  
				if(command.equals("$")){//Current balance
					System.out.println("Your current balance is: "+ player1.getBalance());
					
				}else if(command.equals("h")){//Hit
					//Se fez double down so pode fazer hit uma vez
					System.out.println("player hits");
					Card a=shoe.takeCard();
					player1.acefive.cardRevealed(a);
					player1.hilo.cardRevealed(a);
					if(player1.hit(a)){
						//System.out.println("dealer wins");
						dealer.win();
						player1.lost();
						System.out.println(player1.showHands());
						if(player1.getNextHand()==null){
							player1.hands.remove(player1.getCurrentHand());
							player1.setCurrentHand(null);
							break;
						}else{
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
				}else if(command.equals("u")){
					if((player1.hands.peekFirst()!=null)&&(player1.hands.size()==1)){
						System.out.println("surrender option");
						player1.addBalance((float)player1.getCurrentHand().getBet()/2);
						player1.hands.clear();
						player1.setCurrentHand(null);
						dealer.setCurrentHand(null);
					}else System.out.println("You cannot use side rules at the moment");
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
					System.out.println("According to Basic strategy: " + player1.basic.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
					System.out.println("According to Ace-Five strategy: " + player1.acefive.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
					System.out.println("According to Hi-Low strategy: " + player1.hilo.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
				}else if(command.equals("st")){
					if(dealer.getblackjacks()!=0)
						System.out.println("BJ P/D" + player1.getblackjacks()/dealer.getblackjacks());
					else System.out.println("Dealer has no Blackjacks yet");
					if(player1.roundsplayed()!=0){
						System.out.println("Win " + (float)player1.getwins()/player1.roundsplayed());
						System.out.println("Lose " + (float)player1.getloses()/player1.roundsplayed());
						System.out.println("Push " + (float)player1.getdraws()/player1.roundsplayed());
					}else{
						System.out.println("Win " + player1.getwins());
						System.out.println("Lose " + player1.getloses());
						System.out.println("Push " + player1.getdraws());
					}
					System.out.println("Balance" + player1.getBalance() + "("+ 100*(float)(player1.getBalance()/Integer.parseInt(args[3])) +"%)" );
				}else if(command.equals("q")){
					System.exit(0);
				}else System.out.println("Illegal command");			
			}
			//-----------------Dealer-------------------------------
			if((dealer.getCurrentHand()!=null)&&(player1.hands.size()>0)){
				if(dealer.getCurrentHand().getPoints()==21){//Sem fazer hit ver se tem blackjack
					if(player1.getInsurance()){
						player1.addBalance(2*bet);
						player1.changeInsurance(false);
					}
				}
				while(dealer.getCurrentHand().getPoints()<17){
					System.out.println("dealer hits");
					Card card=shoe.takeCard();
					player1.acefive.cardRevealed(card);
					player1.hilo.cardRevealed(card);
					dealer.hit(card);
					System.out.println(dealer.showHands());
				}
				for(Hand h:player1.hands){//check if a players hand beats the dealer's hand
					if((h.getPoints()>dealer.getCurrentHand().getPoints())||(dealer.getCurrentHand().getPoints()>21)){
						player1.addBalance(2*bet);
						player1.win();
						dealer.lost();
						System.out.println("player wins and his current balance is "+player1.getBalance());
					}else if(h.getPoints()==dealer.getCurrentHand().getPoints()){
						player1.addBalance(bet);
						player1.draw();
						dealer.draw();
						System.out.println("draw");
					}else{
						//System.out.println("dealer wins");
						dealer.win();
						player1.lost();
					}
				}
				//see what card the dealer had
				Card hidden=dealer.returnHiddenCard();
				player1.acefive.cardRevealed(hidden);
				player1.hilo.cardRevealed(hidden);
				//collectCards();
				player1.hands.clear();
				player1.setCurrentHand(null);
				dealer.setCurrentHand(null);
			}
		}
	}
}
