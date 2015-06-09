package phone;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


@SuppressWarnings("serial")
class Contact implements Serializable{
	String name;
	String phone_num;
	String home_num;
	String email;
	String group;
	
	Contact (String n, String p, String h, String e, String g){
		this.name = n;
		this.phone_num = p;
		this.home_num = h;
		this.email = e;
		this.group = g;
	}
	
	String getName(){
		return name;
	}
	
	String getPhone_num(){
		return phone_num;
	}
	
	void setName(String name){
		this.name = name;
	}
}

public class ContactManager extends JFrame /*implements ActionListener*/{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ArrayList<Contact> list = new ArrayList<Contact>();
	Scanner s = new Scanner(System.in);
	String name, email, group, phone_num, home_num;
	Set<String> groupmenu = new HashSet<String>();
	private JList<String> namelist;
	private JTextArea homeuser;
	private JTextField findblank;
	private DefaultListModel<String> model;
	JButton set;
	JButton delete;
	JButton findstart;
	JPanel home;
	JPanel find;
	String[] findkind = {"이름", "폰 번호", "집 번호", "이메일", "그룹"};
	public String search;
	public static int index, combo;
	ContactManager(){
		setTitle("연락처");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		home = new JPanel();
		home.setLayout(new BorderLayout(10, 10));
		find = new JPanel();
		find.setLayout(new FlowLayout());
		JComboBox<String> kind = new JComboBox<String>(findkind);
		kind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				combo = cb.getSelectedIndex();
			}});
		findblank = new JTextField(15);
		findstart = new JButton("검색");
		SearchAction sa = new SearchAction();
		findstart.addActionListener(sa);
		set = new JButton("수정");
		SetAction seta = new SetAction();
		set.addActionListener(seta);
		delete = new JButton("삭제");
		deleteAction da = new deleteAction();
		delete.addActionListener(da);
		find.add(kind);
		find.add(findblank);
		find.add(findstart);
		find.add(set);
		find.add(delete);
		homeuser = new JTextArea("");
		homeuser.setPreferredSize(new Dimension(250,500));
		model = new DefaultListModel<String>();
		namelist = new JList<String>(model);
		namelist.setVisibleRowCount(15);
		namelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(new JScrollPane(namelist));
		home.add("North", find);
		home.add("Center", namelist);
		home.add("East", homeuser);
		this.add(home, "Center");
		creatMenu();
		setSize(500, 500);
		setVisible(true);
	}
	int indexOfContact(String name){
		int index = list.size()+1;
		for(int i = 0 ; i<list.size();i++){
			if(list.get(i).getName().equals(name)){
				index = i;
				break;
			}
		}
		return index;
	}
	void creatMenu(){
		JMenuBar mb = new JMenuBar();
		JMenuItem[] menuItem = new JMenuItem[5];
		String[] itemTitle = {"Home", "연락처 추가", "연락처 저장", "연락처 가져오기", "스팸관리"};
		for (int i=0; i<menuItem.length;i++){
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(new MenuActionListener());
			mb.add(menuItem[i]);
		}
		this.setJMenuBar(mb);
	}
	class MenuActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String select = e.getActionCommand();
			if(select.equals("Home")){
				home();
			}else if(select.equals("연락처 추가")){
				addContact a = new addContact();
				home();
			}else if(select.equals("연락처 저장")){
				Output();
			}else if(select.equals("연락처 가져오기")){
				//Input();
			}else if(select.equals("스팸관리")){
				
			}	
		}
	}
	public void home(){
		try{
			model.clear();
		}catch(Exception ex){}
		try{
			for(int i=0;i<list.size();i++){
				model.add(0,list.get(i).name);
			}
		}catch(Exception ex){System.out.println("error");}
		namelist.addListSelectionListener(new JListHandler());
	}
	private class JListHandler implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent event){
			try{
				homeuser.setEditable(false);
				homeuser.setText("");
				String name = namelist.getSelectedValue();
				index = indexOfContact(name);
				System.out.println(index);
				if(index<list.size()+1){
					homeuser.append("이름 : "+list.get(index).name+"\n");
					homeuser.append("폰 번호 : "+list.get(index).phone_num+"\n");
					homeuser.append("집 번호  : "+list.get(index).home_num+"\n");
					homeuser.append("이메일 : "+list.get(index).email+"\n");
					homeuser.append("그룹 : "+list.get(index).group+"\n");
				}
			}catch(Exception ex){System.out.println("error2");}
		
		}
	}
	int indexOflist(String name){
		int index = list.size()+1;
		for(int i =0;i<list.size();i++){
			if(list.get(i).name.equals(name)){
				index =i;
			}
		}
		return index;
	}
	private class SearchAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.clear();
			search = findblank.getText();
			System.out.println(search);
			
			if(combo == 0){
				for(int i=0;i<list.size();i++){
					if(list.get(i).name.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
				System.out.println("이름");

			}else if(combo == 1){
				for(int i=0;i<list.size();i++){
					if(list.get(i).phone_num.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
				System.out.println("폰번");
			}else if(combo == 2){
				for(int i=0;i<list.size();i++){
					if(list.get(i).home_num.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
				System.out.println("집번");
			}else if(combo == 3){
				for(int i=0;i<list.size();i++){
					if(list.get(i).email.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
				System.out.println("이메일");
			}else if(combo == 4){
				for(int i=0;i<list.size();i++){
					if(list.get(i).group.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
				System.out.println("그룹");
			}
			namelist.addListSelectionListener(new JListHandler());
		}
	}
	private class SetAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setContact sc = new setContact();
			home();
		}
	}
	private class deleteAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION){
				list.remove(index);
				home();
			}
		}
	}
	public static void Input(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try{
			fin = new FileInputStream("Contactlist.dat");
			ois = new ObjectInputStream(fin);
			if(ois!=null){
				@SuppressWarnings("unchecked")
				ArrayList<Contact> list2 = (ArrayList<Contact>)ois.readObject();
				for (int i=0; i<list2.size(); i++){
					list.add(list2.get(i));
				}
				JOptionPane.showMessageDialog(null, "가져왔습니다", "가져오기", JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(Exception ex){
		}finally{
			try{
				ois.close();
				fin.close();
			}catch(IOException ioe){}
		} // finally
	}
	public static void Output(){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try{
			fout = new FileOutputStream("Contactlist.dat");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(list);
			oos.reset();
			oos.writeObject(list);
			oos.reset();
			JOptionPane.showMessageDialog(null, "저장되었습니다", "저장", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("저장되었습니다.");
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		} // finally
	}
	
	public static void main(String[] args){
		Input();
		ContactManager c = new ContactManager();
		Output();
	}
}

