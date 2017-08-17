package com.fy.wetoband.tool.commons;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fy.wetoband.tool.dao.CurrencyDAO;
import com.fy.wetoband.tool.dao.MaterialsDAO;
import com.fy.wetoband.tool.dao.ModelDAO;
import com.fy.wetoband.tool.dao.SpecDAO;
import com.fy.wetoband.tool.dao.TypeDAO;
import com.fy.wetoband.tool.dao.UnitDAO;
import com.fy.wetoband.tool.model.BdCurrency;
import com.fy.wetoband.tool.model.BdModel;
import com.fy.wetoband.tool.model.BdSpec;
import com.fy.wetoband.tool.model.BdType;
import com.fy.wetoband.tool.model.BdUnit;

/**
 * 为导入数据准备
 */
public class ImprotReady {

	private Connection conn;
	private Map<String, String> typeMap;
	private Map<String, String> specMap;
	private Map<String, String> modelMap;
	private Map<String, String> currencyMap;
	private Map<String, String> unitMap;
	private Set<String> materialsSet;

	public ImprotReady() {
		super();
	}

	public ImprotReady(Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 初始化已有类别,货币,机型,规格,单位,物料的集合
	 * @throws SQLException
	 */
	public void init() throws SQLException {
		initType();
		initCurrency();
		initModel();
		initSpec();
		initUnit();
		initMaterials();
	}

	private void initType() throws SQLException {
		this.typeMap = new HashMap<String, String>();
		List<BdType> list = new TypeDAO().getAll(conn, null);
		for (BdType type : list) {
			typeMap.put(type.getDescription(), type.getTypeId());
		}
	}

	private void initSpec() throws SQLException {
		this.specMap = new HashMap<String, String>();
		List<BdSpec> list = new SpecDAO().getAll(conn, null);
		for (BdSpec spec : list) {
			specMap.put(spec.getDescription(), spec.getSpecId());
		}
	}

	private void initModel() throws SQLException {
		this.modelMap = new HashMap<String, String>();
		List<BdModel> list = new ModelDAO().getAll(conn, null);
		for (BdModel model : list) {
			modelMap.put(model.getDescription(), model.getModelId());
		}
	}

	private void initCurrency() throws SQLException {
		this.currencyMap = new HashMap<String, String>();
		List<BdCurrency> list = new CurrencyDAO().getAll(conn, null);
		for (BdCurrency currency : list) {
			currencyMap.put(currency.getDescription(), currency.getCurrencyId());
		}
	}

	private void initUnit() throws SQLException {
		this.unitMap = new HashMap<String, String>();
		List<BdUnit> list = new UnitDAO().getAll(conn, null);
		for (BdUnit unit : list) {
			unitMap.put(unit.getDescription(), unit.getUnitId());
		}
	}
	
	private void initMaterials() throws SQLException{
		materialsSet = new HashSet<String>();
		List<Map<String, String>> list = new MaterialsDAO().getAllDescriptionAndSpec(conn);
		for (Map<String, String> map : list) {
			String description = map.get("description") != null ? map.get("description") : "";
			String spec = map.get("spec") != null ? map.get("spec") : "";
			materialsSet.add(description + spec);
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Map<String, String> getTypeMap() {
		return typeMap;
	}

	public void setTypeMap(Map<String, String> typeMap) {
		this.typeMap = typeMap;
	}

	public Map<String, String> getSpecMap() {
		return specMap;
	}

	public void setSpecMap(Map<String, String> specMap) {
		this.specMap = specMap;
	}

	public Map<String, String> getModelMap() {
		return modelMap;
	}

	public void setModelMap(Map<String, String> modelMap) {
		this.modelMap = modelMap;
	}

	public Map<String, String> getCurrencyMap() {
		return currencyMap;
	}

	public void setCurrencyMap(Map<String, String> currencyMap) {
		this.currencyMap = currencyMap;
	}

	public Map<String, String> getUnitMap() {
		return unitMap;
	}

	public void setUnitMap(Map<String, String> unitMap) {
		this.unitMap = unitMap;
	}

	public Set<String> getMaterialsSet() {
		return materialsSet;
	}

	public void setMaterialsSet(Set<String> materialsSet) {
		this.materialsSet = materialsSet;
	}
}
