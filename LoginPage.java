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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPage implements ActionListener, KeyListener, FocusListener {

	boolean focus = false;

	JFrame jFrame = new JFrame("Bank Of Chun - LoginPage");

	JPanel jPanel = new JPanel();

	JPanel jPanelID = new JPanel();
	JPanel jPanelPassword = new JPanel();

	JLabel jLabelPassword = new JLabel("Password ");

	JPanel jPanelButton = new JPanel();
	JLabel jLabelID = new JLabel("        ID        ");

	JTextField jTextFieldID = new JTextField(20);
	JTextField jTextFieldPassword = new JTextField(20);

	JButton jButtonLogin = new JButton("Login");
	JButton jButtonJoin = new JButton("Join");

	public LoginPage() {
		jPanel.setLayout(new GridLayout(3, 1));

		jFrame.add(jPanel);
		jPanel.add(jPanelID);
		jPanel.add(jPanelPassword);
		jPanel.add(jPanelButton);

		jPanelID.add(jLabelID);
		jPanelID.add(jTextFieldID);

		jPanelPassword.add(jLabelPassword);
		jPanelPassword.add(jTextFieldPassword);

		jPanelButton.add(jButtonLogin);
		jPanelButton.add(jButtonJoin);

		jButtonLogin.addActionListener(this);
		jTextFieldPassword.addKeyListener(this);
		jTextFieldPassword.addFocusListener(this);
		jTextFieldID.addActionListener(this);

		jButtonJoin.addActionListener(this);

		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(jButtonLogin)) {//�α��� ��ư �̺�Ʈó�� �κ�
			if (jTextFieldID.getText().equals("Park-Geun-Hye@president.go.kr")) {//���� �̾��̵�� �α����ϸ� ��� ���� ���������� ����
				new ModeOfSoonSiri(jTextFieldID.getText().toString());//���� �������� id�� �Ķ���ͷ� �ϱ�
			}
			if (!jTextFieldID.getText().isEmpty() && !jTextFieldPassword.getText().isEmpty()) {//���� id password��  ���������� ��
				try {
					Class.forName("com.mysql.jdbc.Driver");//JDBC���� �ε�
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");//������ ��Ʈ�� DB����
					Statement sql = DB.createStatement();//������ �غ�
					ResultSet checkid = sql
							.executeQuery("select * from test where id = '" + jTextFieldID.getText() + "'");//������ ����
					if (checkid.next()) {//���� üũ ���̵� �� ����Ŵ 
						while (true) {

							if (checkid.getString(1).equals(jTextFieldID.getText())) {//����ڰ� �� ���̵�� ��� ����Ⱦ��̵� ������ ��

								if (checkid.getString(3).equals(jTextFieldPassword.getText())) {//����ڰ� �� �н������ ��� ����� �н����尡 ������ ��

									jFrame.dispose();//���� ������ ���� dispose�� ���� ���������� �Ӽ� ������ exit�� ���� �����̴�. �Ѵ� �������.

									new AfterLogin(jTextFieldID.getText());// �α��� ���� ������ �̵�.

									break;
								} else {

									JOptionPane.showMessageDialog(jButtonLogin, "�н����尡 Ʋ�Ƚ��ϴ�.");
									break;
								}
							}

						}
					} else {
						JOptionPane.showMessageDialog(jButtonLogin, "���̵� �������� �ʽ��ϴ�.");
					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(jButtonLogin, "������ �ֽ��ϴ�.");
			}

		} else if (e.getSource().equals(jButtonJoin)) {
			jFrame.dispose();

			new Join();
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (focus == true) {
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				jButtonLogin.doClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		focus = true;
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		focus = false;
	}

}
