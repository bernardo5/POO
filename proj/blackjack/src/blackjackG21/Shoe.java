package blackjackG21;

import java.io.*;
import java.util.*;

public class Shoe {
	Deck[] decks;
	
	public Shoe(int numberDecks){
		decks=new Deck[numberDecks];
	}
	
	public void addDeck(int position, Deck deck){
		decks[position]=deck;
	}
	
	
	
	
	@Override
	public String toString() {
		String message= "Shoe: \n";
		for(Deck d:decks){
			message+="Deck1: "+d.toString()+"\n";
		}
		return message;
	}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		int shoe=4;
		Shoe shoeGame=new Shoe(shoe);
		//System.out.println(shoeGame.toString());
		

		String line;
		try{
			int i=0;
			 FileInputStream fileInputStream = new FileInputStream("src/shoe-file.txt");
		     InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		     
		     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		     StringBuffer stringBuffer = new StringBuffer();
		     while (((line = bufferedReader.readLine()) != null)&&(i<shoe)) {
		    	Deck aux=new Deck();
		    	aux.populateDeck(line);
		    	 shoeGame.addDeck(i, aux);
		    	 i++;
		     }
	     }catch(FileNotFoundException e){
	            e.printStackTrace();
	     }catch(IOException e){
	            e.printStackTrace();
	     } 
		System.out.println(shoeGame.toString());
		
		
		
		
	}*/

}
