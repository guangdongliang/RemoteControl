package esp.server8266;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


/**
 * 写消息线程
 * 
 * @author way
 * 
 */
public class OutputThread extends Thread {
	private OutputThreadMap map;
	private BufferedWriter  bufferWriter;
	private String str;
	private boolean isStart = true;// 循环标志位
	private Socket socket;

	public OutputThread(Socket socket, OutputThreadMap map) {
		try {
			this.socket = socket;
			this.map = map;
			bufferWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));// 在构造器里面实例化对象输出流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	// 调用写消息线程，设置了消息之后，唤醒run方法，可以节约资源
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
				// 没有消息写出的时候，线程等待
				synchronized (this) {
					wait();
				}
				if (str != null) {
					bufferWriter.write(str);
					bufferWriter.flush();
				}
			}
			if (bufferWriter != null)// 循环结束后，关闭流，释放资源
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
