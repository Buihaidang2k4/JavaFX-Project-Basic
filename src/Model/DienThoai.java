package Model;

public class DienThoai {
	private String maDienThoai;
	private String tenDienThoai;
	private String mauDienThoai;
	private int giaThanh;
	private String imgPath = "";

	public DienThoai() {
	}

	public DienThoai(String maDienThoai) {
		this.maDienThoai = maDienThoai;
	}

	public DienThoai(String maDienThoai, String tenDienThoai, String mauDienThoai, int giaThanh) {
		this.maDienThoai = maDienThoai;
		this.tenDienThoai = tenDienThoai;
		this.mauDienThoai = mauDienThoai;
		this.giaThanh = giaThanh;
	}

	public DienThoai(String maDienThoai, String tenDienThoai, String mauDienThoai, int giaThanh, String imgPath) {
		this(maDienThoai, tenDienThoai, mauDienThoai, giaThanh);
		this.imgPath = imgPath;
	}

	public String getMaDienThoai() {
		return maDienThoai;
	}

	public void setMaDienThoai(String maDienThoai) {
		this.maDienThoai = maDienThoai;
	}

	public String getTenDienThoai() {
		return tenDienThoai;
	}

	public void setTenDienThoai(String tenDienThoai) {
		this.tenDienThoai = tenDienThoai;
	}

	public String getMauDienThoai() {
		return mauDienThoai;
	}

	public void setMauDienThoai(String mauDienThoai) {
		this.mauDienThoai = mauDienThoai;
	}

	public int getGiaThanh() {
		return giaThanh;
	}

	public void setGiaThanh(int giaThanh) {
		this.giaThanh = giaThanh;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}
