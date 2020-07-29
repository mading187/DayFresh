package fresh.entity;

public class CartInfo {

	private Integer cno;
	private Integer num;
	private Integer mno;
	private Integer gno;
	@Override
	public String toString() {
		return "CartInfo [cno=" + cno + ", num=" + num + ", mno=" + mno + ", gno=" + gno + "]";
	}
	public Integer getCno() {
		return cno;
	}
	public void setCno(Integer cno) {
		this.cno = cno;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getMno() {
		return mno;
	}
	public void setMno(Integer mno) {
		this.mno = mno;
	}
	public Integer getGno() {
		return gno;
	}
	public void setGno(Integer gno) {
		this.gno = gno;
	}
	
	
}
