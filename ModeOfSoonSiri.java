package bank;

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

public class ModeOfSoonSiri implements ActionListener {
	private String id;
	
	ArrayList<String> account = new ArrayList<String>();
	
	JFrame jFrame = new JFrame("Bank Of Chun - ModeOfSoonSil");
	
	JPanel jPanelAccount = new JPanel();
	JPanel jPanelList = new JPanel();
	
	String[] tableList = {"납부자","납부금","납부종류","납부일자"};
	DefaultTableModel tableModel = new DefaultTableModel(tableList, 0);
	
	JTable jTable = new JTable(tableModel);

	JButton jButton = new JButton("Refresh");
	
	JScrollPane jScrollBar = new JScrollPane(jTable);
	
	JTabbedPane jTabbedPane = new JTabbedPane();
	
	JLabel jLabel = new JLabel();


	public ModeOfSoonSiri(String id){
		
		this.id = id;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "4603");
			Statement sql = DB.createStatement();
			ResultSet accountCall = sql.executeQuery("select * from test where id = '" + this.id + "'");
			while (accountCall.next()) {
				account.add(accountCall.getString(6));
			} // 로그인 되어있는 현재 아이디 잔액
			Statement sql1 = DB.createStatement();
			ResultSet transferCall = sql1.executeQuery("select * from transfer2");
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
		jTabbedPane.add("BankOfKorea", jPanelAccount);
		jTabbedPane.add("납세내역", jPanelList);
		
		jLabel.setText("국가 재산 : "+account.get(0));
		jPanelAccount.add(jLabel);
		jPanelAccount.add(jButton);
		
		jPanelList.add(jScrollBar);
		
		jTable.setModel(tableModel);
		
		jFrame.add(jTabbedPane);
		
		jButton.addActionListener(this);
		
		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
				
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jButton)) {
			jFrame.dispose();

			new ModeOfSoonSiri(id);
		}
		
	}

}
