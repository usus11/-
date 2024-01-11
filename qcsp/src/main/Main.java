package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 主应用程序类，用于启动 JavaFX 应用程序。
 */
public class Main extends Application {

	/**
	 * JavaFX 应用程序的入口点。
	 *
	 * @param primaryStage 主舞台
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// 加载 GuanliyuanLoginUI.fxml 文件作为场景，并设置为主舞台的场景
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/GuanliyuanLoginUI.fxml")));

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 主方法，启动 JavaFX 应用程序。
	 *
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
