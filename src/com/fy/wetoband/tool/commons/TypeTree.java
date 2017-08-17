package com.fy.wetoband.tool.commons;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fy.wetoband.tool.dao.TypeDAO;
import com.fy.wetoband.tool.model.BdType;

/**
 * 类别树
 */
public class TypeTree {
	
	public static BdType topNode;
	
	public static Map<String,BdType> map;
	
	private static boolean hasInited = false;
	
	/**
	 * 初始化类别树,初始化前需将hasInited设置为false
	 */
	public static void init(Connection conn) throws ToolException{
		if (hasInited){
			return;
		}
		topNode = new BdType();
		map = new HashMap<String,BdType>();
		TypeDAO typeDAO = new TypeDAO();
		List<BdType> list = null;
		try {
			list = typeDAO.getAll(conn, null);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("初始化类别树失败,请重试");
		}
		for (BdType type : list) {
			map.put(type.getTypeId(), type);
			if (type.getLevel() == 1) {
				type.setBdType(topNode);
			} else {
				BdType superType = map.get(type.getBdType().getTypeId());
				type.setBdType(superType);
				Set<BdType> childSet = superType.getChildTypes();
				if (childSet == null) {
					childSet = new HashSet<BdType>();
					superType.setChildTypes(childSet);
				}
				childSet.add(type);
			}
			map.put(type.getTypeId(), type);
		}
		TypeTree.hasInited = true;
	}

	public static String getTopTypeID(Connection conn, String typeID) throws ToolException {
		init(conn);
		BdType type = map.get(typeID);
		if (type == null) {
			return null;
		}
		while (type.getBdType() != topNode) {
			type = type.getBdType();
		}
		return type.getTypeId();
	}
	
	public static String getChildIDS(Connection conn, String ID) throws ToolException {
		if (!StringUtil.checkNotNull(ID)){
			return ID;
		}
		init(conn);
		StringBuffer sb = new StringBuffer("");
		BdType type = map.get(ID);
		getchildStr(sb, type);
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public static void addType(BdType type) {
		if (!StringUtil.checkNotNull(type.getTypeId())){
			return;
		}
		map.put(type.getTypeId(), type);
		if (type.getBdType() == null) {
			return;
		}
		if (!StringUtil.checkNotNull(type.getBdType().getTypeId())) {
			return;
		}
		BdType superType = map.get(type.getBdType().getTypeId());
		if (superType == null) {
			return;
		}
		type.setBdType(superType);
		Set<BdType> childTypes = superType.getChildTypes();
		if (childTypes == null) {
			childTypes = new HashSet<BdType>();
			childTypes.add(type);
		}
		childTypes.add(type);
	}
	
	private static void getchildStr(StringBuffer sb, BdType type) {
		sb.append("'" + type.getTypeId() + "',");
		Set<BdType> childSet = type.getChildTypes();
		if (childSet == null || childSet.isEmpty()) {
			return;
		} else {
			for (BdType child : childSet) {
				getchildStr(sb, child);
			}
		}
	}
	
	public static boolean isHasInited() {
		return hasInited;
	}

	public static void setHasInited(boolean hasInited) {
		TypeTree.hasInited = hasInited;
	}
	
}
