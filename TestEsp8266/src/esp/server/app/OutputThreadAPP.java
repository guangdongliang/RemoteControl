package esp.server.app;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * д��Ϣ�߳�
 * 
 * @author way
 * 
 */
public class OutputThreadAPP extends Thread {
	private OutputThreadMapAPP map;
	private ObjectOutputStream oos;
	private Object str;
	private boolean isStart = true;// ѭ����־λ
	private Socket socket;

	public OutputThreadAPP(Socket socket, OutputThreadMapAPP map) {
		try {
			this.socket = socket;
			this.map = map;
			oos = new ObjectOutputStream(socket.getOutputStream());// �ڹ���������ʵ�������������
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	// ����д��Ϣ�̣߳���������Ϣ֮�󣬻���run���������Խ�Լ��Դ
	public void setMessage(Object str) {
		this.str = str;
		synchronized (this) {
			notify();
		}
	}

	@Override
	public void run() {
		try {
			while (isStart) {
				// û����Ϣд����ʱ���̵߳ȴ�
				synchronized (this) {
					wait();
				}
				if (str != null) {
					oos.writeObject(str);
					oos.flush();
				}
			}
			if (oos != null)// ѭ�������󣬹ر������ͷ���Դ
				oos.close();
			if (socket != null)
				socket.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
