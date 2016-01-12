package ex4;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * MyDirectory Ŀ¼�洢�ṹ
 */
public class MyDirectory {
    private int inode_address = -1;// ��¼�ļ���ӦInode������
    private String name = "";

    private TreeMap<Integer, Integer> tree = new TreeMap<Integer, Integer>();

    public int getInode_address() {
        return inode_address;
    }

    public void setInode_address(int inode_address) {
        this.inode_address = inode_address;
    }

    /**
     * String getName() �õ�Ŀ¼������
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setName(String name) ����Ŀ¼������
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * TreeMap<INode, Integer> getTree() �õ���Ŀ¼�µ���������
     *
     * @return
     */
    public TreeMap<Integer, Integer> getTree() {
        return tree;
    }

    /**
     * setTree(TreeMap<INode, Integer> tree) ���ô��Ŀ¼�ļ���TreeMap
     *
     * @param tree
     */
    public void setTree(TreeMap<Integer, Integer> tree) {
        this.tree = tree;
    }

    /**
     * setTree(INode inode, int sub) ��Ŀ¼�ļ���TreeMap �������
     *
     * @param inode
     * @param sub
     */
    public void setTree(INode inode, int sub) {
        this.tree.put(inode.getMe(), sub);
    }
}

