package esp.servlet;

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns="/C.jsp")
public class No1Test extends HttpServlet{

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			ServletContext ser=request.getServletContext();
			ser.setAttribute("wifi1", response);
			PrintStream out=new PrintStream(response.getOutputStream());
			out.print("C");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
}
