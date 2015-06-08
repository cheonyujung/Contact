package phone;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
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
	public static int index;
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
				index = cb.getSelectedIndex();
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
		String[] itemTitle = {"Home", "연락처 추가", "연락처 수정", "연락처 삭제", "연락처 검색"};
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
				try{
					model.clear();
				}catch(Exception ex){}
				try{
					for(int i=0;i<list.size();i++){
						model.add(0,list.get(i).name);
					}
				}catch(Exception ex){System.out.println("error");}
				namelist.addListSelectionListener(new JListHandler());
			}else if(select.equals("연락처 추가")){
				addContact a = new addContact();
			}else if(select.equals("연락처 수정")){
				
			}else if(select.equals("연락처 삭제")){
				
			}else if(select.equals("연락처 검색")){
				findContacts f = new findContacts();
			}	
		}
	}
	private class JListHandler implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent event){
			try{
				homeuser.setEditable(false);
				homeuser.setText("");
				String name = namelist.getSelectedValue();
				int index = indexOfContact(name);
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
			if(index == 0){
				for(int i=0;i<list.size();i++){
					if(list.get(i).name.contains(search)){
						model.addElement(list.get(i).name);
					}
				}

			}else if(index == 1){
				for(int i=0;i<list.size();i++){
					if(list.get(i).phone_num.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
			}else if(index == 2){
				for(int i=0;i<list.size();i++){
					if(list.get(i).home_num.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
			}else if(index == 3){
				for(int i=0;i<list.size();i++){
					if(list.get(i).email.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
			}else if(index == 4){
				for(int i=0;i<list.size();i++){
					if(list.get(i).group.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
			}
			namelist.addListSelectionListener(new JListHandler());
		}
	}
	private class SetAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setContact sc = new setContact();
			System.out.println("2");
		}
	}
	private class deleteAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			list.remove(index);
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
			System.out.println("저장되었습니다.");
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		} // finally
	}
	void addContact(){
		System.out.print("이름 : ");
		name = s.nextLine();
		name = s.nextLine();
		System.out.print("폰 번호 : ");
		phone_num = s.next();
		System.out.print("집 번호 : ");
		home_num = s.next();
		System.out.print("이메일 : ");
		email = s.next();
		System.out.print("속할 그룹 : ");
		group = s.next();
		list.add(new Contact(name, phone_num, home_num, email, group));
		System.out.println("추가 되었습니다.");
	}
	void setContact(){
		listname();
		System.out.print("수정하고 싶은 번호을 입력하세요 ");
		int number = s.nextInt();
		System.out.print("이름 : ");
		name = s.nextLine();
		name = s.nextLine();
		System.out.print("폰 번호 : ");
		phone_num = s.next();
		System.out.print("집 번호 : ");
		home_num = s.next();
		System.out.print("이메일 : ");
		email = s.next();
		System.out.print("속할 그룹 : ");
		group = s.next();
		list.set(number-1, new Contact(name, phone_num, home_num, email, group));
		System.out.println("수정되었습니다. ");
	}
	void listname(){
		for (int i=0; i<list.size();i++){
			System.out.println(i+1+") "+list.get(i).name);
		}
	}
	void addgroupMenu(){
		System.out.print("추가하고 싶은 그룹명을 입력하세요 : ");
		String groupname = s.nextLine();
		groupmenu.add(groupname);
		System.out.println("그릅이름이 추가되었습니다.");
	}
	void deleteContact(){
		listname();
		System.out.print("삭제하고 싶은 연락처 번호를 입력하세요 : ");
		int number = s.nextInt();
		list.remove(number-1);
		System.out.println("삭제되었습니다.");
	}
	void listContact(int i){
		System.out.println("이름 : "+list.get(i).name);
		System.out.println("\t휴대번호 : "+list.get(i).phone_num);
		System.out.println("\t집 번호 : "+list.get(i).home_num);
		System.out.println("\t이메일  : "+list.get(i).email);
		System.out.println("\t그룹 : "+list.get(i).group);
	}
	/*void findContact(){
		System.out.print("1. 이름  2. 번호  3. 이메일  4. 그룹명 : ");
		int number = s.nextInt();
		if(number == 1){
			System.out.print("검색하실 이름 : ");
			name = s.next();
			for (int i=0;i<list.size(); i++){
				if(list.get(i).name.contains(name)){
					listContact(i);
				}
			}
		}else if(number == 2){
			System.out.print("검색하실 번호 : ");
			String snum = s.next();
			for (int i=0;i<list.size(); i++){
				if (String.valueOf(list.get(i).home_num).contains(snum) || String.valueOf(list.get(i).phone_num).contains(snum)){
					listContact(i);
				}
			}		
		}else if(number == 3){
			System.out.print("검색하실 이메일 : ");
			email = s.next();
			for (int i=0;i<list.size(); i++){
				if(list.get(i).email.contains(email)){
					listContact(i);
				}
			}
		}else if(number == 4){
			System.out.print("검색하실 그룹명 : ");
			group = s.next();
			for (int i=0;i<list.size(); i++){
				if(list.get(i).group.contains(group)){
					listContact(i);
				}
			}
		}
	}*/
	void Start(){
		int mode = 0;
		while (mode != 6){
			System.out.println("=============================================================");
			System.out.print("1) 연락처 추가 2) 연락처 수정 3) 연락처 삭제 4) 연락처 검색 5) 그룹 추가 6) 종료 : ");
			mode = s.nextInt();
			System.out.println("=============================================================");
			if (mode == 1){
				addContact();
			}else if(mode == 2){
				setContact();
			}else if(mode == 3){
				deleteContact();
			}else if(mode == 4){
				//findContact();
			}else if(mode == 5){
				addgroupMenu();
			}else if(mode == 7){
				for(int i=0; i<list.size(); i++){
					listContact(i);
				}
			}
		}
		System.out.println("종료합니다.");
	}
	public static void main(String[] args){
		Input();
		ContactManager c = new ContactManager();
		c.Start();
		Output();
	}
}

