package com.tag.util;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConnectionUtil {
	private ConnectionUtil(){
		
	}
	private static DataSource getDataSource() {
		final BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("root");
		ds.setPassword("admin");
		ds.setUrl("jdbc:mysql://localhost:3306/tag");
		return ds;
	}

	public static JdbcTemplate getJdbcTemplate() {
		final JdbcTemplate jt = new JdbcTemplate();
		jt.setDataSource(getDataSource());
		return jt;
	}
}
