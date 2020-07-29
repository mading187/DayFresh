package fresh.entity;

import fresh.util.Stringutil;

public class GoodsInfo {
	private Integer  gno;
	private String gname;
	private String tname;
	private String price;
	private String pic;
	private Integer tno;

	private String unit;
	private String intro;
	private String pics;
	
	
	
	

	public GoodsInfo() {
		super();
	}

	@Override
	public String toString() {
		return "GoodsInfo [gno=" + gno + ", gname=" + gname + ", tname=" + tname + ", price=" + price + ", pic=" + pic
				+ ", tno=" + tno + ", unit=" + unit + ", intro=" + intro + ", pics=" + pics + "]";
	}
	
	public Integer getTno() {
		return tno;
	}

	public void setTno(Integer tno) {
		this.tno = tno;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getGno() {
		return gno;
	}
	public void setGno(Integer gno) {
		this.gno = gno;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}

	
	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
		setPic(pics);
	}
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		if(!Stringutil.checkNull(pics)) {
			this.pic = pics.split(";")[0];
			return;
		}
		this.pic = pic;
	}

	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
}
