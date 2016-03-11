package esp.server8266;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


/**
 * д��Ϣ�߳�
 * 
 * @author way
 * 
 */
public class OutputThread extends Thread {
	private OutputThreadMap map;
	private BufferedWriter  bufferWriter;
	private String str;
	private boolean isStart = true;// ѭ����־λ
	private Socket socket;

	public OutputThread(Socket socket, OutputThreadMap map) {
		try {
			this.socket = socket;
			this.map = map;
			bufferWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));// �ڹ���������ʵ�������������
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	// ����д��Ϣ�̣߳���������Ϣ֮�󣬻���run���������Խ�Լ��Դ
	public void setMessage(String str) {
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
					bufferWriter.write(str);
					bufferWriter.flush();
				}
			}
			if (bufferWriter != null)// ѭ�������󣬹ر������ͷ���Դ
				bufferWriter.close();
			if (socket != null)
				socket.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
