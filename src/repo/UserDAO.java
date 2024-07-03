//package repo;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import Model.User;
//
//public class UserDAO {
//
//	public static List<User> listAllUsers() {
//		List<User> listBook = new ArrayList<>();
//
//		String sql = "SELECT * FROM tbluser";
//		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
//		String dbUser = "";
//		String dbPassword = "";
//
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
//
//			java.sql.Statement statement = connection.createStatement();
//			ResultSet resultSet = statement.executeQuery(sql);
//
//			while (resultSet.next()) {
//				String email = resultSet.getString("email");
//				String fullname = resultSet.getString("fullname");
//				String role = resultSet.getString("role");
//				User user = new User(email, fullname, role);
//				listBook.add(user);
//			}
//
//			resultSet.close();
//			statement.close();
//			connection.close();
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Không tìm thấy driver");
//		}
//
//		return listBook;
//	}
//
////	public static boolean addUser(User user) {
////		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
////		String dbUser = "";
////		String dbPassword = "";
////
////		boolean rowInserted = false;
////
////		try {
////			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
////			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
////			String sql = "INSERT INTO tbluser (email,fullname) VALUES (?,?)";
////
////			PreparedStatement statement = connection.prepareStatement(sql);
////			statement.setString(1, user.getEmail());
////			statement.setString(2, user.getFullname());
////
////			rowInserted = statement.executeUpdate() > 0;
////			statement.close();
////			connection.close();
////
////		} catch (ClassNotFoundException | SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////
////		return rowInserted;
////
////	}
//
//	public static boolean addUser(User user) {
//		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
//		String dbUser = "";
//		String dbPassword = "";
//
//		boolean rowInserted = false;
//
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
//			String sql = "INSERT INTO tbluser (email, fullname, role) VALUES (?, ?, ?)";
//
//			PreparedStatement statement = connection.prepareStatement(sql);
//			statement.setString(1, user.getEmail());
//			statement.setString(2, user.getFullname());
//			statement.setString(3, user.getRole());
//
//			rowInserted = statement.executeUpdate() > 0;
//			statement.close();
//			connection.close();
//
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//
//		return rowInserted;
//	}
//	
//	
//
//	public static User checkUser(String email) {
//		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
//		String dbUser = "";
//		String dbPassword = "";
//
//		User user = null;
//
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
//			String sql = "SELECT * from tbluser WHERE email = ? ";
//			PreparedStatement statement = connection.prepareStatement(sql);
//			statement.setString(1, email);
//
//			ResultSet result = statement.executeQuery();
//
//			if (result.next()) {
//				user = new User();
//				user.setFullname(result.getString("fullname"));
//				user.setEmail(email);
//			}
//
//			statement.close();
//			connection.close();
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return user;
//	}
//
//	public static User checkLogin(String email, String password) {
//		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb"; // Adjust the path
//		String dbUser = "";
//		String dbPassword = "";
//
//		User user = null;
//
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
//			String sql = "SELECT * from tbluser WHERE email = ? AND fullname = ?";
//			PreparedStatement statement = connection.prepareStatement(sql);
//			statement.setString(1, email);
//			statement.setString(2, password);
//
//			ResultSet result = statement.executeQuery();
//
//			if (result.next()) {
//				user = new User();
//				user.setFullname(result.getString("fullname"));
//				user.setEmail(email);
////	                user.setImgPath(result.getString("imgview"));  // Ensure this matches your column name
//			}
//
//			result.close();
//			statement.close();
//			connection.close();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//
//		return user;
//	}
//
//	public boolean userExists(String email) {
//		String jdbcURL = "jdbc:ucanaccess://QLNS.accdb";
//		try (Connection conn = DriverManager.getConnection(jdbcURL);
//				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tbluser WHERE email = ?")) {
//			stmt.setString(1, email);
//			try (ResultSet rs = stmt.executeQuery()) {
//				return rs.next();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//}



package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.User;

public class UserDAO {

    private static final String JDBC_URL = "jdbc:ucanaccess://QLNS.accdb";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    // List all users from the database
    public static List<User> listAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM tbluser";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String fullname = resultSet.getString("fullname");
                Boolean role = resultSet.getBoolean("role");
                User user = new User(email, fullname, role);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception occurred");
        }

        return userList;
    }

    // Add a new user to the database
    public static boolean addUser(User user) {
        String sql = "INSERT INTO tbluser (email, fullname, role) VALUES (?, ?, ?)";
        boolean rowInserted = false;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFullname());
            statement.setBoolean(3, user.getRole());

            rowInserted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception occurred");
        }

        return rowInserted;
    }

    // Check if a user exists by email
    public static User checkUser(String email) {
        String sql = "SELECT * FROM tbluser WHERE email = ?";
        User user = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setFullname(resultSet.getString("fullname"));
                    user.setEmail(email);
                    user.setRole(resultSet.getBoolean("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception occurred");
        }

        return user;
    }

    // Check login credentials
    public static User checkLogin(String email, String password) {
        String sql = "SELECT * FROM tbluser WHERE email = ? AND fullname = ?";
        User user = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setFullname(resultSet.getString("fullname"));
                    user.setEmail(email);
                    user.setRole(resultSet.getBoolean("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception occurred");
        }

        return user;
    }

    // Kiểm tra email đã tồn tại trong bảng chưa
    public boolean userExists(String email) {
        String sql = "SELECT * FROM tbluser WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception occurred");
        }

        return false;
    }
}

