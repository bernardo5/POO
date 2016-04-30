package blackjack;


import java.io.*;
import java.util.*;

public class Shoe {
	private ArrayList<Card> sequence;
	private int nbNextCard = 0;
	private int nDecks;
	private int shufflePercentage;
	
	//Getters
	public int getShufflePercentage() {
		return shufflePercentage;
	}
	
	//Constructor
	public Shoe(int numberDecks, int percentage){
		this.sequence=new ArrayList<Card>(nDecks*52);
		this.nDecks=numberDecks;
		this.shufflePercentage=percentage;
		this.nbNextCard=0;
	}
	
	//Methods
	public int calculateUsagePercentage(){
		return ((nDecks*52)-(nbNextCard+1))/(nDecks*52);
	}
	
	public Card takeCard(){
		return this.sequence.get(nbNextCard++);
	}
	
	public void shuffleShoe(){
		Collections.shuffle(sequence);
		System.out.println("shuffling the shoe...");
	}
	
	public void populateShoe(){
		for(int i=0; i<this.nDecks; i++){
			for(Rank c:Rank.values()){
				for(Suit s:Suit.values()){
					Card card= new Card(c,s);
					this.sequence.add(card);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String message= "Shoe: \n";
		for(Card c:sequence){
			message+=c.toString()+"\n";
		}
		return message;
	}
	
	public void populateShoeFromFile(String file){
		String line, rank,suit;
		Rank cardrank = null;
		Suit cardsuit = null;
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
		    
		    int i=0;
		    while (((line = br.readLine()) != null)&&(i<nDecks)) {
		    	String[] arr = line.split(" ");
		    	for ( String card : arr) {
		    		System.out.println(card);
			   	    
		    		if(card.length()==2){//all except 10's
						rank=String.valueOf(card.charAt(0));
						suit=String.valueOf(card.charAt(1));
					}else{
						rank="10";
						suit=String.valueOf(card.charAt(2));
					}
		    		
		    		if(rank.equals("2"))cardrank = Rank.TWO;
		    		if(rank.equals("3"))cardrank = Rank.THREE;
		    		if(rank.equals("4"))cardrank = Rank.FOUR;
		    		if(rank.equals("5"))cardrank = Rank.FIVE;
		    		if(rank.equals("6"))cardrank = Rank.SIX;
		    		if(rank.equals("7"))cardrank = Rank.SEVEN;
		    		if(rank.equals("8"))cardrank = Rank.EIGHT;
		    		if(rank.equals("9"))cardrank = Rank.NINE;
		    		if(rank.equals("10"))cardrank = Rank.TEN;
		    		if(rank.equals("J"))cardrank = Rank.JACK;
		    		if(rank.equals("Q"))cardrank = Rank.QUEEN;
		    		if(rank.equals("K"))cardrank = Rank.KING;
		    		if(rank.equals("A"))cardrank = Rank.ACE;
		    		
		    		if(suit.equals("H"))cardsuit = Suit.HEARTS;
		    		if(suit.equals("S"))cardsuit = Suit.SPADES;
		    		if(suit.equals("D"))cardsuit = Suit.DIAMONDS;
		    		if(suit.equals("C"))cardsuit = Suit.CLUBS;

			   	    sequence.add(new Card(cardrank,cardsuit));
			   	 }
		    	 i++;
		    }
		    br.close();
		    
	     }catch(FileNotFoundException e){
	    	 e.printStackTrace();
	     }catch(IOException e){
            e.printStackTrace();
	     } 
		System.out.println(this.toString());
	}

	
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int shoe=4;
		Shoe shoeGame=new Shoe(shoe,50);
		//shoeGame.populateShoeFromFile("Código/shoe-file.txt");
		shoeGame.populateShoe(shoe);
		shoeGame.shuffleShoe();
		System.out.println(shoeGame.toString());
		//shoeGame.shuffleShoe();
		//System.out.println(shoeGame.toString());	
	}*/
	
}
