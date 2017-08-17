package com.fy.wetoband.tool.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.BeanUtils;

import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.commons.TypeTree;
import com.fy.wetoband.tool.dao.CurrencyDAO;
import com.fy.wetoband.tool.dao.ModelDAO;
import com.fy.wetoband.tool.dao.SpecDAO;
import com.fy.wetoband.tool.dao.TypeDAO;
import com.fy.wetoband.tool.dao.UnitDAO;
import com.fy.wetoband.tool.dto.PType;
import com.fy.wetoband.tool.model.BdCurrency;
import com.fy.wetoband.tool.model.BdModel;
import com.fy.wetoband.tool.model.BdSpec;
import com.fy.wetoband.tool.model.BdType;
import com.fy.wetoband.tool.model.BdUnit;

public class PageLoaderService {

	private Connection conn;
	private TypeDAO typeDAO = new TypeDAO();
	private ModelDAO modelDAO = new ModelDAO();
	private UnitDAO unitDAO = new UnitDAO();
	private SpecDAO specDAO = new SpecDAO();
	private CurrencyDAO currencyDAO = new CurrencyDAO();
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public PageLoaderService() {
		super();
	}

	public PageLoaderService(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public List<BdCurrency> getCurrency(String description) {
		List<BdCurrency> list = null;
		try {
			list = currencyDAO.getAll(conn, description);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<BdUnit> getUnit(String description){
		List<BdUnit> list = null;
		try {
			list = unitDAO.getAll(conn, description);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<BdModel> getModel(String description){
		List<BdModel> list = null;
		try {
			list = modelDAO.getAll(conn, description);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<BdSpec> getSpec(String description){
		List<BdSpec> list = null;
		try {
			list = specDAO.getAll(conn, description);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<PType> getType(String description){
		try {
			TypeTree.init(conn);
		} catch (ToolException e2) {
			e2.printStackTrace();
			TypeTree.setHasInited(false);
		}
		List<PType> plist = null;
		try {
			List<BdType> list = typeDAO.getAll(conn, description);
			plist = new ArrayList<PType>();
			for (BdType bdType : list) {
				plist.add(changeTypeToPageModel(bdType));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return plist;
	}
	
	private PType changeTypeToPageModel(BdType type) {
		PType ptype = new PType();
		BeanUtils.copyProperties(type, ptype);
		ptype.setSuperType(type.getBdType().getTypeId());
		return ptype;
	}
}
