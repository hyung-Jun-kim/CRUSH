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

	JLabel jLabelPassword = new JLabel("계좌 비밀번호");
	JTextField jTextFieldPassword = new JTextField(20);
	JLabel jLabelTransferMoney = new JLabel("이체 금액(원)");
	JTextField jTextFieldTransferMoney = new JTextField(10);

	JPanel jPanelDepositAccount = new JPanel();
	JLabel jLabelBank = new JLabel("입금은행");
	String bank[] = { "농협", "국민", "기업", "신한", "우리", "하나", "SC", "경남", "광주", "대구", "부산", "산업", "수협", "신협", "우체국", "전북",
			"제주", "한국씨티", "산림조합", "BankOfAmerica", "Doich", "HSBC", "JPMogan", "ChinaPublic", "BNP Pariva" };
	JComboBox<String> jComboBox = new JComboBox<>(bank);
	JLabel jLabelDepositAccount = new JLabel("입금계좌");
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

		jLabelWithdrawAccount.setText("출금 계좌번호 :" + array.get(0) + "");
		jLabelNowMoney.setText("현재 잔액 :" + array.get(1) + " ");

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
				Class.forName("com.mysql.jdbc.Driver");//JDBC동적로딩
				Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");//DB연결
				Statement sql = DB.createStatement();//쿼리준비
				ResultSet calltrans = sql.executeQuery("select * from test where id = '" + this.id + "'");//쿼리 실행 , ResultSet은 쿼리실행후 데이터를 가져와야하기때문에 사용
				if (calltrans.next()) {//다음 ROW값가르킴
					while (true) {
						if (jTextFieldPassword.getText().equals(calltrans.getString(3))) {//패스워드 확인
							if (!(calltrans.getInt(6) == 0)) {//잔액 0확인
								if (!(calltrans.getInt(6) < 0) && !(calltrans.getInt(6) < Integer
										.parseInt(jTextFieldTransferMoney.getText()))) {//잔액 부족확인
									if (!(calltrans.getInt(4) < Integer.parseInt(jTextFieldTransferMoney.getText()))) {//이체한도 확인
										int sender = calltrans.getInt(6);//현재 금액 변수에저장
										int transMoney = Integer.parseInt(jTextFieldTransferMoney.getText());//이체금액 변수에저장
										int resultMoney = sender - transMoney; //현재금액에서 이체금액을뺀 금액 변수에 저장
										sql.executeUpdate("update test set account='" + resultMoney + "' where id='"
												+ this.id + "'");//연산한 현재금액 DB에 수정
										
										
										ResultSet callrecive = sql
												.executeQuery("select * from test where phoneNumber = '"
														+ jTextFieldDepositAccount.getText() + "'");//입금계좌 번호로 이체할 위치 가져옴
										if (callrecive.next()) {
											while (true) {

												int a = callrecive.getInt(6);//현재 금액
												int b = Integer.parseInt(jTextFieldTransferMoney.getText());//이체 금액
												int c = a + b;//현재금액 에서 이체금액을 더한것을 변수에 저장
												sql.executeUpdate(
														"update test set account='" + c + "' where phoneNumber='"
																+ jTextFieldDepositAccount.getText() + "'");//연산한 현재 금액 DB에 수정

												JOptionPane.showMessageDialog(TransferButton, "이체가 완료되었습니다");
												Date time = new Date();//이체 시간 기록을위해 DATE 사용
												String realTime = String.valueOf(time);//시간을 String 형으로 저장

												String sender1 = this.id;//보내는사람 로그인되어있는 아이디
												String money = jTextFieldTransferMoney.getText();// 이체금액
												String reciver = jTextFieldDepositAccount.getText();// 받는사람 

												Class.forName("com.mysql.jdbc.Driver");//JDBC동적 로딩
												Connection DB1 = DriverManager.getConnection(
														"jdbc:mysql://localhost:3306/db", "root", "4603");// 설정한 루트로 db커넥트
												Statement sql1 = DB1.createStatement();//쿼리문 준비
												sql1.executeUpdate(String.format(
														"insert into transfer1(sender, money, reciver, date) values ('%s','%s','%s','%s')",
														sender1, money, reciver, realTime));//DB에 보낸사람 이체금액 받는사람 시간 저장

												jFrame.dispose();
												new AfterLogin(id);
												break;

											}
										}
									} else {
										JOptionPane.showMessageDialog(TransferButton, "이체 한도보다 높습니다.");
										break;
									}
								} else {
									JOptionPane.showMessageDialog(TransferButton, "잔액이 부족합니다.");
									break;
								}
							} else {
								JOptionPane.showMessageDialog(TransferButton, "잔액이 부족합니다.");
								break;
							}
						} else {
							JOptionPane.showMessageDialog(TransferButton, "비밀번호가 틀립니다.");
							break;
						}
					}

				} else {
					JOptionPane.showMessageDialog(TransferButton, "이체실패!!");
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
