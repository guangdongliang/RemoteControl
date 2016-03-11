package com.way.chat.common.tran.bean;

import java.io.Serializable;
import java.util.List;

/**
 * ����Ķ���,ֱ��ͨ��Socket�����������
 * 
 * @author way
 */
public class TranObject<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TranObjectType type;// ���͵���Ϣ����
    private String fromID;//������Ϣ����
	private List<String> group;// Ⱥ������Щ�û�

	private T object;// ����Ķ�������������ǿ����Զ����κ�
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
