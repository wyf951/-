package ex4;

/**
 * MyFile  �ļ��洢�ṹ
 * String name �ļ�����
 * substance ��������
 */

public class MyFile {
    private int inode_address=-1;//��¼�ļ���ӦInode������
    private String name="";//�ļ�����
    private String substance="";//��������

    public int getInode_address() {
        return inode_address;
    }
    public void setInode_address(int inode_address) {
        this.inode_address = inode_address;
    }
    /**
     * getName() �õ��ļ�����
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * setName(String name) �����ļ�
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * String getSubstance() �õ��ļ�������
     * @return
     */
    public String getSubstance() {
        return substance;
    }
    /**
     * setSubstance(String substance) �����ļ�������
     * @param substance
     */
    public void setSubstance(String substance) {
        this.substance = substance;
    }
}
