package esp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.way.chat.common.util.Constants;
import com.way.chat.common.util.DButil;
import com.way.chat.common.util.MyDate;

import esp.bean.User;
import esp.dao.UserDao;


public class UserDaoImpl implements UserDao {
	private Statement sta = null;
	@Override
	public ArrayList<User> register(User u) {
		Connection con = DButil.connect();
		String sql1 = "insert into users(_id,_name,_password,_time) values(?,?,?,?)";
		String sql = "select * from users where _id="+u.getpNumber();
		
		try {
			sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = sta.executeQuery(sql);
			ArrayList<User> registList =new ArrayList<User>();
			if(rs.first())
			{
				return null;
			}
			PreparedStatement ps = con.prepareStatement(sql1);
			ps.setLong(1, u.getpNumber());
			ps.setString(2, u.getName());
			ps.setString(3, u.getPassword());
			ps.setString(4, MyDate.getDateCN());
			int res = ps.executeUpdate();
			if (res > 0) {
				registList.add(u);
				return registList;
			}
		} catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			DButil.close(con);
		}
		return null;
	}

	@Override
	public ArrayList<User> login(User u) {
		Connection con = DButil.connect();
		String sql = "select * from users where _id="+u.getpNumber()+" and _password='"+u.getPassword()+"'";
		try {
			sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = sta.executeQuery(sql);
			if (rs.first()) {
				ArrayList<User> refreshList =new ArrayList<User>();
				refreshList.add(u);
				System.out.println("Õý³£µÇÂ¼");
				return refreshList;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DButil.close(con);
		}
		return null;
	}
	
	
	@Override
	public int logout(int id) {
		// TODO Auto-generated method stub
		return 1;
	}

	/*public static void main(String[] args) {
		User u = new User();
		UserDaoImpl dao = new UserDaoImpl();
		// u.setId(2016);
		// u.setName("qq");
		// u.setPassword("123");
		// u.setEmail("158342219@qq.com");
		// System.out.println(dao.register(u));
		// // System.out.println(dao.login(u));
		// // dao.logout(2016);
		// dao.setOnline(2016);
		// // dao.getAllId();
		List<User> list = dao.refresh(2016);
		System.out.println(list);
	}*/

}
