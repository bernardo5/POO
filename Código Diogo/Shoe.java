package blackjackG21;

//import java.io.*;
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
	}
	
	public void populateShoe(int nbDecks){
		for(int i=0; i<nbDecks; i++){
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
	/*
	public void populateShoeFromFile(String file){
		String line, rank,suit;
		int i=0, rank2;
		
		try{
			FileInputStream fileInputStream = new FileInputStream(file);
		    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		    
		    while (((line = bufferedReader.readLine()) != null)&&(i<nDecks)) {
		    	String[] arr = line.split(" ");
		    	for ( String card : arr) {
		    		System.out.println(card);
			   	    rank2 = Integer.(card);
			   	    
		    		if(card.length()==2){//all except 10's
						rank=String.valueOf(card.charAt(0));
						suit=String.valueOf(card.charAt(1));
					}else{
						rank="10";
						suit=String.valueOf(card.charAt(2));
					}
			   	    sequence.add(new Card(Rank(Rrank2)),Suit.valueOf(suit)));
			   	 }
		    	 i++;
		    }
		    bufferedReader.close();
		    
	     }catch(FileNotFoundException e){
	    	 e.printStackTrace();
	     }catch(IOException e){
            e.printStackTrace();
	     } 
		System.out.println(this.toString());
	}


*/
	

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

		
		
		
	}

}
