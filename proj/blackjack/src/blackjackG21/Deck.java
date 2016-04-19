package blackjackG21;

import java.io.*;
import java.util.*;



public class Deck {
	Card[] list;
	
	public Deck(){
		list=new Card[52];
	}
	
	public void populateDeck(String message){
		int positionToAdd=0;
		String name;
		char suit;
		String[] arr = message.split(" ");    
	   	 for ( String card : arr) {
	   	       System.out.println(card);
	   	       
	   	    if(card.length()==2){/*all except 10's*/
				name=String.valueOf(card.charAt(0));
				suit=card.charAt(1);
			}else{
				name="10";
				suit=card.charAt(2);
			}
	   	    list[positionToAdd++]=new Card(name, suit);
	   	 }
		
	}	
	
	
	@Override
	public String toString() {
		return Arrays.toString(list);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Deck deck=new Deck();
			String line;
			
			try{
				 FileInputStream fileInputStream = new FileInputStream("src/shoe-file.txt");
			     InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			     
			     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			     StringBuffer stringBuffer = new StringBuffer();
			     while ((line = bufferedReader.readLine()) != null) {
			    	 deck.populateDeck(line);
			     }
		     }catch(FileNotFoundException e){
		            e.printStackTrace();
		     }catch(IOException e){
		            e.printStackTrace();
		     } 
			System.out.println(deck.toString());
	}

}
