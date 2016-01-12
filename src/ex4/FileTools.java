package ex4;

import java.io.*;

public class FileTools {
	/**
	 * Object read( String name) ��ȡname�ļ�����object���͵�����
	 */
	public static Object read(String name) {
		File file = new File(name);
		ObjectInputStream oin = null;
		Object obj = null;
		try {
			oin = new ObjectInputStream(new FileInputStream(name));
			obj = oin.readObject();
			// System.out.println("read"+obj);EOFException
		} catch (EOFException e1) {
		} catch (FileNotFoundException e) {

			write(name, null);
			read(name);
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		try {
			if (null != oin)
				oin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ObjectInputStream�ر�ʧ�ܣ�");
		}
		return obj;

	}

	/**
	 * write(String name,Object o) ��nameĿ¼�ļ�д�������Ķ���
	 */
	
	public static void write(String name, Object o) {
		File file = new File(name);
		ObjectOutputStream oout = null;
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("����ԭʼ�����ļ�ʧ�ܣ�");
			}
		}

		try {
			oout = new ObjectOutputStream(new FileOutputStream(file));
			if (null != o) {
				oout.writeObject(o);
			}
		} catch (IOException e) {
			System.out.println("��ȡ�����ļ�ʧ�ܣ�");
		} finally {
			try {
				if (null != oout)
					oout.close();
			} catch (IOException e) {
				System.out.println("ObjectOutputStream�ر�ʧ�ܣ�");
			}
		}
	}
}
