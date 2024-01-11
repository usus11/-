package controller;

import java.io.File;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entity.Shoupiao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * 售票信息界面控制器
 */
public class ShoupiaoController implements Initializable {

	@FXML
	private TableView<Shoupiao> shoupiaoTable;
	@FXML
	private TableColumn<Shoupiao, String> checiColumn;
	@FXML
	private TableColumn<Shoupiao, String> shifazhanColumn;
	@FXML
	private TableColumn<Shoupiao, String> zhongdianzhanColumn;
	@FXML
	private TableColumn<Shoupiao, String> riqiColumn;
	@FXML
	private TableColumn<Shoupiao, String> shijianColumn;
	@FXML
	private TableColumn<Shoupiao, String> chengcherenColumn;
	@FXML
	private TableColumn<Shoupiao, String> lianxidianhuaColumn;
	@FXML
	private ComboBox<String> checiCombox;
	@FXML
	private ComboBox<String> checiQueryCombox;
	@FXML
	private TextField shifazhanQueryTxt;
	@FXML
	private TextField shifazhanTxt;
	@FXML
	private TextField zhongdianzhanQueryTxt;
	@FXML
	private TextField zhongdianzhanTxt;
	@FXML
	private TextField riqiQueryTxt;
	@FXML
	private TextField riqiTxt;
	@FXML
	private TextField shijianQueryTxt;
	@FXML
	private TextField shijianTxt;
	@FXML
	private TextField chengcherenQueryTxt;
	@FXML
	private TextField chengcherenTxt;
	@FXML
	private TextField lianxidianhuaQueryTxt;
	@FXML
	private TextField lianxidianhuaTxt;

	private Shoupiao shoupiao = new Shoupiao();
	private ObservableList<Shoupiao> list = FXCollections.observableArrayList();

	/**
	 * 初始化方法，由Initializable接口要求实现
	 */
	public void initialize(URL location, ResourceBundle resources) {
		shoupiaoTable.setItems(list);
		checiColumn.setCellValueFactory(new PropertyValueFactory("checi"));
		shifazhanColumn.setCellValueFactory(new PropertyValueFactory("shifazhan"));
		zhongdianzhanColumn.setCellValueFactory(new PropertyValueFactory("zhongdianzhan"));
		riqiColumn.setCellValueFactory(new PropertyValueFactory("riqi"));
		shijianColumn.setCellValueFactory(new PropertyValueFactory("shijian"));
		chengcherenColumn.setCellValueFactory(new PropertyValueFactory("chengcheren"));
		lianxidianhuaColumn.setCellValueFactory(new PropertyValueFactory("lianxidianhua"));

		shoupiaoTable.setOnMouseClicked(e -> {
			if (shoupiaoTable.getSelectionModel().getSelectedItem() != null) {
				checiCombox.getSelectionModel().select(shoupiaoTable.getSelectionModel().getSelectedItem().getCheci());
				shifazhanTxt.setText(shoupiaoTable.getSelectionModel().getSelectedItem().getShifazhan());
				zhongdianzhanTxt.setText(shoupiaoTable.getSelectionModel().getSelectedItem().getZhongdianzhan());
				riqiTxt.setText(shoupiaoTable.getSelectionModel().getSelectedItem().getRiqi());
				shijianTxt.setText(shoupiaoTable.getSelectionModel().getSelectedItem().getShijian());
				chengcherenTxt.setText(shoupiaoTable.getSelectionModel().getSelectedItem().getChengcheren());
				lianxidianhuaTxt.setText(shoupiaoTable.getSelectionModel().getSelectedItem().getLianxidianhua());
			}
		});

		List<entity.Checi> checilist = new entity.Checi().query();
		for (int i = 0; i < checilist.size(); i++) {
			checiCombox.getItems().add(checilist.get(i).getCheci());
			checiQueryCombox.getItems().add(checilist.get(i).getCheci());
		}

		checiCombox.setOnAction(e -> {
			entity.Checi checi = new entity.Checi();
			checi.setCheci(checiCombox.getSelectionModel().getSelectedItem());
			shifazhanTxt.setText(checi.query().get(0).getShifazhan());
			zhongdianzhanTxt.setText(checi.query().get(0).getZhongdianzhan());
			riqiTxt.setText(checi.query().get(0).getRiqi());
			shijianTxt.setText(checi.query().get(0).getShijian());
		});

		refresh();
	}

