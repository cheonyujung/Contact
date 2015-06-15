package phone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
	ReaderWriter rw= new ReaderWriter();
	JLabel id, pw;
	JTextField tfld;
	JPasswordField tfPwd;
	JPanel login, loginTotal, empty, loginButton;
	ContactLogin(){
		setTitle("로그인");
		rw.InputHashMapID("C:\\Users\\yujung\\workspace\\phone\\src\\phone\\12.txt");
		this.setBounds(200, 200, 350, 250);
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
		JButton searchidpw = new JButton("아이디 비밀번호 찾기");
		searchidpw.addActionListener(new SearchHandler());
		login = new JPanel();
		login.setLayout(new GridLayout(2,2, 20, 20));
		login.setBackground(new Color(33,189,169));
		login.add(id);
		login.add(tfld);
		login.add(pw);
		login.add(tfPwd);
		loginButton = new JPanel();
		JPanel otherButton = new JPanel();
		otherButton.setLayout(new GridLayout(1, 2));
		otherButton.setBackground(new Color(33,189,169));
		otherButton.add(join);
		otherButton.add(searchidpw);
		loginButton.setLayout(new GridLayout(2, 1));
		loginButton.add(loginb);
		loginButton.add(otherButton);
		empty = new JPanel();
		empty.setSize(200, 200);
		empty.setBackground(new Color(33,189,169));
		JPanel empty1 = new JPanel();
		empty1.setSize(200, 200);
		empty1.setBackground(new Color(33,189,169));
		loginTotal = new JPanel();
		loginTotal.setLayout(new BorderLayout(20, 20));
		loginTotal.add("Center", login);
		loginTotal.add("South", loginButton);
		loginTotal.add("East", empty);
		loginTotal.add("North", empty1);
		loginTotal.setBackground(new Color(33,189,169));
		this.add(loginTotal);
	}
	class EventHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			boolean loginok = false;
			String id = tfld.getText();
			String password = new String(tfPwd.getPassword());
			System.out.println(id+" "+password);
			for(String i : ContactManager.idpw.keySet()){
				if(i.equals(id)){
					if(ContactManager.idpw.get(i).equals(password)){
						loginok = true;
					}
				}
			}
			if(loginok){
				ContactManager.getId(id);
				ContactManager.getPw(password);
				rw.Input("C:\\Users\\yujung\\workspace\\phone\\src\\phone\\"+id+".txt");
				rw.InputContactwithID("C:\\Users\\yujung\\workspace\\phone\\src\\phone\\"+id+"id.txt" );
				setVisible(false);
				ContactManager cm = new ContactManager();
				cm.setVisible(true);
				
			}else{
				JOptionPane.showMessageDialog(null, "아이디나 비밀번호를 확인해주세요");
			}
		}
	}
	class JoinHandler extends JFrame implements ActionListener{
		private static final long serialVersionUID = 1L;
		private JTextField tfid, tfname, tfphone, tfhome, tfemail, tfanswer;
		private JPasswordField tpsf;
		JComboBox<String> combo ;
		JoinHandler(){
			setTitle("회원 가입");
			setBounds(200,200,500,500);
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
			String[] Question = {"가장 좋아하는 음식은?", "가장 좋아하는 색깔은?", "가장 존경하는 선생님은?", "자신의 고향은?", "나의 별명은?"};
			combo = new JComboBox<String>(Question);
			tfanswer = new JTextField();
			JPanel joinpage = new JPanel();
			joinpage.setLayout(new GridLayout(8, 2, 20, 20));
			joinpage.setBackground(new Color(155,255,148));
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
			joinpage.add(combo);
			joinpage.add(tfanswer);
			joinpage.add(joinok);
			joinpage.add(joincancle);
			this.add(joinpage);
		}
		public void actionPerformed(ActionEvent e){
			JoinHandler j = new JoinHandler();
			j.setVisible(true);
		}
		class join implements ActionListener{
			public void actionPerformed(ActionEvent e){
				String id = tfid.getText();
				String name = tfname.getText();
				@SuppressWarnings("deprecation")
				String password = tpsf.getText();
				String phone_num = tfphone.getText();
				String home_num = tfhome.getText();
				String email = tfhome.getText();
				int quest = combo.getSelectedIndex();
				String answer = tfanswer.getText();
 				if(id.equals("") || name.equals("") || password.equals("") || phone_num.equals("")){
					JOptionPane.showMessageDialog(null, "아이디, 이름, 비밀번호, 폰번호 중에서 입력 되지 않았습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}else{
					if(email.equals("")){
						email = " ";
					}
					if(home_num.equals("")){
						home_num = " ";
					}
					ContactManager.listid.add(new ContactWithId(name, phone_num, home_num, email, null, id, password, quest, answer));
					ContactManager.idpw.put(id, password);
					ContactManager.getId(id);
					ContactManager.getPw(password);
					makefile(id);
					makefile(id+"id");
				}
				setVisible(false);
			}
		    public void makefile(String id){
		        String fileName = "C:\\Users\\yujung\\workspace\\phone\\src\\phone\\"+id+".txt" ; 
		        try{
		            File file = new File(fileName) ; // 파일 객체 생성
		            FileWriter fw = new FileWriter(fileName);
		            fw.write("");
		            fw.close();   
		        }catch(Exception e){
		            e.printStackTrace();
		        }
			}
		}
		class joincancle implements ActionListener{
			public void actionPerformed(ActionEvent e){
				setVisible(false);	
			}
		}
	}
	class SearchHandler extends JFrame implements ActionListener{
		private static final long serialVersionUID = 1L;
		JPanel search;
		JTextField tfname, tfphone_num, tfanswer;
		JComboBox<String> combo ;
		SearchHandler(){
			setTitle("아이디, 비밀번호 찾기");
			setBounds(200, 200, 400, 300);
			String[] Question = {"가장 좋아하는 음식은?", "가장 좋아하는 색깔은?", "가장 존경하는 선생님은?", "자신의 고향은?", "나의 별명은?"};
			combo = new JComboBox<String>(Question);
			search = new JPanel();
			search.setLayout(new GridLayout(3, 1, 10, 10));
			JPanel search1 = new JPanel();
			search1.setLayout(new GridLayout(2,2,10,10));
			search1.setBackground(new Color(153,224,0));
			JPanel search2 = new JPanel();
			search2.setLayout(new GridLayout(2,1,10,10));
			search2.setBackground(new Color(153,224,0));
			JPanel search3 = new JPanel();
			search3.setLayout(new FlowLayout());
			search3.setBackground(new Color(153,224,0));
			JLabel n = new JLabel("Name :", JLabel.RIGHT);
			JLabel p = new JLabel("Phone : ", JLabel.RIGHT);
			tfname = new JTextField(20);
			tfphone_num = new JTextField(20);
			tfanswer = new JTextField();
			JButton ok = new JButton("확인");
			JButton x = new JButton("취소");
			ok.addActionListener(new Check());
			x.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					setVisible(false);
				}
			});
			search1.add(n);
			search1.add(tfname);
			search1.add(p);
			search1.add(tfphone_num);
			search2.add(combo);
			search2.add(tfanswer);
			search3.add(ok);
			search3.add(x);
			search.add(search1);
			search.add(search2);
			search.add(search3);
			search.setBackground(new Color(153,224,0));
			this.add(search);
		}
		
		public void actionPerformed(ActionEvent e){
			SearchHandler s = new SearchHandler();
			s.setVisible(true);
		}
		class Check extends JFrame implements ActionListener{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e){
				String name = tfname.getText();
				String phone_num = tfphone_num.getText();
				int Quest = combo.getSelectedIndex();
				String answer = tfanswer.getText();
				int count = 0;
				for(String i : ContactManager.idpw.keySet()){
					rw.InputContactwithID("C:\\Users\\yujung\\workspace\\phone\\src\\phone\\"+i+"id.txt" );
					if(ContactManager.listid.get(count).name.equals(name) && ContactManager.listid.get(count).getPhone_num().equals(phone_num)){
						if((ContactManager.listid.get(count).question == Quest) && ContactManager.listid.get(count).getAnswer().equals(answer)){
							JOptionPane.showMessageDialog(null, "ID : "+ContactManager.listid.get(count).id+"\nPassWord : "+ContactManager.listid.get(count).pw+"\n다시 잊어버리기 않게 조심하세요!", "아이디, 비번 공개", JOptionPane.PLAIN_MESSAGE);
							setVisible(false);
							break;
						}
					}
					count++;
				}
				JOptionPane.showMessageDialog(null, "입력하신 정보에 맞는 아이디가 없습니다.", "아이디, 비번 에러", JOptionPane.ERROR_MESSAGE);
				setVisible(false);
			}
		}
	}
}
class ContactWithId extends Contact{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String pw;
	String name;
	String phone_num;
	String email;
	String home_num;
	String group;
	int question;
	String answer;
	ContactWithId(String n, String p, String h, String e, String g,String id,String pw,int q, String ans) {
		super(n, p, h, e, g);
		this.id = id;
		this.pw = pw;
		this.name  = n;
		this.phone_num = p;
		this.home_num = h;
		this.email = e;
		this.group = g;
		this.question = q;
		this.answer = ans;
	}
	public String getId(){
		return id;
	}
	public String getPw(){
		return pw;
	}
	public int getQuestion(){
		return question;
	}
	public String getAnswer(){
		return answer;
	}
	public void setId(String id){
		this.id = id;
	}
	public void setPw(String pw){
		this.pw = pw;
	}
}