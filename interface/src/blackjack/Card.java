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
		CLUBS('C'), DIAMONDS('D'), HEARTS('H'), SPADES('S');
		
		private char index;
		
		
		 Suit(char index){
		    	this.index=index;
		    }
		    
		    //Getters
		    public char getSuitValue() {
		        return index;
		    }
		    
		    public Suit getSuit(char index){
		    	for(Suit c:Suit.values()){
		    		if(c.getSuitValue()==index){
		    			return c;
		    		}
		    	}
				return null;
		    }
	}
		
public class Card {
	
	private Rank rank;	
	private Suit suit;
	
	//Constructors
	public Card(Rank rank, Suit suit){
		this.rank = rank;
		this.suit = suit;
	}
	
	
	public Rank getRank(){
		return this.rank;
	}
	
	public Suit getSuit(){
		return this.suit;
	}
	
	public int getValue(){
		return this.getRank().getRankValue();
	}
	
	/**
	 * Redefines the toString method to print the card according to assignment paper
	 */
	@Override
	public String toString() {
		String Card=Integer.toString(this.getValue());
		if(Card.equals("11"))Card="A";
		if(Card.equals("10")){
			if(this.rank.equals(Rank.JACK))Card="J";
			else if(this.rank.equals(Rank.QUEEN))Card="Q";
			else if(this.rank.equals(Rank.KING))Card="K";
		}
		return Card+this.getSuit().getSuitValue();
	}	

}
