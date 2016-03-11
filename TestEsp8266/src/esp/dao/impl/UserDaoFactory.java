package esp.dao.impl;

import esp.dao.UserDao;

public class UserDaoFactory {
	private static UserDao dao;
	
	//����ģʽ
	public static UserDao getInstance() {
		if (dao == null) {
			dao = new UserDaoImpl();
		}
		return dao;
	}
}
