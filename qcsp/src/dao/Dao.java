package dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 通用数据访问对象（DAO）类
 * @param <T> 实体类类型
 */
public class Dao<T> {
	// 类名字符串
	String clazzname;
	// 类对象
	Class clazz;

	/**
	 * 构造方法，根据传入的实体对象初始化类名和类对象
	 * @param entity 实体对象
	 */
	public Dao(T entity) {
		clazz = entity.getClass();
		clazzname = clazz.getSimpleName().toLowerCase();
	}

	/**
	 * 根据指定字段名和字段值查询数据
	 * @param key 字段名
	 * @param keyvalue 字段值
	 * @return 包含查询结果的实体对象列表
	 */
	public List<T> queryByKey(String key, String keyvalue) {
		String sql = "select * from " + clazzname + " where " + key + "=?";
		List<T> list = (List<T>) DBUnitHelper.executeQuery(sql, clazz, keyvalue);
		return list;
	}

	/**
	 * 根据默认主键字段和字段值查询数据
	 * @param keyvalue 字段值
	 * @return 包含查询结果的实体对象列表
	 */
	public List<T> queryByKey(String keyvalue) {
		String sql = "select * from " + clazzname + " where " + getKey() + "=?";
		List<T> list = (List<T>) DBUnitHelper.executeQuery(sql, clazz, keyvalue);
		return list;
	}

	/**
	 * 根据实体对象的非空字段值进行模糊查询
	 * @param entity 实体对象
	 * @return 包含查询结果的实体对象列表
	 */
	public List<T> query(T entity) {
		StringBuffer sbf = new StringBuffer("select * from " + clazzname + " where 1=1 ");
		for (Field field : clazz.getDeclaredFields()) {
			if (!field.getName().equals(getKey()) && !field.getName().equals("dbutil")) {
				if (getValue(entity, field.getName(), clazz) != null &&
						!getValue(entity, field.getName(), clazz).equals("")) {
					sbf.append(" and " + field.getName() + " like '%" +
							getValue(entity, field.getName(), clazz) + "%'");
				}
			}
		}
		System.out.println(sbf.toString());
		List<T> list = (List<T>) DBUnitHelper.executeQuery(sbf.toString(), clazz);
		return list;
	}

	/**
	 * 获取表中所有数据
	 * @return 包含查询结果的实体对象列表
	 */
	public List<T> getAll() {
		String sql = "select * from " + clazzname;
		List<T> list = (List<T>) DBUnitHelper.executeQuery(sql, clazz);
		return list;
	}

	/**
	 * 执行SQL语句
	 * @param sql SQL语句
	 */
	public void executeSql(String sql) {
		System.out.println(sql);
		DBUnitHelper.executeUpdate(sql);
	}

	/**
	 * 根据主键字段修改实体对象
	 * @param entity 实体对象
	 */
	public void update(T entity) {
		Integer value1 = (Integer) getValue(entity, getKey(), clazz);
		StringBuffer sbf = new StringBuffer();
		sbf.append("update " + clazzname + " set ");
		for (Field field : clazz.getDeclaredFields()) {
			if (!field.getName().equals(getKey()) && !field.getName().equals("dbutil")) {
				Object o = getValue(entity, field.getName(), clazz);
				sbf.append(field.getName().toLowerCase()).append("='").
						append(getValue(entity, field.getName(), clazz)).append("'").append(",");
			}
		}
		sbf = new StringBuffer(sbf.subSequence(0, sbf.length() - 1));
		sbf.append(" where " + getKey() + "='" + value1 + "'");
		executeSql(sbf.toString());
	}

	/**
	 * 添加实体对象
	 * @param t 实体对象
	 */
	public void add(T t) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("insert into " + clazzname).append("(");
		for (Field field : clazz.getDeclaredFields()) {
			if (!field.getName().equals(getKey()) && !field.getName().equals("dbutil")) {
				sbf.append(field.getName().toLowerCase()).append(",");
			}
		}
		sbf = new StringBuffer(sbf.subSequence(0, sbf.length() - 1));
		sbf.append(") values(");
		for (Field field : clazz.getDeclaredFields()) {
			if (!field.getName().equals(getKey()) && !field.getName().equals("dbutil")) {
				sbf.append("'").append(getValue(t, field.getName(), clazz)).append("'").append(",");
			}
		}
		sbf = new StringBuffer(sbf.subSequence(0, sbf.length() - 1));
		sbf.append(")");
		executeSql(sbf.toString());
	}

	/**
	 * 根据主键字段删除数据
	 * @param keyvalue 主键字段值
	 * @return 删除的记录数
	 */
	public Integer delByKey(String keyvalue) {
		String sql = "delete from " + clazzname + " where " + getKey() + "=?";
		return DBUnitHelper.executeUpdate(sql, keyvalue);
	}



	/**
	 * 获取实体对象指定字段的值
	 * @param entity 实体对象
	 * @param fieldName 字段名
	 * @param clazz 类对象
	 * @return 字段值
	 */
	public Object getValue(Object entity, String fieldName, Class clazz) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, clazz);
			Method wM = pd.getReadMethod();
			return wM.invoke(entity) == null ? "" : wM.invoke(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置实体对象指定字段的值
	 * @param fieldName 字段名
	 * @param clazz 类对象
	 * @param o 实体对象
	 * @param fieldValue 字段值
	 */
	public void setValue(String fieldName, Class clazz, Object o, Object fieldValue) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method wM = pd.getWriteMethod();
			wM.invoke(o, fieldValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取实体对象主键字段名
	 * @return 主键字段名
	 */
	public String getKey() {
		try {
			for (Field field : clazz.getDeclaredFields()) {
				return field.getName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
