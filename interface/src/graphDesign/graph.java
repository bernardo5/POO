package graphDesign;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import blackjack.Card;
import blackjack.Game;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import javax.swing.JPanel;

public class graph{

	private JFrame frame;
	private JTextField textField;
	private HashMap<String, Image> cards;
	boolean action;
	Game game;
	JPanel panel, panel_1;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					graph window = new graph();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public graph() {
		action=false;
		initialize();
		try {
			cards=new HashMap<String, Image>(53);
			cards.put("2C", ImageIO.read(getClass().getResourceAsStream("/2c.gif")));
			cards.put("2D", ImageIO.read(getClass().getResourceAsStream("/2d.gif")));
			cards.put("2S", ImageIO.read(getClass().getResourceAsStream("/2s.gif")));
			cards.put("2H", ImageIO.read(getClass().getResourceAsStream("/2h.gif")));
			cards.put("3C", ImageIO.read(getClass().getResourceAsStream("/3c.gif")));
			cards.put("3D", ImageIO.read(getClass().getResourceAsStream("/3d.gif")));
			cards.put("3S", ImageIO.read(getClass().getResourceAsStream("/3s.gif")));
			cards.put("3H", ImageIO.read(getClass().getResourceAsStream("/3h.gif")));
			cards.put("4C", ImageIO.read(getClass().getResourceAsStream("/4c.gif")));
			cards.put("4D", ImageIO.read(getClass().getResourceAsStream("/4d.gif")));
			cards.put("4S", ImageIO.read(getClass().getResourceAsStream("/4s.gif")));
			cards.put("4H", ImageIO.read(getClass().getResourceAsStream("/4h.gif")));
			cards.put("5C", ImageIO.read(getClass().getResourceAsStream("/5c.gif")));
			cards.put("5D", ImageIO.read(getClass().getResourceAsStream("/5d.gif")));
			cards.put("5S", ImageIO.read(getClass().getResourceAsStream("/5s.gif")));
			cards.put("5H", ImageIO.read(getClass().getResourceAsStream("/5h.gif")));
			cards.put("6C", ImageIO.read(getClass().getResourceAsStream("/6c.gif")));
			cards.put("6D", ImageIO.read(getClass().getResourceAsStream("/6d.gif")));
			cards.put("6S", ImageIO.read(getClass().getResourceAsStream("/6s.gif")));
			cards.put("6H", ImageIO.read(getClass().getResourceAsStream("/6h.gif")));
			cards.put("7C", ImageIO.read(getClass().getResourceAsStream("/7c.gif")));
			cards.put("7D", ImageIO.read(getClass().getResourceAsStream("/7d.gif")));
			cards.put("7S", ImageIO.read(getClass().getResourceAsStream("/7s.gif")));
			cards.put("7H", ImageIO.read(getClass().getResourceAsStream("/7h.gif")));
			cards.put("8C", ImageIO.read(getClass().getResourceAsStream("/8c.gif")));
			cards.put("8D", ImageIO.read(getClass().getResourceAsStream("/8d.gif")));
			cards.put("8S", ImageIO.read(getClass().getResourceAsStream("/8s.gif")));
			cards.put("8H", ImageIO.read(getClass().getResourceAsStream("/8h.gif")));
			cards.put("9C", ImageIO.read(getClass().getResourceAsStream("/9c.gif")));
			cards.put("9D", ImageIO.read(getClass().getResourceAsStream("/9d.gif")));
			cards.put("9S", ImageIO.read(getClass().getResourceAsStream("/9s.gif")));
			cards.put("9H", ImageIO.read(getClass().getResourceAsStream("/9h.gif")));
			cards.put("10C", ImageIO.read(getClass().getResourceAsStream("/tc.gif")));
			cards.put("10D", ImageIO.read(getClass().getResourceAsStream("/td.gif")));
			cards.put("10S", ImageIO.read(getClass().getResourceAsStream("/ts.gif")));
			cards.put("10H", ImageIO.read(getClass().getResourceAsStream("/th.gif")));
			cards.put("JC", ImageIO.read(getClass().getResourceAsStream("/jc.gif")));
			cards.put("JD", ImageIO.read(getClass().getResourceAsStream("/jd.gif")));
			cards.put("JS", ImageIO.read(getClass().getResourceAsStream("/js.gif")));
			cards.put("JH", ImageIO.read(getClass().getResourceAsStream("/jh.gif")));
			cards.put("QC", ImageIO.read(getClass().getResourceAsStream("/qc.gif")));
			cards.put("QD", ImageIO.read(getClass().getResourceAsStream("/qd.gif")));
			cards.put("QS", ImageIO.read(getClass().getResourceAsStream("/qs.gif")));
			cards.put("QH", ImageIO.read(getClass().getResourceAsStream("/qh.gif")));
			cards.put("KC", ImageIO.read(getClass().getResourceAsStream("/kc.gif")));
			cards.put("KD", ImageIO.read(getClass().getResourceAsStream("/kd.gif")));
			cards.put("KS", ImageIO.read(getClass().getResourceAsStream("/ks.gif")));
			cards.put("KH", ImageIO.read(getClass().getResourceAsStream("/kh.gif")));
			cards.put("AC", ImageIO.read(getClass().getResourceAsStream("/ac.gif")));
			cards.put("AD", ImageIO.read(getClass().getResourceAsStream("/ad.gif")));
			cards.put("AS", ImageIO.read(getClass().getResourceAsStream("/as.gif")));
			cards.put("AH", ImageIO.read(getClass().getResourceAsStream("/ah.gif")));
			cards.put("hidden", ImageIO.read(getClass().getResourceAsStream("/b.gif")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String minBt=new String(); 
		/*int minBet=0;
		try{
			Integer.parseInt(minBt);
		}catch()
		while()*/
		boolean parameters=true;
		while(parameters){
			JTextField minBetF = new JTextField(5);
			JTextField maxBetF = new JTextField(5);
			JTextField balanceF= new JTextField(5);
			JTextField nDecksF = new JTextField(5);
			JTextField shufflepercentageF = new JTextField(5);
			
			JPanel P = new JPanel();
			P.add(Box.createHorizontalStrut(15));
			P.add(new JLabel("Minimum Bet:"));
			P.add(minBetF);
			P.add(new JLabel("Maximum Bet:"));
			P.add(maxBetF);
			P.add(new JLabel("Player initial balance:"));
			P.add(balanceF);
			P.add(new JLabel("Number of Decks:"));
			P.add(nDecksF);
			P.add(new JLabel("Shuffle Percentage: "));
			P.add(shufflepercentageF);
			
			int r = JOptionPane.showConfirmDialog(null, P, "Enter initial parameters",JOptionPane.OK_CANCEL_OPTION);
			
			if(r== JOptionPane.OK_OPTION){
				try{
					int minBet=Integer.parseInt(minBetF.getText());
					int maxBet=Integer.parseInt(maxBetF.getText());
					int balance=Integer.parseInt(balanceF.getText());
					int nDecks=Integer.parseInt(nDecksF.getText());
					int shufflePercentage=Integer.parseInt(shufflepercentageF.getText());
					parameters=false;
					//System.out.println("Variables: "+minBet+" "+maxBet+" "+balance+" "+nDecks+" "+shufflePercentage+" ");
					
					game=new Game(shufflePercentage, nDecks, balance,minBet,maxBet);
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(frame,
							"At least one variable is not an integer!!! Try again :(");
				}
				
			
			}else{
				System.exit(1);
			}
		}
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground( new Color(0,100,0) );
		frame.getContentPane().setLayout(null);
		
		
		panel = new JPanel();
		panel.setBounds(10, 358, 514, 130);
		panel.setBackground(new Color(0,100,0));
		
		
		panel_1 = new JPanel();
		panel_1.setBounds(10, 118, 514, 114);
		panel_1.setBackground(new Color(0,100,0));
		
		JButton btnStand = new JButton("Stand");
		JButton btnSplit = new JButton("Split");
		JButton btnDouble = new JButton("Double");
		JButton btnInsurance = new JButton("Insurance");
		JButton btnSurrender = new JButton("Surrender");
		JButton btnDeal = new JButton("Deal");
		JButton btnBet = new JButton("Bet");
		
		JButton btnHit = new JButton("Hit");
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(game.hitAction()){
					JOptionPane.showMessageDialog(frame,
							"Your hand busted");
					if(game.playerDidNotBust()){
						if(game.DBlackjack()) game.CheckAndPayIns();
						
						game.DHit();
						game.SetResults();
						
					}
					btnHit.setEnabled(false);
					btnStand.setEnabled(false);
					btnSplit.setEnabled(false);
					btnDouble.setEnabled(false);
					btnInsurance.setEnabled(false);
					btnSurrender.setEnabled(false);
					btnDeal.setEnabled(false);
					btnBet.setEnabled(true);
					ShowDealerCards(true);
					action=false;
				}else{
					ShowGameCards();
					btnDouble.setEnabled(false);
					btnInsurance.setEnabled(false);
					btnSurrender.setEnabled(false);
				}
				
				
			}
		});
		btnHit.setBounds(535, 175, 89, 23);
		btnHit.setEnabled(false);
		frame.getContentPane().add(btnHit);
		
		
		btnDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(game.dealAction()){
					ShowGameCards();
					game.dealWithBlackJack();
					btnHit.setEnabled(false);
					btnStand.setEnabled(false);
					btnSplit.setEnabled(false);
					btnDouble.setEnabled(false);
					btnInsurance.setEnabled(false);
					btnSurrender.setEnabled(false);
					btnDeal.setEnabled(false);
					btnBet.setEnabled(true);
					ShowDealerCards(true);
				}else{
					ShowGameCards();
					btnHit.setEnabled(true);
					btnStand.setEnabled(true);
					btnSplit.setEnabled(true);
					btnDouble.setEnabled(true);
					btnInsurance.setEnabled(true);
					btnSurrender.setEnabled(true);
					btnDeal.setEnabled(false);
				}
				
				game.setBet_deal(0);
			}
		});
		btnDeal.setBounds(535, 499, 89, 23);
		btnDeal.setEnabled(false);
		frame.getContentPane().add(btnDeal);
		
		
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.betAction(textField.getText());
				btnDeal.setEnabled(true);
				btnBet.setEnabled(false);
				DeleteHands();
				action=true;
			}
		});
		btnBet.setBounds(535, 446, 89, 23);
		frame.getContentPane().add(btnBet);
		
		textField = new JTextField();
		textField.setBounds(535, 468, 89, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.standAction()){
					if(game.playerDidNotBust()){
						if(game.DBlackjack()) game.CheckAndPayIns();
						
						game.DHit();
						ShowDealerCards(true);
						action=false;
						game.SetResults();
					}
					btnHit.setEnabled(false);
					btnStand.setEnabled(false);
					btnSplit.setEnabled(false);
					btnDouble.setEnabled(false);
					btnInsurance.setEnabled(false);
					btnSurrender.setEnabled(false);
					btnDeal.setEnabled(false);
					btnBet.setEnabled(true);
					
				}
				
			}
		});
		btnStand.setBounds(535, 209, 89, 23);
		btnStand.setEnabled(false);
		frame.getContentPane().add(btnStand);
		
		
		btnSplit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.splitAction();
			}
		});
		btnSplit.setEnabled(false);
		btnSplit.setBounds(535, 243, 89, 23);
		frame.getContentPane().add(btnSplit);
		
		
		btnDouble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.doubleDownAction();
			}
		});
		btnDouble.setBounds(535, 277, 89, 23);
		btnDouble.setEnabled(false);
		frame.getContentPane().add(btnDouble);
		
		
		btnInsurance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.insureAction();
			}
		});
		btnInsurance.setBounds(535, 311, 89, 23);
		btnInsurance.setEnabled(false);
		frame.getContentPane().add(btnInsurance);
		
		
		btnSurrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.surrenderAction();
			}
		});
		btnSurrender.setBounds(535, 345, 89, 23);
		btnSurrender.setEnabled(false);
		frame.getContentPane().add(btnSurrender);
		
		JButton btnNewButton_2 = new JButton("Advice");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						game.actionAdvices(action));
			}
		});
		btnNewButton_2.setBounds(535, 412, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		//lblCard1 = new JLabel("");
		/*Image card1;
		try {
			card1 = ImageIO.read(getClass().getResourceAsStream("/2c.gif"));
			lblCard1.setIcon(new ImageIcon(card1));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		//lblCard1.setBounds(10, 380, 73, 97);
		//frame.getContentPane().add(lblCard1);
		
		//lblCard2 = new JLabel("");
		/*Image card2;
		try {
			card2 = ImageIO.read(getClass().getResourceAsStream("/2d.gif"));
			lblCard2.setIcon(new ImageIcon(card2));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		//lblCard2.setBounds(80, 380, 73, 97);
		//frame.getContentPane().add(lblCard2);
		
		JLabel lblPlayerHand = new JLabel("Player");
		lblPlayerHand.setForeground(Color.WHITE);
		lblPlayerHand.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblPlayerHand.setBounds(34, 490, 205, 43);
		frame.getContentPane().add(lblPlayerHand);
		
		JLabel lblDealerHand = new JLabel("Dealer");
		lblDealerHand.setForeground(Color.WHITE);
		lblDealerHand.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblDealerHand.setBounds(10, 74, 205, 43);
		frame.getContentPane().add(lblDealerHand);
		
		
		
		
		
		
		
		JLabel lblPlayingHand = new JLabel("Playing Hand:");
		lblPlayingHand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPlayingHand.setForeground(Color.WHITE);
		lblPlayingHand.setBounds(210, 503, 103, 25);
		frame.getContentPane().add(lblPlayingHand);
		
		JLabel handnumber = new JLabel("");
		handnumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		handnumber.setForeground(Color.WHITE);
		handnumber.setBounds(309, 508, 35, 20);
		frame.getContentPane().add(handnumber);
		
		frame.getContentPane().add(panel);
		frame.getContentPane().add(panel_1);

	
	} 
	
	public void ShowGameCards(){
		DeleteHands();
		for(Card c:game.getPlayer().getCurrentHand().getCards()){
			JLabel lblCard = new JLabel("");
			lblCard.setIcon(new ImageIcon(cards.get(c.toString())));
			panel.add(lblCard);
			//System.out.println("Entrou no for do jogador");
		}
		panel.revalidate();
		panel.repaint();
		int i=1;
		for(Card c:game.getDealer().getCurrentHand().getCards()){
			JLabel lblCard = new JLabel("");
			if(i!=2)lblCard.setIcon(new ImageIcon(cards.get(c.toString())));
			else lblCard.setIcon(new ImageIcon(cards.get("hidden")));
			panel_1.add(lblCard);
			i++;
		}
		panel_1.revalidate();
		panel_1.repaint();
	}
	
	public void DeleteHands(){
		panel.removeAll();
		panel_1.removeAll();
		panel_1.revalidate();
		panel_1.repaint();
		panel.revalidate();
		panel.repaint();
	}
	
	public void ShowDealerCards(boolean end){
		panel_1.removeAll();
		for(Card c:game.getDealer().getCurrentHand().getCards()){
			JLabel lblCard = new JLabel("");
			lblCard.setIcon(new ImageIcon(cards.get(c.toString())));
			panel_1.add(lblCard);
			panel_1.revalidate();
			panel_1.repaint();
		}
	}
}
