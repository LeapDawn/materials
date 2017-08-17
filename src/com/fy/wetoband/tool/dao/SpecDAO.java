package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fy.wetoband.tool.model.BdSpec;

public class SpecDAO {

	public String isExists(Connection conn, String description) throws SQLException {
		String sql = "select distinct spec_id from bd_spec where description like '" + description.trim() +"'";
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
	public boolean save(Connection conn, String specId, String description)
			throws SQLException {
		String sql = "insert into bd_spec(spec_id, description) values(?,?)";
		PreparedStatement pt = conn.prepareStatement(sql);
		pt.setString(1, specId);
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

	public void saveBatch(Connection conn, List<BdSpec> list)
			throws SQLException {
		String sql = "insert into bd_spec(spec_id, description) values(?,?)";
		PreparedStatement pt = conn.prepareStatement(sql);
		for (BdSpec spec : list) {
			pt.setString(1, spec.getSpecId());
			pt.setString(2, spec.getDescription());
			pt.addBatch();
		}
		pt.executeBatch();
		if (pt != null) {
			pt.close();
		}
	}
	
	// 获取所有规格名称
	public List<BdSpec> getAll(Connection conn, String description)
			throws SQLException {
		List<BdSpec> list = new ArrayList<BdSpec>();
		String sql = "select spec_id, description from bd_spec ";
		if (description != null && !description.equals("")) {
			sql += " where description like '%" + description + "%'";
		}
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		BdSpec spec = null;
		while (rs.next()) {
			spec = new BdSpec();
			spec.setSpecId(rs.getString("spec_id"));
			spec.setDescription(rs.getString("description"));
			list.add(spec);
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
