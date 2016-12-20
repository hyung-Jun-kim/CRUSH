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

		if (e.getSource().equals(jButtonLogin)) {//로그인 버튼 이벤트처리 부분
			if (jTextFieldID.getText().equals("Park-Geun-Hye@president.go.kr")) {//만약 이아이디로 로그인하면 모드 오브 순실페이지 오픈
				new ModeOfSoonSiri(jTextFieldID.getText().toString());//순실 페이지에 id값 파라미터로 넒김
			}
			if (!jTextFieldID.getText().isEmpty() && !jTextFieldPassword.getText().isEmpty()) {//만약 id password가  비지않으면 참
				try {
					Class.forName("com.mysql.jdbc.Driver");//JDBC동적 로딩
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");//설정한 루트로 DB접속
					Statement sql = DB.createStatement();//쿼리문 준비
					ResultSet checkid = sql
							.executeQuery("select * from test where id = '" + jTextFieldID.getText() + "'");//쿼리문 적립
					if (checkid.next()) {//다음 체크 아이디 를 가르킴 
						while (true) {

							if (checkid.getString(1).equals(jTextFieldID.getText())) {//사용자가 쓴 아이디와 디비에 저장된아이디가 같으면 참

								if (checkid.getString(3).equals(jTextFieldPassword.getText())) {//사용자가 쓴 패스워드와 디비에 저장된 패스워드가 같으면 참

									jFrame.dispose();//현재 프레임 종료 dispose는 다음 새프레임을 켤수 있지만 exit은 완전 종료이다. 둘다 사라진다.

									new AfterLogin(jTextFieldID.getText());// 로그인 다음 페이지 이동.

									break;
								} else {

									JOptionPane.showMessageDialog(jButtonLogin, "패스워드가 틀렸습니다.");
									break;
								}
							}

						}
					} else {
						JOptionPane.showMessageDialog(jButtonLogin, "아이디가 존재하지 않습니다.");
					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(jButtonLogin, "공란이 있습니다.");
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
