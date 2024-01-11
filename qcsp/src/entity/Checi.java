package entity;

import java.util.List;

import dao.Dao;

/**
 * 车次实体类
 */
public class Checi implements java.io.Serializable, Comparable<Checi> {

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 无参构造方法
	 */
	public Checi() {
		super();
	}

	/**
	 * 带参构造方法
	 * @param id 车次ID
	 */
	public Checi(Integer id) {
		super();
		this.id = id;
	}

	// 数据库操作工具类
	Dao<Checi> dbutil = new Dao<Checi>(this);

	private String checi;

	/**
	 * 获取车次
	 * @return 车次
	 */
	public String getCheci() {
		return this.checi;
	}

	/**
	 * 设置车次
	 * @param checi 车次
	 */
	public void setCheci(String checi) {
		this.checi = checi;
	}

	private String shifazhan;

	/**
	 * 获取始发站
	 * @return 始发站
	 */
	public String getShifazhan() {
		return this.shifazhan;
	}

	/**
	 * 设置始发站
	 * @param shifazhan 始发站
	 */
	public void setShifazhan(String shifazhan) {
		this.shifazhan = shifazhan;
	}

	private String zhongdianzhan;

	/**
	 * 获取终点站
	 * @return 终点站
	 */
	public String getZhongdianzhan() {
		return this.zhongdianzhan;
	}

	/**
	 * 设置终点站
	 * @param zhongdianzhan 终点站
	 */
	public void setZhongdianzhan(String zhongdianzhan) {
		this.zhongdianzhan = zhongdianzhan;
	}

	private String riqi;

	/**
	 * 获取日期
	 * @return 日期
	 */
	public String getRiqi() {
		return this.riqi;
	}

	/**
	 * 设置日期
	 * @param riqi 日期
	 */
	public void setRiqi(String riqi) {
		this.riqi = riqi;
	}

	private String shijian;

	/**
	 * 获取时间
	 * @return 时间
	 */
	public String getShijian() {
		return this.shijian;
	}

	/**
	 * 设置时间
	 * @param shijian 时间
	 */
	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	/**
	 * 添加车次记录
	 */
	public void add() {
		dbutil.add(this);
	}

	/**
	 * 删除车次记录
	 */
	public void delete() {
		dbutil.delByKey(id.toString());
	}

	/**
	 * 更新车次记录
	 */
	public void update() {
		dbutil.update(this);
	}

	/**
	 * 查询所有车次记录
	 * @return 车次记录列表
	 */
	public List<Checi> query() {
		return dbutil.query(this);
	}

	/**
	 * 根据ID查询车次记录
	 * @return 车次记录
	 */
	public Checi queryById() {
		return dbutil.queryByKey(id.toString()).get(0);
	}

	/**
	 * 实现Comparable接口的比较方法
	 */
	public int compareTo(Checi o) {
		return 0;
	}
}
