package esp.server.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.way.chat.common.util.Constants;
import com.way.chat.common.util.MyDate;

/**
 * �������������û���¼�����ߡ�ת����Ϣ
 * 
 * @author way
 * 
 */
public class ServerAPP {
	private ExecutorService executorService;// �̳߳�
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private boolean isStarted = true;

	public ServerAPP() {
		try {
			// �����̳߳أ����о���(cpu����*50)���߳�
			executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
					.availableProcessors() * 100);
			serverSocket = new ServerSocket(Constants.SERVER_APP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			quit();
		}
	}

	public void start() {
		System.out.println(MyDate.getDateCN() + " ������������...");
		try {
			while (isStarted) {
				socket = serverSocket.accept();
				socket.setSoTimeout(4000);
				String ip = socket.getInetAddress().toString();
				System.out.println(MyDate.getDateCN() + " �û���" + ip + " �ѽ�������");
				// Ϊ֧�ֶ��û��������ʣ������̳߳ع���ÿһ���û�����������
				if (socket.isConnected())
					executorService.execute(new SocketTask(socket));// ��ӵ��̳߳�
			}
			if (socket != null)
				socket.close();
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
			// isStarted = false;
		}
	}

	private final class SocketTask implements Runnable {
		private Socket socket = null;
		private InputThreadAPP in;
		private OutputThreadAPP out;
		private OutputThreadMapAPP map;


		public SocketTask(Socket socket) {
			this.socket = socket;
			map = OutputThreadMapAPP.getInstance();
		}

		@Override
		public void run() {
			out = new OutputThreadAPP(socket, map);//
			// ��ʵ����д��Ϣ�߳�,���Ѷ�Ӧ�û���д�̴߳���map�������У�
			in = new InputThreadAPP(socket, out, map);// ��ʵ��������Ϣ�߳�
			System.out.println("���������߳�");
			out.setStart(true);
			in.setStart(true);
			in.start();
			out.start();
		}
	}

	/**
	 * �˳�
	 */
	public void quit() {
		try {
			this.isStarted = false;
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ServerAPP().start();
	}
}
