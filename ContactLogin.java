package phone;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ContactLogin extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel id, pw;
	JTextField tfld;
	JPasswordField tfPwd;
	JPanel login, loginTotal, empty, loginButton;
	ContactLogin(){
		setTitle("로그인");
		setSize(300, 200);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		id = new JLabel("ID : ", JLabel.RIGHT);
		id.setSize(10, 10);
		pw = new JLabel("Password : ", JLabel.RIGHT);
		tfld = new JTextField(15);
		tfPwd = new JPasswordField(15);
		JButton loginb = new JButton("Login");
		loginb.setSize(30, 30);
		loginb.addActionListener(new EventHandler());
		JButton join = new JButton("회원 가입");
		join.addActionListener(new JoinHandler());
		login = new JPanel();
		login.setLayout(new GridLayout(2,2, 20, 20));
		login.add(id);
		login.add(tfld);
		login.add(pw);
		login.add(tfPwd);
		loginButton = new JPanel();
		loginButton.setLayout(new GridLayout(1, 2));
		loginButton.add(loginb);
		loginButton.add(join);
		empty = new JPanel();
		empty.setSize(200, 200);
		JPanel empty1 = new JPanel();
		empty1.setSize(200, 200);
		loginTotal = new JPanel();
		loginTotal.setLayout(new BorderLayout(20, 20));
		loginTotal.add("Center", login);
		loginTotal.add("South", loginButton);
		loginTotal.add("East", empty);
		loginTotal.add("North", empty1);
		//login.add(loginb);
		setVisible(true);
		this.add(loginTotal);
		System.out.println("&");
	}
	class EventHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String id = tfld.getText();
			String password = new String(tfPwd.getPassword());
			System.out.println(id+" "+password);
		}
	}
	class JoinHandler extends JFrame implements ActionListener{
		private static final long serialVersionUID = 1L;
		private JPanel joinpage;
		private JTextField tfid, tfname, tfphone, tfhome, tfemail;
		private JPasswordField tpsf;
		JoinHandler(){
			setTitle("회원 가입");
			setSize(500, 500);
			JLabel id = new JLabel("ID : ", JLabel.RIGHT);
			JLabel name = new JLabel("NAME : ", JLabel.RIGHT);
			JLabel pw = new JLabel("PASSWORD : ", JLabel.RIGHT);
			JLabel phone_number = new JLabel("PHONE : ", JLabel.RIGHT);
			JLabel home_number = new JLabel("HOME : ", JLabel.RIGHT);
			JLabel e_mail = new JLabel("EMAIL : ", JLabel.RIGHT);
			tfid = new JTextField("");
			tfname = new JTextField("");
			tpsf = new JPasswordField("");
			tfphone = new JTextField("");
			tfhome = new JTextField("");
			tfemail = new JTextField("");
			JButton joinok = new JButton("확인");
			joinok.addActionListener(new join());
			JButton joincancle = new JButton("취소");
			joincancle.addActionListener(new joincancle());
			JPanel joinpage = new JPanel();
			joinpage.setLayout(new GridLayout(7, 2, 20, 20));
			joinpage.add(id);
			joinpage.add(tfid);
			joinpage.add(name);
			joinpage.add(tfname);
			joinpage.add(pw);
			joinpage.add(tpsf);
			joinpage.add(phone_number);
			joinpage.add(tfphone);
			joinpage.add(home_number);
			joinpage.add(tfhome);
			joinpage.add(e_mail);
			joinpage.add(tfemail);
			joinpage.add(joinok);
			joinpage.add(joincancle);
			this.add(joinpage);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent e){
			JoinHandler j = new JoinHandler();
		}
		class join implements ActionListener{
			public void actionPerformed(ActionEvent e){
				String id = tfid.getText();
				String name = tfname.getText();
				String password = tpsf.getText();
				String phone_num = tfphone.getText();
				String home_num = tfhome.getText();
				String email = tfhome.getText();
				if(id.equals("") || name.equals("") || password.equals("") || phone_num.equals("")){
					JOptionPane.showMessageDialog(null, "아이디, 이름, 비밀번호, 폰번호 중에서 입력 되지 않았습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}else{
					if(email.equals("")){
						email = " ";
					}
					if(home_num.equals("")){
						home_num = " ";
					}
					System.out.println(id);
					System.out.println(name);
					System.out.println(password);
					System.out.println(phone_num);
					System.out.println(home_num);
					System.out.println(email);
				}
				
			}
		}
		class joincancle implements ActionListener{
			public void actionPerformed(ActionEvent e){
				System.out.println("9");
				
			}
		}
	}
	
	
}
