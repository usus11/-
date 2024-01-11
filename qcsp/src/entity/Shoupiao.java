package entity;

import java.util.List;

import dao.Dao;

/**
 * 售票实体类
 */
public class Shoupiao implements java.io.Serializable, Comparable<Shoupiao> {

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
	public Shoupiao() {
		super();
	}

	/**
	 * 带参构造函数
	 * @param id 售票ID
	 */
	public Shoupiao(Integer id) {
		super();
		this.id = id;
	}

	// Dao 实例用于数据库操作
	Dao<Shoupiao> dbutil = new Dao<Shoupiao>(this);

	private String checi;

	/**
	 * 获取车次信息
	 * @return 车次信息
	 */
	public String getCheci() {
		return this.checi;
	}

	/**
	 * 设置车次信息
	 * @param checi 车次信息
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

	private String chengcheren;

	/**
	 * 获取乘车人信息
	 * @return 乘车人信息
	 */
	public String getChengcheren() {
		return this.chengcheren;
	}

	/**
	 * 设置乘车人信息
	 * @param chengcheren 乘车人信息
	 */
	public void setChengcheren(String chengcheren) {
		this.chengcheren = chengcheren;
	}

	private String lianxidianhua;

	/**
	 * 获取联系电话
	 * @return 联系电话
	 */
	public String getLianxidianhua() {
		return this.lianxidianhua;
	}

	/**
	 * 设置联系电话
	 * @param lianxidianhua 联系电话
	 */
	public void setLianxidianhua(String lianxidianhua) {
		this.lianxidianhua = lianxidianhua;
	}

	/**
	 * 添加售票信息
	 */
	public void add() {
		dbutil.add(this);
	}

	/**
	 * 删除售票信息
	 */
	public void delete() {
		dbutil.delByKey(id.toString());
	}

	/**
	 * 更新售票信息
	 */
	public void update() {
		dbutil.update(this);
	}

	/**
	 * 查询售票信息列表
	 * @return 售票信息列表
	 */
	public List<Shoupiao> query() {
		return dbutil.query(this);
	}

	/**
	 * 根据ID查询售票信息
	 * @return 售票信息实例
	 */
	public Shoupiao queryById() {
		return dbutil.queryByKey(id.toString()).get(0);
	}

	/**
	 * 实现 Comparable 接口的 compareTo 方法
	 */
	@Override
	public int compareTo(Shoupiao o) {
		return 0;
	}
}
