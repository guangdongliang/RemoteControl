package esp.socket;

import javax.servlet.ServletContext;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class CheckThread extends Thread{


	Map<String,Socket> socketMap;
	ServletContext servletContext;
	public CheckThread(ServletContext servletContext) {
		this.socketMap = (HashMap<String,Socket>)servletContext.getAttribute("socketMap");
		this.servletContext=servletContext;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			System.out.println("����wifiģ������  "+socketMap.size());
			for(String str:socketMap.keySet())
			{
				Socket socket=socketMap.get(str);
				try {
					socket.sendUrgentData(0xFF);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					try {
						socketMap.remove(str);
						System.out.println("�ɹ��Ƴ�");
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} 
			}
			try {
				servletContext.setAttribute("socketMap", socketMap);
				//�߳̿�ʼ˯��
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("�߳�˯��ʧ��");
			}
		}
	}

	
}
