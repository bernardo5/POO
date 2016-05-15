package blackjack;

/**
 * Methods Override to get the commands from the file
 */

public class Debug extends Game{
	
	/**
	 * 
	 * @param fileShoe - file to get the card order to populate the shoe
	 * @param balance - initial player balance
	 * @param commandFile - file to get the order of the player commands
	 * @param minBet
	 * @param maxBet
	 */
	public Debug(String fileShoe, int balance, String commandFile, int minBet, int maxBet){
		super(minBet, maxBet);
		shoe = new Shoe();
		shoe.populateShoeFromFile(fileShoe);
		player1 = new Player(balance,commandFile, shoe.nCards()/52);
		hilo=new HiLo(shoe.nCards()/52);
	}
	
	/**
	 * Method override in order to quit debug if player has no more money to bet
	 */
	@Override
	public void splitAction(){
		if(player1.getBalance()>=table.getMinBet() && player1.getCurrentHand().getSizeofCards()==2 && player1.hands.size()<4){
			//if cards have the same face value
			if(player1.getCurrentHand().getCards().get(0).getRank().getRankValue() == player1.getCurrentHand().getCards().get(1).getRank().getRankValue()){
				total_hands++;
				System.out.println("player is splitting");
				Card card1 = player1.getCurrentHand().getCards().get(0);
				Card card2 = player1.getCurrentHand().getCards().get(1);
				Hand hand1=new Hand(card1,shoe.takeCard(),bet);
				player1.hands.add(player1.hands.indexOf(player1.getCurrentHand()), hand1);
				player1.hands.add(player1.hands.indexOf(player1.getCurrentHand())+1, new Hand(card2,shoe.takeCard(),bet));
				player1.hands.remove(player1.getCurrentHand());
				player1.setCurrentHand(hand1);
				player1.subtractBalance(bet);
				System.out.println("player's hand ["+player1.handnumber+"]"+player1.showCurrentHand());
			}else System.out.println("p: illegal command -> You need to have two similar cards to split");
		}else {
			System.out.println("p: illegal command -> player blance is "+player1.getBalance()); 
			System.exit(1);
		}
	}
	
	/**
	 * get command from player commands linked list 
	 */
	@Override
	public String getplayerInput(){
		if(player1.commands.isEmpty()) return "q"; //no more commands to read
		else{
			String s=player1.commands.removeFirst();
			if(s.equals("b")){
				try{
					Integer.parseInt(player1.commands.getFirst());
					return s+=" "+player1.commands.removeFirst();
				}catch(NumberFormatException e){
					return s;
				}
			}else return s;					
		}
	}
	
	/**
	 * gets command from previous method and depending on the 
	 * command prints info in the command prompt
	 */
	@Override
	public String getCommandFromPlayer(){
		String command=new String();
		command = getplayerInput();
		if(!command.equals("q")){
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
		return command;
	}
	
	@Override
	public String getPlayCommandFromPlayer(){
		String command=new String();
		command = getplayerInput();
		if((!command.equals("q")))System.out.println("\n-cmd "+command);
		return command;
	}
}
