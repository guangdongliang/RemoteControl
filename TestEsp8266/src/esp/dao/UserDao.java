package esp.dao;

import java.util.ArrayList;

import esp.bean.User;



public interface UserDao {
	//ע��ɹ������û�id
	public ArrayList<User> register(User u);

	public ArrayList<User> login(User u);

	public int logout(int id);
}
