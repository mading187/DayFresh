package fresh.entity;

public class MemberInfo {
	private Integer mno;
	private String account;
	private String pwd;
	private String nikeName;
	private String realName;
	private String tel;
	private String email;
	private String photo;
	private String regDate;
	private Integer status;
	
	
	
	public void setStatus(Integer status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "MemberInfo [account=" + account + ", pwd=" + pwd + ", nikeName=" + nikeName + ", realName=" + realName
				+ ", tel=" + tel + ", email=" + email + ", photo=" + photo + ", regDate=" + regDate + ", status="
				+ status + "]";
	}

	

	public Integer getMno() {
		return mno;
	}


	public void setMno(Integer mno) {
		this.mno = mno;
	}


	public String getNikeName() {
		return nikeName;
	}


	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getRegDate() {
		return regDate;
	}


	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}



	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
