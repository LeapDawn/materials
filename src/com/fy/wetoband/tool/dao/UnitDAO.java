package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fy.wetoband.tool.model.BdUnit;

public class UnitDAO {
	
	public String isExists(Connection conn, String description) throws SQLException {
		String sql = "select distinct unit_id from bd_unit where description like '" + description.trim() +"'";
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
	
	// 新增规格
	public boolean save(Connection conn, String unitId, String description)
			throws SQLException {
		String sql = "insert into bd_unit(unit_id, description) values(?,?)";
		PreparedStatement pt = conn.prepareStatement(sql);
		pt.setString(1, unitId);
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
	
	public void saveBatch(Connection conn, List<BdUnit> list) throws SQLException {
		String sql = "insert into bd_unit(unit_id, description) values(?,?)";
		PreparedStatement pt = conn.prepareStatement(sql);
		for (BdUnit unit : list) {
			pt.setString(1, unit.getUnitId());
			pt.setString(2, unit.getDescription());
			pt.addBatch();
		}
		pt.executeBatch();
		if (pt != null) {
			pt.close();
		}
	}
	
	// 获取所有规格名称
	public List<BdUnit> getAll(Connection conn, String description)
			throws SQLException {
		List<BdUnit> list = new ArrayList<BdUnit>();
		String sql = "select unit_id, description from bd_unit ";
		if (description != null && !description.equals("")) {
			sql += " where description like '%" + description + "%'";
		}
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		BdUnit unit = null;
		while(rs.next()) {
			unit = new BdUnit();
			unit.setUnitId(rs.getString("unit_id"));
			unit.setDescription(rs.getString("description"));
			list.add(unit);
		}
		if (pt != null) {
			pt.close();
		}
		if (rs != null) {
			rs.close();
		}
		return list;
	}
}
