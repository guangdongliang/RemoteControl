package esp.server.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.way.chat.common.util.Constants;
import com.way.chat.common.util.MyDate;

/**
 * 服务器，接受用户登录、离线、转发消息
 * 
 * @author way
 * 
 */
public class ServerAPP {
	private ExecutorService executorService;// 线程池
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private boolean isStarted = true;

	public ServerAPP() {
		try {
			// 创建线程池，池中具有(cpu个数*50)条线程
			executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
					.availableProcessors() * 100);
			serverSocket = new ServerSocket(Constants.SERVER_APP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			quit();
		}
	}

	public void start() {
		System.out.println(MyDate.getDateCN() + " 服务器已启动...");
		try {
			while (isStarted) {
				socket = serverSocket.accept();
				socket.setSoTimeout(4000);
				String ip = socket.getInetAddress().toString();
				System.out.println(MyDate.getDateCN() + " 用户：" + ip + " 已建立连接");
				// 为支持多用户并发访问，采用线程池管理每一个用户的连接请求
				if (socket.isConnected())
					executorService.execute(new SocketTask(socket));// 添加到线程池
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
			// 先实例化写消息线程,（把对应用户的写线程存入map缓存器中）
			in = new InputThreadAPP(socket, out, map);// 再实例化读消息线程
			System.out.println("启动接收线程");
			out.setStart(true);
			in.setStart(true);
			in.start();
			out.start();
		}
	}

	/**
	 * 退出
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
