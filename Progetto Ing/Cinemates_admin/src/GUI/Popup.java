package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class Popup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JLabel messaggio = new JLabel();

	/**
	 * Create the dialog.
	 */
	public Popup(Controller ctr, String msg) {
		messaggio.setText(msg);
		inizializzaPopup(ctr);
		setLocationRelativeTo(null);
	}
	
	private void inizializzaPopup(Controller ctr) {
		setResizable(false);
		setBounds(100, 100, 460, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Popup.class.getResource("/warning.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 11, 434, 64);
		contentPanel.add(lblNewLabel);
		
		messaggio.setForeground(Color.GRAY);
		messaggio.setHorizontalAlignment(SwingConstants.CENTER);
		messaggio.setFont(new Font("Arial", Font.PLAIN, 20));
		messaggio.setBounds(10, 86, 424, 58);
		contentPanel.add(messaggio);
		
		JButton btnSi = new JButton("Si");
		btnSi.setForeground(Color.WHITE);
		btnSi.setFont(new Font("Arial", Font.PLAIN, 20));
		btnSi.setBackground(new Color(0, 165, 255));
		btnSi.setBounds(228, 166, 99, 30);
		contentPanel.add(btnSi);
		
		JButton btnNo = new JButton("No");
		btnNo.setForeground(Color.WHITE);
		btnNo.setFont(new Font("Arial", Font.PLAIN, 20));
		btnNo.setBackground(SystemColor.controlHighlight);
		btnNo.setBounds(118, 166, 99, 30);
		contentPanel.add(btnNo);
		
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnSi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ctr.closeDashboard();
				ctr.openLogin();
			}
		});
	}
}
