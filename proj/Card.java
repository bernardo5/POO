package blackjack;
	
	enum Rank{
		TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
	    SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);
		
	    private int index; 
	    
	    //Constructor
	    Rank(int index){
	    	this.index=index;
	    }
	    
	    //Getters
	    public int getRankValue() {
	        return index;
	    }
	    
	    public Rank getRank(int index){
	    	for(Rank c:Rank.values()){
	    		if(c.getRankValue()==index){
	    			return c;
	    		}
	    	}
			return null;
	    }
	}
	
	enum Suit{
		CLUBS, DIAMONDS, HEARTS, SPADES;
	}
		
public class Card {
	
	private Rank rank;	
	private Suit suit;
	
	//Constructors
	public Card(Rank rank, Suit suit){
		this.rank = rank;
		this.suit = suit;
	}
	
	//Getters
	public Rank getRank(){
		return this.rank;
	}
	public Suit getSuit(){
		return this.suit;
	}
	public int getValue(){
		return this.getRank().getRankValue();
	}

	@Override
	public String toString() {
		return "Card (" + rank +" "+ suit + ")";
	}	

}
