package esp.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import esp.server8266.OutputThreadMap;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
@WebServlet(urlPatterns="/refresh.jsp")
public class APPRefresh extends HttpServlet{

	ObjectOutputStream objectOutputStream;
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("手机连接成功");
		/*ServletResponse response1=(ServletResponse)arg0.getServletContext().getAttribute("wifi1");
			PrintStream out=new PrintStream(response1.getOutputStream());
			out.print("C");
		*/
		//bufferWriter=new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
		objectOutputStream=new ObjectOutputStream(response.getOutputStream());
		if(objectOutputStream!=null)
		{
			
			objectOutputStream.writeObject(OutputThreadMap.getInstance().getMap());
			objectOutputStream.flush();
		}
	}

	
}
