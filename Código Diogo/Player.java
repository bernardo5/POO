package blackjackG21;

public class Player extends Person{
	private int balance;
	
	//Constructor
	public Player(int balance) {
		super();
		this.setBalance(balance);
	}
	
	//Getters
	public int getBalance() {
		return balance;
	}
	//Setters
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public void addBalance(int returnBalance){
		this.balance+=returnBalance;
	}
	
	//Methods
	public String showHands(){
		/*player may have more than one hand and shows all cards*/
		String game=new String();
		int i=1;
		for(Hand aux:hands){
			game+="Hand "+i+": "+aux.toString()+"\n";
		}
		return game;
	}
	
	public boolean placeBet(int bet){//the player has to do at least a bet
		System.out.println("bet command");
		if(bet>getBalance()){
			System.out.println("You cannot afford this bet. Your balance is:"+getBalance());
			return false;
		}else{
			setBalance(getBalance()-bet);
			return true;
		}
	}
	
	public void insurance(){
	}
	
	public void surrender(){
	}
	
	public void splitting(){
	}
	
	public void doubledown(){
	}
	

	/*public static void main(String[] args) {
		RegularPlayer player=new RegularPlayer(new Card("Q",'S'), new Card("A",'S'), 15);
		player.hit(new Card("A",'S'), 0);
		System.out.println(player.showHands());
	}*/

}
