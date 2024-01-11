package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Guanliyuan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 管理员注册界面控制器
 */
public class GuanliyuanRegistController implements Initializable {

	// 用户名输入框
	@FXML
	private TextField yonghumingTxt;

	// 密码输入框
	@FXML
	private TextField mimaTxt;

	/**
	 * 初始化方法，由Initializable接口要求实现
	 */
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * 注册按钮的事件处理方法
	 *
	 * @param e 事件对象
	 */
	@FXML
	public void regist(ActionEvent e) {
		Guanliyuan guanliyuan = new Guanliyuan();
		guanliyuan.setYonghuming(yonghumingTxt.getText());
		guanliyuan.setMima(mimaTxt.getText());
		guanliyuan.add();
		showMsg("注册成功");
		((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
	}

	/**
	 * 关闭按钮的事件处理方法
	 *
	 * @param e 事件对象
	 */
	@FXML
	public void close(ActionEvent e) {
		((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
	}

	/**
	 * 文件拷贝方法
	 *
	 * @param source 源文件
	 * @param dest   目标文件
	 * @throws IOException IO异常
	 */
	private void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
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
