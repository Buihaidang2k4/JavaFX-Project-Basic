package repo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.DienThoai;

public class DienThoaiDao {
	public static List<DienThoai> listAllPhones() {
		List<DienThoai> listPhones = new ArrayList<>();

		String sql = "SELECT * FROM iphone";
		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				String maDienThoai = resultSet.getString("maDienThoai");
				String tenDienThoai = resultSet.getString("tenDienThoai");
				String mauDienThoai = resultSet.getString("mauDienThoai");
				int giaThanh = resultSet.getInt("giaThanh");
				String imgPath = resultSet.getString("imgPath");

				DienThoai dienThoai = new DienThoai(maDienThoai, tenDienThoai, mauDienThoai, giaThanh, imgPath);
				listPhones.add(dienThoai);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Không tìm thấy driver hoặc lỗi kết nối cơ sở dữ liệu");
		}

		return listPhones;
	}

	
	public static DienThoai checkPhone(String maDienThoai) {
		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";

		DienThoai phone = null;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
			String sql = "SELECT * from iphone WHERE maDienThoai = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, maDienThoai);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				phone = new DienThoai();
				phone.setMaDienThoai(result.getString("maDienThoai"));
				phone.setTenDienThoai(result.getString("tenDienThoai"));
				phone.setMauDienThoai(result.getString("mauDienThoai"));
				phone.setGiaThanh(result.getInt("giaThanh"));
				phone.setImgPath(result.getString("imgPath"));
			}

			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return phone;
	}

	public static boolean updatePhone(DienThoai phone) {
		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";

		boolean rowUpdated = false;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
			String sql = "UPDATE iphone SET tenDienThoai = ?, mauDienThoai = ?, giaThanh = ?, imgPath = ? WHERE maDienThoai = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, phone.getTenDienThoai());
			statement.setString(2, phone.getMauDienThoai());
			statement.setInt(3, phone.getGiaThanh());
			statement.setString(4, phone.getImgPath());
			statement.setString(5, phone.getMaDienThoai());

			rowUpdated = statement.executeUpdate() > 0;
			statement.close();
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return rowUpdated;
	}
	public static boolean addPhone(DienThoai phone) {
		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";

		boolean rowInserted = false;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
			String sql = "INSERT INTO iphone (maDienThoai, tenDienThoai, mauDienThoai, giaThanh, imgPath) VALUES (?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, phone.getMaDienThoai());
			statement.setString(2, phone.getTenDienThoai());
			statement.setString(3, phone.getMauDienThoai());
			statement.setInt(4, phone.getGiaThanh());
			statement.setString(5, phone.getImgPath());

			rowInserted = statement.executeUpdate() > 0;
			statement.close();
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return rowInserted;
	}

	public static boolean deletePhone(String maDienThoai) {
		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";

		boolean rowDeleted = false;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
			String sql = "DELETE FROM iphone WHERE maDienThoai = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, maDienThoai);

			// Lấy imgPath của điện thoại cần xóa
			String imgPath = "";
			PreparedStatement imgStatement = connection
					.prepareStatement("SELECT imgPath FROM iphone WHERE maDienThoai = ?");
			imgStatement.setString(1, maDienThoai);
			ResultSet resultSet = imgStatement.executeQuery();
			if (resultSet.next()) {
				imgPath = resultSet.getString("imgPath");
			}
			resultSet.close();
			imgStatement.close();

			rowDeleted = statement.executeUpdate() > 0;
			statement.close();
			connection.close();

			// Xóa ảnh từ thư mục nếu có
			if (!imgPath.isEmpty()) {
				File imgFile = new File(imgPath);
				if (imgFile.exists()) {
					imgFile.delete();
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return rowDeleted;
	}

	public static boolean updateImgPath(String maDienThoai, String imgPath) {
		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";
		boolean rowUpdated = false;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
			String sql = "UPDATE iphone SET imgPath = ? WHERE maDienThoai = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, imgPath);
			statement.setString(2, maDienThoai);
			rowUpdated = statement.executeUpdate() > 0;
			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rowUpdated;
	}
}
