package blackjack;

public class Simulation extends Game{

	Simulation(int balance, String strategy, int nShoe, int shufflePercentage, int minBet, int maxBet){
		super(minBet, maxBet);
		shoe = new Shoe(nShoe,shufflePercentage);
		shoe.populateShoe();
		shoe.shuffleShoe();
		player1 = new Player(balance, shoe.nCards()/52, strategy);
		hilo=new HiLo(shoe.nCards()/52);
	}
	
	
	@Override
	public void betAction(String command, int initialBalance){
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
				}else{
					System.out.println("b: Illegal command");//< balance
					System.exit(1);
				}
			}else{
				System.out.println("b: Illegal command");//more than 2 arguments
				System.exit(1);
			}
		}else{
			System.out.println("b: Illegal command");/*Can´t use bet*/
			this.statistics(initialBalance);
			System.exit(1);
		}
	}
	
	@Override
	public void dealAction(){
		if(bet_deal==1){
			total_hands++;
			player1.handnumber=1;
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
			//System.out.println("dealer's hand "+ dealer.showCurrentHand());
			//System.out.println("player's hand "+ player1.showCurrentHand());

			if(player1.hands.getFirst().getPoints()==21){//blackjack
				if(dealer.getVisibleCard().getRank()!=Rank.ACE){
					//System.out.println("blackjack!!");
					//System.out.println("dealer's hand "+dealer.showCurrentHandAll());
					player1.blackjack();
					if(dealer.getCurrentHand().getPoints()==21){//dealer also has blackjack
						player1.setBalance(player1.getBalance()+bet);
						dealer.blackjack();
						//player1.SetLast("Draw");
						//draw
						player1.draw();
						dealer.draw();
					}else{
						//player won-blackjack pays 3 to 2
						dealer.lost();
						player1.addBalance((float)(1.5*bet+bet));
						player1.SetLast("W");
						//System.out.println("player wins and his current balance is "+player1.getBalance());
					}
					//System.out.println("end of turn");
					//collect the cards
					player1.handnumber=0;
					player1.hands.clear();
					player1.setCurrentHand(null);
					dealer.setCurrentHand(null);
				}//else it is possible to insure and do other possibilities
			}
			bet_deal++;
		}else {
			System.out.println("d: illegal command");
			System.exit(1);
		}
	}
	
	@Override
	public boolean hitAction(){
		//Se fez double down so pode fazer hit uma vez
		//System.out.println("player hits");
		Card a=shoe.takeCard();
		acefive.cardRevealed(a);
		hilo.cardRevealed(a);
		if(player1.hit(a)){
			/*if(player1.handnumber==1&&player1.hands.size()==1)
				System.out.println("player's hand "+ player1.showCurrentHand()+"\nplayer busts");
			else System.out.println("player's hand ["+player1.handnumber+"] "+player1.showCurrentHand());*/
			dealer.win();
			player1.SetLast("L");
			
			if(player1.getNextHand()==null){
				//System.out.println("dealer's hand "+dealer.showCurrentHandAll());
				player1.lost();
				player1.hands.remove(player1.getCurrentHand());
				player1.setCurrentHand(null);							
				return true;
			}else{
				player1.loses();
				Hand remove=player1.getCurrentHand();
				player1.setCurrentHand(player1.getNextHand());
				player1.hands.remove(remove);
				//System.out.println("playing hand "+(++player1.handnumber)+"...");
				//System.out.println("player's hand ["+player1.handnumber+"] "+player1.showCurrentHand());
			}				
			//player1.hands.remove(hand);
		}/*else {
			if(player1.handnumber==1&&player1.hands.size()==1)
				System.out.println("player's hand "+ player1.showCurrentHand()); 
			else System.out.println("player's hand ["+player1.handnumber+"] "+player1.showCurrentHand());
		}*/
		return false;
	}
	
	@Override
	public boolean standAction(){
		//System.out.println("player stands"); 
		if(player1.getNextHand()!=null){
			player1.setCurrentHand(player1.getNextHand());
			//System.out.println("playing hand "+(++player1.handnumber)+"..."); 
			//System.out.println("player's hand "+player1.showCurrentHand());
		}else{
			//System.out.println("dealer's hand "+dealer.showCurrentHandAll());
			return true;
		}
		return false;
	}
	
	@Override
	public void surrenderAction(){
		if(possibleToSurrender()){
			//System.out.println("player surrends...");
			player1.addBalance((float)player1.getCurrentHand().getBet()/2);
			player1.hands.clear();
			player1.setCurrentHand(null);
			dealer.setCurrentHand(null);
		}else{
			System.out.println("You cannot surrender :(");
			System.exit(1);
		}
	}
	
	@Override
	public void splitAction(){
		if(player1.getBalance()>=table.getMinBet() && player1.getCurrentHand().getSizeofCards()==2 && player1.hands.size()<4){
			//if cards have the same face value
			if(player1.getCurrentHand().getCards().get(0).getRank().getRankValue() == player1.getCurrentHand().getCards().get(1).getRank().getRankValue()){
				total_hands++;
				//System.out.println("player is splitting");
				Card card1 = player1.getCurrentHand().getCards().get(0);
				Card card2 = player1.getCurrentHand().getCards().get(1);
				Hand hand1=new Hand(card1,shoe.takeCard(),bet);
				player1.hands.add(hand1);
				player1.hands.add(new Hand(card2,shoe.takeCard(),bet));
				player1.hands.remove(player1.getCurrentHand());
				player1.setCurrentHand(hand1);
				player1.subtractBalance(bet);
				//System.out.println("player's hand ["+player1.handnumber+"]"+player1.showCurrentHand());
			}else {
				System.out.println("p: illegal command -> You need to have two similar cards to split");
				System.exit(1);
			}
		}else {
			System.out.println("p: illegal command -> player balance is "+player1.getBalance()); 
			System.exit(1);
		}
	}
	
	@Override
	public void doubleDownAction(){
		if(player1.getBalance()>=bet){
			player1.subtractBalance(bet);
			player1.getCurrentHand().setBet(2*bet);
			player1.getCurrentHand().addCard(shoe.takeCard());
		}else {
			System.out.println("2: illegal command");
			System.exit(1);
		}
	}
	
	@Override
	public void DHit(){
		while(dealer.getCurrentHand().getPoints()<17){
			//System.out.println("dealer hits");
			Card card=shoe.takeCard();
			acefive.cardRevealed(card);
			hilo.cardRevealed(card);
			dealer.hit(card);
			//System.out.println("dealer's hand "+ dealer.showCurrentHandAll());
		}
		//System.out.println("dealer stands");
	}
	
	public String strategyCommand(int bet_deal, 
			boolean bet_flag, int maxBet, int minBet, int lastBet, 
			Acefive acefive, HiLo hilo, Basic basic, Player player, Card card){
		//strategy mode
			if(bet_flag){//want to know the amount of money to bet
				//System.out.println("bet/deal part: "+bet_deal);
				if(bet_deal==0){
					//System.out.println("bet part");
					if(player.getCurrentStrategy().equals("BS")||player.getCurrentStrategy().equals("HL")){//follow normal bet strategy
						int minus,plus;
						//System.out.println("ok");
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
	
	
	@Override
	public String getCommandFromPlayer(){
		String command=new String();
			command=strategyCommand(bet_deal, true,table.getMaxBet(), table.getMinBet(), bet,
					acefive, hilo, basic, player1, null);
		return command;
	}
	
	
	@Override
	public String getPlayCommandFromPlayer(){
		String command=new String();
		
		command=strategyCommand(bet_deal, false, table.getMaxBet(), table.getMinBet(), bet,
				acefive, hilo, basic, player1, dealer.getVisibleCard());
		 
		return command;
	}
	
	@Override
	public void SetResults(){
		for(Hand h:player1.hands){//check if a players hand beats the dealer's hand
			if(h.getPoints()==21&&h.getCards().size()==2)System.out.println("blackjack!!");
			if((h.getPoints()>dealer.getCurrentHand().getPoints())||(dealer.getCurrentHand().getPoints()>21)){
				player1.addBalance(2*bet);
				player1.win();//System.out.println("player wins and his current balance is "+player1.getBalance());
				dealer.lost();
				player1.SetLast("W");
			}else if(h.getPoints()==dealer.getCurrentHand().getPoints()){
				player1.addBalance(bet);
				player1.draw();//System.out.println("player pushes and his current balance is "+player1.getBalance());
				dealer.draw();
				player1.SetLast("D");
				//System.out.println("draw");
			}else{
				//System.out.println("dealer wins");
				dealer.win();
				player1.lost();//System.out.println("player loses and his current balance is "+player1.getBalance());
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
	
	@Override
	public void checkShuffle(){
		if(shoe.calculateUsagePercentage()>=shoe.getShufflePercentage()){
			acefive.resetCount();
			hilo.restartRunningCount();
			shoe.shuffleShoe();//Shuffle
			//System.out.println("shuffling the shoe...\n");
			s_number++;
		}
	}
	
	public void play(String mode, String initialBalance, String nshuffles){
		String command = " ";
		while(true){
			if(checkEndSimulationMode(Integer.parseInt(nshuffles))){
				statistics(Integer.parseInt(initialBalance));
				System.exit(0);
			}
			
			if(allowedShuffling()) checkShuffle();
					//System.out.println(player1.getCurrentStrategy());
			
			//----------------------Before Bet and Deal----------------------------
			while(!CardsDealt()){
				
				command=getCommandFromPlayer();
				
				if(command.startsWith("b")){//Bet
					betAction(command, Integer.parseInt(initialBalance));
				}else if(command.equals("d")){//Deal
					dealAction();
				}else if(command.equals("q")){
					System.exit(0);
				}else{
					System.out.println("Illegal command: "+command);
					System.exit(-1);
				}
			}
			bet_deal=0;
			//---------------------After Bet and Deal-------------------------------
			while(playableHand()){
				//Check if player doubled down
				if(doubledDown()) command = "s";
				else{
					command=getPlayCommandFromPlayer();
				}
				
				if(command.equals("h")){//Hit
					if(hitAction())break;
				}else if(command.equals("s")){//Stand
					if(standAction())break;
				}else if(command.equals("u")){
					surrenderAction();
				}else if(command.equals("p")){//allow resplitting until the player has as many as four hands and doubling a hand after splitting
					splitAction();
				}else if(command.equals("2")){//only on an opening hand worth 9,10,11 and always doubles the bet;take only one more card from the dealer
					doubleDownAction();
				}else if(command.equals("st")){
					statistics(Integer.parseInt(initialBalance));
				}else if(command.equals("q")){
					System.exit(0);
				}else if(command.equals("i")){
					player1.changeInsurance(true);
				}else System.out.println("Illegal command");			
			}
			//-----------------Dealer-------------------------------
			if(playerDidNotBust()){
				if(DBlackjack()) CheckAndPayIns();
			DHit();
			SetResults();
			}
			
		}
	}
}
