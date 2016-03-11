package esp.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.way.chat.common.tran.bean.TranObject;
import com.way.chat.common.tran.bean.TranObjectType;
import com.way.chat.common.util.HttpUtil;

import esp.bean.User;
import esp.dao.UserDao;
import esp.dao.impl.UserDaoFactory;
@WebServlet(urlPatterns="/regist.jsp")
public class Register extends HttpServlet{

	
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String pNumber=request.getParameter("pNumber");
		String pass=request.getParameter("pass");
		String name=request.getParameter("name");
		User user=new User();
		user.setpNumber(Long.parseLong(pNumber));
		user.setPassword(pass);
		user.setName(name);
		UserDao userDaoImpl=UserDaoFactory.getInstance();
		List<User> list=userDaoImpl.register(user);
		Map map=new HashMap<>();
		map.put("users", list);
		System.out.println("³É¹¦×¢²á");
		HttpUtil.http(map, response);
	}
	
	

}
