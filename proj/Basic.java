package blackjack;

public class Basic implements IStrategy{
											// 2   3   4   5   6   7   8   9   10  A
	private final String[][] hard_advice = { {"h","h","h","h","h","h","h","h","h","h"},//5
											 {"h","h","h","h","h","h","h","h","h","h"},//6
											 {"h","h","h","h","h","h","h","h","h","h"},//7
											 {"h","h","h","h","h","h","h","h","h","h"},//8
											 {"h","2","2","2","2","h","h","h","h","h"},//9
											 {"2","2","2","2","2","2","2","2","h","h"},//10
											 {"2","2","2","2","2","2","2","2","2","h"},//11
											 {"h","h","s","s","s","h","h","h","h","h"},//12
											 {"s","s","s","s","s","h","h","h","h","h"},//13
											 {"s","s","s","s","s","h","h","h","h","h"},//14
											 {"s","s","s","s","s","h","h","h","u","h"},//15
											 {"s","s","s","s","s","h","h","u","u","u"},//16
											 {"s","s","s","s","s","s","s","s","s","s"},//17
											 {"s","s","s","s","s","s","s","s","s","s"},//18
											 {"s","s","s","s","s","s","s","s","s","s"},//19
											 {"s","s","s","s","s","s","s","s","s","s"},//20
											 {"s","s","s","s","s","s","s","s","s","s"} //21
										   };
											 // 2   3   4   5   6   7   8   9   10  A
	private final String[][] soft_advice = {  {"h","h","h","2","2","h","h","h","h","h"},//13
											  {"h","h","h","2","2","h","h","h","h","h"},//14
											  {"h","h","2","2","2","h","h","h","h","h"},//15
											  {"h","h","2","2","2","h","h","h","h","h"},//16
											  {"h","2","2","2","2","h","h","h","h","h"},//17
											  {"s","2","2","2","2","s","s","h","h","h"},//18
											  {"s","s","s","s","s","s","s","s","s","s"},//19
											  {"s","s","s","s","s","s","s","s","s","s"},//20
											  {"s","s","s","s","s","s","s","s","s","s"} //21
											};
	 										// 2   3   4   5   6   7   8   9   10  A
	private final String[][] pair_advice = { {"h","h","p","p","p","p","h","h","h","h"},//2,2
											 {"h","h","p","p","p","p","h","h","h","h"},//3,3
											 {"h","h","h","h","h","h","h","h","h","h"},//4,4
											 {"2","2","2","2","2","2","2","2","h","h"},//5,5
											 {"h","p","p","p","p","h","h","h","h","h"},//6,6
											 {"p","p","p","p","p","p","h","h","h","h"},//7,7
											 {"p","p","p","p","p","p","p","p","p","p"},//8,8
											 {"p","p","p","p","p","p","s","p","s","s"},//9,9
											 {"s","s","s","s","s","s","s","s","s","s"},//10,10
											 {"p","p","p","p","p","p","p","p","p","p"} //A,A
											};
	
	@Override
	public String advice(Hand player_hand,Card dealer_card) {
		if (player_hand.getSizeofCards() == 2 && player_hand.getCards().getFirst().getRank()==player_hand.getCards().getLast().getRank()) 
			return pair_advice[player_hand.getCards().getFirst().getValue()-2][dealer_card.getValue()-2];
		else if (player_hand.numberAces()>0) return soft_advice[player_hand.getPoints()-13][dealer_card.getValue()-2];
		else return hard_advice[player_hand.getPoints()-5][dealer_card.getValue()-2];
		
	}

}
