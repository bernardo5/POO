package blackjack;

public interface ActionStrategy{
	/**
	 * The action strategy returns advisable commands based on the hard, soft and pair matrices
	 */
	
	 String[][] hard_advice = { {"h","h","h","h","h","h","h","h","h","h"},//5
								 {"h","h","h","h","h","h","h","h","h","h"},//6
								 {"h","h","h","h","h","h","h","h","h","h"},//7
								 {"h","h","h","h","h","h","h","h","h","h"},//8
								 {"h","2h","2h","2h","2h","h","h","h","h","h"},//9
								 {"2h","2h","2h","2h","2h","2h","2h","2h","h","h"},//10
								 {"2h","2h","2h","2h","2h","2h","2h","2h","2h","h"},//11
								 {"h","h","s","s","s","h","h","h","h","h"},//12h
								 {"s","s","s","s","s","h","h","h","h","h"},//13
								 {"s","s","s","s","s","h","h","h","h","h"},//14
								 {"s","s","s","s","s","h","h","h","uh","h"},//15
								 {"s","s","s","s","s","h","h","uh","uh","uh"},//16
								 {"s","s","s","s","s","s","s","s","s","s"},//17
								 {"s","s","s","s","s","s","s","s","s","s"},//18
								 {"s","s","s","s","s","s","s","s","s","s"},//19
								 {"s","s","s","s","s","s","s","s","s","s"},//2h0
								 {"s","s","s","s","s","s","s","s","s","s"} //2h1
		   };
			 // 2h   3   4   5   6   7   8   9   10  A
		String[][] soft_advice = {  {"h","h","h","2h","2h","h","h","h","h","h"},//13
									{"h","h","h","2h","2h","h","h","h","h","h"},//14
									{"h","h","2h","2h","2h","h","h","h","h","h"},//15
									{"h","h","2h","2h","2h","h","h","h","h","h"},//16
									{"h","2h","2h","2h","2h","h","h","h","h","h"},//17
									{"s","2s","2s","2s","2s","s","s","h","h","h"},//18
									{"s","s","s","s","s","s","s","s","s","s"},//19
									{"s","s","s","s","s","s","s","s","s","s"},//2h0
									{"s","s","s","s","s","s","s","s","s","s"} //2h1
									};
									// 2h   3   4   5   6   7   8   9   10  A
		String[][] pair_advice = { {"h","h","p","p","p","p","h","h","h","h"},//2h,2h
								   {"h","h","p","p","p","p","h","h","h","h"},//3,3
								   {"h","h","h","h","h","h","h","h","h","h"},//4,4
								   {"2h","2h","2h","2h","2h","2h","2h","2h","h","h"},//5,5
								   {"h","p","p","p","p","h","h","h","h","h"},//6,6
								   {"p","p","p","p","p","p","h","h","h","h"},//7,7
								   {"p","p","p","p","p","p","p","p","p","p"},//8,8
								   {"p","p","p","p","p","p","s","p","s","s"},//9,9
								   {"s","s","s","s","s","s","s","s","s","s"},//10,10
								   {"p","p","p","p","p","p","p","p","p","p"} //A,A
								};
		/**
		 * 
		 * @param hand - player hand
		 * @param card - dealer visible card
		 * @return - advisable command based on the matrices and dealer and player visible cards
		 */
	public String advice(Hand hand, Card card);
}
