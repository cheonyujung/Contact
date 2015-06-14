package phone;

import java.io.Serializable;

public class Contact implements Serializable{
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
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
	
	public String getName(){
		return name;
	}
		
	public String getPhone_num(){
		return phone_num;
	}
		
	public String getHome_num(){
		return home_num;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getGroup(){
		return group;
	}
		
	public void setName(String name){
		this.name = name;
	}
	
	public void setPhone_num(String phone_num){
		this.phone_num = phone_num;
	}
	
	public void setHome_num(String home_num){
		this.home_num = home_num;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setGroup(String group){
		this.group = group;
	}
	
	public String PrintInfo(){
		return "이름 : "+getName()+"\n폰 번호 : "+getPhone_num()+"\n집 번호  : "+getHome_num()+"\n이메일 : "+getEmail()+"\n그룹 : "+getGroup()+"\n";
	}
}
