package katt;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;

public class StartScreen extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField jTextField1 = null;
	private JButton jButton1 = null;
	private JButton skickaButton = null;
	private  JButton nejButton = null;
	private JTextField namnField = null;
	private JTextField mailField = null;
	private JTextField telField = null;
	private JTextArea jTextArea = null;
	
	private static String namn;
	private static String mail;
	private static String tel;
	
	/**
	 * @param owner
	 */
	public StartScreen(Frame owner) {
		super(owner);
		initialize();
	}

	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		this.setSize(480, 320);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Color.white);
			jContentPane.add(getSkickaButton(), null);
			jContentPane.add(getNamnField(), null);
			jContentPane.add(getMailField(), null);
			jContentPane.add(getTelField(), null);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(getNejButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes skickaButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSkickaButton() {
		if (skickaButton == null) {
			skickaButton = new JButton();
			skickaButton.setBounds(new Rectangle(15, 219, 94, 22));
			skickaButton.setText("Skicka");
			skickaButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					
					namn = namnField.getText();
					mail = mailField.getText();
					tel = telField.getText();
					
					System.out.println(namn + "\n" + mail + "\n" + tel);
					
					setVisible(false);
				}
			});
		}
		return skickaButton;
	}

	/**
	 * This method initializes namnField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNamnField() {
		if (namnField == null) {
			namnField = new JTextField();
			namnField.setLocation(new Point(15, 118));
			namnField.setPreferredSize(new Dimension(150, 20));
			namnField.setText("Namn");
			namnField.setSize(new Dimension(150, 20));
		}
		return namnField;
	}

	/**
	 * This method initializes mailField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMailField() {
		if (mailField == null) {
			mailField = new JTextField();
			mailField.setLocation(new Point(15, 148));
			mailField.setPreferredSize(new Dimension(150, 20));
			mailField.setText("Mail");
			mailField.setSize(new Dimension(150, 20));
		}
		return mailField;
	}

	/**
	 * This method initializes telField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTelField() {
		if (telField == null) {
			telField = new JTextField();
			telField.setLocation(new Point(15, 180));
			telField.setPreferredSize(new Dimension(150, 20));
			telField.setText("Telefonnummer");
			telField.setSize(new Dimension(150, 20));
		}
		return telField;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			
			jTextArea.setBounds(new Rectangle(15, 19, 429, 73));
			jTextArea.setEditable(false);
			jTextArea.setEnabled(false);
			jTextArea.setFont(new Font("Verdana", Font.BOLD, 12));
			jTextArea.setText("Vill du delta i tävlingen där du kan vinna en miljard kilo kattmat? \nMata då in dina uppgifter nedan.");
		}
		return jTextArea;
	}



	/**
	 * This method initializes nejButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNejButton() {
		if (nejButton == null) {
			nejButton = new JButton();
			nejButton.setText("Delta ej");
			nejButton.setLocation(new Point(142, 219));
			nejButton.setPreferredSize(new Dimension(73, 26));
			nejButton.setSize(new Dimension(94, 22));
			nejButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					
					namn = "";
					mail = "";
					tel = "";
					
					setVisible(false);
				}
			});
		}
		return nejButton;
	}
	
	public String getNamn() {
		return namn;
	}
	
	public String getMail() {
		return mail;
	}
	
	public String getTel() {
		return tel;
	}
	
	

}  //  @jve:decl-index=0:visual-constraint="90,74"
