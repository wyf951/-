package ex1;

import java.util.*;

public class PCB {
	public static List<PCB> readyl = new ArrayList<PCB>();
	public static List<PCB> blockl = new ArrayList<PCB>();
	public static List<PCB> norml = new ArrayList<PCB>();
	public static PCB run = new PCB();
	public static int finishNum = 0;
	private int ID;
	private int Priority;
	private int cpuTime;
	private int allTime;
	private int startBlock;
	private int blockTime;
	private String state;

	public PCB(int id, int priority, int cpuTime, int allTime, int startBlock, int blockTime, String state) {
		ID = id;
		Priority = priority;
		this.cpuTime = cpuTime;
		this.allTime = allTime;
		this.startBlock = startBlock;
		this.blockTime = blockTime;
		this.state = state;
	}

	public PCB() {
	}

	public void readyPri() {
		this.Priority++;
	}

	public void runPri() {
		this.Priority -= 3;
	}

	public void runCup() {
		this.cpuTime++;
	}

	public void runAll() {
		this.allTime--;
	}

	public void setState(String state) {
		this.state = state;
	}

	public static void listAdd(PCB[] p) {
		for (int i = 0; i < p.length; i++) {
			norml.add(p[i]);
			if (p[i].state == "ready") {
				readyl.add(p[i]);
			}
			if (p[i].state == "block") {
				blockl.add(p[i]);
			}
		}
	}

	public static void readysort() {
		PCB temp = new PCB();
		for (int i = 0; i < readyl.size() - 1; i++) {
			for (int j = i + 1; j < readyl.size(); j++) {
				if (readyl.get(i).Priority < readyl.get(j).Priority) {
					temp = readyl.get(i);
					readyl.set(i, readyl.get(j));
					readyl.set(j, temp);
				}
			}
		}
	}

	public static void readyPrint() {
		System.out.print("READY-QUEUE:");
		for (int i = 0; i < readyl.size(); i++) {
			System.out.print("->id" + readyl.get(i).ID);
		}
		System.out.println();
	}

	public static void blocksort() {
		PCB temp = new PCB();
		for (int i = 0; i < blockl.size() - 1; i++) {
			for (int j = i + 1; j < blockl.size(); j++) {
				if (blockl.get(i).blockTime > blockl.get(j).blockTime) {
					temp = blockl.get(i);
					blockl.set(i, blockl.get(j));
					blockl.set(j, temp);
				}
			}
		}
	}

	public static void blockPrint() {
		System.out.print("BLOCK-QUEUE:");
		for (int i = 0; i < blockl.size(); i++) {
			System.out.print("->id" + blockl.get(i).ID);
		}
		System.out.println();
	}

	public static void selectRun() {
		if (readyl.size() > 0 && readyl.get(0) == run) {
			readyl.remove(0);
			run.setState("run");
		}
		if (readyl.size() > 0 && readyl.get(0).Priority > run.Priority) {
			run.setState("block");
			blockl.add(run);
			run = readyl.get(0);
			readyl.remove(0);
			run.setState("run");
		}
		System.out.println("RUNNNING PROG:" + run.ID);
		run.runPri();
		run.runCup();
		run.runAll();
		if (run.allTime == 0) {
			run.setState("finish");
			finishNum++;
			if (readyl.size() > 0) {
				PCB temp1 = readyl.get(0);
				if (blockl.size() > 0) {
					if (blockl.get(0).Priority > temp1.Priority && blockl.get(0).blockTime == 0) {
						temp1 = blockl.get(0);
					}
				}
				run = temp1;
			}
		}
	}

	public static void readyProg() {
		for (int i = 0; i < readyl.size(); i++) {
			readyl.get(i).readyPri();
		}
	}

	public static void printNorm(List<PCB> norml2) {
		System.out.println("======================================");
		System.out.print("ID\t\t");
		for (int i = 0; i < norml2.size(); i++) {
			System.out.print(norml2.get(i).ID + "\t");
		}
		System.out.println();
		System.out.print("PRIORITY\t");
		for (int i = 0; i < norml2.size(); i++) {
			System.out.print(norml2.get(i).Priority + "\t");
		}
		System.out.println();
		System.out.print("CPUTIME\t\t");
		for (int i = 0; i < norml2.size(); i++) {
			System.out.print(norml2.get(i).cpuTime + "\t");
		}
		System.out.println();
		System.out.print("ALLTIME\t\t");
		for (int i = 0; i < norml2.size(); i++) {
			System.out.print(norml2.get(i).allTime + "\t");
		}
		System.out.println();
		System.out.print("STARTBLOCK\t");
		for (int i = 0; i < norml2.size(); i++) {
			System.out.print(norml2.get(i).startBlock + "\t");
		}
		System.out.println();
		System.out.print("BLOCKTIME\t");
		for (int i = 0; i < norml2.size(); i++) {
			System.out.print(norml2.get(i).blockTime + "\t");
		}
		System.out.println();
		System.out.print("STATE\t\t");
		for (int i = 0; i < norml2.size(); i++) {
			System.out.print(norml2.get(i).state + "\t");
		}
		System.out.println();
		System.out.println();
	}

	public static void removeBlock() {
		for (int i = 0; i < blockl.size(); i++) {
			if (blockl.get(i).blockTime == 0) {
				blockl.get(i).setState("ready");
				readyl.add(blockl.get(i));
				blockl.remove(blockl.get(i));
			} else {
				blockl.get(i).blockTime--;
			}
		}
	}

	public static void main(String[] args) {
		PCB[] pcb = new PCB[5];
		pcb[0] = new PCB(0, 9, 0, 3, 2, 3, "ready");
		pcb[1] = new PCB(1, 38, 0, 3, -1, 0, "ready");
		pcb[2] = new PCB(2, 30, 0, 6, -1, 0, "ready");
		pcb[3] = new PCB(3, 29, 0, 3, -1, 0, "ready");
		pcb[4] = new PCB(4, 0, 0, 4, -1, 0, "ready");
		listAdd(pcb);
		readysort();
		run = readyl.get(0);
		readyl.remove(0);
		run.setState("run");
		while (finishNum != 5) {
			readysort();
			selectRun();
			readyProg();
			readyPrint();
			blocksort();
			blockPrint();
			printNorm(norml);
			removeBlock();
		}

	}
}
