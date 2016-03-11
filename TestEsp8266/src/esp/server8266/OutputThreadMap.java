package esp.server8266;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���д�̵߳Ļ�����
 * 
 * @author way
 */
public class OutputThreadMap {
	private HashMap<String, OutputThread> map;
	private static OutputThreadMap instance;

	// ˽�й���������ֹ������ʵ�����Ķ���
	private OutputThreadMap() {
		map = new HashMap<String, OutputThread>();
	}

	// ����ģʽ�������ṩ�ö���
	public synchronized static OutputThreadMap getInstance() {
		if (instance == null) {
			instance = new OutputThreadMap();
		}
		return instance;
	}

	// ���д�̵߳ķ���
	public synchronized void add(String id, OutputThread out) {
		map.put(id, out);
	}

	// �Ƴ�д�̵߳ķ���
	public synchronized void remove(String id) {
		map.remove(id);
	}

	// ȡ��д�̵߳ķ���,Ⱥ�ĵĻ������Ա���ȡ����Ӧд�߳�
	public synchronized OutputThread getById(String id) {
		return map.get(id);
	}

	// �õ�����д�̷߳��������������������û����͹㲥
	public synchronized List<OutputThread> getAll() {
		List<OutputThread> list = new ArrayList<OutputThread>();
		for (Map.Entry<String, OutputThread> entry : map.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	public HashMap<String, OutputThread> getMap() {
		return map;
	}
}
