package ex2;

import java.util.ArrayList;

public class best_fit {
	public static ArrayList<memory> kongxianList = new ArrayList<memory>();

	public memory alloc(int num, int length) {
		memory nf = null;
		for (memory object : kongxianList) {
			if (object.getLength() >= length) {
				nf = new memory(object.getStar(), object.getStar() + length - 1, length);
				int i = kongxianList.indexOf(object);
				kongxianList.remove(i);
				if (object.getLength() > length) {
					memory nl = new memory(nf.getEnd() + 1, object.getEnd());
					kongxianList.add(i, nl);
				}
				break;
			}
		}
		if (nf == null) {
			System.out.println("����ռ�ʧ��");
			return nf;
		}
		System.out.println("������ҵ" + num + "��Ŀ��з�����Ϊ:");
		for (memory memory : kongxianList) {
			System.out.print(memory);
		}
		System.out.println();
		return nf;
	}

	public void free(int num, memory f) {
		memory fm = f;
		for (memory memory : kongxianList) {
			if (fm.getEnd() < memory.getStar()) {
				int i = kongxianList.indexOf(memory);
				kongxianList.add(i, fm);
				break;
			}
		}
		for (int i = 0; i < kongxianList.size() - 1; i++) {
			memory mc = kongxianList.get(i);
			for (int j = 0; j < kongxianList.size(); j++) {
				memory mn = kongxianList.get(j);
				if (mc.getEnd() == mn.getStar() - 1) {
					mc.setEnd(mn.getEnd());
					mc.setLength(mc.getEnd() - mc.getStar() + 1);
					kongxianList.remove(mn);
				}
			}
		}
		for (int i = 0; i < kongxianList.size() - 1; i++) {
			for (int j = i + 1; j < kongxianList.size(); j++) {
				if (kongxianList.get(i).getLength() > kongxianList.get(j).getLength()) {
					memory temp1 = kongxianList.get(i);
					memory temp2 = kongxianList.get(j);
					kongxianList.remove(i);
					kongxianList.add(i, temp2);
					kongxianList.remove(j);
					kongxianList.add(j, temp1);
				}
			}
		}
		System.out.println("�ͷ���ҵ" + num + "��Ŀ��з�����Ϊ:");
		for (memory memory : kongxianList) {
			System.out.print(memory);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		best_fit ff = new best_fit();
		memory kongxian = new memory(0, 639);
		ff.kongxianList.add(kongxian);
		memory zuoye1 = ff.alloc(1, 130);
		memory zuoye2 = ff.alloc(2, 60);
		memory zuoye3 = ff.alloc(3, 100);
		ff.free(2, zuoye2);
		memory zuoye4 = ff.alloc(4, 200);
		ff.free(3, zuoye3);
		ff.free(1, zuoye1);
		memory zuoye5 = ff.alloc(5, 140);
		memory zuoye6 = ff.alloc(6, 60);
		memory zuoye7 = ff.alloc(7, 50);
		ff.free(6, zuoye6);
	}
}
