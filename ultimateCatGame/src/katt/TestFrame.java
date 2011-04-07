package katt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;


//VS4E -- DO NOT REMOVE THIS LINE!
public class TestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField pointsField;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField phoneField;
	private JButton updateButton;
	private JButton resetButton;
	private Database database; 
	private UpdateScore update;
	private JButton sendScoresButton;
	public TestFrame() {
		initComponents();
		update = new UpdateScore();
		database = new Database();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getPointsField(), new Constraints(new Leading(12, 91, 10, 10), new Leading(12, 12, 12)));
		add(getNameField(), new Constraints(new Leading(12, 90, 12, 12), new Leading(50, 12, 12)));
		add(getEmailField(), new Constraints(new Leading(12, 90, 12, 12), new Leading(88, 12, 12)));
		add(getPhoneField(), new Constraints(new Leading(12, 90, 12, 12), new Leading(126, 12, 12)));
		add(getResetButton(), new Constraints(new Leading(144, 108, 12, 12), new Leading(56, 12, 12)));
		add(getUpdateButton(), new Constraints(new Leading(144, 108, 12, 12), new Leading(12, 12, 12)));
		add(getSendScoresButton(), new Constraints(new Leading(144, 108, 12, 12), new Leading(100, 12, 12)));
		setSize(358, 302);
	}

	private JButton getSendScoresButton() {
		if (sendScoresButton == null) {
			sendScoresButton = new JButton();
			sendScoresButton.setText("Skicka");
			sendScoresButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					sendScoresButtonActionActionPerformed(event);
				}
			});
		}
		return sendScoresButton;
	}

	private JTextField getPointsField() {
		if (pointsField == null) {
			pointsField = new JTextField();
			pointsField.setText("Poäng");
		}
		return pointsField;
	}

	private JButton getResetButton() {
		if (resetButton == null) {
			resetButton = new JButton();
			resetButton.setText("Reset");
			resetButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					resetButtonActionActionPerformed(event);
				}
			});
		}
		return resetButton;
	}

	private JButton getUpdateButton() {
		if (updateButton == null) {
			updateButton = new JButton();
			updateButton.setText("Uppdatera");
			updateButton.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					updateButtonActionActionPerformed(event);
				}
			});
		}
		return updateButton;
	}

	private JTextField getPhoneField() {
		if (phoneField == null) {
			phoneField = new JTextField();
			phoneField.setText("Telefon");
		}
		return phoneField;
	}

	private JTextField getEmailField() {
		if (emailField == null) {
			emailField = new JTextField();
			emailField.setText("Epost");
		}
		return emailField;
	}

	private JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();
			nameField.setText("Namn");
		}
		return nameField;
	}

	private void updateButtonActionActionPerformed(ActionEvent event) {
		Long insertedScore = Long.parseLong(pointsField.getText());
		String name = nameField.getText();
		update.update(name, insertedScore);
		
	}

	private void resetButtonActionActionPerformed(ActionEvent event) {
		update.reset();
	}

	private void sendScoresButtonActionActionPerformed(ActionEvent event) {
		database.sendHighscore(nameField.getText(), (int) Long.parseLong(pointsField.getText()), emailField.getText(), phoneField.getText());
	}

}
