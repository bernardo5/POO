package graphDesign;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class graph {

	private JFrame frame;
	private JTextField textField;

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
		initialize();
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
		
		JButton btnHit = new JButton("Hit");
		btnHit.setBounds(535, 175, 89, 23);
		frame.getContentPane().add(btnHit);
		
		JButton btnNewButton = new JButton("Deal");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(535, 499, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Bet");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(535, 446, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		textField = new JTextField();
		textField.setBounds(535, 468, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnStand = new JButton("Stand");
		btnStand.setBounds(535, 209, 89, 23);
		frame.getContentPane().add(btnStand);
		
		JButton btnSplit = new JButton("Split");
		btnSplit.setBounds(535, 243, 89, 23);
		frame.getContentPane().add(btnSplit);
		
		JButton btnDouble = new JButton("Double");
		btnDouble.setBounds(535, 277, 89, 23);
		frame.getContentPane().add(btnDouble);
		
		JButton btnInsurance = new JButton("Insurance");
		btnInsurance.setBounds(535, 311, 89, 23);
		frame.getContentPane().add(btnInsurance);
		
		JButton btnSurrender = new JButton("surrender");
		btnSurrender.setBounds(535, 345, 89, 23);
		frame.getContentPane().add(btnSurrender);
		
		JButton btnNewButton_2 = new JButton("Advice");
		btnNewButton_2.setBounds(535, 412, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblCard1 = new JLabel("");
		lblCard1.setBackground(Color.LIGHT_GRAY);
		lblCard1.setOpaque(true);
		Image card1;
		try {
			card1 = ImageIO.read(getClass().getResourceAsStream("/2c.gif"));
			lblCard1.setIcon(new ImageIcon(card1));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		lblCard1.setBounds(34, 382, 73, 97);
		frame.getContentPane().add(lblCard1);
		
		JLabel lblCard2 = new JLabel("");
		lblCard2.setBackground(Color.LIGHT_GRAY);
		lblCard2.setOpaque(true);
		Image card2;
		try {
			card2 = ImageIO.read(getClass().getResourceAsStream("/2d.gif"));
			lblCard2.setIcon(new ImageIcon(card2));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		lblCard2.setBounds(57, 382, 73, 97);
		frame.getContentPane().add(lblCard2);
		
		JLabel lblPlayerHand = new JLabel("Player Hand");
		lblPlayerHand.setForeground(Color.WHITE);
		lblPlayerHand.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblPlayerHand.setBounds(34, 490, 205, 43);
		frame.getContentPane().add(lblPlayerHand);
		
		JLabel lblDealerHand = new JLabel("Dealer Hand");
		lblDealerHand.setForeground(Color.WHITE);
		lblDealerHand.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblDealerHand.setBounds(34, 71, 205, 43);
		frame.getContentPane().add(lblDealerHand);
		
		JLabel lblDealerCard1 = new JLabel("");
		lblDealerCard1.setOpaque(true);
		lblDealerCard1.setBackground(Color.LIGHT_GRAY);
		lblDealerCard1.setBounds(34, 125, 73, 97);
		frame.getContentPane().add(lblDealerCard1);
		
		JLabel lblDealerCard2 = new JLabel("");
		lblDealerCard2.setOpaque(true);
		lblDealerCard2.setBackground(Color.LIGHT_GRAY);
		lblDealerCard2.setBounds(116, 125, 73, 97);
		frame.getContentPane().add(lblDealerCard2);
		
		JLabel lblCard3 = new JLabel("");
		lblCard3.setOpaque(true);
		lblCard3.setBackground(Color.LIGHT_GRAY);
		lblCard3.setBounds(77, 382, 73, 97);
		Image card3;
		try {
			card3 = ImageIO.read(getClass().getResourceAsStream("/2d.gif"));
			lblCard3.setIcon(new ImageIcon(card3));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.getContentPane().add(lblCard3);
		
		JLabel lblCard4 = new JLabel("");
		lblCard4.setOpaque(true);
		lblCard4.setBackground(Color.LIGHT_GRAY);
		lblCard4.setBounds(92, 382, 73, 97);
		Image card4;
		try {
			card4 = ImageIO.read(getClass().getResourceAsStream("/2d.gif"));
			lblCard4.setIcon(new ImageIcon(card4));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.getContentPane().add(lblCard4);
		
		JLabel label = new JLabel("");
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBounds(202, 382, 73, 97);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setOpaque(true);
		label_1.setBackground(Color.LIGHT_GRAY);
		label_1.setBounds(225, 382, 73, 97);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setOpaque(true);
		label_2.setBackground(Color.LIGHT_GRAY);
		label_2.setBounds(245, 382, 73, 97);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("");
		label_3.setOpaque(true);
		label_3.setBackground(Color.LIGHT_GRAY);
		label_3.setBounds(260, 382, 73, 97);
		frame.getContentPane().add(label_3);
	}
}
