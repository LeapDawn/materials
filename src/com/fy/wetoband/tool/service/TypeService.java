package com.fy.wetoband.tool.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.fy.wetoband.tool.commons.IDGenerator;
import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.commons.TypeTree;
import com.fy.wetoband.tool.dao.MaterialsDAO;
import com.fy.wetoband.tool.dao.TypeDAO;

public class TypeService {
	
	private Connection conn;
	private MaterialsDAO materialsDao = new MaterialsDAO();
	private TypeDAO typedao = new TypeDAO();
	public TypeService(Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 添加类别
	 * @param description
	 * @param superType
	 * @return
	 * @throws ToolException
	 */
	public boolean add(String description,String superType) throws ToolException {
		IDGenerator.startInitRecord(conn);
		boolean success = false;
		String type_id = IDGenerator.getTypeID();
		try {
			success = typedao.save(conn, type_id, description, superType);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("新增类别时对数据库操作失败");
		}
		if (success) {
			TypeTree.setHasInited(false);
		}
		return success;
	}
	
	/**
	 * 更新类别
	 * @param type_id
	 * @param description
	 * @return
	 * @throws ToolException
	 */
	public boolean update(String type_id,String description) throws ToolException {
		boolean success = false;
		try {
			success = typedao.update(conn, type_id, description);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("修改类别名称时对数据库操作失败");
		}
		return success;
	}
	
	public boolean delete(String type_id) throws ToolException {
		int count = 0;
		String childIDS = TypeTree.getChildIDS(conn, type_id);
		try {
			count = materialsDao.count(conn, childIDS, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count > 0) {
			throw new ToolException("该类别(包括下级类别)下中含有数据,请先删除数据");
		}
		boolean success;
		try {
			success = typedao.delete(conn, childIDS);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("删除类别时对数据库操作失败");
		}
		return success;
	}
}
