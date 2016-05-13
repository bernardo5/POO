package blackjack;

public class Debug extends Game{
	Debug(String fileShoe, int balance, String commandFile, int minBet, int maxBet){
		super(minBet, maxBet);
		shoe = new Shoe();
		shoe.populateShoeFromFile(fileShoe);
		player1 = new Player(balance,commandFile, shoe.nCards()/52);
		hilo=new HiLo(shoe.nCards()/52);
	}
	
	
	@Override
	public String getplayerInput() /*throws IOException*/{
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
		
		//return null;
	}
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
