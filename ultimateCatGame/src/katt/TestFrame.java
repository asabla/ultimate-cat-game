package katt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
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
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton jButton0;
	
	
	
	public TestFrame() {
		initComponents();
		update = new UpdateScore();
		database = new Database();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getUpdateButton(), new Constraints(new Leading(245, 108, 10, 10), new Leading(12, 12, 12)));
		add(getResetButton(), new Constraints(new Leading(245, 108, 12, 12), new Leading(56, 12, 12)));
		add(getSendScoresButton(), new Constraints(new Leading(245, 108, 12, 12), new Leading(100, 12, 12)));
		add(getPointsField(), new Constraints(new Leading(12, 136, 10, 10), new Leading(12, 12, 12)));
		add(getNameField(), new Constraints(new Leading(12, 136, 12, 12), new Leading(50, 12, 12)));
		add(getEmailField(), new Constraints(new Leading(12, 136, 12, 12), new Leading(88, 12, 12)));
		add(getPhoneField(), new Constraints(new Leading(12, 136, 12, 12), new Leading(126, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(247, 104, 12, 12), new Leading(144, 12, 12)));
		add(getJScrollPane0(), new Constraints(new Leading(19, 294, 10, 10), new Bilateral(182, 12, 26)));
		setSize(500, 450);
	}

	private JButton getJButton0(){
		if(jButton0 == null){
			jButton0 = new JButton();
			jButton0.setText("Hämta");
			jButton0.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent event){
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			jTable0.setModel(new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return jTable0;
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

	private void jButton0ActionActionPerformed(ActionEvent event) {
		DefaultTableModel model = (DefaultTableModel) jTable0.getModel();
		String[] tableColumnsName = {"Namn", "Highscore"};
		model.setColumnIdentifiers(tableColumnsName);
		model.setNumRows(0);
		jTable0.setModel(model); 
		
		int colNo = 0;
		ResultSetMetaData rsmd = null;
		ResultSet rs = database.getTopScore();
		
		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			colNo = rsmd.getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while(rs.next()){
				Object[] objects = new Object[colNo];
				for(int i = 0; i < colNo; i++){
					objects[i] = rs.getObject(i + 1);
					
				}
				model.addRow(objects);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jTable0.setModel(model);
		database.close();
	}

}
