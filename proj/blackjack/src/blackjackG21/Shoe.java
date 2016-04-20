package blackjackG21;

import java.io.*;
import java.util.*;

public class Shoe {
	private Card[] sequence;
	private int nbNextCard;
	
	public Shoe(int numberDecks){
		sequence=new Card[numberDecks*52];
		nbNextCard=0;
	}
	
	public void addCard(int position, Card c){
		sequence[position]=c;
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
			   	    sequence[positionToAdd++]=new Card(name, suit);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int shoe=4;
		Shoe shoeGame=new Shoe(shoe);
		shoeGame.populateShoeFromFile("src/shoe-file.txt", shoe);
		System.out.println(shoeGame.toString());
		


		
		
		
	}

}
