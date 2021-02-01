package GUI;

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
import javax.swing.JPasswordField;
import javax.swing.border.MatteBorder;

public class Dashboard extends JFrame {
	
	private JPanel blankPanel = new JPanel();
	private JPanel reviewsPanel = new JPanel();
	private JPanel settingsPanel = new JPanel();
	private JPanel contentPane;
	private JPasswordField newPw;
	private JPasswordField confNewPw;
	private JLabel error;
	private JLabel icon;

	
	public void blankPanelStart(Controller ctr) {
		blankPanel = new JPanel(); 
		blankPanel.setBackground(Color.WHITE);
		blankPanel.setBounds(230, 11, 684, 539);
		blankPanel.setLayout(null);
		contentPane.add(blankPanel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Dashboard.class.getResource("/select.png")));
		lblNewLabel_1.setBounds(214, 77, 256, 256);
		blankPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Benvenuto nella dashboard!");
		lblNewLabel_2.setForeground(Color.GRAY);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 25));
		lblNewLabel_2.setBounds(10, 344, 664, 46);
		blankPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Per iniziare, seleziona un'operazione dal menu laterale");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setForeground(Color.GRAY);
		lblNewLabel_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_2_1.setBounds(10, 391, 664, 46);
		blankPanel.add(lblNewLabel_2_1);
	}
	
	// panello recensioni segnalate
	public void reviewsPanelStart(Controller ctr) {
		reviewsPanel = new JPanel();
		reviewsPanel.setBackground(Color.WHITE);
		reviewsPanel.setBounds(230, 11, 684, 539);
		reviewsPanel.setVisible(true);
		contentPane.add(reviewsPanel);
		
		JLabel lblNewLabel_3_1 = new JLabel("Recensioni segnalate");
		lblNewLabel_3_1.setForeground(Color.GRAY);
		lblNewLabel_3_1.setFont(new Font("Arial", Font.BOLD, 25));
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1.setBounds(10, 11, 664, 30);
		reviewsPanel.add(lblNewLabel_3_1);
		
		
	}
	
	// pannello impostazioni
	public void settingsPanelStart(Controller ctr) {
		settingsPanel = new JPanel();
		settingsPanel.setBackground(Color.WHITE);
		settingsPanel.setBounds(230, 11, 684, 539);
		settingsPanel.setLayout(null);
		settingsPanel.setVisible(true);
		contentPane.add(settingsPanel);
		
		JLabel lblNewLabel_3 = new JLabel("Impostazioni");
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 25));
		lblNewLabel_3.setForeground(Color.GRAY);
		lblNewLabel_3.setBounds(10, 11, 664, 30);
		settingsPanel.add(lblNewLabel_3);
		
		newPw = new JPasswordField();
		newPw.setForeground(Color.GRAY);
		newPw.setFont(new Font("Arial", Font.PLAIN, 25));
		newPw.setEchoChar('•');
		newPw.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.DARK_GRAY));
		newPw.setBounds(211, 285, 260, 30);
		settingsPanel.add(newPw);
		
		JLabel lblNewLabel_4 = new JLabel("Nuova password");
		lblNewLabel_4.setForeground(Color.GRAY);
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_4.setBounds(211, 242, 260, 32);
		settingsPanel.add(lblNewLabel_4);
		
		confNewPw = new JPasswordField();
		confNewPw.setForeground(Color.GRAY);
		confNewPw.setFont(new Font("Arial", Font.PLAIN, 25));
		confNewPw.setEchoChar('•');
		confNewPw.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.DARK_GRAY));
		confNewPw.setBounds(211, 369, 260, 30);
		settingsPanel.add(confNewPw);
		
		JLabel lblNewLabel_4_1 = new JLabel("Conferma nuova password");
		lblNewLabel_4_1.setForeground(Color.GRAY);
		lblNewLabel_4_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_4_1.setBounds(211, 326, 260, 32);
		settingsPanel.add(lblNewLabel_4_1);
		
		JButton aggiorna = new JButton("Aggiorna password");
		aggiorna.setForeground(Color.WHITE);
		aggiorna.setFont(new Font("Arial", Font.PLAIN, 25));
		aggiorna.setBorder(null);
		aggiorna.setBackground(new Color(0, 165, 255));
		aggiorna.setBounds(210, 426, 260, 35);
		settingsPanel.add(aggiorna);
		
		icon = new JLabel();
		icon.setIcon(new ImageIcon(Dashboard.class.getResource("/userSettings.png")));
		icon.setBounds(277, 75, 128, 128);
		settingsPanel.add(icon);
		
		error = new JLabel();
		error.setHorizontalAlignment(SwingConstants.CENTER);
		error.setForeground(Color.RED);
		error.setFont(new Font("Arial", Font.PLAIN, 15));
		error.setBounds(10, 472, 664, 30);
		settingsPanel.add(error);
		
		// pressione tasto aggiorna password
		aggiorna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				if(confNewPw.equals(newPw)) {
					ctr.showPopup("Le Password non combaciano!");
					
				}else if(Controller.admin.aggiornaPassword(ctr.admin.getPassword(),newPw.getText() )){
					System.out.println("TRUE");
					ctr.admin.setPassword(newPw.getText());
					ctr.adminDAO.updatePassword(newPw.getText());
					ctr.showPopup("La password é stata aggiornata correttamente!");
				}
			}
		});
	}
	
	
	/*** Create the frame. */
	public Dashboard(Controller ctr) {
		setTitle("Dashboard admin");
		inizializzaFrame(ctr);
		setLocationRelativeTo(null);
		blankPanelStart(ctr);
	}
	
	private void inizializzaFrame(Controller ctr) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOperazioni_1 = new JLabel("");
		lblOperazioni_1.setIcon(new ImageIcon(Dashboard.class.getResource("/dashboard_logo.png")));
		lblOperazioni_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperazioni_1.setForeground(Color.WHITE);
		lblOperazioni_1.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblOperazioni_1.setBounds(10, 11, 200, 55);
		contentPane.add(lblOperazioni_1);
		
		JLabel lblOperazioni = new JLabel("Operazioni");
		lblOperazioni.setHorizontalAlignment(SwingConstants.LEFT);
		lblOperazioni.setForeground(Color.WHITE);
		lblOperazioni.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblOperazioni.setBounds(10, 100, 200, 36);
		contentPane.add(lblOperazioni);
		
		JButton review = new JButton("Recensioni segnalate");
		review.setIcon(new ImageIcon(Dashboard.class.getResource("/segnalazioni_icon.png")));
		review.setForeground(Color.WHITE);
		review.setBackground(Color.decode("#201849"));
		review.setFocusPainted(false);
		review.setVerticalTextPosition(SwingConstants.BOTTOM);
		review.setHorizontalTextPosition(SwingConstants.CENTER);
		review.setFont(new Font("Arial", Font.PLAIN, 18));
		review.setBounds(0, 147, 220, 67);
		contentPane.add(review);
		
		JButton settings = new JButton("Impostazioni");
		settings.setIcon(new ImageIcon(Dashboard.class.getResource("/settings.png")));
		settings.setVerticalTextPosition(SwingConstants.BOTTOM);
		settings.setHorizontalTextPosition(SwingConstants.CENTER);
		settings.setForeground(Color.WHITE);
		settings.setFocusPainted(false);
		settings.setFont(new Font("Arial", Font.PLAIN, 18));
		settings.setBackground(Color.decode("#201849"));
		settings.setBounds(0, 214, 220, 67);
		contentPane.add(settings);
		
		JButton logout = new JButton("Logout");
		logout.setIcon(new ImageIcon(Dashboard.class.getResource("/logout.png")));
		logout.setBackground(Color.decode("#201849"));
		logout.setForeground(Color.WHITE);
		logout.setFocusPainted(false);
		logout.setFont(new Font("Arial", Font.PLAIN, 18));
		logout.setBounds(0, 483, 220, 67);
		logout.setVerticalTextPosition(SwingConstants.BOTTOM);
		logout.setHorizontalTextPosition(SwingConstants.CENTER);
		contentPane.add(logout);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Dashboard.class.getResource("/lateral_bar.png")));
		lblNewLabel.setBounds(0, 0, 220, 561);
		contentPane.add(lblNewLabel);
		
		// pressione tasto impostazioni
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blankPanel.setVisible(false);
				reviewsPanel.setVisible(false);
				settingsPanelStart(ctr);
				review.setBackground(Color.decode("#201849"));
				settings.setBackground(Color.decode("#00A5FF"));	
			}
		});
		
		// pressione tasto recensioni segnalate
		review.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blankPanel.setVisible(false);
				settingsPanel.setVisible(false);
				reviewsPanelStart(ctr);
				settings.setBackground(Color.decode("#201849"));
				review.setBackground(Color.decode("#00A5FF"));				
			}
		});
		
		// pressione tasto logout
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctr.showPopup("Sei sicuro di voler effettuare il logout?");
			}
		});
	}	
}
