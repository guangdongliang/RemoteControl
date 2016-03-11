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
 * ����Ϣ�̺߳ʹ�����
 * 
 * @author way
 * 
 */
public class InputThreadAPP extends Thread{
	private Socket socket;// socket����
	private OutputThreadAPP outAPP;// ���ݽ�����д��Ϣ�̣߳���Ϊ����Ҫ���û��ظ���Ϣ��
	private OutputThreadMapAPP map;// д��Ϣ�̻߳�����
	private ObjectInputStream ois;// ����������
	private boolean isStart = true;// �Ƿ�ѭ������Ϣ
    private OutputThreadMap outputThreadMap;
    private int second;
    private int startCheck;
	public InputThreadAPP(Socket socket, OutputThreadAPP outAPP, OutputThreadMapAPP map) {
		this.socket = socket;
		this.outAPP = outAPP;
		this.map = map;
		this.outputThreadMap=OutputThreadMap.getInstance();
		try {
			ois = new ObjectInputStream(socket.getInputStream());// ʵ��������������
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setStart(boolean isStart) {// �ṩ�ӿڸ��ⲿ�رն���Ϣ�߳�
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
						System.out.println("�ɹ��޳���APP");
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
				// ��ȡ��Ϣ
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
	 * ����Ϣ�Լ�������Ϣ���׳��쳣
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readMessage() throws IOException, ClassNotFoundException {
		Object readObject = ois.readObject();// �����ж�ȡ����
		second=Calendar.getInstance().get(Calendar.SECOND);
		startCheck=1;
		if (readObject != null && readObject instanceof TranObject) {
			TranObject read_tranObject = (TranObject<String>) readObject;// ת���ɴ������
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
			case RED:// ������˳����������ݿ�����״̬��ͬʱȺ���������������û�
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
			case DIODE:// �����ת����Ϣ�������Ⱥ����
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
