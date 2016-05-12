package blackjack;

public class Game {
	
	Player player1;
	Shoe shoe;
	Table table;
	Dealer dealer;
	Acefive acefive;
	Basic basic;
	HiLo hilo;
	
	int s_number;
	int nshoe;
	
	Game(int minBet, int maxBet){
		s_number=0;
		nshoe=0;
		table = new Table(minBet, maxBet);
		acefive=new Acefive();
		basic=new Basic();
		dealer = new Dealer();
	}
	
	Game(int nshoe, int shuffle, int numberDecks, int balance, int minBet, int maxBet){
		this(minBet, maxBet);
		this.nshoe=nshoe;
		shoe = new Shoe(numberDecks, shuffle);
		shoe.populateShoe();
		shoe.shuffleShoe();
		player1 = new Player(balance, shoe.nCards()/52);
		hilo=new HiLo(shoe.nCards()/52);
	}
	
	Game(String fileShoe, int balance, String commandFile, int minBet, int maxBet){
		this(minBet, maxBet);
		shoe = new Shoe();
		shoe.populateShoeFromFile(fileShoe);
		player1 = new Player(balance,commandFile, shoe.nCards()/52);
		hilo=new HiLo(shoe.nCards()/52);
	}
	
	Game(int balance, String strategy, int nShoe, int shufflePercentage, int minBet, int maxBet){
		this(minBet, maxBet);
		shoe = new Shoe(nShoe,shufflePercentage);
		shoe.populateShoe();
		shoe.shuffleShoe();
		player1 = new Player(balance, shoe.nCards()/52, strategy);
		hilo=new HiLo(shoe.nCards()/52);
	}
	
	public String strategyCommand(int bet_deal, 
			boolean bet_flag, int maxBet, int minBet, int lastBet, 
			Acefive acefive, HiLo hilo, Basic basic, Player player, Card card){
		//strategy mode
			if(bet_flag){//want to know the amount of money to bet
				System.out.println("bet/deal part: "+bet_deal);
				if(bet_deal==0){
					System.out.println("bet part");
					if(player.getCurrentStrategy().equals("BS")||player.getCurrentStrategy().equals("HL")){//follow normal bet strategy
						int minus,plus;
						System.out.println("ok");
						if(lastBet+minBet<=maxBet)plus=lastBet+minBet; else plus=maxBet;
						if((lastBet-minBet)>=minBet)minus=lastBet-minBet; else minus=minBet;
						if(player.getLast().equals("first"))return "b "+(int)minBet;
						else if(player.getLast().equals("W"))return "b "+(int)(plus);
						else if(player.getLast().equals("L"))return "b "+(int)(minus);
						else /*draw*/return "b "+(int)lastBet;
					}else{//follow ace-five bet strategy
						if(acefive.advice().equals("min_bet"))return "b "+(int)minBet;
						else {
							/*if(acefive.advice().equals("double last bet"))*/
							if(2*lastBet<maxBet){
								if(player.getBalance()<(2*lastBet))return "b "+(int)player.getBalance();
								else return "b "+(int)(2*lastBet);
							}else 
								if(player.getBalance()<maxBet)return "b "+(int)player.getBalance();
										else return "b "+(int)maxBet;
						}
						
						//else return acefive.advice();
					}
				}else return "d";
			}else{//want to know what action to perform
				if(player.getCurrentStrategy().equals("BS")||player.getCurrentStrategy().equals("BS-AF")){
					if(basic.advice(player.current, card).length()==2){
						String first=Character.toString(basic.advice(player.current, card).charAt(0));
						String second=Character.toString(basic.advice(player.current, card).charAt(1));
						if(player.current.getCards().size()==2){
							if(first.equals("2")&&player.getBalance()<lastBet) return second;
							return first;
						}
						else return second;
					}else return basic.advice(player.current, card);
				}else /*if(player.getCurrentStrategy().equals("HL")ou HL-AF)*/{
					if((hilo.advice(player.current, card).contains("Using basic:"))||((hilo.advice(player.current, card).equals("u"))&&(player.getCurrentHand().getCards().size()!=2))){
						if(basic.advice(player.current, card).length()==2){
							String first=Character.toString(basic.advice(player.current, card).charAt(0));
							String second=Character.toString(basic.advice(player.current, card).charAt(1));
							if(player.current.getCards().size()==2){
								if(first.equals("2")&&player.getBalance()<lastBet) return second;
								return first;
							}
							else return second;
						}else return basic.advice(player.current, card);
					}
					else{
						if(hilo.advice(player.current, card).length()==2){//two options
							String first=Character.toString(hilo.advice(player.current, card).charAt(0));
							String second=Character.toString(hilo.advice(player.current, card).charAt(1));
							if(player.current.getCards().size()==2){
								if(first.equals("2")&&player.getBalance()<lastBet) return second;
								return first;
							}
							else return second;
						}else return hilo.advice(player.current, card);
					}
				}
			}
	}
	
