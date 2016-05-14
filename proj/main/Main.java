package main;

import blackjack.Debug;
import blackjack.Game;
import blackjack.Simulation;

public class Main {
	public static void main(String[] args){
		if(args.length<6)System.exit(1);
		if(Integer.parseInt(args[1])<1)System.exit(3);//minbet>1
		if(Integer.parseInt(args[2])<10*Integer.parseInt(args[1]) ||Integer.parseInt(args[2])>20*Integer.parseInt(args[1])) System.exit(4);//10*minbet<=maxbet<=20*minbet
		if(Integer.parseInt(args[3])<50*Integer.parseInt(args[1]))System.exit(5);//balance>=50*minbet
		//interactive mode
		if(args[0].equals("-i")){
			if(args.length != 6){
				System.out.println("Usage for interactive mode: -i min-bet max-bet balance shoe shuffle");
				System.exit(0);
			}
			if(Integer.parseInt(args[4])<4||Integer.parseInt(args[4])>8)System.exit(6);
			int shuffle=Integer.parseInt(args[5]);
			if(shuffle<10||shuffle>100)System.exit(7);
			//construct game for interactive mode
			Game game=new Game(Integer.parseInt(args[4]), shuffle, Integer.parseInt(args[4]), Integer.parseInt(args[3]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
			game.play(args[3]);
		}
		//debug mode
		else if(args[0].equals("-d")){
			if(args.length != 6){
				System.out.println("Usage for debug mode: -d min-bet max-bet balance shoe-file cmd-file");
				System.exit(0);
			}
			//construct game for debug mode
			Debug debug=new Debug(args[4], Integer.parseInt(args[3]), args[5], Integer.parseInt(args[1]),Integer.parseInt(args[2]));
			debug.play(args[3]);
		}
		//simulation mode
		else if(args[0].equals("-s")){
			if(args.length != 8){
				System.out.println("Usage for simulation mode: -s min-bet max-bet balance shuffle s-number strategy");
				System.exit(0);
			}
			//construct game for simulation mode
			Simulation simulation=new Simulation(Integer.parseInt(args[3]), args[7], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[1]),Integer.parseInt(args[2]));	
			simulation.play(args[3], args[6]);
		}else{
			System.out.println("Bad input parameters");
			System.exit(2);
		}
	}
}
