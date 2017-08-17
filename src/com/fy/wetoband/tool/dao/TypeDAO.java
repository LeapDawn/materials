package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fy.wetoband.tool.model.BdType;

public class TypeDAO {
	
	public String isExists(Connection conn, String description) throws SQLException {
		String sql = "select distinct type_id from bd_type where isvalid=1 and description like '" + description.trim() +"'";
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
	
	/**
	 * 修改类别名称
	 */
	public boolean update(Connection conn, String type_id, String description) throws SQLException{
		String sql = "update bd_type set description='" + description + "'"
				+ " where type_id like '" + type_id.trim() +"'";
		PreparedStatement pt = conn.prepareStatement(sql);
		int result = pt.executeUpdate();
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除类别
	 * @param conn
	 * @param type_id
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(Connection conn, String type_ids) throws SQLException {
		String sql = "update bd_type set isvalid=0 where type_id in (" + type_ids +")";
		PreparedStatement pt = conn.prepareStatement(sql);
		int result = pt.executeUpdate();
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// 新增类别
	public boolean save(Connection conn, String typeId, String description, String super_type)
			throws SQLException {
		String sql = "insert into bd_type(type_id,description,super_type_id,level,isvalid) "
					+ "values('"+ typeId +"','"+ description +"',";
		if (super_type != null && !super_type.equals("")) {
			sql += "'" + super_type + "',"
					+ "(SELECT t.level+1 from bd_type t where t.type_id like '" + super_type + "'),1)";
		} else {
			sql += "null,1,1)";
		}
		PreparedStatement pt = conn.prepareStatement(sql);
		int rs = pt.executeUpdate();
		if (pt != null) {
			pt.close();
		}
		if (rs == 0) {
			return false;
		}
		return true;
	}

	// 获取所有类型
	public List<BdType> getAll(Connection conn, String description) throws SQLException {
		List<BdType> list = new ArrayList<BdType>();
		String sql = "select t.type_id,t.description,t.level,t.super_type_id from bd_type t where t.isvalid = 1";
		if (description != null && !description.equals("")) {
			sql += " and t.description like '%" + description + "%'";
		}
		sql += " order by t.level, t.super_type_id,t.type_id";
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		BdType type = null;
		while(rs.next()) {
			type = new BdType();
			type.setTypeId(rs.getString("type_id"));
			type.setDescription(rs.getString("description"));
			type.setLevel(rs.getInt("level"));
			type.setBdType(new BdType(rs.getString("super_type_id"),""));
			list.add(type);
		}
		if (rs != null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	
	// 获取所有类型名称
	public List<BdType> getTypeNames(Connection conn, String description)
			throws SQLException {
		List<BdType> list = new ArrayList<BdType>();
		String sql = "select distinct description from bd_type where isvalid = 1 ";
		if (description != null && !description.equals("")) {
			sql += " and description like '%" + description + "%'";
		}
		sql += " order by super_type_id,type_id";
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		BdType type = null;
		while(rs.next()) {
			type = new BdType();
			type.setDescription(rs.getString("description"));
			list.add(type);
		}
		if (rs != null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	// 获取没有child的类型
	public List<BdType> getDataWithoutChild(Connection conn ,String description)
			throws SQLException {
		List<BdType> list = new ArrayList<BdType>();
		String sql = "select t.type_id,t.description from bd_type t where t.isvalid = 1 and t.type_id not in (select tt.super_type_id from bd_type tt where tt.super_type_id is not null) ";
		if (description != null && !description.equals("")) {
			sql += " and t.description like '%" + description + "%'";
		}
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		BdType type = null;
		while(rs.next()) {
			type = new BdType();
			type.setTypeId(rs.getString("type_id"));
			type.setDescription(rs.getString("description"));
			list.add(type);
		}
		if (rs != null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
		
	public boolean hasChild(Connection conn,String superType) throws SQLException {
		String sql = "select type_id from bd_type t where isvalid = 1 and super_type_id like '" + superType +"'";
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
}