	boolean interactiveMode(String mode){
		if(mode.equals("-i"))return true;
		else return false;
	} 
	boolean debugMode(String mode){
		if(mode.equals("-d"))return true;
		else return false;
	}
	boolean simulationMode(String mode){
		if(mode.equals("-s"))return true;
		else return false;
	}
	
	boolean checkEndSimulationMode(int nShuffles){
		if(s_number==nShuffles)return true;
		else return false;
	}
	
	public void statistics(int initialbalance){
		if(dealer.getblackjacks()!=0)
			System.out.println("BJ P/D" + player1.getblackjacks()+"/"+dealer.getblackjacks());
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
		
		System.out.println("Balance " + player1.getBalance() + " ("+ ((100*(float)(player1.getBalance()/initialbalance))-100) +"%)" );
	}

	public static void main(String[] args){
		if(args.length<6)System.exit(1);
		if(Integer.parseInt(args[1])<1)System.exit(3);//minbet>1
		if(Integer.parseInt(args[2])<10*Integer.parseInt(args[1]) ||Integer.parseInt(args[2])>20*Integer.parseInt(args[1])) System.exit(4);//10*minbet<=maxbet<=20*minbet
		if(Integer.parseInt(args[3])<50*Integer.parseInt(args[1]))System.exit(5);//balance>=50*minbet
		Game game;
		//interactive mode
		if(args[0].equals("-i")){
			if(args.length != 6){
				System.out.println("Usage for interactive mode: -i min-bet max-bet balance shoe shuffle");
				System.exit(0);
			}
			if(Integer.parseInt(args[4])<4||Integer.parseInt(args[4])>8)System.exit(6);
			int shuffle=Integer.parseInt(args[5]);
			if(shuffle<10||shuffle>100)System.exit(7);
			//construct game for interactive mode
			game=new Game(Integer.parseInt(args[4]), shuffle, Integer.parseInt(args[4]), Integer.parseInt(args[3]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		}
		//debug mode
		else if(args[0].equals("-d")){
			if(args.length != 6){
				System.out.println("Usage for debug mode: -d min-bet max-bet balance shoe-file cmd-file");
				System.exit(0);
			}
			//construct game for debug mode
			game=new Game(args[4], Integer.parseInt(args[3]), args[5], Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		}
		//simulation mode
		else if(args[0].equals("-s")){
			if(args.length != 8){
				System.out.println("Usage for simulation mode: -s min-bet max-bet balance shuffle s-number strategy");
				System.exit(0);
			}
			//construct game for simulation mode
			game=new Game(Integer.parseInt(args[3]), args[7], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[1]),Integer.parseInt(args[2]));	
		}else{
			game=null;
			System.out.println("Bad input parameters");
			System.exit(2);
		}
//-----------------------Begining of the game------------------------------//
		int bet = game.table.getMinBet();
		String command = " ";
		int bet_deal = 0;
		while(true){
			if(game.simulationMode(args[0])){//end simulation mode
				if(game.checkEndSimulationMode(Integer.parseInt(args[6]))){
					game.statistics(Integer.parseInt(args[3]));
					System.exit(0);
				}
			}
			if(game.shoe.getShufflePercentage()!=101)
				//System.out.println("PERCENTAGE: "+shoe.calculateUsagePercentage());
				if(game.shoe.calculateUsagePercentage()>=game.shoe.getShufflePercentage()){
					game.acefive.resetCount();
					game.hilo.restartRunningCount();
					game.shoe.shuffleShoe();//Shuffle
					game.s_number++;
				}
					//System.out.println(player1.getCurrentStrategy());
			
			//----------------------Before Bet and Deal----------------------------
			while(bet_deal<2){
				
				if(args[0].equals("-i")||args[0].equals("-d")){
					command = game.player1.getplayerInput(args[0]);
					if(args[0].equals("-d")&&(!command.equals("q"))){
						System.out.println("\n-cmd "+command);
						if((command.indexOf("b")!=-1)){
							String[]aux=command.split(" ");
							try{
								System.out.println("player is betting "+aux[1]);
							}catch(ArrayIndexOutOfBoundsException e){
								System.out.println("player is betting "+bet);
							}
						}
					}
				}else{
					command=game.strategyCommand(bet_deal, true, game.table.getMaxBet(), game.table.getMinBet(), bet,
							game.acefive, game.hilo, game.basic, game.player1, null);
				} 
				
				
				//System.out.println(command);
				//if(command.equals("2"))System.exit(1);
				//if(args[0].equals("-s"))command -  pedir a estrategia
				
				if(command.startsWith("b")){//Bet
					//System.out.println("starts b: "+bet_deal);
					if(game.player1.getBalance()>=game.table.getMinBet() && bet_deal == 0){//Se forem dadas as cartas ja nao pode apostar e so pode apostar se tiver maior balance que a aposta minima
						String[] bets = command.split(" ");
						if(bets.length==1){
							game.player1.subtractBalance(bet);
							bet_deal++;
						}else if(bets.length==2){
							//System.out.println(bets[1]+" bets[1]");
							if(Integer.parseInt(bets[1])<=game.player1.getBalance()){
								bet=Integer.parseInt(bets[1]);
								game.player1.subtractBalance(bet);
								bet_deal++;
							}else System.out.println("b: Illegal command-menor balance");//< balance
						}else System.out.println("b: Illegal command-mais 2 args");//more than 2 arguments
					}else{
						System.out.println("b: Illegal command");/*Can´t use bet*/
						if(args[0].equals("-s")){
							System.out.println("Player loses(lost all money)-Balance="+game.player1.getBalance());
							System.exit(1);
						}
					}
				}else if(command.equals("d")){//Deal
					if(bet_deal==1){
						game.player1.handnumber=1;
						//distributeCards();
						Card a=game.shoe.takeCard();
						game.acefive.cardRevealed(a);
						game.hilo.cardRevealed(a);
						Hand d=new Hand(a, game.shoe.takeCard(),0);
						a=game.shoe.takeCard();
						Card b=game.shoe.takeCard();
						game.acefive.cardRevealed(a);
						game.acefive.cardRevealed(b);
						game.hilo.cardRevealed(a);
						game.hilo.cardRevealed(b);
						Hand p=new Hand(a, b,bet);
						game.player1.hands.add(p);
						game.player1.setCurrentHand(p);
						game.dealer.setCurrentHand(d);
						//printCards();
						System.out.println("dealer's hand "+ game.dealer.showCurrentHand());
						System.out.println("player's hand "+ game.player1.showCurrentHand());

						if(game.player1.hands.getFirst().getPoints()==21){//blackjack
							if(game.dealer.getVisibleCard().getRank()!=Rank.ACE){
								System.out.println("blackjack!!");
								System.out.println("dealer's hand "+game.dealer.showCurrentHandAll());
								game.player1.blackjack();
								if(game.dealer.getCurrentHand().getPoints()==21){//dealer also has blackjack
									game.player1.setBalance(game.player1.getBalance()+bet);
									game.dealer.blackjack();
									game.player1.SetLast("Draw");
									//draw
									game.player1.draw();
									game.dealer.draw();
								}else{
									//player won-blackjack pays 3 to 2
									game.dealer.lost();
									game.player1.addBalance((float)(1.5*bet+bet));
									game.player1.SetLast("W");
									System.out.println("player wins and his current balance is "+game.player1.getBalance());
								}
								//System.out.println("end of turn");
								//collect the cards
								game.player1.handnumber=0;
								game.player1.hands.clear();
								game.player1.setCurrentHand(null);
								game.dealer.setCurrentHand(null);
							}//else it is possible to insure and do other possibilities
						}
						bet_deal++;
					}else System.out.println("d: illegal command");
					
				}else if(command.equals("$")){//Current balance
					System.out.println("player current balance is "+ game.player1.getBalance());
					
				}else if(command.equals("q")){
					System.exit(0);
				}else if(command.equals("ad")){
						System.out.println("According to Ace-Five strategy: " + game.acefive.advice());
				}else if(command.equals("st")){
					game.statistics(Integer.parseInt(args[3]));
				}else System.out.println("Illegal command");
			}
			bet_deal=0;
			//---------------------After Bet and Deal-------------------------------
			while(game.player1.getCurrentHand()!=null){
				
				
				//Check if player doubled down
					
				if(game.player1.getCurrentHand().getBet()==2*bet) command = "s";
				else{
					if(args[0].equals("-i")||args[0].equals("-d")){
						command = game.player1.getplayerInput(args[0]);
						if(args[0].equals("-d")&&(!command.equals("q")))System.out.println("\n-cmd "+command);
					}else{
						command=game.strategyCommand(bet_deal, false, game.table.getMaxBet(), game.table.getMinBet(), bet,
								game.acefive, game.hilo, game.basic, game.player1, game.dealer.getVisibleCard());
					} 
				}
					
				//if(args[0].equals("-d"))command -  ir buscar ao ficheiro
				//if(args[0].equals("-s"))command -  pedir a estrategia
				  
				if(command.equals("$")){//Current balance
					System.out.println("player current balance is "+ game.player1.getBalance());
					
				}else if(command.equals("h")){//Hit
					//Se fez double down so pode fazer hit uma vez
					System.out.println("player hits");
					Card a=game.shoe.takeCard();
					game.acefive.cardRevealed(a);
					game.hilo.cardRevealed(a);
					if(game.player1.hit(a)){
						if(game.player1.handnumber==1&&game.player1.hands.size()==1)
							System.out.println("player's hand "+ game.player1.showCurrentHand()+"\nplayer busts");
						else System.out.println("player's hand ["+game.player1.handnumber+"] "+game.player1.showCurrentHand());
						game.dealer.win();
						game.player1.SetLast("L");
						
						if(game.player1.getNextHand()==null){
							System.out.println("dealer's hand "+game.dealer.showCurrentHandAll());
							game.player1.lost();
							game.player1.hands.remove(game.player1.getCurrentHand());
							game.player1.setCurrentHand(null);							
							break;
						}else{
							game.player1.loses();
							Hand remove=game.player1.getCurrentHand();
							game.player1.setCurrentHand(game.player1.getNextHand());
							game.player1.hands.remove(remove);
							System.out.println("playing hand "+(++game.player1.handnumber)+"...");
							System.out.println("player's hand ["+game.player1.handnumber+"] "+game.player1.showCurrentHand());
						}				
						//player1.hands.remove(hand);
					}else {
						if(game.player1.handnumber==1&&game.player1.hands.size()==1)
							System.out.println("player's hand "+ game.player1.showCurrentHand()); 
						else System.out.println("player's hand ["+game.player1.handnumber+"] "+game.player1.showCurrentHand());
					}
				}else if(command.equals("s")){//Stand
					System.out.println("player stands"); 
					if(game.player1.getNextHand()!=null){
						game.player1.setCurrentHand(game.player1.getNextHand());
						System.out.println("playing hand "+(++game.player1.handnumber)+"..."); 
						System.out.println("player's hand "+game.player1.showCurrentHand());
					}else{
						System.out.println("dealer's hand "+game.dealer.showCurrentHandAll());
						break;
					}
				}else if(command.equals("u")){
					if((game.player1.hands.peekFirst()!=null)&&(game.player1.current.getCards().size()==2)){
						System.out.println("surrender option");
						game.player1.addBalance((float)game.player1.getCurrentHand().getBet()/2);
						game.player1.hands.clear();
						game.player1.setCurrentHand(null);
						game.dealer.setCurrentHand(null);
					}else System.out.println("You cannot use side rules at the moment");
				}else if(command.equals("p")){//allow resplitting until the player has as many as four hands and doubling a hand after splitting
					if(game.player1.getBalance()>=game.table.getMinBet() && game.player1.getCurrentHand().getSizeofCards()==2 && game.player1.hands.size()<4){
						//if cards have the same face value
						if(game.player1.getCurrentHand().getCards().get(0).getRank().getRankValue() == game.player1.getCurrentHand().getCards().get(1).getRank().getRankValue()){
							System.out.println("player is splitting");
							Card card1 = game.player1.getCurrentHand().getCards().get(0);
							Card card2 = game.player1.getCurrentHand().getCards().get(1);
							Hand hand1=new Hand(card1,game.shoe.takeCard(),bet);
							game.player1.hands.add(hand1);
							game.player1.hands.add(new Hand(card2,game.shoe.takeCard(),bet));
							game.player1.hands.remove(game.player1.getCurrentHand());
							game.player1.setCurrentHand(hand1);
							game.player1.subtractBalance(bet);
							System.out.println("player's hand ["+game.player1.handnumber+"]"+game.player1.showCurrentHand());
						}
					}else {
						System.out.println("p: illegal command -> player blance is "+game.player1.getBalance()); 
						if(args[0].equals("-d"))System.exit(1);
					}
				}else if(command.equals("2")){//only on an opening hand worth 9,10,11 and always doubles the bet;take only one more card from the dealer
					if(game.player1.getBalance()>=bet){
						game.player1.subtractBalance(bet);
						game.player1.getCurrentHand().setBet(2*bet);
						game.player1.getCurrentHand().addCard(game.shoe.takeCard());
					}else System.out.println("2: illegal command");
				}else if(command.equals("ad")){
					System.out.println("According to Basic strategy: " + game.basic.advice(game.player1.getCurrentHand(),game.dealer.getVisibleCard()));
					System.out.println("According to Ace-Five strategy: " + game.acefive.advice(game.player1.getCurrentHand(),game.dealer.getVisibleCard()));
					System.out.println("According to Hi-Low strategy: " + game.hilo.advice(game.player1.getCurrentHand(),game.dealer.getVisibleCard()));
				}else if(command.equals("st")){
					if(game.dealer.getblackjacks()!=0)
						System.out.println("BJ P/D" + game.player1.getblackjacks()/game.dealer.getblackjacks());
					else System.out.println("Dealer has no Blackjacks yet");
					if(game.player1.roundsplayed()!=0){
						System.out.println("Win " + (float)game.player1.getwins()/game.player1.roundsplayed());
						System.out.println("Lose " + (float)game.player1.getloses()/game.player1.roundsplayed());
						System.out.println("Push " + (float)game.player1.getdraws()/game.player1.roundsplayed());
					}else{
						System.out.println("Win " + game.player1.getwins());
						System.out.println("Lose " + game.player1.getloses());
						System.out.println("Push " + game.player1.getdraws());
					}
					System.out.println("Balance" + game.player1.getBalance() + "("+ 100*(float)(game.player1.getBalance()/Integer.parseInt(args[3])) +"%)" );
				}else if(command.equals("q")){
					System.exit(0);
				}else if(command.equals("i")){
					game.player1.changeInsurance(true);
				}else System.out.println("Illegal command");			
			}
			//-----------------Dealer-------------------------------
			if((game.dealer.getCurrentHand()!=null)&&(game.player1.hands.size()>0)){
				if(game.dealer.getCurrentHand().getPoints()==21){//Sem fazer hit ver se tem blackjack
					if(game.player1.getInsurance()){
						game.player1.addBalance(2*bet);
						game.player1.changeInsurance(false);
					}
				}
				while(game.dealer.getCurrentHand().getPoints()<17){
					System.out.println("dealer hits");
					Card card=game.shoe.takeCard();
					game.acefive.cardRevealed(card);
					game.hilo.cardRevealed(card);
					game.dealer.hit(card);
					System.out.println("dealer's hand "+ game.dealer.showCurrentHandAll());
				}
				System.out.println("dealer stands");
				for(Hand h:game.player1.hands){//check if a players hand beats the dealer's hand
					if(h.getPoints()==21&&h.getCards().size()==2)System.out.println("blackjack!!");
					if((h.getPoints()>game.dealer.getCurrentHand().getPoints())||(game.dealer.getCurrentHand().getPoints()>21)){
						game.player1.addBalance(2*bet);
						game.player1.win();
						game.dealer.lost();
						game.player1.SetLast("W");
					}else if(h.getPoints()==game.dealer.getCurrentHand().getPoints()){
						game.player1.addBalance(bet);
						game.player1.draw();
						game.dealer.draw();
						game.player1.SetLast("D");
						//System.out.println("draw");
					}else{
						//System.out.println("dealer wins");
						game.dealer.win();
						game.player1.lost();
						game.player1.SetLast("L");
					}
				}
				//see what card the dealer had
				Card hidden=game.dealer.returnHiddenCard();
				game.acefive.cardRevealed(hidden);
				game.hilo.cardRevealed(hidden);
				//collectCards();
				game.player1.hands.clear();
				game.player1.setCurrentHand(null);
				game.dealer.setCurrentHand(null);
			}
			
		}
	}
}
