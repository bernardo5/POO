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
					betAction(command, mode, Integer.parseInt(initialBalance));
				}else if(command.equals("d")){//Deal
					dealAction();
				}else if(command.equals("$")){//Current balance
					System.out.println("player current balance is "+ player1.getBalance());
				}else if(command.equals("q")){
					System.exit(0);
				}else if(command.equals("ad")){
						System.out.println("According to Ace-Five strategy: " + acefive.advice());
				}else if(command.equals("st")){
					statistics(Integer.parseInt(initialBalance));
				}else System.out.println("Illegal command");
			}
			bet_deal=0;
			//---------------------After Bet and Deal-------------------------------
			while(playableHand()){
				//Check if player doubled down
				if(doubledDown()) command = "s";
				else{
					command=getPlayCommandFromPlayer();
				}
				
				if(command.equals("$")){//Current balance
					System.out.println("player current balance is "+ player1.getBalance());
				}else if(command.equals("h")){//Hit
					if(hitAction())break;
				}else if(command.equals("s")){//Stand
					if(standAction())break;
				}else if(command.equals("u")){
					surrenderAction();
				}else if(command.equals("p")){//allow resplitting until the player has as many as four hands and doubling a hand after splitting
					splitAction(mode);
				}else if(command.equals("2")){//only on an opening hand worth 9,10,11 and always doubles the bet;take only one more card from the dealer
					doubleDownAction();
				}else if(command.equals("ad")){
					System.out.println("According to Basic strategy: " + basic.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
					System.out.println("According to Ace-Five strategy: " + acefive.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
					System.out.println("According to Hi-Low strategy: " + hilo.advice(player1.getCurrentHand(),dealer.getVisibleCard()));
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
