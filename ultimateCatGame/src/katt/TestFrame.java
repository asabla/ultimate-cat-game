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
	private JTextField jTextField0;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JTextField jTextField3;
	private JButton jButton0;
	private JButton jButton1;
	
	private UpdateScore update;

	public TestFrame() {
		initComponents();
		update = new UpdateScore();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJTextField0(), new Constraints(new Leading(12, 91, 10, 10), new Leading(12, 12, 12)));
		add(getJTextField1(), new Constraints(new Leading(12, 90, 12, 12), new Leading(50, 12, 12)));
		add(getJTextField2(), new Constraints(new Leading(12, 90, 12, 12), new Leading(88, 12, 12)));
		add(getJTextField3(), new Constraints(new Leading(12, 90, 12, 12), new Leading(126, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(144, 10, 10), new Leading(12, 12, 12)));
		add(getJButton1(), new Constraints(new Leading(144, 12, 12), new Leading(56, 12, 12)));
		setSize(358, 302);
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setText("Poäng");
		}
		return jTextField0;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Reset");
			jButton1.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jButton1ActionActionPerformed(event);
				}
			});
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Uppdatera");
			jButton0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setText("Telefon");
		}
		return jTextField3;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setText("Epost");
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setText("Namn");
		}
		return jTextField1;
	}

	private void jButton0ActionActionPerformed(ActionEvent event) {
		Long insertedScore = Long.parseLong(jTextField0.getText());
		String name = jTextField1.getText();
		update.update(name, insertedScore);
		
	}

	private void jButton1ActionActionPerformed(ActionEvent event) {
		update.reset();
	}

}
