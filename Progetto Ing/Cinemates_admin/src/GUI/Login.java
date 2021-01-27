package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.geom.RoundRectangle2D;

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

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private Amministratore_DAO adminDAO;

	public Login(Controller ctr) {
		inizializzaFrame(ctr);
		adminDAO= new Amministratore_DAO();
		setLocationRelativeTo(null);
	}
		
	private void inizializzaFrame(Controller ctr) {		
		setTitle("Cinemates");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 860, 590);
		contentPane = new JPanel();
		contentPane.setBackground(Color.decode("#201849"));;
		setContentPane(contentPane);
		contentPane.setLayout(null);
						
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.WHITE, 100, true));
		panel.setBackground(Color.WHITE);
		panel.setBounds(204, 125, 450, 300);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel error = new JLabel("");
		error.setForeground(Color.RED);
		error.setFont(new Font("Arial", Font.PLAIN, 15));
		error.setHorizontalAlignment(SwingConstants.CENTER);
		error.setBounds(10, 11, 430, 32);
		panel.add(error);
		
		username = new JTextField();
		username.requestFocus();
		username.setForeground(Color.GRAY);
		username.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.DARK_GRAY));
		username.setFont(new Font("Arial", Font.PLAIN, 25));
		username.setBounds(96, 96, 260, 30);
		username.setColumns(10);
		panel.add(username);
		
		password = new JPasswordField();
		password.setEchoChar('•');
		password.setForeground(Color.GRAY);
		password.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.DARK_GRAY));
		password.setFont(new Font("Arial", Font.PLAIN, 25));
		password.setBounds(96, 185, 260, 30);
		panel.add(password);
		
		JButton loginbtn = new JButton("Accedi");
		loginbtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(ctr.checkExists(username.getText() ,String.valueOf(password.getPassword()),adminDAO)) {
					ctr.getAll(adminDAO);
					ctr.openDashboard();
				}else
					error.setText("Credenziali incorrette!");
					
					
				
				
			}
		});
		loginbtn.setFont(new Font("Arial", Font.PLAIN, 25));
		loginbtn.setForeground(Color.WHITE);
		loginbtn.setBackground(Color.decode("#00A5FF"));
		loginbtn.setBounds(96, 238, 260, 30);
		panel.add(loginbtn);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/user.png")));
		lblNewLabel_1.setBounds(96, 53, 32, 32);
		panel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/lock.png")));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(96, 142, 32, 32);
		panel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Username");
		lblNewLabel_3.setForeground(Color.GRAY);
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_3.setBounds(138, 53, 80, 32);
		panel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Password");
		lblNewLabel_4.setForeground(Color.GRAY);
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_4.setBounds(138, 142, 80, 32);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel = new JLabel("Cinemates Admin Panel");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 26, 834, 44);
		contentPane.add(lblNewLabel);
		
		username.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (username.getText().equals("Username..."))
					username.setText("");
			}
		});		
	}
}
