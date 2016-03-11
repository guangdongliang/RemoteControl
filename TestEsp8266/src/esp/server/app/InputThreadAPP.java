package esp.server.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.way.chat.common.tran.bean.TranObject;
import com.way.chat.common.tran.bean.TranObjectType;
import com.way.chat.common.util.MyDate;

import esp.server8266.OutputThread;
import esp.server8266.OutputThreadMap;

/**
 * 读消息线程和处理方法
 * 
 * @author way
 * 
 */
public class InputThreadAPP extends Thread{
	private Socket socket;// socket对象
	private OutputThreadAPP outAPP;// 传递进来的写消息线程，因为我们要给用户回复消息啊
	private OutputThreadMapAPP map;// 写消息线程缓存器
	private ObjectInputStream ois;// 对象输入流
	private boolean isStart = true;// 是否循环读消息
    private OutputThreadMap outputThreadMap;
    private int second;
    private int startCheck;
	public InputThreadAPP(Socket socket, OutputThreadAPP outAPP, OutputThreadMapAPP map) {
		this.socket = socket;
		this.outAPP = outAPP;
		this.map = map;
		this.outputThreadMap=OutputThreadMap.getInstance();
		try {
			ois = new ObjectInputStream(socket.getInputStream());// 实例化对象输入流
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setStart(boolean isStart) {// 提供接口给外部关闭读消息线程
		this.isStart = isStart;
	}

	private void checkAPP()
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
						setStart(false);
						outAPP.setMessage(null);
						outAPP.setStart(false);
						System.out.println("成功剔除该APP");
					}	
					try {
						Thread.currentThread().sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	@Override
	public void run() {
		try {
			while (isStart) {
				// 读取消息
				readMessage();
			}
			if (ois != null) {
				ois.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读消息以及处理消息，抛出异常
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readMessage() throws IOException, ClassNotFoundException {
		Object readObject = ois.readObject();// 从流中读取对象
		second=Calendar.getInstance().get(Calendar.SECOND);
		startCheck=1;
		if (readObject != null && readObject instanceof TranObject) {
			TranObject read_tranObject = (TranObject<String>) readObject;// 转换成传输对象
			List<String> list=read_tranObject.getGroup();
			System.out.println(read_tranObject.getObject());
			System.out.println(read_tranObject.getFromID());
			System.out.println(read_tranObject.getGroup().get(0));
			switch (read_tranObject.getType()) {
			case MOTOR:
				
				for(String i:list)
				{
					OutputThread out=outputThreadMap.getById(i);
					if(out!=null)
					{
						System.out.println(read_tranObject.getObject());
						System.out.println(read_tranObject.getFromID());
						out.setMessage(read_tranObject.getGroup().get(0).toString());
						map.add(i, outAPP);
					}
				}
				break;
			case GREEN:
				for(String i:list)
				{
					OutputThread out=outputThreadMap.getById(i);
					if(out!=null)
					{
						System.out.println(read_tranObject.getObject());
						System.out.println(read_tranObject.getFromID());
						out.setMessage(read_tranObject.getGroup().get(0).toString());
						map.add(i, outAPP);
					}
				}
				break;
			case RED:// 如果是退出，更新数据库在线状态，同时群发告诉所有在线用户
				for(String i:list)
				{
					OutputThread out=outputThreadMap.getById(i);
					if(out!=null)
					{
						System.out.println(read_tranObject.getObject());
						System.out.println(read_tranObject.getFromID());
						out.setMessage(read_tranObject.getGroup().get(0).toString());
						map.add(i, outAPP);
					}
				}
				break;
			case DIODE:// 如果是转发消息（可添加群发）
				for(String i:list)
				{
					OutputThread out=outputThreadMap.getById(i);
					if(out!=null)
					{
						System.out.println(read_tranObject.getObject());
						System.out.println(read_tranObject.getFromID());
						out.setMessage(read_tranObject.getGroup().get(0).toString());
						map.add(i, outAPP);
					}
				}
				break;
			case BUZZER:
				for(String i:list)
				{
					OutputThread out=outputThreadMap.getById(i);
					if(out!=null)
					{
						System.out.println(read_tranObject.getObject());
						System.out.println(read_tranObject.getFromID());
						out.setMessage(read_tranObject.getGroup().get(0).toString());
						map.add(i, outAPP);
					}
				}
				break;
			default:
				break;
			}
		}

	}
}
