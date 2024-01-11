package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entity.Guanliyuan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 控制管理员登录界面的控制器
 */
public class GuanliyuanLoginController implements Initializable {

	// 静态变量用于保存登录后的用户名
	public static String username;

	// 用户名输入框
	@FXML
	private TextField usernameTxt;

	// 密码输入框
	@FXML
	private PasswordField passwordTxt;

	/**
	 * 初始化方法，由Initializable接口要求实现
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * 登录按钮的事件处理方法
	 *
	 * @param e 事件对象
	 */
	@FXML
	public void login(ActionEvent e) {
		Guanliyuan guanliyuan = new Guanliyuan();
		guanliyuan.setYonghuming(usernameTxt.getText());
		List<Guanliyuan> list = guanliyuan.query();
		if (list.size() > 0) {
			if (list.get(0).getMima().equals(passwordTxt.getText())) {
				username = list.get(0).getYonghuming();
				showMsg("登录成功");
				Stage stage = new Stage();
				stage.setTitle("系统功能");
				try {
					stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/GuanliyuanMainUI.fxml"))));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				stage.show();
				((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
				return;
			}
		}
		showMsg("用户名或密码错误，登录失败");
	}

	/**
	 * 注册按钮的事件处理方法
	 */
	@FXML
	public void regist() {
		try {
			Stage stage = new Stage();
			stage.setTitle("注册");
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/GuanliyuanRegistUI.fxml"))));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示信息提示框
	 *
	 * @param msg 提示信息
	 */
	public void showMsg(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.titleProperty().set("提示");
		alert.headerTextProperty().set(msg);
		alert.showAndWait();
	}
}
