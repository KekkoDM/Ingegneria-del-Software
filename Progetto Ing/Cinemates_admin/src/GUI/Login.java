package GUI;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;

import DAO.Amministratore_DAO;
import controller.Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	private JLabel error;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private Amministratore_DAO adminDAO;

	/** Create the frame. */
	public Login(Controller ctr) {
		inizializzaFrame(ctr);
		adminDAO = new Amministratore_DAO();
		setLocationRelativeTo(null);
	}
		
	private void inizializzaFrame(Controller ctr) {		
		setTitle("Cinemates");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 720, 590);
		contentPane = new JPanel();
		contentPane.setBackground(Color.decode("#201849"));;
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.WHITE, 100, true));
		panel.setBackground(Color.WHITE);
		panel.setBounds(132, 81, 450, 407);
		contentPane.add(panel);
		panel.setLayout(null);
		
		username = new JTextField();
		username.requestFocus();
		username.setForeground(Color.GRAY);
		username.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(64, 64, 64)));
		username.setFont(new Font("Arial", Font.PLAIN, 25));
		username.setBounds(98, 175, 260, 30);
		username.setColumns(10);
		panel.add(username);
		
		password = new JPasswordField();
		password.setEchoChar('•');
		password.setForeground(Color.GRAY);
		password.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(64, 64, 64)));
		password.setFont(new Font("Arial", Font.PLAIN, 25));
		password.setBounds(98, 264, 260, 30);
		panel.add(password);
		
		JLabel showPW = new JLabel("");
		showPW.setForeground(Color.GRAY);
		showPW.setHorizontalAlignment(SwingConstants.LEFT);
		showPW.setFont(new Font("Arial", Font.PLAIN, 15));
		showPW.setBackground(Color.GRAY);
		showPW.setBounds(98, 295, 260, 30);
		showPW.setVisible(false);
		panel.add(showPW);
		
		JButton pwbtn = new JButton("");
		pwbtn.setIcon(new ImageIcon(Login.class.getResource("/showPW.png")));
		pwbtn.setBounds(359, 264, 24, 24);
		pwbtn.setOpaque(false);
		pwbtn.setContentAreaFilled(false);
		pwbtn.setBorderPainted(false);
		panel.add(pwbtn);
		
		JButton loginbtn = new JButton("Accedi");
		loginbtn.setFont(new Font("Arial", Font.PLAIN, 25));
		loginbtn.setForeground(Color.WHITE);
		loginbtn.setBackground(Color.decode("#00A5FF"));
		loginbtn.setBounds(98, 356, 260, 35);
		loginbtn.setFocusPainted(false);
		panel.add(loginbtn);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/user.png")));
		lblNewLabel_1.setBounds(98, 132, 32, 32);
		panel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/lock.png")));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(98, 221, 32, 32);
		panel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Username");
		lblNewLabel_3.setForeground(Color.GRAY);
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_3.setBounds(140, 132, 80, 32);
		panel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Password");
		lblNewLabel_4.setForeground(Color.GRAY);
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_4.setBounds(140, 221, 80, 32);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(10, 11, 430, 55);
		panel.add(lblNewLabel_5);
		lblNewLabel_5.setIcon(new ImageIcon(Login.class.getResource("/dashboard_logo.png")));
		
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setFont(new Font("Arial", Font.PLAIN, 15));
		error.setHorizontalAlignment(SwingConstants.CENTER);
		error.setBounds(10, 77, 430, 44);
		panel.add(error);
		
		JLabel lblNewLabel = new JLabel("Cinemates Admin Panel");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 11, 694, 44);
		contentPane.add(lblNewLabel);
		
				
		// PRESSIONE TASTO MOSTRA PASSWORD
		pwbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showPW.isVisible()) {
					showPW.setVisible(false);
					pwbtn.setIcon(new ImageIcon(Login.class.getResource("/showPW.png")));
				}
				else {
					showPW.setVisible(true);
					pwbtn.setIcon(new ImageIcon(Login.class.getResource("/hidePW.png")));
				}
			}
		});
		
		
		// PRESSIONE TASTO INVIO USERNAME
		username.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login(ctr);
				}
			}
		});		
		
		
		// FOCUS USERNAME
		username.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				username.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.DARK_GRAY));
			}
		});
		
		
		// FOCUS PASSWORD
		password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				password.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.DARK_GRAY));
			}
		});
		
		
		// AGGIORNAMENTO LABEL MOSTRA PASSWORD
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				showPW.setText(password.getText());
			}
			
			// PRESSIONE TASTO INVIO PASSWORD
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login(ctr);
				} else if (!password.getText().equals("")) {
					password.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.DARK_GRAY));
				}
			}
		});
		
		
		// PRESSIONE TASTO ACCEDI
		loginbtn.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				login(ctr);
			}				
		});
	}
	
	
	// CONTROLLO CREDENZIALI
	private void login(Controller ctr) {
		if ((username.getText().equals("")) || (password.getText()).equals("")) {
			System.out.println("LOG: Campo/i credenziali vuoti");
			username.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.RED));
			password.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.RED));
			error.setText("I campi non possono essere vuoti");
		} else if (ctr.checkExists(username.getText(), password.getText(), adminDAO)) {
			Controller.adminDAO = adminDAO;
			Controller.admin.setAll(adminDAO.getDAO(Controller.admin));
			System.out.println("LOG: Credenziali corrette, accesso consentito");
			ctr.openDashboard();
		}
		else {
			System.out.println("LOG: Username o password incorretti");
			error.setText("Username o password incorretti");
			username.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.RED));
			password.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.RED));
		}
	}
}
