package phone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ContactManager extends JFrame /*implements ActionListener*/{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ReaderWriter rw = new ReaderWriter();
	static ArrayList<Contact> list = new ArrayList<Contact>();
	static ArrayList<ContactWithId> listid = new ArrayList<ContactWithId>(); 
	static HashMap<String, String> idpw = new HashMap<String, String>();
	Scanner s = new Scanner(System.in);
	String name, email, group, phone_num, home_num;
	Set<String> groupmenu = new HashSet<String>();
	private JList<String> namelist;
	private JTextArea homeuser;
	private JTextField findblank;
	private DefaultListModel<String> model;
	static String id;
	static String pw;
	Container ctp;
	JButton set;
	JButton delete;
	JButton findstart;
	JPanel home;
	JPanel find;
	JPanel spam;
	String[] findkind = {"�̸�", "�� ��ȣ", "�� ��ȣ", "�̸���", "�׷�"};
	public String search;
	public static int index, combo,selectright;
	ContactManager(){
		setTitle("����ó");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ContactLogin cl = new ContactLogin();
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
		this.addWindowListener(new windowCloseListener());
		findblank = new JTextField(15);
		findstart = new JButton("�˻�");
		SearchAction sa = new SearchAction();
		findstart.addActionListener(sa);
		set = new JButton("����");
		set.addActionListener(new setListener());
		delete = new JButton("����");
		deleteAction da = new deleteAction();
		delete.addActionListener(da);
		find.add(kind);
		find.add(findblank);
		find.add(findstart);
		find.add(set);
		find.add(delete);
		JPopupMenu rightMenu = new JPopupMenu("Popup");
		JMenuItem item = new JMenuItem("���� �߰�");
	    item.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(list.get(selectright).getGroup().equals("spam")){
	    			JOptionPane.showMessageDialog(null, "�̹� �������� ó�����ֽ��ϴ�. ", "���Կ���", JOptionPane.ERROR_MESSAGE);
	    		}else{
	    			list.get(selectright).setGroup("spam");
	    		}
	        }
	    });
	    JMenuItem item2 = new JMenuItem("���� ����");
	    item2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(!list.get(selectright).getGroup().equals("spam")){
	    			JOptionPane.showMessageDialog(null, "���� ������ �ƴմϴ�. ", "���Կ���", JOptionPane.ERROR_MESSAGE);
	    		}else{
	    			list.remove(selectright);
	    		}
	    	}
	    });
	    rightMenu.add(item);
	    rightMenu.add(item2);
		homeuser = new JTextArea("");
		homeuser.setPreferredSize(new Dimension(250,500));
		model = new DefaultListModel<String>();
		namelist = new JList<String>(model);
		namelist.setVisibleRowCount(15);
		namelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		namelist.addListSelectionListener(new JListHandler());
		namelist.addMouseListener( new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                 if ( SwingUtilities.isRightMouseButton(e) )
                 {
                	 rightMenu.show(e.getComponent(), e.getX(), e.getY());
                 }
            }

            public void mouseReleased(MouseEvent e)
            {
                 if ( SwingUtilities.isRightMouseButton(e) )
                 {
                	 @SuppressWarnings("unchecked")
                     JList<String> list = (JList<String>)e.getSource();
                     selectright = indexOfContact(String.valueOf(list.getSelectedValue()));
                 }
            }
       });
		JScrollPane scroll = new JScrollPane(namelist);
		find.setBackground(new Color(33,189,169));
		home.setBackground(new Color(33,189,169));
		JPanel empty = new JPanel();
		empty.setBackground(new Color(33,189,169));
		home.add("North", find);
		home.add("Center", scroll);
		home.add("East", homeuser);
		home.add("South", empty);
		this.add(home, "Center");//
		creatMenu();
		setSize(500, 500);
		setVisible(true);
		home();
	}
	static void getId(String id){
		ContactManager.id = id;
	}
	static void getPw(String pw){
		ContactManager.pw= pw;
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
		JMenuItem[] menuItem = new JMenuItem[3];
		String[] itemTitle = {"Home", "����ó �߰�", "���԰���"};
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
			}else if(select.equals("����ó �߰�")){
				new addContact();
				home();
			}else if(select.equals("���԰���")){
				Spam();
			}	
		}
	}

	public void Spam(){
		try{
			model.clear();
		}catch(Exception ex){}
		try{
			System.out.println(list.size());
			for(int i=0;i<list.size();i++){
				if(list.get(i).group.equals("spam")){
					model.addElement(list.get(i).getName());
				}
			}
		}catch(Exception ex){System.out.println("error");}
	}
	public void home(){
		try{
			model.clear();
		}catch(Exception ex){}
		try{
			for(int i=0;i<list.size();i++){
				if(!list.get(i).group.equals("spam")){
					model.addElement(list.get(i).getName());
				}
			}
		}catch(Exception ex){System.out.println("error");}

	}
	private class JListHandler implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent event){
			try{
				homeuser.setEditable(false);
				homeuser.setText("");
				String name = namelist.getSelectedValue();
				System.out.println(name);
				index = indexOfContact(name);
				System.out.println("index"+index);
				if(index<list.size()+1){
					homeuser.append(list.get(index).PrintInfo());
				}
			}catch(Exception ex){System.out.println("error2");}
		
		}
	}
	class setListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setContact sc =new setContact();
			String name = namelist.getSelectedValue();
			index = indexOfContact(name);
			sc.createPanel(list.get(index));
			sc.pack();
			sc.setVisible(true);
			home();
		}
	}
	static int indexOflist(String name){
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
				System.out.println("�̸�");

			}else if(combo == 1){
				for(int i=0;i<list.size();i++){
					if(list.get(i).phone_num.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
				System.out.println("����");
			}else if(combo == 2){
				for(int i=0;i<list.size();i++){
					if(list.get(i).home_num.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
				System.out.println("����");
			}else if(combo == 3){
				for(int i=0;i<list.size();i++){
					if(list.get(i).email.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
				System.out.println("�̸���");
			}else if(combo == 4){
				for(int i=0;i<list.size();i++){
					if(list.get(i).group.contains(search)){
						model.addElement(list.get(i).name);
					}
				}
				System.out.println("�׷�");
			}
			namelist.addListSelectionListener(new JListHandler());
		}
	}
	private class deleteAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION){
				list.remove(index);
				home();
			}
		}
	}
	class windowCloseListener implements WindowListener{
		public void windowClosing(WindowEvent e){
			System.out.println(ContactManager.id);
			rw.Output("C:\\Users\\yujung\\workspace\\phone\\src\\phone\\"+ContactManager.id+".txt");
			rw.OutputContactwithID("C:\\Users\\yujung\\workspace\\phone\\src\\phone\\"+ContactManager.id+"id.txt" );
			rw.OutputHashMapID("C:\\Users\\yujung\\workspace\\phone\\src\\phone\\12.txt");
			dispose();
		}

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static void main(String[] args){
		//Input();
		ContactLogin l = new ContactLogin();
		l.setVisible(true);
		//ContactManager c = new ContactManager();
		//Output();
	}
}

