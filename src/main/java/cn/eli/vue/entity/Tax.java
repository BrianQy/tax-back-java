package cn.eli.vue.entity;

public class Tax {

	@Override
	public String toString() {
		return "Tax{" +
				"uid=" + uid +
				", username='" + username + '\'' +
				", taxBefore='" + taxBefore + '\'' +
				", sxyj='" + sxyj + '\'' +
				", noTax='" + noTax + '\'' +
				", zxkc='" + zxkc + '\'' +
				", qtkc='" + qtkc + '\'' +
				", taxAfter='" + taxAfter + '\'' +
				", tax='" + tax + '\'' +
				", user_name='" + user_name + '\'' +
				'}';
	}

	private Integer uid;

	private String username;

	private String taxBefore;

	private String sxyj;

	private String noTax;

	private String zxkc;

	private String qtkc;

	private String taxAfter;

	private String tax;

	private String user_name;




	public Integer getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}




	public String getSxyj() {
		return sxyj;
	}

	public void setSxyj(String sxyj) {
		this.sxyj = sxyj;
	}

	public String getNoTax() {
		return noTax;
	}

	public void setNoTax(String noTax) {
		this.noTax = noTax;
	}

	public String getZxkc() {
		return zxkc;
	}

	public void setZxkc(String zxkc) {
		this.zxkc = zxkc;
	}

	public String getQtkc() {
		return qtkc;
	}

	public void setQtkc(String qtkc) {
		this.qtkc = qtkc;
	}

	public String getTaxAfter() {
		return taxAfter;
	}

	public void setTaxAfter(String taxAfter) {
		this.taxAfter = taxAfter;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTaxBefore() {
		return taxBefore;
	}

	public void setTaxBefore(String taxBefore) {
		this.taxBefore = taxBefore;
	}


	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
