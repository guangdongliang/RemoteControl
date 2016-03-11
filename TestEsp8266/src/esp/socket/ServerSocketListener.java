package esp.socket;

import java.util.HashMap;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import esp.server.app.ServerAPP;

import java.net.*;

public class ServerSocketListener implements ServletContextListener{

	private ServerAPP serverAPP=new ServerAPP();
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		serverAPP.quit();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		serverAPP.start();
	}

	
}
