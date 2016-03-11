package esp.server.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存放写线程的缓存器
 * 
 * @author way
 */
public class OutputThreadMapAPP {
	private HashMap<String, OutputThreadAPP> map;
	private static OutputThreadMapAPP instance;

	// 私有构造器，防止被外面实例化改对像
	private OutputThreadMapAPP() {
		map = new HashMap<String, OutputThreadAPP>();
	}

	// 单例模式像外面提供该对象
	public synchronized static OutputThreadMapAPP getInstance() {
		if (instance == null) {
			instance = new OutputThreadMapAPP();
		}
		return instance;
	}

	// 添加写线程的方法
	public synchronized void add(String id, OutputThreadAPP out) {
		map.put(id, out);
	}

	// 移除写线程的方法
	public synchronized void remove(String id) {
		map.remove(id);
	}

	// 取出写线程的方法,群聊的话，可以遍历取出对应写线程
	public synchronized OutputThreadAPP getById(String id) {
		return map.get(id);
	}

	// 得到所有写线程方法，用于向所有在线用户发送广播
	public synchronized List<OutputThreadAPP> getAll() {
		List<OutputThreadAPP> list = new ArrayList<OutputThreadAPP>();
		for (Map.Entry<String, OutputThreadAPP> entry : map.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
}
