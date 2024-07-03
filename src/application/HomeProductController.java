package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.DienThoai;
import Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import repo.DienThoaiDao;

public class HomeProductController implements Initializable {
	// Name user
	@FXML
	private Label loginFullName;

	// Table
	@FXML
	private TableView<DienThoai> dienThoaiListTV;

	@FXML
	private TableColumn<DienThoai, String> maDienThoaiCol;

	@FXML
	private TableColumn<DienThoai, String> tenDienThoaiCol;

	@FXML
	private TableColumn<DienThoai, String> mauDienThoaiCol;

	@FXML
	private TableColumn<DienThoai, Integer> giaThanhDienThoaiCol;

	// Exit
	@FXML
	private Hyperlink exitHyperlink;

	@FXML
	private ImageView imgView;
	// TextFied
	@FXML
	private TextField maDienThoaiTF;
	@FXML
	private TextField tenDienThoaiTF;
	@FXML
	private TextField mauDienThoaiTF;
	@FXML
	private TextField giaThanhTF;

	// Button
	@FXML
	private Button addButton;

	@FXML
	private Button editButton;

	@FXML
	private Button deleteButton;
	
	@FXML
	private Button changeButton;
	
	

	private User loginedUser;

	public User getLoginedUser() {
		return loginedUser;
	}

	public void setLoginedUser(User loginedUser) {
		this.loginedUser = loginedUser;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			loginFullName.setText(" " + loginedUser.getEmail());
			if (!loginedUser.getRole()) {
				// Nếu không phải admin, ẩn các nút thêm, sửa, xóa đi
				addButton.setVisible(false);
				editButton.setVisible(false);
				deleteButton.setVisible(false);
				changeButton.setVisible(false);
			}
		});

		maDienThoaiCol.setCellValueFactory(new PropertyValueFactory<>("maDienThoai"));
		tenDienThoaiCol.setCellValueFactory(new PropertyValueFactory<>("tenDienThoai"));
		mauDienThoaiCol.setCellValueFactory(new PropertyValueFactory<>("mauDienThoai"));
		giaThanhDienThoaiCol.setCellValueFactory(new PropertyValueFactory<>("giaThanh"));

		// Đưa dữ liệu cho tableView
		List<DienThoai> dienThoaiList = DienThoaiDao.listAllPhones();
		ObservableList<DienThoai> obsList = FXCollections.observableArrayList(dienThoaiList);
		dienThoaiListTV.setItems(obsList);
	}

	@FXML
	public void onClickExit() {
		exitHyperlink.getScene().getWindow().hide();
	}

	@FXML
	public void onClickRow() {
		DienThoai selectedItem = dienThoaiListTV.getSelectionModel().getSelectedItem();

		if (selectedItem != null) { // Kiểm tra selectedItem có null hay không
			maDienThoaiTF.setText(selectedItem.getMaDienThoai());
			tenDienThoaiTF.setText(selectedItem.getTenDienThoai());
			mauDienThoaiTF.setText(selectedItem.getMauDienThoai());
			giaThanhTF.setText(String.valueOf(selectedItem.getGiaThanh()));

			try {
				if (selectedItem.getImgPath() != null) {
					File file = new File(selectedItem.getImgPath());
					if (file.exists()) {
						FileInputStream input = new FileInputStream(file);
						Image image = new Image(input);
						imgView.setImage(image);
					} else {
						imgView.setImage(null);
						System.err.println("Image file not found: " + selectedItem.getImgPath());
					}
				} else {
					imgView.setImage(null);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void onClickAddButton() {
		String maDienThoai = maDienThoaiTF.getText();
		String tenDienThoai = tenDienThoaiTF.getText();
		String mau = mauDienThoaiTF.getText();
		int giaThanh = Integer.parseInt(giaThanhTF.getText());
		String imgPath = ""; // Cần thêm chức năng chọn ảnh để lấy imgPath

		DienThoai dienThoai = new DienThoai(maDienThoai, tenDienThoai, mau, giaThanh, imgPath);
		if (DienThoaiDao.addPhone(dienThoai)) {
			dienThoaiListTV.getItems().add(dienThoai);
		} else {
			showAlert("Error", "Thêm điện thoại thất bại.");
		}
	}
	
	


	@FXML
	public void onClickEditButton() {
		DienThoai selectedItem = dienThoaiListTV.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			selectedItem.setMaDienThoai(maDienThoaiTF.getText());
			selectedItem.setTenDienThoai(tenDienThoaiTF.getText());
			selectedItem.setMauDienThoai(mauDienThoaiTF.getText());
			selectedItem.setGiaThanh(Integer.parseInt(giaThanhTF.getText()));
			selectedItem.setImgPath(""); // Cần thêm chức năng chọn ảnh để cập nhật imgPath

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Xác nhận cập nhật");
			alert.setHeaderText(null);
			alert.setContentText("Bạn có chắc chắn muốn cập nhật thông tin điện thoại này?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				if (DienThoaiDao.updatePhone(selectedItem)) {
					dienThoaiListTV.refresh();
					showAlert("Thông báo", "Cập nhật điện thoại thành công.");
				} else {
					showAlert("Lỗi", "Cập nhật điện thoại thất bại.");
				}
			}
		} else {
			showAlert("Cảnh báo", "Không có điện thoại nào được chọn.");
		}
	}

	@FXML
	public void onClickDeleteButton() {
		int selectedIndex = dienThoaiListTV.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Bạn có muốn xóa ?");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				DienThoai selectedItem = dienThoaiListTV.getItems().get(selectedIndex);
				if (DienThoaiDao.deletePhone(selectedItem.getMaDienThoai())) {
					dienThoaiListTV.getItems().remove(selectedIndex);
				} else {
					showAlert("Error", "Xóa điện thoại thất bại.");
				}
			}
		} else {
			showAlert("Warning", "Không có điện thoại nào được chọn.");
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	public void onClickChangeImgButton() {
		// Mở hộp thoại chọn tệp để người dùng chọn ảnh mới
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			// Cập nhật ảnh trong imgView
			try {
				Image newImage = new Image(selectedFile.toURI().toString());
				imgView.setImage(newImage);
			} catch (Exception e) {
				showAlert1("Error", "Không thể đọc tệp ảnh.");
				e.printStackTrace();
			}

			// Lưu đường dẫn của ảnh vào cơ sở dữ liệu
			DienThoai selectedItem = dienThoaiListTV.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				String imgPath = selectedFile.getAbsolutePath();
				selectedItem.setImgPath(imgPath);

				// Cập nhật đường dẫn ảnh trong cơ sở dữ liệu
				if (!DienThoaiDao.updateImgPath(selectedItem.getMaDienThoai(), imgPath)) {
					showAlert("Error", "Lỗi khi cập nhật đường dẫn ảnh vào cơ sở dữ liệu.");
				}
			}
		}
	}

	private void showAlert1(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	public void onClickClearButton() {
		// Xóa trắng các TextField
		maDienThoaiTF.clear();
		tenDienThoaiTF.clear();
		mauDienThoaiTF.clear();
		giaThanhTF.clear();

		imgView.setImage(null);

		// Bỏ chọn các cột trong TableView
		dienThoaiListTV.getSelectionModel().clearSelection();
	}
}
