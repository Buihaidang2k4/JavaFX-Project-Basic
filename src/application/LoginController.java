package application;

import java.io.IOException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repo.UserDAO;

public class LoginController {
	@FXML
	private TextField emailIf;

	@FXML
	private PasswordField passwordTF;

	@FXML
	private Label message;

	@FXML
	private Hyperlink hyperlink;
	private Model.User user;

	  @FXML
	    public void onClickLoginBtn() {
	        // Lấy giá trị từ các trường nhập liệu
	        String email = emailIf.getText();
	        String password = passwordTF.getText();

	        // Biểu thức chính quy để kiểm tra định dạng email
	        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	        Pattern pattern = Pattern.compile(emailRegex);

	        // Kiểm tra nếu trường email hoặc password bị trống
	        if (email.isEmpty() || password.isEmpty()) {
	            emailIf.setStyle(
	                    "-fx-border-color: red;-fx-background-color: transparent;-fx-border-width: 0px 0px 2px 0px;");
	            passwordTF.setStyle(
	                    "-fx-border-color: red;-fx-background-color: transparent;-fx-border-width: 0px 0px 2px 0px;");

	            message.setText("Email và mật khẩu không được để trống.");
	            return; // Dừng lại không tiếp tục thực hiện các bước sau
	        }

	        // Kiểm tra định dạng email
	        if (!pattern.matcher(email).matches()) {
	            emailIf.setStyle(
	                    "-fx-border-color: red;-fx-background-color: transparent;-fx-border-width: 0px 0px 2px 0px;");
	            message.setText("Email không đúng định dạng.");
	            return; // Dừng lại không tiếp tục thực hiện các bước sau
	        }

	        // In thông tin ra console
	        System.out.println("Email: " + email);
	        System.out.println("Password: " + password);

	        // Kiểm tra user với email và password trong cơ sở dữ liệu
	        user = UserDAO.checkLogin(email, password);

	        if (user != null) {
	            // Chuyển đến màn hình chính và truyền thông tin User đăng nhập
	            try {
	                goToHomeScreen(user);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } else {
	            message.setText("Lỗi: Email hoặc mật khẩu không đúng.");
	        }
	    }

	
	private void goToHomeScreen(Model.User user) throws IOException {
	    System.out.println("Loading HomeProduct.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/HomeProduct.fxml"));
	    Parent root = fxmlLoader.load();

	    System.out.println("FXML loaded successfully");
	    HomeProductController hc = fxmlLoader.getController();
	    System.out.println("Controller loaded: " + hc);
	    hc.setLoginedUser(user);

	    Scene scene = new Scene(root);
	    Stage homeStage = new Stage();
	    homeStage.setScene(scene);
	    homeStage.setTitle("Home");
	    homeStage.show();
	    

	    // Close the login stage
	    Stage loginStage = (Stage) emailIf.getScene().getWindow();
	    loginStage.close();
	}

	@FXML
	public void onHyperlinkClick(ActionEvent event) {
	    try {
	        // Load the new FXML document for the registration scene
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/Register.fxml"));
	        Parent registerRoot = fxmlLoader.load();

	        // Get the current stage from a component in your current scene
	        Stage currentStage = (Stage) hyperlink.getScene().getWindow();

	        // Set the new scene to the stage
	        Scene registerScene = new Scene(registerRoot);
	        currentStage.setScene(registerScene);
	        currentStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
