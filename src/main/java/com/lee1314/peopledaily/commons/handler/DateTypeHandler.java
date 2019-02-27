package com.lee1314.peopledaily.commons.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.lee1314.peopledaily.utils.DateUtil;

/**
 * Mybatis的TypeHandler 修改时间格式
 * 
 * @Title: DateTypeHandler
 * @Description:
 * @author: 雷力
 * @date: 2019年1月22日 下午12:29:17
 *
 */
public class DateTypeHandler extends BaseTypeHandler<String> {

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return null;
	}

	@Override
	public String getNullableResult(ResultSet rs, int i) throws SQLException {
		return null;
	}

	@Override
	public String getNullableResult(CallableStatement cs, int i) throws SQLException {
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String data, JdbcType jdbcType) throws SQLException {
		ps.setString(i, DateUtil.getTime(Long.valueOf(data) * 1000));
	}

}
