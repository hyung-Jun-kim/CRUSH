package bank;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BankJob implements ActionListener {

	private String id;
	JFrame jFrame = new JFrame("Bank Of Chun - BankJob");
	JPanel jPanel = new JPanel();

	JButton jButtonCheck = new JButton("조회");
	JButton jButtonTransfer = new JButton("이체");
	JButton jButtonTax = new JButton("공과금");
	JButton jButtonBack = new JButton("Back");

	public BankJob(String id) {
		this.id = id;

		jFrame.add(jPanel);

		jPanel.add(jButtonCheck);
		jPanel.add(jButtonTransfer);
		jPanel.add(jButtonTax);
		jPanel.add(jButtonBack);

		jButtonCheck.addActionListener(this);
		jButtonTransfer.addActionListener(this);
		jButtonTax.addActionListener(this);
		jButtonBack.addActionListener(this);

		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFrame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jButtonCheck)) {
			jFrame.dispose();

			new Check(id);
		}
		if (e.getSource().equals(jButtonTransfer)) {
			jFrame.dispose();

			new Transfer(id);
		}
		if (e.getSource().equals(jButtonTax)) {
			jFrame.dispose();

			new Tax(id);
		}
		if (e.getSource().equals(jButtonBack)) {
			jFrame.dispose();

			new AfterLogin(id);
		}

	}

}
