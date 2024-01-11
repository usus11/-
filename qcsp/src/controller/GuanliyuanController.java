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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 管理员信息界面控制器
 */
public class GuanliyuanController implements Initializable {

	@FXML
	private TableView<Guanliyuan> guanliyuanTable;
	@FXML
	private TableColumn<Guanliyuan, String> yonghumingColumn;
	@FXML
	private TableColumn<Guanliyuan, String> mimaColumn;
	@FXML
	private TextField yonghumingQueryTxt;
	@FXML
	private TextField yonghumingTxt;
	@FXML
	private TextField mimaQueryTxt;
	@FXML
	private TextField mimaTxt;

	// Guanliyuan实体类的实例，用于执行与管理员信息相关的操作
	private Guanliyuan guanliyuan = new Guanliyuan();

	// 用于在UI中显示的ObservableList
	private ObservableList<Guanliyuan> list = FXCollections.observableArrayList();

	// 初始化方法，由Initializable接口要求实现
	public void initialize(URL location, ResourceBundle resources) {
		// 将表格与ObservableList进行绑定
		guanliyuanTable.setItems(list);

		// 配置表格的列，通过PropertyValueFactory将列与实体类的属性关联
		yonghumingColumn.setCellValueFactory(new PropertyValueFactory("yonghuming"));
		mimaColumn.setCellValueFactory(new PropertyValueFactory("mima"));

		// 设置表格行点击事件
		guanliyuanTable.setOnMouseClicked(e -> {
			if (guanliyuanTable.getSelectionModel().getSelectedItem() != null) {
				// 将选中行的数据显示在对应的文本框中
				yonghumingTxt.setText(guanliyuanTable.getSelectionModel().getSelectedItem().getYonghuming());
				mimaTxt.setText(guanliyuanTable.getSelectionModel().getSelectedItem().getMima());
			}
		});

		// 初始加载数据
		refresh();
	}

	// 刷新表格数据
	@FXML
	public void refresh() {
		list.clear();
		list.addAll(guanliyuan.query());
		// 重置输入框
		reset();
	}

	// 删除选中的数据
	@FXML
	public void delete() {
		if (guanliyuanTable.getSelectionModel().getSelectedItem() != null) {
			// 调用实体类的delete方法删除数据
			guanliyuanTable.getSelectionModel().getSelectedItem().delete();
			showMsg("删除成功");
			// 刷新表格
			refresh();
		}
	}

	// 添加新数据
	@FXML
	public void add() {
		Guanliyuan guanliyuan = new Guanliyuan();
		// 从文本框中获取数据，并设置到实体类中
		guanliyuan.setYonghuming(yonghumingTxt.getText());
		guanliyuan.setMima(mimaTxt.getText());
		// 调用实体类的add方法添加数据
		guanliyuan.add();
		showMsg("添加成功");
		// 刷新表格
		refresh();
	}

	// 修改选中的数据
	@FXML
	public void edit() {
		if (guanliyuanTable.getSelectionModel().getSelectedItem() != null) {
			Guanliyuan guanliyuan = guanliyuanTable.getSelectionModel().getSelectedItem();
			// 从文本框中获取数据，并设置到实体类中
			guanliyuan.setYonghuming(yonghumingTxt.getText());
			guanliyuan.setMima(mimaTxt.getText());
			// 调用实体类的update方法更新数据
			guanliyuan.update();
			showMsg("修改成功");
			// 刷新表格
			refresh();
		}
	}

	// 重置输入框
	@FXML
	public void reset() {
		yonghumingTxt.clear();
		mimaTxt.clear();
		yonghumingQueryTxt.clear();
		mimaQueryTxt.clear();
	}

	// 根据条件查询数据
	@FXML
	public void query() {
		list.clear();
		Guanliyuan guanliyuan = new Guanliyuan();
		// 从查询框中获取条件，并设置到实体类中
		guanliyuan.setYonghuming(yonghumingQueryTxt.getText());
		guanliyuan.setMima(mimaQueryTxt.getText());
		// 调用实体类的query方法查询数据
		list.addAll(guanliyuan.query());
	}

	// 文件拷贝方法
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

	// 显示信息提示框
	public void showMsg(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.titleProperty().set("提示");
		alert.headerTextProperty().set(msg);
		alert.showAndWait();
	}
}
