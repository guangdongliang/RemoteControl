package esp.server8266;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.way.chat.common.tran.bean.TextMessage;
import com.way.chat.common.tran.bean.TranObject;
import com.way.chat.common.tran.bean.TranObjectType;

import esp.server.app.OutputThreadAPP;
import esp.server.app.OutputThreadMapAPP;





public class InputThread extends Thread{

	private Socket socket;// socket对象
	private OutputThread out;// 传递进来的写消息线程，因为我们要给用户回复消息啊
	private OutputThreadMap map;// 写消息线程缓存器
	private InputStream is;// 对象输入流
	private boolean isStart = true;// 是否循环读消息
	private int second;
	private String ID;
	public String password;
	public PasswordMap pMap;
	private int startCheck;
	BufferedReader in;
	OutputThreadMapAPP outAPP;
	public InputThread(Socket socket, OutputThread out, OutputThreadMap map,PasswordMap pMap) {
		this.socket = socket;
		this.out = out;
		this.map = map;
		this.pMap=pMap;
		this.outAPP=OutputThreadMapAPP.getInstance();
		try {
			is = socket.getInputStream();// 实例化对象输入流
			in = new BufferedReader(new InputStreamReader(is));
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setStart(boolean isStart) {// 提供接口给外部关闭读消息线程
		this.isStart = isStart;
	}
	
	private void checkTime()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isStart)
				{
					System.out.println(Calendar.getInstance().get(Calendar.SECOND));
					if((Calendar.getInstance().get(Calendar.SECOND)-second>3)&&startCheck==1)
					{
						OutputThread out=map.getById(ID);
						out.setMessage("");
						out.setStart(false);
						map.remove(ID);
						pMap.remove(ID);
						setStart(false);
						System.out.println("成功移除并关闭资源");
						break;
					}
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();;
		
		
	}
	@Override
	public void run() {
		try {
			checkTime();
			
			while (isStart) {
				
				
				System.out.print(second);
				// 读取消息
				System.out.println("开始读消息");
				readMessage();
				
			}
			if (is != null) {
				is.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}

	}
	
	/**
	 * 读消息以及处理消息，抛出异常
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readMessage() throws IOException, ClassNotFoundException {
		String str=null;
		
			str=in.readLine();
			//if(password==null)
			{
				System.out.println(str);
			    
				if(str.length()>8&&str.charAt(0)=='I'&&str.charAt(1)=='D')
				{
					String id=str.substring(2, 4);
					ID=id;
					map.add(ID, out);
					System.out.println("成功加入map");
					second=Calendar.getInstance().get(Calendar.SECOND);
					startCheck=1;
				}
				System.out.println("ID"+ID);
				if(str.length()>8&&str.charAt(4)=='P'&&str.charAt(5)=='A')
				{
					String password=str.substring(6);
					this.password=password;
					pMap.add(ID, password);
					System.out.println("密码"+password);
				}
				if((str.length()>6)&&str.charAt(0)=='S'&&str.charAt(1)=='U')
				{
					TranObject tranObject=new TranObject<TextMessage>(TranObjectType.DOSUCCESS);
					tranObject.setFromID(ID);
					TextMessage textMessage=new TextMessage();
					textMessage.setMessage(str);
					tranObject.setObject(textMessage);
					outAPP.getById(ID).setMessage(tranObject);
				}
			}
			
		

	}
}
