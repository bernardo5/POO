package blackjackG21;

public class Dealer extends Player{

	public Dealer(Card card1, Card card2) {
		super(card1, card2);
	}

	/*public void stand(Hand h){
		if((h.getTotal())>=17){
			//do something
			System.out.println("You will now stand");
		}
	}*/
	
	public String showHands(){
		/*Dealer only has one hand and shows all cards except the first*/
		String game=new String();
		Hand h=hands.getFirst();
		int i=0;
		for(Card c:h.hand){
			if(i!=0)game+=c.toString();
			i++;
		}
		return game;
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dealer player=new Dealer(new Card("Q",'S', null), new Card("A",'S', null));
		player.hit(new Card("10",'S', null), 0);
		System.out.println(player.showHands());
	}*/

}
