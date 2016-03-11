package esp.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketOperate extends Thread{

	private Socket socket;
	BufferedReader in;
	PrintWriter out;
	public SocketOperate(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!socket.isClosed())
		{
			//receiveAndSendString();
			//System.out.println("�յ�����11");
		} 
	}
	private Boolean receiveAndSendString()
	{
		String line = "";
		try {
			//��socket��ʱʱ�����޳�
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
				while ((line=in.readLine()) != null) {
					//��������������
					System.out.println("�յ���Ϣ1"+line);
					SendString(socket,"success");
				}

			} 
		catch (Exception e) 
		{

			//e.printStackTrace();
			try {
				in.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	
	//ת������
	private Boolean SendString(Socket socket,String str)
	{
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())), true);
			out.write(str);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("ת�����ݳ���");
			e.printStackTrace();
			return false;
		}
		return true;
	}
    
	
}
