package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 控制管理员主界面的控制器
 */
public class GuanliyuanMainController implements Initializable {

	// 主界面的VBox
	@FXML
	private VBox mainbox;

	/**
	 * 初始化方法，由Initializable接口要求实现
	 */
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * 打开管理员管理界面
	 */
	@FXML
	public void openGuanliyuanManageUI() {
		try {
			Stage stage = new Stage();
			stage.setTitle("管理员管理");
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/GuanliyuanManageUI.fxml"))));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开车次管理界面
	 */
	@FXML
	public void openCheciManageUI() {
		try {
			Stage stage = new Stage();
			stage.setTitle("车次管理");
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CheciManageUI.fxml"))));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开售票管理界面
	 */
	@FXML
	public void openShoupiaoManageUI() {
		try {
			Stage stage = new Stage();
			stage.setTitle("售票管理");
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ShoupiaoManageUI.fxml"))));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退出应用程序
	 */
	@FXML
	public void exit() {
		System.exit(0);
	}
}
