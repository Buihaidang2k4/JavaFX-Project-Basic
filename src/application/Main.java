package application;


// Xử lý hiển thị ảnh user
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			javafx.scene.Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root);

			scene.setOnKeyPressed(e -> {
				if(e.getCode() == KeyCode.ENTER) {
					System.out.println("ENTER");
				}
			});
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Đăng nhập");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error!");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