	/**
	 * 刷新表格数据
	 */
	@FXML
	public void refresh() {
		list.clear();
		list.addAll(shoupiao.query());
		reset();
	}

	/**
	 * 删除选中的数据
	 */
	@FXML
	public void delete() {
		if (shoupiaoTable.getSelectionModel().getSelectedItem() != null) {
			shoupiaoTable.getSelectionModel().getSelectedItem().delete();
			showMsg("删除成功");
			refresh();
		}
	}

	/**
	 * 添加新数据
	 */
	@FXML
	public void add() {
		Shoupiao shoupiao = new Shoupiao();
		shoupiao.setCheci(checiCombox.getSelectionModel().getSelectedItem());
		shoupiao.setShifazhan(shifazhanTxt.getText());
		shoupiao.setZhongdianzhan(zhongdianzhanTxt.getText());
		shoupiao.setRiqi(riqiTxt.getText());
		shoupiao.setShijian(shijianTxt.getText());
		shoupiao.setChengcheren(chengcherenTxt.getText());
		shoupiao.setLianxidianhua(lianxidianhuaTxt.getText());
		shoupiao.add();
		showMsg("添加成功");
		refresh();
	}

	/**
	 * 修改选中的数据
	 */
	@FXML
	public void edit() {
		if (shoupiaoTable.getSelectionModel().getSelectedItem() != null) {
			Shoupiao shoupiao = shoupiaoTable.getSelectionModel().getSelectedItem();
			shoupiao.setCheci(checiCombox.getSelectionModel().getSelectedItem());
			shoupiao.setShifazhan(shifazhanTxt.getText());
			shoupiao.setZhongdianzhan(zhongdianzhanTxt.getText());
			shoupiao.setRiqi(riqiTxt.getText());
			shoupiao.setShijian(shijianTxt.getText());
			shoupiao.setChengcheren(chengcherenTxt.getText());
			shoupiao.setLianxidianhua(lianxidianhuaTxt.getText());
			shoupiao.update();
			showMsg("修改成功");
			refresh();
		}
	}

	/**
	 * 重置输入框
	 */
	@FXML
	public void reset() {
		checiCombox.getSelectionModel().select(null);
		shifazhanTxt.clear();
		zhongdianzhanTxt.clear();
		riqiTxt.clear();
		shijianTxt.clear();
		chengcherenTxt.clear();
		lianxidianhuaTxt.clear();
		checiQueryCombox.getSelectionModel().select(null);
		shifazhanQueryTxt.clear();
		zhongdianzhanQueryTxt.clear();
		riqiQueryTxt.clear();
		shijianQueryTxt.clear();
		chengcherenQueryTxt.clear();
		lianxidianhuaQueryTxt.clear();
	}

	/**
	 * 根据条件查询数据
	 */
	@FXML
	public void query() {
		list.clear();
		Shoupiao shoupiao = new Shoupiao();
		shoupiao.setCheci(checiQueryCombox.getSelectionModel().getSelectedItem());
		shoupiao.setShifazhan(shifazhanQueryTxt.getText());
		shoupiao.setZhongdianzhan(zhongdianzhanQueryTxt.getText());
		shoupiao.setRiqi(riqiQueryTxt.getText());
		shoupiao.setShijian(shijianQueryTxt.getText());
		shoupiao.setChengcheren(chengcherenQueryTxt.getText());
		shoupiao.setLianxidianhua(lianxidianhuaQueryTxt.getText());
		list.addAll(shoupiao.query());
	}

	/**
	 * 文件拷贝方法
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
	 */
	public void showMsg(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.titleProperty().set("提示");
		alert.headerTextProperty().set(msg);
		alert.showAndWait();
	}
}
