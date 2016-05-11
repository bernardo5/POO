package blackjack;

public class Game {
	
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
				if(player.getCurrentStrategy().equals("BS")){
					if(basic.advice(player.current, card).equals("2")&&player.getBalance()<lastBet) return "h";
					else return basic.advice(player.current, card);
				}else if(player.getCurrentStrategy().equals("BS-AF")){
					//at this point the action taken is the same as the basic
					if(basic.advice(player.current, card).equals("2")&&player.getBalance()<lastBet) return "h";
					else return basic.advice(player.current, card);
				}else if(player.getCurrentStrategy().equals("HL")){
					if(hilo.advice(player.current, card).contains("Using basic:")){
						if(basic.advice(player.current, card).equals("2")&&player.getBalance()<lastBet) return "h";
						else return basic.advice(player.current, card);
					}
					else{
						if(hilo.advice(player.current, card).equals("2")&&player.getBalance()<lastBet) return "h";
						else return hilo.advice(player.current, card);
					}
				}else{//HL-AF
					if(hilo.advice(player.current, card).contains("Using basic:")){
						if(basic.advice(player.current, card).equals("2")&&player.getBalance()<lastBet) return "h";
						else return basic.advice(player.current, card);
					}
					else{
						if(hilo.advice(player.current, card).equals("2")&&player.getBalance()<lastBet) return "h";
						else return hilo.advice(player.current, card);
					}
				}
			}
		
	}

	public static void main(String[] args){
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
			shoe = new Shoe(Integer.parseInt(args[4]),Integer.parseInt(args[5]));
			shoe.populateShoe();
			shoe.shuffleShoe();
			player1 = new Player(Integer.parseInt(args[3]), shoe.nCards()/52, args[7]);	
		}else{
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
		Acefive acefive=new Acefive();
		Basic basic=new Basic();
		HiLo hilo=new HiLo(shoe.nCards()/52);
		Game game=new Game();
		
		while(true){
			if((args[0].equals("-s"))){//end simulation mode
				if((s_number==Integer.parseInt(args[6]))){
				System.out.println("Your final balance is: "+ player1.getBalance());
				System.exit(0);
				}
			}
			if(shoe.getShufflePercentage()!=100)
				//System.out.println("PERCENTAGE: "+shoe.calculateUsagePercentage());
				if(shoe.calculateUsagePercentage()>=shoe.getShufflePercentage()){
					acefive.resetCount();
					hilo.restartRunningCount();
					shoe.shuffleShoe();//Shuffle
					s_number++;
				}
					//System.out.println(player1.getCurrentStrategy());
			
			//----------------------Before Bet and Deal----------------------------
			while(bet_deal<2){
				
				if(args[0].equals("-i")||args[0].equals("-d")){
					command = player1.getplayerInput(args[0]);
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
					command=game.strategyCommand(bet_deal, true, table.getMaxBet(), table.getMinBet(), bet,
							acefive, hilo, basic, player1, null);
				} 
				
				
				//System.out.println(command);
				//if(command.equals("2"))System.exit(1);
				//if(args[0].equals("-s"))command -  pedir a estrategia
				
				if(command.startsWith("b")){//Bet
					//System.out.println("starts b: "+bet_deal);
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
							}else System.out.println("b: Illegal command-menor balance");//< balance
						}else System.out.println("b: Illegal command-mais 2 args");//more than 2 arguments
					}else{
						System.out.println("b: Illegal command");/*Can´t use bet*/
						if(args[0].equals("-s")){
							System.out.println("Player loses(lost all money)-Balance="+player1.getBalance());
							System.exit(1);
						}
					}
				}else if(command.equals("d")){//Deal
					if(bet_deal==1){
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
							if(dealer.getVisibleCard().getValue()!=10&&dealer.getVisibleCard().getValue()!=11){
								System.out.println("blackjack!!");
								System.out.println("dealer's hand "+dealer.showCurrentHandAll());
								player1.blackjack();
								if(dealer.getCurrentHand().getPoints()==21){//dealer also has blackjack
									player1.setBalance(player1.getBalance()+bet);
									dealer.blackjack();
									player1.SetLast("Draw");
									//draw
									player1.draw();
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
								player1.hands.clear();
								player1.setCurrentHand(null);
								dealer.setCurrentHand(null);
							}//else it is possible to insure and do other possibilities
						}
						bet_deal++;
					}else System.out.println("d: illegal command");
					
				}else if(command.equals("$")){//Current balance
					System.out.println("player current balance is "+ player1.getBalance());
					
				}else if(command.equals("q")){
					System.exit(0);
				}else if(command.equals("ad")){
						System.out.println("According to Ace-Five strategy: " + acefive.advice());
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
				else{
					if(args[0].equals("-i")||args[0].equals("-d")){
						command = player1.getplayerInput(args[0]);
						if(args[0].equals("-d")&&(!command.equals("q")))System.out.println("\n-cmd "+command);
					}else{
						command=game.strategyCommand(bet_deal, false, table.getMaxBet(), table.getMinBet(), bet,
								acefive, hilo, basic, player1, dealer.getVisibleCard());
					} 
				}
					
				//if(args[0].equals("-d"))command -  ir buscar ao ficheiro
				//if(args[0].equals("-s"))command -  pedir a estrategia
				  
				if(command.equals("$")){//Current balance
					System.out.println("player current balance is "+ player1.getBalance());
					
				}else if(command.equals("h")){//Hit
					//Se fez double down so pode fazer hit uma vez
					System.out.println("player hits");
					Card a=shoe.takeCard();
					acefive.cardRevealed(a);
					hilo.cardRevealed(a);
					if(player1.hit(a)){
						System.out.println("player's hand "+ player1.showCurrentHand()+"\nplayer busts");
						dealer.win();
						player1.SetLast("L");
						
						if(player1.getNextHand()==null){
							System.out.println("dealer's hand "+dealer.showCurrentHandAll());
							player1.lost();
							player1.hands.remove(player1.getCurrentHand());
							player1.setCurrentHand(null);							
							break;
						}else{
							player1.loses();
							Hand remove=player1.getCurrentHand();
							player1.setCurrentHand(player1.getNextHand());
							player1.hands.remove(remove);
						}				
						//player1.hands.remove(hand);
					}else System.out.println("player's hand "+ player1.showCurrentHand());
				}else if(command.equals("s")){//Stand
					System.out.println("player stands");
					System.out.println("dealer's hand "+dealer.showCurrentHandAll());
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
							System.out.println(player1.showCurrentHand());
						}
					}else System.out.println("p: illegal command");
				}else if(command.equals("2")){//only on an opening hand worth 9,10,11 and always doubles the bet;take only one more card from the dealer
					if(player1.getBalance()>=bet){
						player1.subtractBalance(bet);
						player1.getCurrentHand().setBet(2*bet);
						player1.getCurrentHand().addCard(shoe.takeCard());
					}else System.out.println("u: illegal command");
				}else if(command.equals("ad")){
					System.out.println("According to Basic strategy: " + basic.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
					System.out.println("According to Ace-Five strategy: " + acefive.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
					System.out.println("According to Hi-Low strategy: " + hilo.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
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
					acefive.cardRevealed(card);
					hilo.cardRevealed(card);
					dealer.hit(card);
					System.out.println("dealer's hand "+ dealer.showCurrentHandAll());
				}
				System.out.println("dealer stands");
				for(Hand h:player1.hands){//check if a players hand beats the dealer's hand
					if(h.getPoints()==21&&h.getCards().size()==2)System.out.println("blackjack!!");
					if((h.getPoints()>dealer.getCurrentHand().getPoints())||(dealer.getCurrentHand().getPoints()>21)){
						player1.addBalance(2*bet);
						player1.win();
						dealer.lost();
						player1.SetLast("W");
					}else if(h.getPoints()==dealer.getCurrentHand().getPoints()){
						player1.addBalance(bet);
						player1.draw();
						dealer.draw();
						player1.SetLast("D");
						//System.out.println("draw");
					}else{
						//System.out.println("dealer wins");
						dealer.win();
						player1.lost();
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
			
		}
	}
}
