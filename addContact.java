package phone;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class addContact extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel add;
	private Container content;
	private JTextField addtext1;
	private JTextField addtext2;
	private JTextField addtext3;
	private JTextField addtext4;
	private JTextField addtext5;
	String name, phone_num, home_num, email, group;
	Contact con;
	addContact(){
		setTitle("����ó �߰�");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		content = getContentPane();
		addtext1 = new JTextField("");
		add = new JPanel();
		add.setLayout(new GridLayout(6, 2, 20, 20));
		add.add(new JLabel("�̸�"));
		add.add(addtext1);
		addtext2 = new JTextField("");
		add.add(new JLabel("�� ��ȣ"));
		add.add(addtext2);
		addtext3 = new JTextField("");
		add.add(new JLabel("�� ��ȣ"));
		add.add(addtext3);
		addtext4 = new JTextField("");
		add.add(new JLabel("�̸���"));
		add.add(addtext4);
		add.add(new JLabel("�׷�"));
		addtext5 = new JTextField("");
		add.add(addtext5);
		add.setBackground(new Color(206,242,121));
		JButton btn1 = new JButton("����");
		btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				name = addtext1.getText();
				phone_num = addtext2.getText();
				home_num = addtext3.getText();
				email = addtext4.getText();
				group = addtext5.getText();
				if(name.equals("")){
					JOptionPane.showMessageDialog(null, "�̸��� �Էµ��� �ʾҽ��ϴ�.", "����ó �߰�", JOptionPane.ERROR_MESSAGE);
				}else{
					if(phone_num.matches("^-?[0-9]+(\\.[0-9]+)?$") && home_num.matches("^-?[0-9]+(\\.[0-9]+)?$")){
						con = new Contact(name,phone_num,home_num,email,group);
						if(checkContact(con)){
							JOptionPane.showMessageDialog(null, "�̹� �ԷµǾ����ϴ�.", "����ó �߰�", JOptionPane.ERROR_MESSAGE);
						}else{
							ContactManager.list.add(con);
						}
						setVisible(false);
					}else{
						JOptionPane.showMessageDialog(null, "�� ��ȣ�� �� ��ȣ�� ���ڰ� �ֽ��ϴ�.", "����ó �߰�", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		JButton btn2 = new JButton("���");
		btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		add.add(btn1);
		add.add(btn2);
		content.add(add);
		setSize(500, 500);
		setVisible(true);
		
	}
	boolean checkContact(Contact con){
		for(Contact c : ContactManager.list){
			if(con.equals(c)){
				return true;
			}
		}
		return false;
	}
}
