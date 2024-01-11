package entity;

import java.util.List;

import dao.Dao;

/**
 * 管理员实体类
 */
public class Guanliyuan implements java.io.Serializable, Comparable<Guanliyuan> {

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 无参构造函数
	 */
	public Guanliyuan() {
		super();
	}

	/**
	 * 带参构造函数
	 * @param id 管理员ID
	 */
	public Guanliyuan(Integer id) {
		super();
		this.id = id;
	}

	// Dao 实例用于数据库操作
	Dao<Guanliyuan> dbutil = new Dao<Guanliyuan>(this);

	private String yonghuming;

	/**
	 * 获取用户名
	 * @return 用户名
	 */
	public String getYonghuming() {
		return this.yonghuming;
	}

	/**
	 * 设置用户名
	 * @param yonghuming 用户名
	 */
	public void setYonghuming(String yonghuming) {
		this.yonghuming = yonghuming;
	}

	private String mima;

	/**
	 * 获取密码
	 * @return 密码
	 */
	public String getMima() {
		return this.mima;
	}

	/**
	 * 设置密码
	 * @param mima 密码
	 */
	public void setMima(String mima) {
		this.mima = mima;
	}

	/**
	 * 添加管理员
	 */
	public void add() {
		dbutil.add(this);
	}

	/**
	 * 删除管理员
	 */
	public void delete() {
		dbutil.delByKey(id.toString());
	}

	/**
	 * 更新管理员信息
	 */
	public void update() {
		dbutil.update(this);
	}

	/**
	 * 查询管理员列表
	 * @return 管理员列表
	 */
	public List<Guanliyuan> query() {
		return dbutil.query(this);
	}

	/**
	 * 根据ID查询管理员信息
	 * @return 管理员实例
	 */
	public Guanliyuan queryById() {
		return dbutil.queryByKey(id.toString()).get(0);
	}

	/**
	 * 实现 Comparable 接口的 compareTo 方法
	 */
	@Override
	public int compareTo(Guanliyuan o) {
		return 0;
	}
}

