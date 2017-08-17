package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fy.wetoband.tool.model.BdModel;

public class ModelDAO {

	public String isExists(Connection conn, String description) throws SQLException {
		String sql = "select distinct model_id from bd_model where description like '" + description.trim() +"'";
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
	
	// 新增机型
	public boolean save(Connection conn, String modelId, String description)
			throws SQLException {
		String sql = "insert into bd_model(model_id, description) values(?,?)";
		PreparedStatement pt = conn.prepareStatement(sql);
		pt.setString(1, modelId);
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
	
	public void saveBatch(Connection conn, List<BdModel> list)
			throws SQLException {
		String sql = "insert into bd_model(model_id, description) values(?,?)";
		PreparedStatement pt = conn.prepareStatement(sql);
		for (BdModel model : list) {
			pt.setString(1, model.getModelId());
			pt.setString(2, model.getDescription());
			pt.addBatch();
		}
		pt.executeBatch();
		if (pt != null) {
			pt.close();
		}
	}
	
	// 获取所有机型名称
	public List<BdModel> getAll(Connection conn, String description)
			throws SQLException {
		List<BdModel> list = new ArrayList<BdModel>();
		String sql = "select model_id, description from bd_model ";
		if (description != null && !description.equals("")) {
			sql += " where description like '%" + description + "%'";
		}
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		BdModel bdModel = null;
		while (rs.next()) {
			bdModel = new BdModel();
			bdModel.setModelId(rs.getString("model_id"));
			bdModel.setDescription(rs.getString("description"));
			list.add(bdModel);
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
