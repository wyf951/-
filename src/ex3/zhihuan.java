package ex3;

import java.util.*;

public class  zhihuan{
	public static page[] p = new page[4];
	private int pc;
	private int n;
	public static List<Integer> randomList = new ArrayList<Integer>();

	public void suijishu() {
		Scanner scan = new Scanner(System.in);
		pc = scan.nextInt();
		int order = pc;
		System.out.println("生成的随机数为:");
		int flag = 0;
		for (int i = 0; i < 320; i++) {
			randomList.add(pc);
			if (flag % 2 == 0) {
				pc = ++pc % 320;
			}
			if (flag == 1) {
				pc = (int) (Math.random() * (pc - 1));
			}
			if (flag == 3) {
				pc = (int) (pc + 1 + (Math.random() * (320 - (pc + 1))));
			}
			flag = ++flag % 4;
		}
	}

	public void init(){
		for(int i=0;i<p.length;i++){
			p[i]=new page();
		}
	}
	public int findExist(int curpage) {
		for (int i = 0; i < 4; i++) {
			if (p[i].getPagenum() == curpage) {
				return i;
			}
		}
		return -1;
	}

	public int findSpace() {
		for (int i = 0; i < 4; i++) {
			if (p[i].getPagenum() == -1) {
				return i;
			}
		}
		return -1;
	}

	public int findReplace() {
		int pos = 0;
		for (int i = 0; i < 4; i++) {
			if (p[i].getAccessed() > p[pos].getAccessed()) {
				pos = i;
			}
		}
		return pos;
	}

	public void display() {
		for (int i = 0; i < 4; i++) {
			if (p[i].getPagenum() != -1) {
				System.out.print(p[i].getPagenum()+"\t");
			}
			else
				System.out.print("null\t");
		}
		System.out.println();
	}

	public void printn() {
		System.out.println("产生的320个随机数为：");
		for (int i = 0; i < randomList.size(); i++) {
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
			System.out.print(String.format(" %03d", randomList.get(i)));
		}
	}

	public void printp() {
		System.out.println("调用页面队列：");
		for (int i = 0; i < randomList.size(); i++) {
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
			System.out.print(String.format(" %02d", randomList.get(i) / 10));
		}
	}

	public void OPT() {
		init();
		n=0;
		int exist, space, position;
		int curpage;
		for (int i = 0; i < randomList.size(); i++) {
			pc = randomList.get(i);
			curpage = pc / 10;
			exist = findExist(curpage);
			if (exist == -1) {
				space = findSpace();
				if (space != -1) {
					p[space].setPagenum(curpage);
					display();
					n++;
				} else {
					for (int k = 0; k < 4; k++) {
						for (int j = i; j < 320; j++) {
							if (p[k].getPagenum() != (randomList.get(j) / 10)) {
								p[k].setAccessed(1000);
							} else {
								p[k].setAccessed(j);
								break;
							}
						}
					}
					position = findReplace();
					p[position].setPagenum(curpage);
					display();
					n++;
				}
			}
		}
		System.out.println("缺页次数:" + n);	
		System.out.println("缺页率" + (((n / 320.0) * 100) + "%"));
	}

	public void LRU(){
		init();
		n=0;
		int exist,space,position;
		int curpage;
		for(int i=0;i<320;i++){
			pc=randomList.get(i);
			curpage=pc/10;
			exist=findExist(curpage);
			if(exist==-1){
				space=findSpace();
				if(space!=-1){
					p[space].setPagenum(curpage);
					display();
					n++;
				}
				else{
					position=findReplace();
					p[position].setPagenum(curpage);
					display();
					n++;
				}
			}
			else{
				p[exist].setAccessed(-1);
			}
			for(int j=0;j<4;j++){
				p[j].setAccessed(p[j].getAccessed()+1);
			}
		}
		System.out.println("缺页次数："+n);
		System.out.println("缺页率："+(n/320.0)*100+"%");
	}
	public void FIFO(){
		init();
		n=0;
		int exist,space,position;
		int curpage;
		for(int i=0;i<320;i++){
			pc=randomList.get(i);
			curpage=pc/10;
			exist=findExist(curpage);
			if(exist==-1){
				space=findSpace();
				if(space!=-1){
					p[space].setPagenum(curpage);
					display();
					n++;
				}
				else{
					position=findReplace();
					p[position].setPagenum(curpage);
					display();
					n++;
					p[position].setAccessed(p[position].getAccessed()-1);
				}
			}
			for(int j=0;j<4;j++){
				p[j].setAccessed(p[j].getAccessed()+1);
			}
		}
		System.out.println("缺页次数："+n);
		System.out.println("缺页率："+(n/320.0)*100+"%");
	}
		
	public static void main(String[] args) {
		System.out.print("请输入第一条指令号:");
		zhihuan zh = new zhihuan();
		zh.suijishu();
		zh.printn();
		System.out.println();
		zh.init();
		zh.printp();
		System.out.println();
		System.out.println("OPT算法");
		zh.OPT();
		System.out.println();
		System.out.println("LRU算法");
		zh.LRU();
		System.out.println();
		System.out.println("FIFO算法");
		zh.FIFO();
	}
}
