package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class Dashboard extends JFrame {

	private JPanel contentPane;

	/*** Create the frame. */
	public Dashboard(Controller ctr) {
		inizializzaFrame(ctr);
		setLocationRelativeTo(null);
	}
	
	private void inizializzaFrame(Controller ctr) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOperazioni = new JLabel("Operazioni");
		lblOperazioni.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperazioni.setForeground(Color.WHITE);
		lblOperazioni.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblOperazioni.setBounds(10, 11, 200, 36);
		contentPane.add(lblOperazioni);
		
		JButton logout = new JButton("Esci");
		logout.setBackground(SystemColor.menu);
		logout.setForeground(Color.DARK_GRAY);
		logout.setFont(new Font("Arial", Font.PLAIN, 25));
		logout.setBounds(10, 495, 200, 55);
		contentPane.add(logout);
		
		JButton review = new JButton("Segnalazioni");
		review.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		review.setForeground(Color.DARK_GRAY);
		review.setBackground(SystemColor.menu);
		review.setFont(new Font("Arial", Font.PLAIN, 25));
		
		review.setBounds(10, 58, 200, 55);
		contentPane.add(review);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Dashboard.class.getResource("/lateral_bar.png")));
		lblNewLabel.setBounds(0, 0, 220, 561);
		contentPane.add(lblNewLabel);
		
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctr.showPopup("Sei sicuro di voler effettuare il logout?");
			}
		});
	}
}
