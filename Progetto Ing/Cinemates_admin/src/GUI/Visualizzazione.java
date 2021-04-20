package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.Controller;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Visualizzazione extends JFrame {

	private JPanel contentPane;
	public JLabel motivoSeg = new JLabel();
	public JLabel segnalatoreRew = new JLabel();
	public JLabel numReact = new JLabel();
	public JLabel autoreRew = new JLabel();
	public JLabel idRew = new JLabel();
	public JLabel titoloRew = new JLabel();
	public JTextArea descrRew = new JTextArea();
	public JLabel oggetto = new JLabel();
	private static JScrollPane scrollPane = new JScrollPane();

	/**
	 * Create the frame.
	 */
	public Visualizzazione(Controller ctr) {
		setBackground(Color.WHITE);
		setTitle("Visualizza recensione");
		inizializzaFrame(ctr);
		setLocationRelativeTo(null);
	}
	
	public void inizializzaFrame(Controller ctr) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		oggetto.setHorizontalAlignment(SwingConstants.RIGHT);
		oggetto.setForeground(Color.WHITE);
		oggetto.setFont(new Font("Arial", Font.PLAIN, 15));
		oggetto.setBounds(77, 270, 131, 18);
		contentPane.add(oggetto);
		
		JLabel lblOggetto = new JLabel("Oggetto");
		lblOggetto.setFont(new Font("Arial", Font.BOLD, 15));
		lblOggetto.setForeground(Color.WHITE);
		lblOggetto.setHorizontalAlignment(SwingConstants.LEFT);
		lblOggetto.setBounds(10, 270, 70, 18);
		contentPane.add(lblOggetto);
		
		JLabel lblDisapprova = new JLabel();
		lblDisapprova.setText("Disapprova");
		lblDisapprova.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisapprova.setForeground(Color.WHITE);
		lblDisapprova.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDisapprova.setBounds(29, 382, 74, 18);
		contentPane.add(lblDisapprova);
		
		JLabel lblApprova = new JLabel();
		lblApprova.setText("Approva");
		lblApprova.setHorizontalAlignment(SwingConstants.CENTER);
		lblApprova.setForeground(Color.WHITE);
		lblApprova.setFont(new Font("Arial", Font.PLAIN, 15));
		lblApprova.setBounds(123, 382, 53, 18);
		contentPane.add(lblApprova);
		
		titoloRew.setHorizontalAlignment(SwingConstants.LEFT);
		titoloRew.setForeground(Color.GRAY);
		titoloRew.setFont(new Font("Arial", Font.BOLD, 25));
		titoloRew.setBounds(230, 11, 394, 30);
		contentPane.add(titoloRew);
		descrRew.setEditable(false);
		descrRew.setLineWrap(true);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		descrRew.setPreferredSize(new Dimension(100, 100));
		descrRew.setForeground(Color.BLACK);
		descrRew.setFont(new Font("Arial", Font.PLAIN, 15));
		descrRew.setBounds(240, 23, 394, 348);
		
		scrollPane.setBounds(220, 11, 404, 389);
		scrollPane.getViewport().setBackground(Color.WHITE);;
		scrollPane.setViewportView(descrRew);
		
		contentPane.add(scrollPane);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.decode("#00A5FF"));
		separator.setBackground(Color.decode("#00A5FF"));
		separator.setBounds(33, 199, 150, 13);
		contentPane.add(separator);
		
		motivoSeg.setHorizontalAlignment(SwingConstants.RIGHT);
		motivoSeg.setForeground(Color.WHITE);
		motivoSeg.setFont(new Font("Arial", Font.PLAIN, 15));
		motivoSeg.setBounds(67, 250, 141, 18);
		contentPane.add(motivoSeg);
		
		
		
		JLabel lblMotivo = new JLabel("Motivo");
		lblMotivo.setHorizontalAlignment(SwingConstants.LEFT);
		lblMotivo.setForeground(Color.WHITE);
		lblMotivo.setFont(new Font("Arial", Font.BOLD, 15));
		lblMotivo.setBounds(10, 250, 49, 18);
		contentPane.add(lblMotivo);
		
		
		segnalatoreRew.setHorizontalAlignment(SwingConstants.RIGHT);
		segnalatoreRew.setForeground(Color.WHITE);
		segnalatoreRew.setFont(new Font("Arial", Font.PLAIN, 15));
		segnalatoreRew.setBounds(106, 230, 102, 18);
		contentPane.add(segnalatoreRew);
		
		JLabel lblSegnalatore = new JLabel("Segnalatore");
		lblSegnalatore.setHorizontalAlignment(SwingConstants.LEFT);
		lblSegnalatore.setForeground(Color.WHITE);
		lblSegnalatore.setFont(new Font("Arial", Font.BOLD, 15));
		lblSegnalatore.setBounds(10, 230, 86, 18);
		contentPane.add(lblSegnalatore);
		numReact.setText("0");
		
		
		numReact.setHorizontalAlignment(SwingConstants.RIGHT);
		numReact.setForeground(Color.WHITE);
		numReact.setFont(new Font("Arial", Font.PLAIN, 15));
		numReact.setBounds(138, 130, 70, 18);
		contentPane.add(numReact);
		
		JLabel lblratings = new JLabel("Totale Reazioni");
		lblratings.setHorizontalAlignment(SwingConstants.LEFT);
		lblratings.setForeground(Color.WHITE);
		lblratings.setFont(new Font("Arial", Font.BOLD, 15));
		lblratings.setBounds(10, 130, 111, 18);
		contentPane.add(lblratings);
		
		autoreRew.setHorizontalAlignment(SwingConstants.RIGHT);
		autoreRew.setForeground(Color.WHITE);
		autoreRew.setFont(new Font("Arial", Font.PLAIN, 15));
		autoreRew.setBounds(67, 108, 141, 18);
		contentPane.add(autoreRew);
		
		JLabel lblAutore = new JLabel("Autore");
		lblAutore.setHorizontalAlignment(SwingConstants.LEFT);
		lblAutore.setForeground(Color.WHITE);
		lblAutore.setFont(new Font("Arial", Font.BOLD, 15));
		lblAutore.setBounds(10, 108, 47, 18);
		contentPane.add(lblAutore);
		
		idRew.setHorizontalAlignment(SwingConstants.RIGHT);
		idRew.setForeground(Color.WHITE);
		idRew.setFont(new Font("Arial", Font.PLAIN, 15));
		idRew.setBounds(33, 86, 175, 18);
		contentPane.add(idRew);
		
		JLabel labelID = new JLabel("ID");
		labelID.setHorizontalAlignment(SwingConstants.LEFT);
		labelID.setForeground(Color.WHITE);
		labelID.setFont(new Font("Arial", Font.BOLD, 15));
		labelID.setBounds(10, 86, 15, 18);
		contentPane.add(labelID);
		
		JButton approveBtn = new JButton("");
		approveBtn.setIcon(new ImageIcon(Dashboard.class.getResource("/approve.png")));
		approveBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		approveBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		approveBtn.setForeground(Color.GRAY);
		approveBtn.setFont(new Font("Arial", Font.PLAIN, 18));
		approveBtn.setFocusPainted(false);
		approveBtn.setBackground(Color.decode("#201849"));
		approveBtn.setBounds(129, 331, 40, 40);
		contentPane.add(approveBtn);
		
		JButton disapprovaBtn = new JButton("");
		disapprovaBtn.setIcon(new ImageIcon(Dashboard.class.getResource("/reject.png")));
		disapprovaBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		disapprovaBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		disapprovaBtn.setForeground(Color.GRAY);
		disapprovaBtn.setFont(new Font("Arial", Font.PLAIN, 18));
		disapprovaBtn.setFocusPainted(false);
		disapprovaBtn.setBackground(Color.decode("#201849"));
		disapprovaBtn.setBounds(47, 331, 40, 40);
		contentPane.add(disapprovaBtn);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Visualizzazione.class.getResource("/showReviews.png")));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 11, 220, 64);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon(Visualizzazione.class.getResource("/lateral_bar.png")));
		lblNewLabel.setBounds(0, 0, 220, 421);
		contentPane.add(lblNewLabel);
		labelID.setHorizontalAlignment(SwingConstants.LEFT);
		labelID.setForeground(Color.WHITE);
		labelID.setFont(new Font("Arial", Font.BOLD, 15));
		labelID.setBounds(10, 86, 15, 18);
		
		
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
					disapprovaBtn.setBackground(Color.decode("#201849"));
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
					approveBtn.setBackground(Color.decode("#201849"));
					approveBtn.setForeground(Color.GRAY);
				}
			}
		});
		
		
		// PRESSIONE TASTO APPROVA
		approveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctr.showPopup("Confermando verrá inviata una notifica agli utenti", "approva");
			}
		});
		
				
		// PRESSIONE TASTO DISAPPROVA
		disapprovaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctr.showPopup("Confermando verrá inviata una notifica agli utenti", "disapprova");
			}
		});
	}
}
