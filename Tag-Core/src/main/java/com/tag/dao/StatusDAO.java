package com.tag.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.model.Status;
import com.tag.util.ConnectionUtil;

public class StatusDAO {

	private JdbcTemplate jdbcTemplate=ConnectionUtil.getJdbcTemplate();
	
	public List<Status> getStatus(){
		String sql = "SELECT ID,NAME FROM STATUS";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			final Status status = new Status();
			status.setId(rs.getInt("ID"));
			status.setName(rs.getString("name"));
			return status;
		});
	}
}
