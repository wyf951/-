package ex4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * �ļ�����ϵͳ ����
 */

public class Systems {
	Scanner sc = new Scanner(System.in);// �ӿ���̨��ȡ����

	public static SuperBlock sb = null;// ������ ��¼������̵�����Ϣ
	public static ArrayList<String> users;// �û�������;
	public static INode[] inodes = new INode[100];// i�ڵ��¼���ݽṹ
	public static Object[] blocks = new Object[100];// �ļ���Ľṹ��
	public static String name = null;// ��ǰ��¼�û���
	public static INode now_inode = null;// ��ǰ�ڵ�
	public static Object now_file = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Systems sts = new Systems();
		sts.init();// ��ʼ�����ݣ�
		sts.login();

	}

	public void init() {
		users = (ArrayList<String>) FileTools.read("d:\\users.dat");
		sb = (SuperBlock) FileTools.read("d:\\super.dat");
		if (null == sb || sb.getAlreadyuse() == 0) {
			for (int i = 0; i < 100; i++) {
				inodes[i] = new INode();
			}
			sb = new SuperBlock();
			for (int i = 0; i < 100; i++) {
				sb.setInode_free(i);
			}
			FileTools.write("d:\\super.dat", sb);
		}
		if (null == users) {
			users = new ArrayList<String>();// ��������ļ�ϵͳ
			users.add("admin");
			FileTools.write("d:\\users.dat", users);
		}

	}

	public void login() {
		System.out.println("***************��ӭʹ�ø��ļ�����ϵͳ*************");
		System.out.println("���ȵ�¼->");
		name = sc.next();
		if (!this.isInNames(name)) {
			System.out.println("���û��������ڣ��Ƿ�ע����û���y/n");
			if ("y".equals(sc.next())) {
				if (regeist(name)) {
					System.out.println(name + "ע��ɹ���");
					login();
				} else {
					System.out.println("ע��ʧ�ܣ�");
					System.exit(0);
				}

			} else {
				login();
			}

		} else {
			now_inode = getInode(name + "->");// �õ���ǰ��inode
			now_file = blocks[now_inode.getAddress()];// �õ���ǰ��Ŀ¼as
			System.out.println("��¼�ɹ�");
			execute();
		}
	}

	/**
	 * ����ִ�е�������
	 */
	public void execute() {
		help();
		String commond = null;
		String cmd[] = null;// ������������ cmd[0] ���������� cmd[1]�������ļ�
		while (true) {
			System.out.print(now_inode.getPath());
			commond = sc.nextLine();
			if (commond.equals(""))
				commond = sc.nextLine();
			cmd = commond.trim().split(" ");
			// �о�ͬһ�����û������ļ�Ŀ¼
			if (cmd[0].trim().equals("dir")) {
				int m = 0;
				System.out.println("�ļ���\t�û���\t��ַ\t�ļ�����\tֻ��1/��д2\t�򿪿���");
				if (now_file instanceof MyDirectory) {
					MyDirectory now__real_file = (MyDirectory) now_file;
					m = now__real_file.getTree().size();
					if (m == 0) {
						System.out.println("û��Ŀ¼��");
					} else {

						Set<Integer> dir_inodes = now__real_file.getTree().keySet();
						Iterator<Integer> iteratore = dir_inodes.iterator();
						while (iteratore.hasNext()) {

							Object file = blocks[now__real_file.getTree().get(iteratore.next())];
							if (file instanceof MyDirectory) {
								MyDirectory real_file = (MyDirectory) file;
								INode real_inode = inodes[real_file.getInode_address()];
								// "�ļ���\t�û���\t��ַ\t�ļ�����\tֻ��1/��д2\t�򿪿���\t����ʱ��"
								System.out.println(real_file.getName() + "\t" + real_inode.getUsers() + "\t"
										+ real_inode.getAddress() + "\t" + real_inode.getLength() + "B\t"
										+ real_inode.getRight() + "\t" + real_inode.getState() + "\t"
										+ real_inode.getModifytime());

							} else {
								MyFile real_file = (MyFile) file;
								INode real_inode = inodes[real_file.getInode_address()];
								System.out.println(real_file.getName() + "\t" + real_inode.getUsers() + "\t"
										+ real_inode.getAddress() + "\t" + real_inode.getLength() + "B\t"
										+ real_inode.getRight() + "\t" + real_inode.getState() + "\t"
										+ real_inode.getModifytime());

							}

						}
						System.out.println("�ļ�����---" + m);
					}

				} else {
					MyFile now__real_file = (MyFile) now_file;
				}

			}
			// �����ļ�
			else if (cmd[0].equals("create")) {

				int index = getFreeInode();
				if (index != -1) {
					MyFile my_file = new MyFile();
					my_file.setName(cmd[1]);
					INode inode = new INode();
					inode.setFather(now_inode.getMe());
					inode.setUsers(name);
					inode.setMe(index);
					inode.setModifytime();
					if (inode.getFather() == -1) {
						inode.setPath(name + "->");
					} else {
						inode.setPath(inodes[inode.getFather()].getPath() + cmd[1] + "->");
					}
					inode.setRight(1);// ��д
					inode.setState("open");
					inode.setType(1);// �ļ�
					inode.setAddress(index);
					inodes[index] = inode;
					my_file.setInode_address(index);
					MyDirectory real_file = (MyDirectory) now_file;
					blocks[index] = my_file;
					real_file.getTree().put(index, index);
					System.out.println(cmd[1] + "�ļ��Ѿ��򿪣����������ݡ�����#end��������");
					StringBuffer content = new StringBuffer();
					while (true) {
						String tem = sc.nextLine();
						if (tem.equals("#end")) {
							System.out.println("�ļ��������");
							break;// �ļ��������
						}
						content.append(tem + "\r\n");
					}
					my_file.setSubstance(content.toString());
					inodes[index].setLength(content.length());
					inodes[index].setState("close");
					System.out.println(cmd[1] + "�ļ��ѹرգ�");
					sb.setAlreadyuse(content.length());
					sb.setInode_busy(index);
				} else {
					System.out.println("inode����ʧ�ܣ�");
				}

			}
			// �����ļ�Ŀ¼
			else if (cmd[0].trim().equals("mkdir")) {
				int index = getFreeInode();
				if (index != -1) {
					MyDirectory my_file = new MyDirectory();
					my_file.setName(cmd[1]);
					INode inode = new INode();
					inode.setFather(now_inode.getMe());
					inode.setUsers(name);
					inode.setMe(index);
					inode.setModifytime();
					inode.setPath(now_inode.getPath() + cmd[1] + "->");
					inode.setRight(1);// ��д
					inode.setType(0);// �ļ�
					inode.setAddress(index);
					inodes[index] = inode;
					my_file.setInode_address(index);

					MyDirectory real_file = (MyDirectory) now_file;
					blocks[index] = my_file;
					real_file.getTree().put(index, index);
					inodes[index].setLength(0);
					sb.setInode_busy(index);

				} else {
					System.out.println("inode����ʧ�ܣ�");
				}

			}
			// ɾ���ļ��Ĳ���
			else if (cmd[0].trim().equals("delete")) {

				Object o = this.getFileByName(cmd[1]);
				if (null != o) {
					if (o instanceof MyDirectory) {
						MyDirectory o1 = (MyDirectory) o;

						if (o1.getTree().size() == 0) {
							int index = o1.getInode_address();
							sb.setInode_free(index);
							// ���ýڵ�
							inodes[index] = new INode();
							// �������ݿ�
							blocks[o1.getInode_address()] = new Object();
							// ��Ŀ¼��tree��ɾ������
							MyDirectory file = (MyDirectory) now_file;
							file.getTree().remove(index);

							System.out.println(o1.getName() + "Ŀ¼��ɾ����");
						} else {
							System.out.println(o1.getName() + "Ŀ¼��Ϊ�գ�������ɾ��");
						}
					} else if (o instanceof MyFile) {
						MyFile o1 = (MyFile) o;

						int index = o1.getInode_address();
						// ���ó�����
						sb.setInode_free(index);
						sb.setFreeuse(inodes[index].getLength());
						// ���ýڵ�
						inodes[index] = new INode();
						// �������ݿ�
						blocks[o1.getInode_address()] = new Object();
						// ��Ŀ¼��tree��ɾ������
						MyDirectory file = (MyDirectory) now_file;
						file.getTree().remove(index);

						System.out.println(o1.getName() + "�ļ���ɾ����");

					} else {
						System.out.println(cmd[1] + "�ļ������ڣ�");
					}
				}

			} else if (cmd[0].trim().equals("cd")) {
				if (".".equals(cmd[1])) {

				} else if ("..".equals(cmd[1])) {
					if (now_inode.getFather() == -1) {
						System.out.println("��ǰĿ¼Ϊ��Ŀ¼��");
					} else {
						MyDirectory now_directory = (MyDirectory) now_file;
						now_inode = inodes[now_inode.getFather()];
						now_file = blocks[now_inode.getAddress()];
					}
				} else if (null != getFileByName(cmd[1])) {
					Object o1 = getFileByName(cmd[1]);
					if (o1 instanceof MyDirectory) {
						MyDirectory o = (MyDirectory) o1;
						now_file = o;
						now_inode = inodes[o.getInode_address()];
					} else {
						System.out.println("�����Ŀ¼�����ڣ����飡");
					}

				} else {
					System.out.println("�����Ŀ¼�����ڣ����飡");
				}

			} else if (cmd[0].trim().equals("open")) {
				// ûʱ��д��
			}

			else if (cmd[0].trim().equals("close")) {
				// ûʱ��д��
			} else if (cmd[0].trim().equals("rename")) {

				// System.out.println("�ļ�" + file[0] + "�Ѿ��ر�");
				if (rename(cmd)) {
					System.out.println("�������ɹ���");
				} else {
					System.out.println("������ʧ�ܣ�");
				}

			}
			// read�������ļ��Ѿ��򿪵Ļ�����ִ���ļ��Ķ�����������ļ�û�д򿪣������ִ���ļ��Ķ��������򲻿��ԣ�
			else if (cmd[0].trim().equals("read")) {

				Object o = this.getFileByName(cmd[1]);
				if (null != o) {
					if (o instanceof MyDirectory) {
						MyDirectory o1 = (MyDirectory) o;
						System.out.println(o1.getName() + "Ŀ¼����ִ�д����");
					} else if (o instanceof MyFile) {

						MyFile o1 = (MyFile) o;
						System.out.println(o1.getName() + "�ļ��������£�");
						System.out.println(o1.getSubstance().substring(0, o1.getSubstance().lastIndexOf("\r\n")));
					}
				}
			} else if (cmd[0].trim().equals("write")) {

				Object o = this.getFileByName(cmd[1]);
				if (null != o) {
					if (o instanceof MyDirectory) {
						MyDirectory o1 = (MyDirectory) o;
						System.out.println(o1.getName() + "Ŀ¼����ִ�д����");
					} else if (o instanceof MyFile) {
						MyFile o1 = (MyFile) o;
						// System.out.println(o1.getName());
						System.out.println("1.��д;2.��д; ��ѡ��");
						String select = sc.next();
						while (true) {

							if ("1".equals(select)) {
								System.out.println("��������д�����ݣ���#end����");
								StringBuffer content = new StringBuffer(
										o1.getSubstance().substring(0, o1.getSubstance().lastIndexOf("\r\n")));
								while (true) {
									String tem = sc.next();
									if (tem.equals("#end")) {
										System.out.println("�ļ��������");
										break;// �ļ��������
									}
									content.append(tem + "\r\n");
								}
								o1.setSubstance(content.toString());
								System.out.println("��д�����ɹ���");
								break;

							} else if ("2".equals(select)) {
								System.out.println("��������д�����ݣ���#end����");
								StringBuffer content = new StringBuffer();
								while (true) {
									String tem = sc.next();
									if (tem.equals("#end")) {
										System.out.println("�ļ��������");
										break;// �ļ��������
									}
									content.append(tem + "\r\n");
								}
								o1.setSubstance(content.toString());
								System.out.println("��д�����ɹ���");
								break;

							} else {
								System.out.println("����������������룡");
								select = sc.next();
							}
						}
					}
				} else {
					System.out.println("����������������룡");

				}
			}
			// �˳�����---��������
			else if (cmd[0].trim().equals("exit")) {

				System.exit(0);
			}
			// help����
			else if (cmd[0].trim().equals("help")) {
				help();

			} else {
				System.out.println(commond);
				System.out.println("�������������help������вο�");
			}

		}

	}

	/**
	 * regeist(String name) ע���û�
	 *
	 * @param name
	 */
	public boolean regeist(String name) {
		int inode_free_index = this.getFreeInode();
		if (inode_free_index > -1) {
			now_inode = inodes[inode_free_index];
			now_inode.setAddress(inode_free_index);// �ļ���ĵ�ַ
			now_inode.setModifytime();
			now_inode.setRight(1);
			now_inode.setState("close");
			now_inode.setType(0);
			now_inode.setUsers(name);
			now_inode.setPath(name + "->");
			now_inode.setMe(inode_free_index);// ��ǰInode������
			inodes[inode_free_index] = now_inode;
			MyDirectory block = new MyDirectory();
			block.setName(name);
			blocks[inode_free_index] = block;
			users.add(name);
			FileTools.write("d:\\users.dat", users);
			FileTools.write("d:\\inodes.dat", inodes);
			return true;
		}

		return false;
	}

	public void help() {
		System.out.println();
		System.out.print("create ");
		System.out.println("�����ļ�");
		System.out.print("dir    ");
		System.out.println("��Ŀ¼�ļ�");
		System.out.print("exit   ");
		System.out.println("�˳�");
		System.out.println("������������ļ���");
		System.out.println("eg��open ***");
		System.out.print("open   ");
		System.out.println("���ļ�");
		System.out.print("close  ");
		System.out.println("�ر��ļ�");
		System.out.print("read   ");
		System.out.println("���ļ�");
		System.out.print("write  ");
		System.out.println("д�ļ�");
		System.out.print("delete ");
		System.out.println("ɾ���ļ�");
		System.out.println();
	}

	private Object getFileByName(String name) {
		for (Object o : blocks) {
			if (o instanceof MyDirectory) {
				MyDirectory o1 = (MyDirectory) o;
				if (o1.getName().equals(name)) {
					return o1;
				}
			} else if (o instanceof MyFile) {
				MyFile o1 = (MyFile) o;
				if (o1.getName().equals(name)) {
					return o1;
				}
			}
		}
		return null;

	}

	/**
	 * isInNames(String name) �ж��û����Ƿ����
	 *
	 * @param name
	 * @return
	 */
	private boolean isInNames(String name) {
		for (String n : users) {
			if (n.equals(name))
				return true;
		}
		return false;
	}

	/**
	 * getFreeInode() �õ��յ�inode
	 * 
	 * @return
	 */
	private int getFreeInode() {

		return sb.getInode_free();
	}

	/**
	 * getInode(String path) ��path�õ�Inode
	 * 
	 * @param name
	 * @return
	 */
	private INode getInode(String path) {
		for (int i = 0; i < 100; i++) {
			if (path.equals(inodes[i].getPath())) {
				return inodes[i];
			}
		}
		return null;
	}

	/**
	 * getBlock() �õ����е�block�����
	 *
	 * @param name
	 * @return
	 */
	private int getBlock() {
		for (int i = 0; i < 100; i++) {
			if (null == blocks[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * rename(String[] cmd) ����������
	 *
	 * @param cmd
	 * @return
	 */
	private boolean rename(String[] cmd) {
		if (cmd.length < 3) {
			System.out.println("�����������");
			return false;
		}
		Object o = getFileByName(cmd[1]);
		if (null == o)
			return false;
		else {
			if (o instanceof MyDirectory) {
				MyDirectory oo = (MyDirectory) o;
				oo.setName(cmd[2]);
				// inode.setPath(now_inode.getPath() + cmd[1] + "->");
				inodes[oo.getInode_address()].setPath(now_inode.getPath() + cmd[2] + "->");
				return true;
			} else {
				MyFile oo = (MyFile) o;
				oo.setName(cmd[2]);
				return true;
			}
		}

	}
}
