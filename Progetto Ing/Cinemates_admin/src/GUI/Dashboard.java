package GUI;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

import classes.Commento;
import classes.Recensione;
import classes.Segnalazione;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JSeparator;

public class Dashboard extends JFrame {

	public JPanel homePanel;
	public JPanel reviewsPanel;
	public JPanel settingsPanel;
	private JPanel contentPane;
	private JPasswordField newPw;
	private JPasswordField confNewPw;
	private JLabel error;
	private JLabel icon;
	private JTable table;

	// PANNELLO HOME
	public void homePanelStart(Controller ctr) {
		homePanel = new JPanel(); 
		homePanel.setBackground(Color.WHITE);
		homePanel.setBounds(230, 11, 684, 537);
		homePanel.setLayout(null);
		homePanel.setVisible(true);
		
		contentPane.add(homePanel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Dashboard.class.getResource("/select.png")));
		lblNewLabel_1.setBounds(214, 77, 256, 256);
		homePanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Benvenuto nella dashboard!");
		lblNewLabel_2.setForeground(Color.GRAY);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 25));
		lblNewLabel_2.setBounds(10, 344, 664, 46);
		homePanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Per iniziare, seleziona un'operazione dal menu laterale");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setForeground(Color.GRAY);
		lblNewLabel_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_2_1.setBounds(10, 391, 664, 46);
		homePanel.add(lblNewLabel_2_1);
	}
	
	
	// PANNELLO RECENSIONI SEGNALATE
	public void reviewsPanelStart(Controller ctr) {
		reviewsPanel = new JPanel();
		reviewsPanel.setBackground(Color.WHITE);
		reviewsPanel.setBounds(230, 11, 684, 537);
		reviewsPanel.setLayout(null);
		reviewsPanel.setVisible(true);
		contentPane.add(reviewsPanel);
				
		List<Segnalazione> s = ctr.getSegnalazioni();
		
		if (s.isEmpty()) {
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setIcon(new ImageIcon(Dashboard.class.getResource("/noSegnalazioni.png")));
			lblNewLabel_1.setBounds(214, 79, 256, 256);
			reviewsPanel.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("\u00C9 tutto sereno su Cinemates!");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setForeground(Color.GRAY);
			lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 25));
			lblNewLabel_2.setBounds(10, 346, 664, 46);
			reviewsPanel.add(lblNewLabel_2);
			
			JLabel lblNewLabel_2_1 = new JLabel("Non c'\u00E9 nessuna segnalazione da elaborare");
			lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2_1.setForeground(Color.GRAY);
			lblNewLabel_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
			lblNewLabel_2_1.setBounds(10, 393, 664, 46);
			reviewsPanel.add(lblNewLabel_2_1);			
		}
		else {
			String colonne[] = {"ID", "Utente", "Recensione","Commento", "Tipologia"};
			DefaultTableModel model = new DefaultTableModel(colonne, 0) {
				@Override
				public boolean isCellEditable(int row, int col) { return false; }
		    };
			
			for (int i=0; i<s.size(); i++) {
				Object[] reports = {s.get(i).getId(), s.get(i).getUtente(), s.get(i).getRecensione(),s.get(i).getCommento(), s.get(i).getTipo()};
				model.addRow(reports);
			}
			
			JLabel lblNewLabel_3_1 = new JLabel("Recensioni segnalate");
			lblNewLabel_3_1.setForeground(Color.GRAY);
			lblNewLabel_3_1.setFont(new Font("Arial", Font.BOLD, 25));
			lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3_1.setBounds(10, 11, 255, 30);
			reviewsPanel.add(lblNewLabel_3_1);
			
			table = new JTable();
			table.setForeground(Color.GRAY);
			table.setFont(new Font("Arial", Font.PLAIN, 18));
			table.setBounds(0, 0, 1, 1);
			table.setModel(model);
			table.setShowVerticalLines(false);
			table.setSurrendersFocusOnKeystroke(true);
			table.setBackground(Color.WHITE);
			table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
			table.getTableHeader().setBackground(Color.decode("#201849"));
			table.getTableHeader().setForeground(Color.WHITE);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(200);
			table.setBorder(null);
			table.setSelectionBackground(Color.decode("#00a5ff"));
			table.setSelectionForeground(Color.WHITE);
			table.setRowHeight(30);
			
			DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer) table.getDefaultRenderer(String.class);
			stringRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(0, 52, 674, 415);
			scrollPane.getViewport().setBackground(Color.WHITE);
			scrollPane.setBorder(null);
			scrollPane.setBackground(Color.WHITE);
			reviewsPanel.add(scrollPane);
			
			JButton approveBtn = new JButton("Approva");
			approveBtn.setIcon(new ImageIcon(Dashboard.class.getResource("/approve.png")));
			approveBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			approveBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
			approveBtn.setForeground(Color.GRAY);
			approveBtn.setFont(new Font("Arial", Font.PLAIN, 18));
			approveBtn.setFocusPainted(false);
			approveBtn.setBackground(SystemColor.menu);
			approveBtn.setBounds(534, 486, 140, 40);
			approveBtn.setEnabled(false);
			reviewsPanel.add(approveBtn);
			
			JButton disapprovaBtn = new JButton("Disapprova");
			disapprovaBtn.setIcon(new ImageIcon(Dashboard.class.getResource("/reject.png")));
			disapprovaBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			disapprovaBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
			disapprovaBtn.setForeground(Color.GRAY);
			disapprovaBtn.setFont(new Font("Arial", Font.PLAIN, 18));
			disapprovaBtn.setFocusPainted(false);
			disapprovaBtn.setBackground(SystemColor.menu);
			disapprovaBtn.setBounds(362, 486, 162, 40);
			disapprovaBtn.setEnabled(false);
			reviewsPanel.add(disapprovaBtn);
			
			JButton mostraBtn = new JButton("Mostra");
			mostraBtn.setIcon(new ImageIcon(Dashboard.class.getResource("/showReview.png")));
			mostraBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
			mostraBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
			mostraBtn.setForeground(Color.GRAY);
			mostraBtn.setFont(new Font("Arial", Font.PLAIN, 18));
			mostraBtn.setFocusPainted(false);
			mostraBtn.setBackground(SystemColor.menu);
			mostraBtn.setBounds(0, 486, 125, 40);
			mostraBtn.setEnabled(false);
			reviewsPanel.add(mostraBtn);
			
			
			// MOUSE SU TASTO DISAPPROVA
			disapprovaBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if (disapprovaBtn.isEnabled()) {
						disapprovaBtn.setBackground(Color.decode("#00a5ff"));
						disapprovaBtn.setForeground(Color.WHITE);
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {
					if (disapprovaBtn.isEnabled()) {
						disapprovaBtn.setBackground(SystemColor.menu);
						disapprovaBtn.setForeground(Color.GRAY);
					}
				}
			});
			
			
			// MOUSE SU TASTO APPROVA
			approveBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if (approveBtn.isEnabled()) {
						approveBtn.setBackground(Color.decode("#00a5ff"));
						approveBtn.setForeground(Color.WHITE);
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {
					if (approveBtn.isEnabled()) {
						approveBtn.setBackground(SystemColor.menu);
						approveBtn.setForeground(Color.GRAY);
					}
				}
			});
			
			
			// MOUSE SU TASTO MOSTRA
			mostraBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if (mostraBtn.isEnabled()) {
						mostraBtn.setBackground(Color.decode("#00a5ff"));
						mostraBtn.setForeground(Color.WHITE);
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {
					if (mostraBtn.isEnabled()) {
						mostraBtn.setBackground(SystemColor.menu);
						mostraBtn.setForeground(Color.GRAY);
					}
				}
			});
			
			
			// PRESSIONE TASTO APPROVA
			approveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Controller.segnalazione.setId(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()));
					Controller.recensione.setId(table.getValueAt(table.getSelectedRow(), 2).toString());
					ctr.showPopup("Confermando verrá inviata una notifica agli utenti", "approva");
				}
			});
			
			
			// PRESSIONE TASTO DISAPPROVA
			disapprovaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Controller.segnalazione.setId(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()));
					Controller.recensione.setId(table.getValueAt(table.getSelectedRow(), 2).toString());
					ctr.showPopup("Confermando verrá inviata una notifica agli utenti", "disapprova");
				}
			});
			
			
			// PRESSIONE TASTO MOSTRA
			mostraBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Controller.segnalazione.setId(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()));
					Controller.recensione = null;
					Controller.commento = null;
					
					if(table.getValueAt(table.getSelectedRow(), 2) != null) {
						Controller.recensione = new Recensione();
						Controller.recensione.setId(table.getValueAt(table.getSelectedRow(), 2).toString());
					}
						
					else {
						Controller.commento = new Commento();
						Controller.commento.setId(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 3).toString()));
					}
						
					try {
						ctr.showReport();
					} catch (JSONException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			// ABILITAZIONE/DISABILITAZIONE TASTI
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(table.getSelectionModel().isSelectionEmpty()) {
						approveBtn.setEnabled(false);
						disapprovaBtn.setEnabled(false);
						mostraBtn.setEnabled(false);
					}
					else {
						approveBtn.setEnabled(true);
						disapprovaBtn.setEnabled(true);
						mostraBtn.setEnabled(true);
					}
				}
			});
		}
	}
	
	
	// PANNELLO IMPOSTAZIONI
	public void settingsPanelStart(Controller ctr) {
		settingsPanel = new JPanel();
		settingsPanel.setBackground(Color.WHITE);
		settingsPanel.setBounds(230, 11, 684, 537);
		settingsPanel.setLayout(null);
		settingsPanel.setVisible(true);
		contentPane.add(settingsPanel);
		
		JLabel lblNewLabel_3 = new JLabel("Impostazioni");
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 25));
		lblNewLabel_3.setForeground(Color.GRAY);
		lblNewLabel_3.setBounds(10, 11, 664, 30);
		settingsPanel.add(lblNewLabel_3);
		
		icon = new JLabel();
		icon.setIcon(new ImageIcon(Dashboard.class.getResource("/userSettings.png")));
		icon.setBounds(277, 75, 128, 128);
		settingsPanel.add(icon);
		
		JLabel lblNewLabel_4 = new JLabel("Nuova password");
		lblNewLabel_4.setForeground(Color.GRAY);
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_4.setBounds(211, 242, 260, 32);
		settingsPanel.add(lblNewLabel_4);
		
		newPw = new JPasswordField();
		newPw.setForeground(Color.GRAY);
		newPw.setFont(new Font("Arial", Font.PLAIN, 25));
		newPw.setEchoChar('•');
		newPw.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.DARK_GRAY));
		newPw.setBounds(211, 285, 260, 30);
		settingsPanel.add(newPw);
		
		JLabel lblNewLabel_4_1 = new JLabel("Conferma nuova password");
		lblNewLabel_4_1.setForeground(Color.GRAY);
		lblNewLabel_4_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_4_1.setBounds(211, 326, 260, 32);
		settingsPanel.add(lblNewLabel_4_1);
		
		confNewPw = new JPasswordField();
		confNewPw.setForeground(Color.GRAY);
		confNewPw.setFont(new Font("Arial", Font.PLAIN, 25));
		confNewPw.setEchoChar('•');
		confNewPw.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.DARK_GRAY));
		confNewPw.setBounds(211, 369, 260, 30);
		settingsPanel.add(confNewPw);
		
		JButton aggiorna = new JButton("Aggiorna password");
		aggiorna.setForeground(Color.WHITE);
		aggiorna.setFont(new Font("Arial", Font.PLAIN, 25));
		aggiorna.setBackground(new Color(0, 165, 255));
		aggiorna.setBounds(210, 426, 260, 35);
		aggiorna.setFocusPainted(false);
		settingsPanel.add(aggiorna);
		
		error = new JLabel();
		error.setHorizontalAlignment(SwingConstants.CENTER);
		error.setForeground(Color.RED);
		error.setFont(new Font("Arial", Font.PLAIN, 15));
		error.setBounds(10, 472, 664, 30);
		settingsPanel.add(error);
		
		
		// PRESSIONE TASTO AGGIORNA PASSWORD
		aggiorna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (confNewPw.getText().length() >= 6 && newPw.getText().length() >= 6) {
					if(confNewPw.getText().equals(newPw.getText())) {
						if (Controller.admin.aggiornaPassword(newPw.getText())) {
							ctr.admin.setPassword(newPw.getText());
							ctr.adminDAO.updatePassword(newPw.getText());
							icon.setIcon(new ImageIcon(Dashboard.class.getResource("/okIcon.png")));
							newPw.setEditable(false);
							confNewPw.setEditable(false);
							aggiorna.setBackground(Color.decode("#93d9ff"));
							aggiorna.setEnabled(false);
							aggiorna.setForeground(Color.WHITE);
							error.setForeground(Color.decode("#1fce00"));
							error.setText("La password é stata aggiornata correttamente!");
							System.out.println("LOG: Password aggiornata correttamente");
						}
						else {
							System.out.println("LOG: La password é uguale a quella attuale");
							error.setForeground(Color.RED);
							error.setText("La nuova password é uguale a quella attuale");
						}
					} else if (Controller.admin.aggiornaPassword(newPw.getText())) {
						System.out.println("LOG: Le due password sono diverse");
						error.setForeground(Color.RED);
						error.setText("Le due password non sono uguali");
					}
				}
				else {
					error.setForeground(Color.RED);
					error.setText("La password deve essere lunga almeno 6 caratteri");
				}
			}
		});
	}
	
	
	/*** Create the frame. */
	public Dashboard(Controller ctr) {
		setTitle("Dashboard admin");
		inizializzaFrame(ctr);
		setLocationRelativeTo(null);
		reviewsPanelStart(ctr);
		settingsPanelStart(ctr);
		reviewsPanel.setVisible(false);
		settingsPanel.setVisible(false);
		homePanelStart(ctr);
	}
	
	private void inizializzaFrame(Controller ctr) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 588);
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
		lblOperazioni.setFont(new Font("Arial", Font.BOLD, 18));
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
		review.setBounds(0, 136, 220, 67);
		contentPane.add(review);
		
		JButton settings = new JButton("Impostazioni");
		settings.setIcon(new ImageIcon(Dashboard.class.getResource("/settings.png")));
		settings.setVerticalTextPosition(SwingConstants.BOTTOM);
		settings.setHorizontalTextPosition(SwingConstants.CENTER);
		settings.setForeground(Color.WHITE);
		settings.setFocusPainted(false);
		settings.setFont(new Font("Arial", Font.PLAIN, 18));
		settings.setBackground(Color.decode("#201849"));
		settings.setBounds(0, 203, 220, 67);
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
		
		
		// PRESSIONE TASTO IMPOSTAZIONI
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (homePanel.isVisible()) {
					homePanel.setVisible(false);
				}
				
				if (reviewsPanel.isVisible()) {
					reviewsPanel.setVisible(false);
				}
				
				if (settingsPanel.isVisible()) {
					settingsPanel.validate();
					settingsPanel.repaint();
				}
				else {
					settingsPanelStart(ctr);
					review.setBackground(Color.decode("#201849"));
					settings.setBackground(Color.decode("#00A5FF"));
				}					
			}
		});
		
		
		// PRESSIONE TASTO RECENSIONI SEGNALATE
		review.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (homePanel.isVisible()) {
					homePanel.setVisible(false);
				}
				
				if (settingsPanel.isVisible()) {
					settingsPanel.setVisible(false);
				}
				
				if (reviewsPanel.isVisible()) {
					reviewsPanel.validate();
					reviewsPanel.repaint();
				}
				else {
					reviewsPanelStart(ctr);
					settings.setBackground(Color.decode("#201849"));
					review.setBackground(Color.decode("#00A5FF"));
				}
			}
		});
		
		
		// PRESSIONE TASTO LOGOUT
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctr.showPopup("Confermando verrai reindirizzato alla schermata di login", "logout");
			}
		});
	}	
}
