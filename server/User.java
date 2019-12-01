package server;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userName;
	private int passHash;
	private ArrayList<LotPermits> permits;
	public User(String user, int pass, ArrayList<LotPermits> permitlist) {
		userName = user;
		passHash = pass;
		permits = permitlist;
	}
	
	public void changePassword(int newpass, int oldpass) throws InvalidPasswordException{
		if(oldpass != passHash)
			throw new InvalidPasswordException();
		passHash = newpass;
	}
	
	public ArrayList<LotPermits> getPermits(int pass) throws InvalidPasswordException{
		if(pass != passHash)
			throw new InvalidPasswordException();
		return permits;
	}
}
