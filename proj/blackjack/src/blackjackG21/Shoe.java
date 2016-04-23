package blackjackG21;

import java.io.*;
import java.util.*;

import blackjackG21.Card.Rank;
import blackjackG21.Card.Suit;

public class Shoe {
	private Card[] sequence;
	private int nbNextCard;
	
	public Shoe(int numberDecks){
		sequence=new Card[numberDecks*52];
		nbNextCard=0;
	}
	
	public Card takeCard(){
		if(nbNextCard<52)return sequence[nbNextCard++];
		return null;
	}
	
	
	
	
	@Override
	public String toString() {
		String message= "Shoe: \n";
		for(Card c:sequence){
			message+=c.toString()+"\n";
		}
		return message;
	}
	
	public void populateShoeFromFile(String file, int shoe){
		String line, name;
		char suit;
		Rank rank=null;
		Suit suits=null;
		int positionToAdd=0;
		int i=0;
		try{
			 FileInputStream fileInputStream = new FileInputStream(file);
		     InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		     
		     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		     StringBuffer stringBuffer = new StringBuffer();
		     while (((line = bufferedReader.readLine()) != null)&&(i<shoe)) {
		    	 String[] arr = line.split(" ");
		    	 for ( String card : arr) {
			   	       System.out.println(card);
			   	       
			   	    if(card.length()==2){/*all except 10's*/
						name=String.valueOf(card.charAt(0));
						suit=card.charAt(1);
					}else{
						name="10";
						suit=card.charAt(2);
					}
			   	    ///////////////////////////////////////////////////////////
			   	    switch(name){
			   	    	case "2":
			   	    		rank = Rank.DEUCE;
			   	    		break;
			   	    	case "3":
			   	    		rank = Rank.THREE;
			   	    		break;
			   	    	case "4":
			   	    		rank = Rank.FOUR;
			   	    		break;
			   	    	case "5":
			   	    		rank = Rank.FIVE;
			   	    		break;
			   	    	case "6":
			   	    		rank = Rank.SIX;
			   	    		break;
			   	    	case "7":
			   	    		rank = Rank.SEVEN;
			   	    		break;
			   	    	case "8":
			   	    		rank = Rank.EIGHT;
			   	    		break;
			   	    	case "9":
			   	    		rank = Rank.NINE;
			   	    		break;
			   	    	case "10":
			   	    		rank = Rank.TEN;
			   	    		break;
			   	    	case "J":
			   	    		rank = Rank.JACK;
			   	    		break;
			   	    	case "Q":
			   	    		rank = Rank.QUEEN;
			   	    		break;
			   	    	case "K":
			   	    		rank = Rank.KING;
			   	    		break;
			   	    	case "A":
			   	    		rank = Rank.ACE;
			   	    		break;
			   	    }
			   	    
			   	    switch(suit){
			   	    	case 'H':
			   	    		suits=Suit.HEARTS;
			   	    		break;
			   	    	case 'S':
			   	    		suits=Suit.SPADES;
			   	    		break;
						case 'D':
							suits=Suit.DIAMONDS;	
							break;
						case 'C':
							suits=Suit.CLUBS;	
							break;
			
			   	    }
			   	    
			   	    //////////////////////////////////////////////////////////////
			   	 sequence[positionToAdd++]=new Card(rank, suits);
			   	 }
		    	 i++;
		     }
	     }catch(FileNotFoundException e){
	            e.printStackTrace();
	     }catch(IOException e){
	            e.printStackTrace();
	     } 
		System.out.println(this.toString());
	}
	
	public void shuffle(){
		Collections.shuffle(Arrays.asList(sequence));
	}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		int shoe=4;
		Shoe shoeGame=new Shoe(shoe);
		shoeGame.populateShoeFromFile("src/shoe-file.txt", shoe);
		System.out.println(shoeGame.toString());
		shoeGame.shuffle();
		
		System.out.println(shoeGame.toString());

		
		
		
	}*/

}
