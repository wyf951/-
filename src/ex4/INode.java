package ex4;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * String user; �û� int length; �ļ��ĳ��� int right; �ļ��Ķ�дȨ��//0ֻ��/1ֻд String
 * state;�ļ��Ƿ�򿪵ı�־ String modifytime; �ļ����޸�ʱ�� address;//��Ӧ�ļ���ĵ�ַ---�����
 *
 * String path ·����
 * 
 */
public class INode implements Serializable, Comparable<INode> {
	private String path = "";// ��ǰ�ĵ�ַ
	private int length = 0;
	private String users = "";
	private int right = 1;// 0ֻ��/1ֻд
	private String state = "close";// �ļ��Ƿ�򿪵ı�־
	private String modifytime;// �޸�ʱ��
	private int type = 0; /* 0����Ŀ¼��1������ͨ�ļ� */
	private int address = -1;// ��Ӧ�ļ���ĵ�ַ---�����
	private int father = -1;// ���ڵ�
	private int me = -1;// �Լ��ĵ�ǰ�ڵ�

	public int getFather() {
		return father;
	}

	public void setFather(int father) {
		this.father = father;
	}

	public int getMe() {
		return me;
	}

	public void setMe(int me) {
		this.me = me;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getRight() {
		if (this.right == 0)
			return "R";
		else
			return "W";
	}

	public void setRight(int right) {
		this.right = right;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getModifytime() {
		return modifytime;
	}

	/**
	 * setModifytime() �趨�ļ�������޸�����
	 */
	// @Test
	public void setModifytime() {
		Date date = new Date();
		SimpleDateFormat adf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		// System.out.println(adf.format(date));
		this.modifytime = adf.format(date);
	}

	public String toString() {
		return this.getUsers() + "\t" + this.getLength() + "b\t" + this.getRight() + "\t" + this.getModifytime();
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	/**
	 * Comparable �ķ�����Ϊ��ʵ��INode������
	 */
	@Override
	public int compareTo(INode o) {

		return (this.modifytime.hashCode() + this.getType()) - (o.modifytime.hashCode() + o.getType());
	}

}
