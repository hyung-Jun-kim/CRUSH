package bank;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AfterLogin implements ActionListener {
	JFrame jFrame = new JFrame("Bank Of Chun - SelectPage");
	JPanel jPanel = new JPanel();

	JButton jButton = new JButton("은행업무");
	JButton jButton1 = new JButton("고객정보 조회 및 수정");
	JButton jButton2 = new JButton("종료");
	private String id;

	public AfterLogin(String id) {

		this.id = id;
		jFrame.add(jPanel);

		jPanel.add(jButton);
		jPanel.add(jButton1);
		jPanel.add(jButton2);

		jButton.addActionListener(this);
		jButton1.addActionListener(this);
		jButton2.addActionListener(this);

		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jButton)) {
			jFrame.dispose();

			new BankJob(id);
		}

		if (e.getSource().equals(jButton1)) {
			jFrame.dispose();

			new GustInfo(id);
		}
		if (e.getSource().equals(jButton2)) {
			System.exit(0);
		}

	}

}
