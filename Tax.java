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
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Tax implements ActionListener {

	ArrayList<String> array = new ArrayList<String>();

	private String id;

	JFrame jFrame = new JFrame("Bank Of Chun - Tax");

	JPanel jPanel = new JPanel();

	JPanel TaxPanel = new JPanel();
	JPanel TaxPanel1 = new JPanel();
	JPanel TaxPanel2 = new JPanel();

	JPanel ButtonPanel = new JPanel();

	JLabel jLabel = new JLabel("������");
	String Tax[] = { "���� ����", "������ ����", "���漼 ����", "����� ��� ����", "���ο��� ����", "��������", "���� ����", "�����Ģ�� ����", "����û ������ ����" };
	JComboBox<String> jComboBox = new JComboBox<>(Tax);

	JLabel jLabelAccount = new JLabel("������ ����");
	JLabel jLabelAccount1 = new JLabel();

	JLabel jLabelNowMoney = new JLabel("���� �ܾ�");
	JLabel jLabelNowMoney1 = new JLabel();

	JLabel jLabelSendMoney = new JLabel("������ �ݾ�");
	JTextField jTextSendMoney = new JTextField();

	JLabel jLabelPassword = new JLabel("PassWord");
	JTextField jTextPassword = new JTextField(15);

	JButton jButton = new JButton("����");
	JButton jButton1 = new JButton("back");

	public Tax(String id) {
		this.id = id;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
			Statement sql = DB.createStatement();
			ResultSet callInfo = sql.executeQuery("select * from test where id = '" + this.id + "'");
			while (callInfo.next()) {
				array.add(callInfo.getString(5));
				array.add(callInfo.getString(6));
			}
		} catch (

		ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		jFrame.add(jPanel);

		jPanel.add(TaxPanel);

		jPanel.add(TaxPanel1);
		jPanel.add(TaxPanel2);
		jPanel.add(ButtonPanel);

		jPanel.setLayout(new GridLayout(0, 1));

		TaxPanel.add(jLabel);
		TaxPanel.add(jComboBox);

		TaxPanel.setLayout(new GridLayout(0, 1));

		TaxPanel1.add(jLabelAccount);
		jLabelAccount1.setText(array.get(0));
		TaxPanel1.add(jLabelAccount1);
		TaxPanel1.add(jLabelNowMoney);
		TaxPanel1.add(jLabelNowMoney1);
		jLabelNowMoney1.setText(array.get(1));
		TaxPanel1.setLayout(new GridLayout(0, 1));

		TaxPanel2.add(jLabelPassword);
		TaxPanel2.add(jTextPassword);
		TaxPanel2.add(jLabelSendMoney);
		TaxPanel2.add(jTextSendMoney);
		TaxPanel2.setLayout(new GridLayout(0, 1));

		ButtonPanel.add(jButton);
		ButtonPanel.add(jButton1);

		jButton.addActionListener(this);
		jButton1.addActionListener(this);
		jComboBox.addActionListener(this);

		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jButton)) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
				Statement sql = DB.createStatement();
				ResultSet calltrans = sql.executeQuery("select * from test where id = '" + this.id + "'");
				if (calltrans.next()) {
					while (true) {
						if (jTextPassword.getText().equals(calltrans.getString(3))) {
							if (!(calltrans.getInt(6) == 0)) {
								if (!(calltrans.getInt(6) < 0)
										&& !(calltrans.getInt(6) < Integer.parseInt(jTextSendMoney.getText()))) {
									if (!(calltrans.getInt(4) < Integer.parseInt(jTextSendMoney.getText()))) {
										int sender = calltrans.getInt(6);
										int transMoney = Integer.parseInt(jTextSendMoney.getText());

										int resultMoney = sender - transMoney;
										sql.executeUpdate("update test set account='" + resultMoney + "' where id='"
												+ this.id + "'");

										ResultSet callrecive = sql
												.executeQuery("select * from test where name = '�ڱ���'");

										if (callrecive.next()) {
											while (true) {

												int a = callrecive.getInt(6);
												int b = Integer.parseInt(jTextSendMoney.getText());
												int c = a + b;
												sql.executeUpdate(
														"update test set account='" + c + "' where name='�ڱ���'");

												JOptionPane.showMessageDialog(jButton, "��ü�� �Ϸ�Ǿ����ϴ�");
												Date time = new Date();
												String realTime = String.valueOf(time);

												String sender1 = this.id;
												String money = jTextSendMoney.getText();
												String whatTax = jComboBox.getSelectedItem().toString();
												String tax = jComboBox.getSelectedItem().toString();

												Class.forName("com.mysql.jdbc.Driver");
												Connection DB1 = DriverManager.getConnection(
														"jdbc:mysql://localhost:3306/db", "root", "4603");
												Statement sql1 = DB1.createStatement();
												sql1.executeUpdate(String.format(
														"insert into transfer2(sender, tax, whatTax, date) values ('%s','%s','%s','%s')",
														sender1, money, whatTax, realTime));

												Statement sql2 = DB1.createStatement();
												sql2.executeUpdate(String.format(
														"insert into transfer1(sender, money, reciver, date) values ('%s','%s','%s','%s')",
														sender1, money, tax, realTime));

												jFrame.dispose();
												new AfterLogin(id);
												break;

											}
										}
									} else {
										JOptionPane.showMessageDialog(jButton, "��ü �ѵ����� �����ϴ�.");
										break;
									}
								} else {
									JOptionPane.showMessageDialog(jButton, "�ܾ��� �����մϴ�.");
									break;
								}
							} else {
								JOptionPane.showMessageDialog(jButton, "�ܾ��� �����մϴ�.");
								break;
							}
						} else {
							JOptionPane.showMessageDialog(jButton, "��й�ȣ�� Ʋ���ϴ�.");
							break;
						}
					}

				} else {
					JOptionPane.showMessageDialog(jButton, "��ü����!!");
				}

			} catch (

			ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getSource().equals(jButton1)) {
			jFrame.dispose();

			new BankJob(id);
		}
	}
}
