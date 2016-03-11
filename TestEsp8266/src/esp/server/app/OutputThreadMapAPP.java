package esp.server.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���д�̵߳Ļ�����
 * 
 * @author way
 */
public class OutputThreadMapAPP {
	private HashMap<String, OutputThreadAPP> map;
	private static OutputThreadMapAPP instance;

	// ˽�й���������ֹ������ʵ�����Ķ���
	private OutputThreadMapAPP() {
		map = new HashMap<String, OutputThreadAPP>();
	}

	// ����ģʽ�������ṩ�ö���
	public synchronized static OutputThreadMapAPP getInstance() {
		if (instance == null) {
			instance = new OutputThreadMapAPP();
		}
		return instance;
	}

	// ���д�̵߳ķ���
	public synchronized void add(String id, OutputThreadAPP out) {
		map.put(id, out);
	}

	// �Ƴ�д�̵߳ķ���
	public synchronized void remove(String id) {
		map.remove(id);
	}

	// ȡ��д�̵߳ķ���,Ⱥ�ĵĻ������Ա���ȡ����Ӧд�߳�
	public synchronized OutputThreadAPP getById(String id) {
		return map.get(id);
	}

	// �õ�����д�̷߳��������������������û����͹㲥
	public synchronized List<OutputThreadAPP> getAll() {
		List<OutputThreadAPP> list = new ArrayList<OutputThreadAPP>();
		for (Map.Entry<String, OutputThreadAPP> entry : map.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
}
