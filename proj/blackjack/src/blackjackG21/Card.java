package blackjackG21;

public class Card {
	
	 public enum Rank {
	        DEUCE(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(
	                9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

	        private int RankPoints;

	        Rank(int points) {
	            this.RankPoints = points;
	        }

	        public int getRankPoints() {
	            return this.RankPoints;
	        }

	    }

	    public enum Suit {
	        CLUBS, DIAMONDS, HEARTS, SPADES;
	    }

	    final Rank rank;
	    final Suit suit;

	    public Card(Rank rank, Suit suit) {
	        this.rank = rank;
	        this.suit = suit;
	    }

	    public Rank getRank() {
	        return this.rank;
	    }

	    public Suit getSuit() {

	        return this.suit;

	    }

	    public String toString() {
	        return rank + " of " + suit;
	    }	
	
}
