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

public class Transfer implements ActionListener {

	ArrayList<String> array = new ArrayList<String>();

	private String id;

	JFrame jFrame = new JFrame("Bank Of Chun - TrandferPage");

	JPanel jPanel = new JPanel();

	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();

	JPanel jPanelWithdrawAccount = new JPanel();
	JLabel jLabelWithdrawAccount = new JLabel();
	JLabel jLabelNowMoney = new JLabel();

	JLabel jLabelPassword = new JLabel("���� ��й�ȣ");
	JTextField jTextFieldPassword = new JTextField(20);
	JLabel jLabelTransferMoney = new JLabel("��ü �ݾ�(��)");
	JTextField jTextFieldTransferMoney = new JTextField(10);

	JPanel jPanelDepositAccount = new JPanel();
	JLabel jLabelBank = new JLabel("�Ա�����");
	String bank[] = { "����", "����", "���", "����", "�츮", "�ϳ�", "SC", "�泲", "����", "�뱸", "�λ�", "���", "����", "����", "��ü��", "����",
			"����", "�ѱ���Ƽ", "�긲����", "BankOfAmerica", "Doich", "HSBC", "JPMogan", "ChinaPublic", "BNP Pariva" };
	JComboBox<String> jComboBox = new JComboBox<>(bank);
	JLabel jLabelDepositAccount = new JLabel("�Աݰ���");
	JTextField jTextFieldDepositAccount = new JTextField(20);

	JButton TransferButton = new JButton("Transfer");
	JButton BackButton = new JButton("Back");

	public Transfer(String id) {

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

		jPanel.add(jPanel1);
		jPanel.add(jPanel2);

		jPanel.setLayout(new GridLayout(0, 1));

		jPanel1.add(jPanelWithdrawAccount);
		jPanelWithdrawAccount.setLayout(new GridLayout(0, 1));

		jLabelWithdrawAccount.setText("��� ���¹�ȣ :" + array.get(0) + "");
		jLabelNowMoney.setText("���� �ܾ� :" + array.get(1) + " ");

		jPanelWithdrawAccount.add(jLabelWithdrawAccount);
		jPanelWithdrawAccount.add(jLabelNowMoney);
		jPanelWithdrawAccount.add(jLabelPassword);
		jPanelWithdrawAccount.add(jTextFieldPassword);
		jPanelWithdrawAccount.add(jLabelTransferMoney);
		jPanelWithdrawAccount.add(jTextFieldTransferMoney);
		jPanelWithdrawAccount.add(jLabelBank);

		jPanel2.add(jPanelDepositAccount);
		jPanelDepositAccount.setLayout(new GridLayout(0, 1));
		jPanelDepositAccount.add(jComboBox);
		jPanelDepositAccount.add(jLabelDepositAccount);
		jPanelDepositAccount.add(jTextFieldDepositAccount);
		jPanelDepositAccount.add(TransferButton);
		jPanelDepositAccount.add(BackButton);

		TransferButton.addActionListener(this);
		BackButton.addActionListener(this);
		jTextFieldPassword.addActionListener(this);
		jTextFieldTransferMoney.addActionListener(this);
		jTextFieldDepositAccount.addActionListener(this);

		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFrame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(TransferButton)) {
			try {
				Class.forName("com.mysql.jdbc.Driver");//JDBC�����ε�
				Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");//DB����
				Statement sql = DB.createStatement();//�����غ�
				ResultSet calltrans = sql.executeQuery("select * from test where id = '" + this.id + "'");//���� ���� , ResultSet�� ���������� �����͸� �����;��ϱ⶧���� ���
				if (calltrans.next()) {//���� ROW������Ŵ
					while (true) {
						if (jTextFieldPassword.getText().equals(calltrans.getString(3))) {//�н����� Ȯ��
							if (!(calltrans.getInt(6) == 0)) {//�ܾ� 0Ȯ��
								if (!(calltrans.getInt(6) < 0) && !(calltrans.getInt(6) < Integer
										.parseInt(jTextFieldTransferMoney.getText()))) {//�ܾ� ����Ȯ��
									if (!(calltrans.getInt(4) < Integer.parseInt(jTextFieldTransferMoney.getText()))) {//��ü�ѵ� Ȯ��
										int sender = calltrans.getInt(6);//���� �ݾ� ����������
										int transMoney = Integer.parseInt(jTextFieldTransferMoney.getText());//��ü�ݾ� ����������
										int resultMoney = sender - transMoney; //����ݾ׿��� ��ü�ݾ����� �ݾ� ������ ����
										sql.executeUpdate("update test set account='" + resultMoney + "' where id='"
												+ this.id + "'");//������ ����ݾ� DB�� ����
										
										
										ResultSet callrecive = sql
												.executeQuery("select * from test where phoneNumber = '"
														+ jTextFieldDepositAccount.getText() + "'");//�Աݰ��� ��ȣ�� ��ü�� ��ġ ������
										if (callrecive.next()) {
											while (true) {

												int a = callrecive.getInt(6);//���� �ݾ�
												int b = Integer.parseInt(jTextFieldTransferMoney.getText());//��ü �ݾ�
												int c = a + b;//����ݾ� ���� ��ü�ݾ��� ���Ѱ��� ������ ����
												sql.executeUpdate(
														"update test set account='" + c + "' where phoneNumber='"
																+ jTextFieldDepositAccount.getText() + "'");//������ ���� �ݾ� DB�� ����

												JOptionPane.showMessageDialog(TransferButton, "��ü�� �Ϸ�Ǿ����ϴ�");
												Date time = new Date();//��ü �ð� ��������� DATE ���
												String realTime = String.valueOf(time);//�ð��� String ������ ����

												String sender1 = this.id;//�����»�� �α��εǾ��ִ� ���̵�
												String money = jTextFieldTransferMoney.getText();// ��ü�ݾ�
												String reciver = jTextFieldDepositAccount.getText();// �޴»�� 

												Class.forName("com.mysql.jdbc.Driver");//JDBC���� �ε�
												Connection DB1 = DriverManager.getConnection(
														"jdbc:mysql://localhost:3306/db", "root", "4603");// ������ ��Ʈ�� dbĿ��Ʈ
												Statement sql1 = DB1.createStatement();//������ �غ�
												sql1.executeUpdate(String.format(
														"insert into transfer1(sender, money, reciver, date) values ('%s','%s','%s','%s')",
														sender1, money, reciver, realTime));//DB�� ������� ��ü�ݾ� �޴»�� �ð� ����

												jFrame.dispose();
												new AfterLogin(id);
												break;

											}
										}
									} else {
										JOptionPane.showMessageDialog(TransferButton, "��ü �ѵ����� �����ϴ�.");
										break;
									}
								} else {
									JOptionPane.showMessageDialog(TransferButton, "�ܾ��� �����մϴ�.");
									break;
								}
							} else {
								JOptionPane.showMessageDialog(TransferButton, "�ܾ��� �����մϴ�.");
								break;
							}
						} else {
							JOptionPane.showMessageDialog(TransferButton, "��й�ȣ�� Ʋ���ϴ�.");
							break;
						}
					}

				} else {
					JOptionPane.showMessageDialog(TransferButton, "��ü����!!");
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
		if (e.getSource().equals(BackButton))

		{
			jFrame.dispose();

			new BankJob(id);
		}

	}
}
