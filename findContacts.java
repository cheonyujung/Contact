package phone;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class findContacts extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container content;
	//private JPanel find;
	//private JPanel enter;
	String[] findkind = {"이름", "폰 번호", "집 번호", "이메일", "그룹"};
	findContacts(){
		setTitle("연락처 검색");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		content = getContentPane();
		JComboBox<String> kind = new JComboBox<String>(findkind);
		kind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				int index = cb.getSelectedIndex();
				System.out.println(index);
			}});
		JPanel enter = new JPanel();
		enter.setLayout(new FlowLayout());
		enter.add(kind);
		JPanel find = new JPanel();
		find.setLayout(new BorderLayout());
		find.add(enter,"North");
		content.add(find);

	}
}
