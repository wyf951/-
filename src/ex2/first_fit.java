package ex2;

import java.util.ArrayList;

public class first_fit {
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
			System.out.println("分配空间失败");
			return nf;
		}
		System.out.println("申请作业" + num + "后的空闲分区链为:");
		for (memory memory : kongxianList) {
			System.out.print(memory);
		}
		System.out.println();
		return nf;
	}

	public void free(int num, memory f) {
		memory fm = f;
		for (memory memory : kongxianList) {
			if(fm.getEnd()<memory.getStar()){
				int i=kongxianList.indexOf(memory);
				kongxianList.add(i, fm);
				break;
			}
		}
		for(int i=0;i<kongxianList.size()-1;i++){
			memory mc=kongxianList.get(i);
			memory mn=kongxianList.get(i+1);
			if(mc.getEnd()==mn.getStar()-1){
				mc.setEnd(mn.getEnd());
				mc.setLength(mc.getEnd()-mc.getStar()+1);
				kongxianList.remove(mn);
			}
		}
		System.out.println("释放作业" + num + "后的空闲分区链为:");
		for (memory memory : kongxianList) {
			System.out.print(memory);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		first_fit ff = new first_fit();
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
