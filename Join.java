package bank;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Join implements ActionListener, KeyListener, FocusListener {

	String email;
	String name;
	String password;
	String transferLimit;
	String phone;
	int account;

	boolean focus = false;

	JFrame jFrame = new JFrame("Bank Of Chun - JoinPage");

	JPanel jPanelEmail = new JPanel();
	JLabel jLabelEmail = new JLabel("              Email               ");
	JTextField jTextFieldEmail = new JTextField(20);

	JPanel jPanelPassword = new JPanel();
	JLabel jLabelPassword = new JLabel("          Password           ");
	JTextField jTextFieldPassword = new JTextField(20);

	JPanel jPanelName = new JPanel();
	JLabel jLabelName = new JLabel("              Name               ");
	JTextField jTextFieldName = new JTextField(20);

	JPanel jPanelPhone = new JPanel();
	JLabel jLabelPhone = new JLabel("                 Tel.                 ");
	JTextField jTextFieldPhone = new JTextField(20);

	JPanel jPanelTransferLimit = new JPanel();
	JLabel jLabelTransferLimit = new JLabel("       Transfer Limit       ");
	JTextField jTextFieldTransferLimit = new JTextField(20);

	JPanel jPanelButton = new JPanel();
	JButton jButton = new JButton("Join");
	JButton jButton1 = new JButton("Back");
	JButton jButtonCheck = new JButton("Check");
	JButton jButtonCheck1 = new JButton("Check");

	public Join() {

		jFrame.setLayout(new GridLayout(6, 1));
		// JFrame jFrame1 = new JFrame();

		jFrame.add(jPanelEmail);
		jFrame.add(jPanelPassword);
		jFrame.add(jPanelName);
		jFrame.add(jPanelPhone);
		jFrame.add(jPanelTransferLimit);
		jFrame.add(jPanelButton);

		jPanelEmail.add(jLabelEmail);
		jPanelEmail.add(jTextFieldEmail);
		jPanelEmail.add(jButtonCheck);

		jPanelPassword.add(jLabelPassword);
		jPanelPassword.add(jTextFieldPassword);

		jPanelName.add(jLabelName);
		jPanelName.add(jTextFieldName);

		jPanelPhone.add(jLabelPhone);
		jPanelPhone.add(jTextFieldPhone);
		jPanelPhone.add(jButtonCheck1);

		jPanelTransferLimit.add(jLabelTransferLimit);
		jPanelTransferLimit.add(jTextFieldTransferLimit);

		jPanelButton.add(jButton);
		jPanelButton.add(jButton1);

		jButton.addActionListener(this);
		jButton1.addActionListener(this);
		jButtonCheck.addActionListener(this);
		jButtonCheck1.addActionListener(this);

		jTextFieldEmail.addActionListener(this);
		jTextFieldName.addActionListener(this);
		jTextFieldPassword.addActionListener(this);
		jTextFieldTransferLimit.addActionListener(this);
		jTextFieldPhone.addActionListener(this);

		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!jTextFieldEmail.getText().isEmpty() && !jTextFieldName.getText().isEmpty()
				&& !jTextFieldPassword.getText().isEmpty() && !jTextFieldTransferLimit.getText().isEmpty()
				&& !jTextFieldPhone.getText().isEmpty() && !e.getSource().equals(jButton1)) {

			email = jTextFieldEmail.getText();
			name = jTextFieldName.getText();
			password = jTextFieldPassword.getText();
			transferLimit = jTextFieldTransferLimit.getText();
			phone = jTextFieldPhone.getText();
			account = 0;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
				Statement sql = DB.createStatement();

				sql.executeUpdate(String.format(
						"insert into test(id,name,password,limitMoney,phoneNumber,account) values ('%s','%s','%s','%s','%s','%d')",
						email, name, password, transferLimit, phone, account));
			} catch (Exception e1) {
			}

			JOptionPane.showMessageDialog(jButton, "가입이 완료되었습니다.");
			jFrame.dispose();

			new LoginPage();
			
		} else if (jTextFieldEmail.getText().isEmpty() && jTextFieldName.getText().isEmpty()
				&& jTextFieldPassword.getText().isEmpty() && jTextFieldTransferLimit.getText().isEmpty()
				&& jTextFieldPhone.getText().isEmpty() && !e.getSource().equals(jButton1))

		{
			JOptionPane.showMessageDialog(jButton, "빈칸이 있습니다.");
		}

		if (e.getSource().equals(jButton1)) {
			jFrame.dispose();

			new LoginPage();
		}

		if (e.getSource().equals(jButtonCheck)) {
			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
				String sql = "select * from test where id = '" + jTextFieldEmail.getText() + "'";
				Statement stm = DB.prepareStatement(sql);//
				ResultSet checkid = stm.executeQuery(sql);
				Boolean check = true;

				while (check = checkid.next()) {
					System.out.println(checkid.getString(1));
					if (checkid.getString(1).equals(jTextFieldEmail.getText())) {
						JOptionPane.showMessageDialog(jButtonCheck, "중복된 아이디입니다.");
						break;
					}
				}
				if (!check)
					JOptionPane.showMessageDialog(jButtonCheck, "사용 가능한 아이디 입니다.");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getSource().equals(jButtonCheck1)) {
			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
				String sql = "select * from test where phoneNumber = '" + jTextFieldPhone.getText() + "'";
				Statement stm = DB.prepareStatement(sql);
				ResultSet checkid = stm.executeQuery(sql);
				Boolean temp = true;

				while (temp = checkid.next()) {
					System.out.println(checkid.getString(5));
					if (checkid.getString(5).equals(jTextFieldPhone.getText())) {
						JOptionPane.showMessageDialog(jButtonCheck, "중복된 번호입니다.");
						break;
					}
				}
				if (!temp)
					JOptionPane.showMessageDialog(jButtonCheck, "사용 가능한 번호입니다.");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent args0) {
		if (focus == true) {
			if (args0.getKeyCode() == KeyEvent.VK_ENTER) {
				jButton.doClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent e) {
		focus = true;

	}

	@Override
	public void focusLost(FocusEvent e) {
		focus = false;
	}
}
