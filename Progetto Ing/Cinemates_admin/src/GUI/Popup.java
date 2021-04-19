package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;

import controller.Controller;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class Popup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JLabel messaggio2 = new JLabel();
	public String ctx = null;

	/**
	 * Create the dialog.
	 */
	public Popup(Controller ctr, String msg, String context) {
		messaggio2.setText(msg);
		ctx = context;
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
		setModal(true);
		contentPanel.setLayout(null);
				
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Popup.class.getResource("/warning.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 454, 64);
		contentPanel.add(lblNewLabel);
		
		JLabel messaggio = new JLabel();
		messaggio.setText("Sei sicuro?");
		messaggio.setForeground(Color.GRAY);
		messaggio.setHorizontalAlignment(SwingConstants.CENTER);
		messaggio.setFont(new Font("Arial", Font.BOLD, 20));
		messaggio.setBounds(0, 80, 454, 30);
		contentPanel.add(messaggio);
		
		JButton btnSi = new JButton("Si");
		btnSi.setForeground(Color.WHITE);
		btnSi.setFont(new Font("Arial", Font.PLAIN, 20));
		btnSi.setBackground(new Color(0, 165, 255));
		btnSi.setBounds(234, 164, 99, 30);
		btnSi.setFocusPainted(false);
		contentPanel.add(btnSi);
		
		JButton btnNo = new JButton("No");
		btnNo.setForeground(Color.WHITE);
		btnNo.setFont(new Font("Arial", Font.PLAIN, 20));
		btnNo.setBackground(SystemColor.scrollbar);
		btnNo.setBounds(120, 164, 99, 30);
		btnNo.setFocusPainted(false);
		contentPanel.add(btnNo);
		
		messaggio2.setHorizontalAlignment(SwingConstants.CENTER);
		messaggio2.setForeground(Color.GRAY);
		messaggio2.setFont(new Font("Arial", Font.PLAIN, 15));
		messaggio2.setBounds(0, 110, 454, 30);
		contentPanel.add(messaggio2);
		
		
		// PRESSIONE TASTO NO
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		// PRESSIONE TASTO SI
		btnSi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switch (ctx) {
					case "approva":
						ctr.sendReportResult(true);
						ctr.showReports();
						dispose();
						break;
							
					case "disapprova":
						ctr.sendReportResult(false);
						ctr.showReports();
						dispose();
						break;
					
					default:
						dispose();
						ctr.closeDashboard();
						ctr.openLogin();
					}
				}catch(IOException io) {
					io.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
