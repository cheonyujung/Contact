package phone;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class setContact extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel set;
	private Container content;
	private JTextField addtext1;
	private JTextField addtext2;
	private JTextField addtext3;
	private JTextField addtext4;
	private JTextField addtext5;
	String name, phone_num, home_num, email, group;
	JButton btn1;
	JButton btn2;
	setContact(){
		setTitle("연락처 수정");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		System.out.println("index1-"+ContactManager.index);
		content = getContentPane();

	}
	void createPanel(Contact con){
		set = new JPanel();
		set.setLayout(new GridLayout(6, 2, 20, 20));
		addtext1 = new JTextField(con.name);
		addtext2 = new JTextField(con.phone_num);
		addtext3 = new JTextField(con.home_num);
		addtext4 = new JTextField(con.email);
		addtext5 = new JTextField(con.group);
		set.add(new JLabel("이름"));
		set.add(addtext1);
		set.add(new JLabel("폰 번호"));
		set.add(addtext2);
		set.add(new JLabel("집 번호"));
		set.add(addtext3);
		set.add(new JLabel("이메일"));
		set.add(addtext4);
		set.add(new JLabel("그룹"));
		set.add(addtext5);
		btn1 = new JButton("저장");
		btn2 = new JButton("취소");
		set.add(btn1);
		set.add(btn2);
		btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				name = addtext1.getText();
				phone_num = addtext2.getText();
				home_num = addtext3.getText();
				email = addtext4.getText();
				group = addtext5.getText();
				System.out.println("index2-"+ContactManager.index);
				con.setName(name);
				con.setPhone_num(phone_num);
				con.setHome_num(home_num);
				con.setEmail(email);
				con.setGroup(group);
				setVisible(false);
			}
		});

		btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		content.add(set);
	}
}
