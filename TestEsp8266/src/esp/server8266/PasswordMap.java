package esp.server8266;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordMap {

	private HashMap<String, String> map;
	private static PasswordMap instance;

	// ˽�й���������ֹ������ʵ�����Ķ���
	private PasswordMap() {
		map = new HashMap<String, String>();
	}

	// ����ģʽ�������ṩ�ö���
	public synchronized static PasswordMap getInstance() {
		if (instance == null) {
			instance = new PasswordMap();
		}
		return instance;
	}

	// ���д�̵߳ķ���
	public synchronized void add(String id, String out) {
		map.put(id, out);
	}

	// �Ƴ�д�̵߳ķ���
	public synchronized void remove(String id) {
		map.remove(id);
	}

	// ȡ��д�̵߳ķ���,Ⱥ�ĵĻ������Ա���ȡ����Ӧд�߳�
	public synchronized String getById(String id) {
		return map.get(id);
	}

	// �õ�����д�̷߳��������������������û����͹㲥
	public synchronized List<String> getAll() {
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
}
