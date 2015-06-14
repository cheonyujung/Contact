package phone;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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
	static ArrayList<Contact> list = new ArrayList<Contact>();
	Scanner s = new Scanner(System.in);
	String name, email, group, phone_num, home_num;
	Set<String> groupmenu = new HashSet<String>();
	private JList<String> namelist;
	private JTextArea homeuser;
	private JTextField findblank;
	private DefaultListModel<String> model;
	Container ctp;
	JButton set;
	JButton delete;
	JButton findstart;
	JPanel home;
	JPanel find;
	JPanel spam;
	private JPanel Spammenu;
	String[] findkind = {"이름", "폰 번호", "집 번호", "이메일", "그룹"};
	public String search;
	public static int index, combo,selectright;
	ContactManager(){
		setTitle("연락처");
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
		findblank = new JTextField(15);
		findstart = new JButton("검색");
		SearchAction sa = new SearchAction();
		findstart.addActionListener(sa);
		set = new JButton("수정");
		set.addActionListener(new setListener());
		delete = new JButton("삭제");
		deleteAction da = new deleteAction();
		delete.addActionListener(da);
		find.add(kind);
		find.add(findblank);
		find.add(findstart);
		find.add(set);
		find.add(delete);
		JPopupMenu rightMenu = new JPopupMenu("Popup");
		JMenuItem item = new JMenuItem("스팸 추가");
	    item.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
                list.get(selectright).setGroup("spam");
	        }
	    });
	    JMenuItem item2 = new JMenuItem("스팸 삭제");
	    item2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		list.get(selectright).setGroup("before");
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
                	 System.out.println("1");
                	 rightMenu.show(e.getComponent(), e.getX(), e.getY());
                 }
            }

            public void mouseReleased(MouseEvent e)
            {
                 if ( SwingUtilities.isRightMouseButton(e) )
                 {
                      JList list = (JList)e.getSource();
                      selectright = indexOfContact(String.valueOf(list.getSelectedValue()));
                      System.out.println(selectright);
                      System.out.println(list.getSelectedValue() + " selected");
                 }
            }
       });
		this.add(new JScrollPane(namelist));
		home.add("North", find);
		home.add("Center", namelist);
		home.add("East", homeuser);
		this.add(home, "Center");//
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
				new addContact();
				home();
			}else if(select.equals("연락처 저장")){
				Output();
			}else if(select.equals("연락처 가져오기")){
				//Input();
			}else if(select.equals("스팸관리")){
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
		public void check(MouseEvent e){
			if(e.isPopupTrigger()){
				System.out.println("right");
			}
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
				System.out.println(list2);
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
		ContactLogin l = new ContactLogin();
		ContactManager c = new ContactManager();
		Output();
	}
}

