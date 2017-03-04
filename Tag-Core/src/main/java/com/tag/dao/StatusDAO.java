package com.tag.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tag.util.ConnectionUtil;

public class StatusDAO {

	private JdbcTemplate jdbcTemplate=ConnectionUtil.getJdbcTemplate();
}
