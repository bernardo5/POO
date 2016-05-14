package blackjack;

public class Table {
	private int minBet;
	private int maxBet;
	
	//Getters
	public int getMinBet() {
		return minBet;
	}
	public int getMaxBet() {
		return maxBet;
	}
	
	//Setters
	public void setMinBet(int minBet) {
		this.minBet = minBet;
	}
	public void setMaxBet(int maxBet) {
		this.maxBet = maxBet;
	}
	
	//Constructor
	public Table(int minBet, int maxBet){
		this.minBet=minBet;
		this.maxBet=maxBet;
	}
	
	@Override
	public String toString() {
		return "Table [minBet=" + minBet + ", maxBet=" + maxBet + "]";
	}
	
}
