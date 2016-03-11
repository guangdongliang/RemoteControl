package esp.server8266;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordMap {

	private HashMap<String, String> map;
	private static PasswordMap instance;

	// 私有构造器，防止被外面实例化改对像
	private PasswordMap() {
		map = new HashMap<String, String>();
	}

	// 单例模式像外面提供该对象
	public synchronized static PasswordMap getInstance() {
		if (instance == null) {
			instance = new PasswordMap();
		}
		return instance;
	}

	// 添加写线程的方法
	public synchronized void add(String id, String out) {
		map.put(id, out);
	}

	// 移除写线程的方法
	public synchronized void remove(String id) {
		map.remove(id);
	}

	// 取出写线程的方法,群聊的话，可以遍历取出对应写线程
	public synchronized String getById(String id) {
		return map.get(id);
	}

	// 得到所有写线程方法，用于向所有在线用户发送广播
	public synchronized List<String> getAll() {
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
}
