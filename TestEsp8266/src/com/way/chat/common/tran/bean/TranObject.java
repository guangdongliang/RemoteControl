package com.way.chat.common.tran.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 传输的对象,直接通过Socket传输的最大对象
 * 
 * @author way
 */
public class TranObject<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TranObjectType type;// 发送的消息类型
    private String fromID;//发送消息的人
	private List<String> group;// 群发给哪些用户

	private T object;// 传输的对象，这个对象我们可以自定义任何
	public TranObject(TranObjectType type) {
		this.type = type;
	}




	public String getFromID() {
		return fromID;
	}




	public void setFromID(String fromID) {
		this.fromID = fromID;
	}




	public TranObjectType getType() {
		return type;
	}




	public List<String> getGroup() {
		return group;
	}




	public void setGroup(List<String> group) {
		this.group = group;
	}



	public T getObject() {
		return object;
	}




	public void setObject(T object) {
		this.object = object;
	}
	
}
