package com.tag.dao;

import java.sql.Types;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.tag.util.ConnectionUtil;

public class RatingDetailsDAO {
	private JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();

	public void addRating(Integer employeeId,Integer userId,Integer lrating) {

		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
		call.withProcedureName("addrating");
		call.declareParameters(new SqlParameter("EMPLOYEEID", Types.INTEGER), new SqlParameter("USERID", Types.INTEGER),
				new SqlParameter("LRATING", Types.INTEGER), new SqlOutParameter("RESULT", Types.VARCHAR));
		call.setAccessCallParameterMetaData(false);

		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("EMPLOYEEID", employeeId);
		in.addValue("USERID", userId);
		in.addValue("LRATING", lrating);
		
		Map<String, Object> execute = call.execute(in);

		String status = (String) execute.get("RESULT");
		System.out.println(status);
	}

}
