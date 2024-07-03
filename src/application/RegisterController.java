package application;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repo.UserDAO;

public class RegisterController {
	@FXML
	private CheckBox AdminBox;

	@FXML
	private CheckBox UserBox;

	@FXML
	private TextField emailTF;

	@FXML
	private TextField passwordTF;

	@FXML
	private TextField passwordTF1;

	@FXML
	private Hyperlink goLoginHyper;

	// Chuyển đến trang login
	@FXML
	public void onClickGoLogin(ActionEvent event) {
		try {
			// Load the new FXML document for the registration scene
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent registerRoot = fxmlLoader.load();

			// Get the current stage from a component in your current scene
			Stage currentStage = (Stage) goLoginHyper.getScene().getWindow();

			// Set the new scene to the stage
			Scene registerScene = new Scene(registerRoot);
			currentStage.setScene(registerScene);
			currentStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@FXML
	public void initialize() {
		AdminBox.setOnAction(event -> {
			if (AdminBox.isSelected()) {
				UserBox.setSelected(false);
			}
		});

		UserBox.setOnAction(event -> {
			if (UserBox.isSelected()) {
				AdminBox.setSelected(false);
			}
		});
	}

	// Đăng ký 
	@FXML
	public void onClickRegisterBtn() {
	    String email = emailTF.getText();
	    String password = passwordTF.getText();
	    String confirmPassword = passwordTF1.getText();

	    if (!isValidEmail(email)) {
	        showAlert("Email Không Hợp Lệ", "Vui lòng nhập địa chỉ email hợp lệ.");
	        return;
	    }

	    if (!password.equals(confirmPassword)) {
	        showAlert("Mật Khẩu Không Khớp", "Mật khẩu không khớp.");
	        return;
	    }

	    Boolean role = getSelectedRole();
	    if (role == null) {
	        showAlert("ERORR!", "Vui lòng chọn quyền tài khoản.");
	        return;
	    }

	    UserDAO userDAO = new UserDAO();
	    if (userDAO.userExists(email)) {
	        showAlert("Người Dùng Đã Tồn Tại", "Một người dùng với địa chỉ email này đã tồn tại.");
	        return;
	    }

	    User newUser = new User(email, password, role);
	    boolean success = UserDAO.addUser(newUser);
	    if (success) {
	        showAlert("Đăng Ký Thành Công","Đăng ký thành công."+"\nTài khoản: "+email +"\nMật khẩu: "+ password);
	    } else {
	        showAlert("Đăng Ký Thất Bại", "Đã xảy ra lỗi trong quá trình đăng ký người dùng.");
	    }
	}


	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private boolean getSelectedRole() {
		if (AdminBox.isSelected()) {
			return true;
		} else if (UserBox.isSelected()) {
			return false;
		} else {
			return (Boolean) null;
		}
	}

}
