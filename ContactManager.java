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
	String[] findkind = {"�̸�", "�� ��ȣ", "�� ��ȣ", "�̸���", "�׷�"};
	public String search;
	public static int index;
	ContactManager(){
		setTitle("����ó");
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
		findstart = new JButton("�˻�");
		SearchAction sa = new SearchAction();
		findstart.addActionListener(sa);
		set = new JButton("����");
		SetAction seta = new SetAction();
		set.addActionListener(seta);
		delete = new JButton("����");
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
		String[] itemTitle = {"Home", "����ó �߰�", "����ó ����", "����ó ����", "����ó �˻�"};
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
			}else if(select.equals("����ó �߰�")){
				addContact a = new addContact();
			}else if(select.equals("����ó ����")){
				
			}else if(select.equals("����ó ����")){
				
			}else if(select.equals("����ó �˻�")){
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
					homeuser.append("�̸� : "+list.get(index).name+"\n");
					homeuser.append("�� ��ȣ : "+list.get(index).phone_num+"\n");
					homeuser.append("�� ��ȣ  : "+list.get(index).home_num+"\n");
					homeuser.append("�̸��� : "+list.get(index).email+"\n");
					homeuser.append("�׷� : "+list.get(index).group+"\n");
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
			System.out.println("����Ǿ����ϴ�.");
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		} // finally
	}
	void addContact(){
		System.out.print("�̸� : ");
		name = s.nextLine();
		name = s.nextLine();
		System.out.print("�� ��ȣ : ");
		phone_num = s.next();
		System.out.print("�� ��ȣ : ");
		home_num = s.next();
		System.out.print("�̸��� : ");
		email = s.next();
		System.out.print("���� �׷� : ");
		group = s.next();
		list.add(new Contact(name, phone_num, home_num, email, group));
		System.out.println("�߰� �Ǿ����ϴ�.");
	}
	void setContact(){
		listname();
		System.out.print("�����ϰ� ���� ��ȣ�� �Է��ϼ��� ");
		int number = s.nextInt();
		System.out.print("�̸� : ");
		name = s.nextLine();
		name = s.nextLine();
		System.out.print("�� ��ȣ : ");
		phone_num = s.next();
		System.out.print("�� ��ȣ : ");
		home_num = s.next();
		System.out.print("�̸��� : ");
		email = s.next();
		System.out.print("���� �׷� : ");
		group = s.next();
		list.set(number-1, new Contact(name, phone_num, home_num, email, group));
		System.out.println("�����Ǿ����ϴ�. ");
	}
	void listname(){
		for (int i=0; i<list.size();i++){
			System.out.println(i+1+") "+list.get(i).name);
		}
	}
	void addgroupMenu(){
		System.out.print("�߰��ϰ� ���� �׷���� �Է��ϼ��� : ");
		String groupname = s.nextLine();
		groupmenu.add(groupname);
		System.out.println("�׸��̸��� �߰��Ǿ����ϴ�.");
	}
	void deleteContact(){
		listname();
		System.out.print("�����ϰ� ���� ����ó ��ȣ�� �Է��ϼ��� : ");
		int number = s.nextInt();
		list.remove(number-1);
		System.out.println("�����Ǿ����ϴ�.");
	}
	void listContact(int i){
		System.out.println("�̸� : "+list.get(i).name);
		System.out.println("\t�޴��ȣ : "+list.get(i).phone_num);
		System.out.println("\t�� ��ȣ : "+list.get(i).home_num);
		System.out.println("\t�̸���  : "+list.get(i).email);
		System.out.println("\t�׷� : "+list.get(i).group);
	}
	/*void findContact(){
		System.out.print("1. �̸�  2. ��ȣ  3. �̸���  4. �׷�� : ");
		int number = s.nextInt();
		if(number == 1){
			System.out.print("�˻��Ͻ� �̸� : ");
			name = s.next();
			for (int i=0;i<list.size(); i++){
				if(list.get(i).name.contains(name)){
					listContact(i);
				}
			}
		}else if(number == 2){
			System.out.print("�˻��Ͻ� ��ȣ : ");
			String snum = s.next();
			for (int i=0;i<list.size(); i++){
				if (String.valueOf(list.get(i).home_num).contains(snum) || String.valueOf(list.get(i).phone_num).contains(snum)){
					listContact(i);
				}
			}		
		}else if(number == 3){
			System.out.print("�˻��Ͻ� �̸��� : ");
			email = s.next();
			for (int i=0;i<list.size(); i++){
				if(list.get(i).email.contains(email)){
					listContact(i);
				}
			}
		}else if(number == 4){
			System.out.print("�˻��Ͻ� �׷�� : ");
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
			System.out.print("1) ����ó �߰� 2) ����ó ���� 3) ����ó ���� 4) ����ó �˻� 5) �׷� �߰� 6) ���� : ");
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
		System.out.println("�����մϴ�.");
	}
	public static void main(String[] args){
		Input();
		ContactManager c = new ContactManager();
		c.Start();
		Output();
	}
}

