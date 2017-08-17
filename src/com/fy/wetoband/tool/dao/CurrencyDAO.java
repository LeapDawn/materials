package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fy.wetoband.tool.model.BdCurrency;

public class CurrencyDAO {

	public String isExists(Connection conn, String description) throws SQLException {
		String sql = "select distinct currency_id from bd_currency where description like '" + description.trim() +"'";
		String currencyId = null;
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		if (rs.next()) {
			currencyId = rs.getString(1);
		}
		if (rs != null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return currencyId;
	}
	
	
	// 新增货币单位
	public boolean save(Connection conn, String currencyId, String description)
			throws SQLException {
		String sql = "insert into bd_currency(currency_id, description) values(?,?)";
		PreparedStatement pt = conn.prepareStatement(sql);
		pt.setString(1, currencyId);
		pt.setString(2, description);
		int rs = pt.executeUpdate();
		if (pt != null) {
			pt.close();
		}
		if (rs == 0) {
			return false;
		}
		return true;
	}

	public void saveBatch(Connection conn, List<BdCurrency> list) throws SQLException {
		String sql = "insert into bd_currency(currency_id, description) values(?,?)";
		PreparedStatement pt = conn.prepareStatement(sql);
		for (BdCurrency bdCurrency : list) {
			pt.setString(1, bdCurrency.getCurrencyId());
			pt.setString(2, bdCurrency.getDescription());
			pt.addBatch();
		}
		pt.executeBatch();
		if (pt != null) {
			pt.close();
		}
	}
	
	// 获取所有货币单位名称
	public List<BdCurrency> getAll(Connection conn, String description)
			throws SQLException {
		List<BdCurrency> list = new ArrayList<BdCurrency>();
		String sql = "select currency_id, description from bd_currency ";
		if (description != null && !description.equals("")) {
			sql += " where description like '%" + description + "%'";
		}
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		BdCurrency currency = null;
		while(rs.next()) {
			currency = new BdCurrency();
			currency.setCurrencyId(rs.getString("currency_id"));
			currency.setDescription(rs.getString("description"));
			list.add(currency);
		}
		if (rs != null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		
		return list;
	}
	
}
