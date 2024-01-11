package dao;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * 数据库操作辅助类，提供数据库连接、表创建、数据查询等功能
 */
public class DBUnitHelper {

	/**
	 * 数据库名称
	 */
	static String dbname = "aproject";
	/**
	 * 数据库用户名
	 */
	static String username = "root";
	/**
	 * 数据库密码
	 */
	static String password = "123456";

	/**
	 * 获取数据库连接
	 * @return 数据库连接对象
	 */
	public static Connection getConn() {
		Connection conn = null;
		try {
			// 加载数据库驱动
			DbUtils.loadDriver("com.mysql.jdbc.Driver");
			// 获取数据库连接
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + dbname + "?characterEncoding=utf-8", username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 创建数据库
	 * @return 执行结果信息
	 */
	public static String createDB() {
		Connection connect = null;
		try {
			// 加载数据库驱动
			DbUtils.loadDriver("com.mysql.jdbc.Driver");
			// 获取数据库连接
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + "?characterEncoding=utf-8", username, password);
			// 执行创建数据库的SQL语句
			connect.createStatement().execute("CREATE DATABASE  if NOT EXISTS " + dbname + ";");
			return "执行成功";
		} catch (Exception e) {
			return "数据库用户名或密码错误";
		} finally {
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建实体类对应的数据库表
	 */
	public static void createTables() {
		// 获取实体类所在目录
		File javafile = new File("src//entity");
		// 获取目录下所有文件
		File[] files = javafile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 获取实体类的类路径
			String clazzpath = "entity." + files[i].getName().replace(".java", "");
			// 获取表名
			String tablename = files[i].getName().replace(".java", "").toLowerCase();
			// 执行删除表的SQL语句
			String droptable = "DROP TABLE IF EXISTS `" + tablename + "`;";
			try {
				getConn().createStatement().executeUpdate(droptable);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 构建创建表的SQL语句
			StringBuffer sbf = new StringBuffer();
			sbf.append("CREATE TABLE if not exists `" + tablename + "` (");
			List<String> list = getAllColumns(clazzpath);
			for (int j = 0; j < list.size(); j++) {
				if (j == 0) {
					sbf.append("  `" + list.get(j) + "` int(11) NOT NULL auto_increment,");
				} else {
					sbf.append("  `" + list.get(j) + "` varchar(200) default NULL,");
				}
			}
			sbf.append("  PRIMARY KEY  (`" + list.get(0) + "`)");
			sbf.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
			try {
				// 执行创建表的SQL语句
				getConn().createStatement().executeUpdate(sbf.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取实体类的所有字段名
	 * @param classPath 实体类的类路径
	 * @return 包含所有字段名的列表
	 */
	public static List<String> getAllColumns(String classPath) {
		List<String> list = new ArrayList<>();
		try {
			Class clazz = Class.forName(classPath);
			for (Field field : clazz.getDeclaredFields()) {
				if (!field.getGenericType().getTypeName().equals("byte[]")) {
					list.add(field.getName());
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 主方法，用于创建实体类对应的数据库表
	 * @param args
	 */
	public static void main(String[] args) {
		DBUnitHelper.createTables();
	}

	/**
	 * 执行更新SQL语句
	 * @param sql SQL语句
	 * @param objects SQL参数
	 * @return 受影响的记录数
	 */
	public static Integer executeUpdate(String sql, Object... objects) {
		Connection conn = getConn();
		QueryRunner qr = new QueryRunner();
		Integer rtn = 0;
		try {
			// 执行更新SQL语句
			if (objects == null) {
				rtn = qr.update(conn, sql);
			} else {
				rtn = qr.update(conn, sql, objects);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭数据库连接
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}

	/**
	 * 执行更新SQL语句
	 * @param sql SQL语句
	 * @return 受影响的记录数
	 */
	public static Integer executeUpdate(String sql) {
		return executeUpdate(sql, null);
	}

	/**
	 * 执行查询SQL语句
	 * @param sql SQL语句
	 * @param cls 结果集映射的类
	 * @param objects SQL参数
	 * @param <T> 结果集映射的类类型
	 * @return 包含查询结果的实体对象列表
	 */
	public static <T> List<T> executeQuery(String sql, Class<T> cls, Object... objects) {
		Connection conn = getConn();
		List<T> list = null;
		try {
			QueryRunner rq = new QueryRunner();
			// 执行查询SQL语句
			if (objects == null) {
				list = rq.query(conn, sql, new BeanListHandler<T>(cls));
			} else {
				list = rq.query(conn, sql, new BeanListHandler<T>(cls), objects);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭数据库连接
				DbUtils.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 执行查询SQL语句
	 * @param sql SQL语句
	 * @param cls 结果集映射的类
	 * @param <T> 结果集映射的类类型
	 * @return 包含查询结果的实体对象列表
	 */
	public static <T> List<T> executeQuery(String sql, Class<T> cls) {
		return executeQuery(sql, cls, null);
	}
}
