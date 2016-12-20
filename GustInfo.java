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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GustInfo implements ActionListener {

	ArrayList<String> info = new ArrayList<>();

	private String id;
	JFrame jFrame = new JFrame("Bank Of Chun - GustInfo.");

	JPanel jPanelEmail = new JPanel();
	JLabel jLabelEmail = new JLabel();

	JPanel jPanelPassword = new JPanel();
	JLabel jLabelPassword = new JLabel();
	JTextField jTextFieldPassword = new JTextField(20);
	JButton jButtonPassword = new JButton("수정");

	JPanel jPanelName = new JPanel();
	JLabel jLabelName = new JLabel();
	JTextField jTextFieldName = new JTextField(20);
	JButton jButtonName = new JButton("수정");

	JPanel jPanelPhone = new JPanel();
	JLabel jLabelPhone = new JLabel();
	JTextField jTextFieldPhone = new JTextField(20);
	JButton jButtonPhone = new JButton("수정");

	JPanel jPanelTransferLimit = new JPanel();
	JLabel jLabelTransferLimit = new JLabel();
	JTextField jTextFieldTransferLimit = new JTextField(20);
	JButton jButtonTransferLimit = new JButton("수정");

	JPanel jPanelButton = new JPanel();

	JButton jButton = new JButton("Back");

	public GustInfo(String id) {
		this.id = id;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
			Statement sql = DB.createStatement();
			ResultSet checkAll = sql.executeQuery("select * from test where id = '" + this.id + "'");
			if (checkAll.next()) {
				while (true) {
					info.add(checkAll.getString(1));
					info.add(checkAll.getString(2));
					info.add(checkAll.getString(3));
					info.add(checkAll.getString(4));
					info.add(checkAll.getString(5));
					break;

				}
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		jFrame.setLayout(new GridLayout(0, 1));

		jFrame.add(jPanelEmail);
		jFrame.add(jPanelPassword);
		jFrame.add(jPanelName);

		jFrame.add(jPanelPhone);
		jFrame.add(jPanelTransferLimit);

		jFrame.add(jPanelButton);

		jPanelEmail.add(jLabelEmail);
		jLabelEmail.setText("              Email               " + info.get(0));

		jPanelPassword.add(jLabelPassword);
		jLabelPassword.setText("            Password             " + info.get(2));
		jPanelPassword.add(jTextFieldPassword);
		jPanelPassword.add(jButtonPassword);

		jPanelName.add(jLabelName);
		jLabelName.setText("              Name               " + info.get(1));
		jPanelName.add(jTextFieldName);
		jPanelName.add(jButtonName);

		jPanelPhone.add(jLabelPhone);
		jLabelPhone.setText("                 Tel.                 " + info.get(4));
		jPanelPhone.add(jTextFieldPhone);
		jPanelPhone.add(jButtonPhone);

		jPanelTransferLimit.add(jLabelTransferLimit);
		jLabelTransferLimit.setText("       Transfer Limit       " + info.get(3));
		jPanelTransferLimit.add(jTextFieldTransferLimit);
		jPanelTransferLimit.add(jButtonTransferLimit);

		jPanelButton.add(jButton);

		jTextFieldName.addActionListener(this);
		jTextFieldPassword.addActionListener(this);
		jTextFieldPhone.addActionListener(this);
		jTextFieldTransferLimit.addActionListener(this);

		jButtonPassword.addActionListener(this);
		jButtonName.addActionListener(this);
		jButtonPhone.addActionListener(this);
		jButtonTransferLimit.addActionListener(this);

		jButton.addActionListener(this);

		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jButtonPassword)) {
			try {
				if (!(jTextFieldPassword.getText().toString().equals(""))) {
					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
					Statement sql = DB.createStatement();
					sql.executeUpdate("update test set password='" + jTextFieldPassword.getText() + "' where id='"
							+ this.id.toString() + "'");
					JOptionPane.showMessageDialog(jButtonPassword, "수정이 완료되었습니다.");
					JOptionPane.showMessageDialog(jButtonPassword, "다시 로그인 됩니다.");
					jFrame.dispose();
					new LoginPage();
				} else {
					JOptionPane.showMessageDialog(jButtonPassword, "공란입니다");
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(jButtonName)) {
			try {
				if (!(jTextFieldName.getText().toString().equals(""))) {
					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
					Statement sql = DB.createStatement();
					sql.executeUpdate("update test set name='" + jTextFieldName.getText() + "' where id='"
							+ this.id.toString() + "'");
					JOptionPane.showMessageDialog(jButtonName, "수정이 완료되었습니다.");
					JOptionPane.showMessageDialog(jButtonName, "다시 로그인 됩니다.");
					jFrame.dispose();
					new LoginPage();
				} else {
					JOptionPane.showMessageDialog(jButtonPassword, "공란입니다");
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(jButtonPhone)) {
			try {
				if (!(jTextFieldPhone.getText().toString().equals(""))) {
					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
					Statement sql = DB.createStatement();
					sql.executeUpdate("update test set phoneNumber='" + jTextFieldPhone.getText() + "' where id='"
							+ this.id.toString() + "'");
					JOptionPane.showMessageDialog(jButtonPhone, "수정이 완료되었습니다.");
					JOptionPane.showMessageDialog(jButtonPhone, "다시 로그인 됩니다.");
					jFrame.dispose();
					new LoginPage();
				} else {
					JOptionPane.showMessageDialog(jButtonPassword, "공란입니다");
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(jButtonTransferLimit)) {
			try {
				if (!(jTextFieldTransferLimit.getText().toString().equals(""))) {
					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
					Statement sql = DB.createStatement();
					sql.executeUpdate("update test set limitMoney='" + jTextFieldTransferLimit.getText()
							+ "' where id='" + this.id.toString() + "'");
					JOptionPane.showMessageDialog(jButtonTransferLimit, "수정이 완료되었습니다.");
					JOptionPane.showMessageDialog(jButtonTransferLimit, "다시 로그인 됩니다.");
					jFrame.dispose();
					new LoginPage();
				} else {
					JOptionPane.showMessageDialog(jButtonPassword, "공란입니다");
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(jButton)) {
			jFrame.dispose();
			new AfterLogin(id);
		}
	}
}
