package com.leigq.quartz.config.quartz;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Setter;
import org.quartz.SchedulerException;
import org.quartz.utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Druid连接池的Quartz扩展类
 * <br/>
 * 参考：
 * <ul>
 *     <li>
 *         <a href='https://www.cnblogs.com/zouhao510/p/5313600.html'>Quartz学习笔记(五) quartz扩展druid连接池</a>
 *     </li>
 * </ul>
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019-03-06 15:01 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Setter
public class DruidConnectionProvider implements ConnectionProvider {
	/*
	 * 常量配置，与quartz.properties文件的key保持一致(去掉前缀)，
	 * 同时提供set方法，Quartz框架自动注入值。
	 * */
	// JDBC驱动
	public String driver;
	// JDBC连接串
	public String url;
	// 数据库用户名
	public String user;
	// 数据库用户密码
	public String password;
	// 数据库最大连接数
	public int maxActive;
	// 数据库SQL查询每次连接返回执行到连接池，以确保它仍然是有效的。
	public String validationQuery;

	private boolean validateOnCheckout;

	private int idleConnectionValidationSeconds;

	public String maxCachedStatementsPerConnection;

	public static final int DEFAULT_DB_MAX_CONNECTIONS = 10;

	public static final int DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION = 120;

	//Druid连接池
	private DruidDataSource datasource;

	@Override
	public Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}

	@Override
	public void shutdown() {
		datasource.close();
	}

	@Override
	public void initialize() throws SQLException {
		if (this.url == null) {
			throw new SQLException("DBPool could not be created: DB URL cannot be null");
		}

		if (this.driver == null) {
			throw new SQLException("DBPool driver could not be created: DB driver class name cannot be null!");
		}

		if (this.maxActive < 0) {
			throw new SQLException("DBPool maxConnectins could not be created: Max connections must be greater than zero!");
		}

		datasource = new DruidDataSource();
		try {
			datasource.setDriverClassName(this.driver);
		} catch (Exception e) {
			try {
				throw new SchedulerException("Problem setting driver class name on datasource: " + e.getMessage(), e);
			} catch (SchedulerException ignored) {

			}
		}
		datasource.setUrl(this.url);
		datasource.setUsername(this.user);
		datasource.setPassword(this.password);
		datasource.setMaxActive(this.maxActive);
		datasource.setMinIdle(1);
		datasource.setMaxWait(0);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION);

		if (this.validationQuery != null) {
			datasource.setValidationQuery(this.validationQuery);
			if (!this.validateOnCheckout) {
				datasource.setTestOnReturn(true);
			} else {
				datasource.setTestOnBorrow(true);
			}
			datasource.setValidationQueryTimeout(this.idleConnectionValidationSeconds);
		}
	}
}
