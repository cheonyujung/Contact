package phone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class ReaderWriter {
	@SuppressWarnings("unchecked")
	public void Input(String f){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try{
			fin = new FileInputStream(f);
			ois = new ObjectInputStream(fin);
			if(ois!=null){
				ArrayList<Contact> list2 = (ArrayList<Contact>)ois.readObject();
				for (int i=0; i<list2.size(); i++){
					ContactManager.list.add(list2.get(i));
				}
			}
		}catch(Exception ex){
		}finally{
			try{
				if(ois!=null && fin!=null){
				ois.close();
				fin.close();
				}
			}catch(IOException ioe){}
		} // finally
	}
	public void InputContactwithID(String f){
		FileInputStream fin1 = null;
		ObjectInputStream ois1 = null;
		try{
			fin1 = new FileInputStream(f);
			ois1 = new ObjectInputStream(fin1);
			if(ois1!=null){
				@SuppressWarnings("unchecked")
				ArrayList<ContactWithId> listinputid = (ArrayList<ContactWithId>)ois1.readObject();
				for (int i=0; i<listinputid.size(); i++){
					ContactManager.listid.add(listinputid.get(i));
				}
			}
		}catch(Exception ex){
		}finally{
			try{
				if(ois1!=null && fin1!=null){
				ois1.close();
				fin1.close();
				}
			}catch(IOException ioe){}
		} // finally
	}
	public void InputHashMapID(String f){
		File file = new File(f) ;
		FileInputStream fin2 = null;
		ObjectInputStream ois2 = null;
		try{
			fin2 = new FileInputStream(file);
			ois2 = new ObjectInputStream(fin2);
				@SuppressWarnings("unchecked")
				HashMap<String,String> listid2 = (HashMap<String,String>)ois2.readObject();
				for (String i : listid2.keySet()){
					ContactManager.idpw.put(i,listid2.get(i));
				}
		}catch(Exception ex){
		}finally{
			try{
				if(ois2!=null && fin2!=null){
					ois2.close();
					fin2.close();
				}
			}catch(IOException ioe){}
		} // finally
	}
	public void Output(String f){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try{
			fout = new FileOutputStream(f);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(ContactManager.list);
			oos.reset();
			oos.writeObject(ContactManager.list);
			oos.reset();
		}catch(Exception ex){
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		} // finally
	}
	public void OutputContactwithID(String f){
		FileOutputStream fout1 = null;
		ObjectOutputStream oos1 = null;
		try{
			fout1 = new FileOutputStream(f);
			oos1 = new ObjectOutputStream(fout1);
			oos1.writeObject(ContactManager.listid);
			oos1.reset();
			oos1.writeObject(ContactManager.listid);
			oos1.reset();
		}catch(Exception ex){
		}finally{
			try{
				oos1.close();
				fout1.close();
			}catch(IOException ioe){}
		} // finally
	}
	public void OutputHashMapID(String f){
		FileOutputStream fout2 = null;
		ObjectOutputStream oos2 = null;
		try{
			fout2 = new FileOutputStream(f);
			oos2 = new ObjectOutputStream(fout2);
			oos2.writeObject(ContactManager.idpw);
			oos2.reset();
			oos2.writeObject(ContactManager.idpw);
			oos2.reset();
		}catch(Exception ex){
		}finally{
			try{
				oos2.close();
				fout2.close();
			}catch(IOException ioe){}
		} // finally
	}
}
