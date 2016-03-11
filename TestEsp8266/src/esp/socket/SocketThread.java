package esp.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.servlet.ServletContext;

public class SocketThread extends Thread{

	ServletContext servletContext;
	ServerSocket serverSocket=null;
	public SocketThread(ServerSocket serverSocket,ServletContext servletContext)
	{
		try {
			if(serverSocket==null)
			{
				this.serverSocket=serverSocket;
				this.serverSocket=new ServerSocket(8877);
				System.out.println("socket start");
				this.servletContext=servletContext;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("SocketThread创建socket服务出错");  
            e.printStackTrace();  
		}
	}
	public void run()
	{  
        while(!this.isInterrupted()){  
            try {  
                Socket socket = serverSocket.accept();
                
                socket.setSoTimeout(1000);
                if(null != socket && !socket.isClosed()){   
					((HashMap<String,Socket>)servletContext.getAttribute("socketMap")).put("stm"+(int)Math.random()*100,socket);
                	System.out.println("转换成功");
                    //处理接受的数据  
                    new SocketOperate(socket).start();  
                }  
                  
            }catch (Exception e) {  
                e.printStackTrace(); 
                System.out.println("转换错误");
            }  
        }  
    } 
	public void closeSocketServer(){  
	       try {  
	            if(null!=serverSocket && !serverSocket.isClosed())  
	            {  
	             serverSocket.close();  
	            }  
	       } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	       }  
	     }  
}
