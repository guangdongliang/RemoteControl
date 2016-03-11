package esp.bean;

import java.io.Serializable;

/**
 * �û�����
 * 
 * @author way
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long pNumber;// QQ����
	private String name;// �ǳ�
	private String password;// ����

	

	public long getpNumber() {
		return pNumber;
	}

	public void setpNumber(long pNumber) {
		this.pNumber = pNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			User user = (User) o;
			if (user.getpNumber() == pNumber ) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "User [id=" + pNumber + ", name=" + name
				+ ", password=" + password + "]";
	}

}
