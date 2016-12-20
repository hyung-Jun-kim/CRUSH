package bank;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Check implements ActionListener {

	ArrayList<String> accountArray = new ArrayList<String>();

	private String id;

	JFrame jFrame = new JFrame("Bank Of Chun - CheckPage");

	JTabbedPane jTabbedPane = new JTabbedPane();

	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();

	JLabel jLabelMoney = new JLabel();

	String[] tableList = { "보낸사람", "금액", "받은사람", "시간" };
	DefaultTableModel tableModel = new DefaultTableModel(tableList, 0);

	JTable jTable = new JTable(tableModel);

	JButton jButton = new JButton("Back");
	JButton jButtonTransfer = new JButton("이체");
	JButton jButtonRe = new JButton("Refresh");

	JScrollPane jScrollBar = new JScrollPane(jTable);

	public Check(String id) {
		this.id = id;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
			Statement sql = DB.createStatement();
			ResultSet accountCall = sql.executeQuery("select * from test where id = '" + this.id + "'");
			while (accountCall.next()) {
				accountArray.add(accountCall.getString(6));
			} // 로그인 되어있는 현재 아이디 잔액
			Statement sql1 = DB.createStatement();
			ResultSet transferCall = sql1.executeQuery("select * from transfer1 ");
			while (transferCall.next()) {
				String[] transferList = { transferCall.getString(1), transferCall.getString(2),
						transferCall.getString(3), transferCall.getString(4) };
				tableModel.addRow(transferList);
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		jTabbedPane.add("잔액 조회", jPanel1);
		jTabbedPane.add("거래 내역", jPanel2);

		jPanel1.add(jLabelMoney);
		jLabelMoney.setText("현재 잔액 : " + accountArray.get(0));
		jPanel1.add(jButton);
		jPanel1.add(jButtonTransfer);
		jPanel1.add(jButtonRe);

		jPanel2.setLayout(new GridLayout(0, 1));
		jPanel2.add(jScrollBar);
		jTable.setModel(tableModel);

		jButton.addActionListener(this);
		jButtonTransfer.addActionListener(this);
		jButtonRe.addActionListener(this);

		jFrame.setBounds(0, 0, 1000, 500);
		
		jFrame.add(jTabbedPane);

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
		if (e.getSource().equals(jButtonTransfer)) {
			jFrame.dispose();

			new Transfer(id);
		}
		if (e.getSource().equals(jButtonRe)) {
			jFrame.dispose();

			new Check(id);
		}

	}
}
