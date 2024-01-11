package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entity.Checi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 车次管理界面控制器
 */
public class CheciController implements Initializable {

	@FXML
	private TableView<Checi> checiTable;
	@FXML
	private TableColumn<Checi, String> checiColumn;
	@FXML
	private TableColumn<Checi, String> shifazhanColumn;
	@FXML
	private TableColumn<Checi, String> zhongdianzhanColumn;
	@FXML
	private TableColumn<Checi, String> riqiColumn;
	@FXML
	private TableColumn<Checi, String> shijianColumn;
	@FXML
	private TextField checiQueryTxt;
	@FXML
	private TextField checiTxt;
	@FXML
	private TextField shifazhanQueryTxt;
	@FXML
	private TextField shifazhanTxt;
	@FXML
	private TextField zhongdianzhanQueryTxt;
	@FXML
	private TextField zhongdianzhanTxt;
	@FXML
	private DatePicker riqiQueryDate;
	@FXML
	private DatePicker riqiDate;
	@FXML
	private TextField shijianQueryTxt;
	@FXML
	private TextField shijianTxt;

	// Checi实体类的实例，用于执行与火车信息相关的操作
	private Checi checi = new Checi();

	// 用于在UI中显示的ObservableList
	private ObservableList<Checi> list = FXCollections.observableArrayList();

	// 初始化方法，由Initializable接口要求实现
	public void initialize(URL location, ResourceBundle resources) {
		// 将表格与ObservableList进行绑定
		checiTable.setItems(list);

		// 配置表格的列，通过PropertyValueFactory将列与实体类的属性关联
		checiColumn.setCellValueFactory(new PropertyValueFactory("checi"));
		shifazhanColumn.setCellValueFactory(new PropertyValueFactory("shifazhan"));
		zhongdianzhanColumn.setCellValueFactory(new PropertyValueFactory("zhongdianzhan"));
		riqiColumn.setCellValueFactory(new PropertyValueFactory("riqi"));
		shijianColumn.setCellValueFactory(new PropertyValueFactory("shijian"));

		// 设置表格行点击事件
		checiTable.setOnMouseClicked(e -> {
			if (checiTable.getSelectionModel().getSelectedItem() != null) {
				// 将选中行的数据显示在对应的文本框和日期选择器中
				checiTxt.setText(checiTable.getSelectionModel().getSelectedItem().getCheci());
				shifazhanTxt.setText(checiTable.getSelectionModel().getSelectedItem().getShifazhan());
				zhongdianzhanTxt.setText(checiTable.getSelectionModel().getSelectedItem().getZhongdianzhan());
				riqiDate.setValue(LocalDate.parse(checiTable.getSelectionModel().getSelectedItem().getRiqi()));
				shijianTxt.setText(checiTable.getSelectionModel().getSelectedItem().getShijian());
			}
		});

		// 初始加载数据
		refresh();
	}

	// 刷新表格数据
	@FXML
	public void refresh() {
		list.clear();
		list.addAll(checi.query());
		// 重置输入框
		reset();
	}

	// 删除选中的数据
	@FXML
	public void delete() {
		if (checiTable.getSelectionModel().getSelectedItem() != null) {
			// 调用实体类的delete方法删除数据
			checiTable.getSelectionModel().getSelectedItem().delete();
			showMsg("删除成功");
			// 刷新表格
			refresh();
		}
	}

	@FXML
	public void add() {
		Checi checi = new Checi();
		// 从文本框和日期选择器中获取数据，并设置到实体类中
		checi.setCheci(checiTxt.getText());
		checi.setShifazhan(shifazhanTxt.getText());
		checi.setZhongdianzhan(zhongdianzhanTxt.getText());
		if (riqiDate.getValue() != null)
			checi.setRiqi(riqiDate.getValue().toString());
		checi.setShijian(shijianTxt.getText());
		// 调用实体类的add方法添加数据
		checi.add();
		showMsg("添加成功");
		// 刷新表格
		refresh();
	}

	/**
	 *
	 */// 修改选中的数据
	@FXML
	public void edit() {
		if (checiTable.getSelectionModel().getSelectedItem() != null) {
			Checi checi = checiTable.getSelectionModel().getSelectedItem();
			// 从文本框和日期选择器中获取数据，并设置到实体类中
			checi.setCheci(checiTxt.getText());
			checi.setShifazhan(shifazhanTxt.getText());
			checi.setZhongdianzhan(zhongdianzhanTxt.getText());
			if (riqiDate.getValue() != null)
				checi.setRiqi(riqiDate.getValue().toString());
			checi.setShijian(shijianTxt.getText());
			// 调用实体类的update方法更新数据
			checi.update();
			showMsg("修改成功");
			// 刷新表格
			refresh();
		}
	}

	// 重置输入框
	@FXML
	public void reset() {
		checiTxt.clear();
		shifazhanTxt.clear();
		zhongdianzhanTxt.clear();
		riqiDate.setValue(null);
		shijianTxt.clear();
		checiQueryTxt.clear();
		shifazhanQueryTxt.clear();
		zhongdianzhanQueryTxt.clear();
		riqiQueryDate.setValue(null);
		shijianQueryTxt.clear();
	}

	@FXML
	public void query() {
		list.clear();
		Checi checi = new Checi();
		// 从查询框中获取条件，并设置到实体类中
		checi.setCheci(checiQueryTxt.getText());
		checi.setShifazhan(shifazhanQueryTxt.getText());
		checi.setZhongdianzhan(zhongdianzhanQueryTxt.getText());
		if (riqiQueryDate.getValue() != null)
			checi.setRiqi(riqiQueryDate.getValue().toString());
		checi.setShijian(shijianQueryTxt.getText());
		// 调用实体类的query方法查询数据
		list.addAll(checi.query());
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
