package ex3;

public class page {
	private int pagenum;
	private int accessed;
	
	public page() {
		this.pagenum = -1;
		this.accessed = 0;
	}
	public int getPagenum() {
		return pagenum;
	}
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
	public int getAccessed() {
		return accessed;
	}
	public void setAccessed(int accessed) {
		this.accessed = accessed;
	}
}
