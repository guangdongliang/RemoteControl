package esp.dao;

import java.util.ArrayList;

import esp.bean.User;



public interface UserDao {
	//注册成功返回用户id
	public ArrayList<User> register(User u);

	public ArrayList<User> login(User u);

	public int logout(int id);
}
