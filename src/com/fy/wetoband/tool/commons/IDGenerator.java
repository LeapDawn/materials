package com.fy.wetoband.tool.commons;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.fy.wetoband.tool.dao.MaterialsDAO;

public class IDGenerator {

	public static int MaterialsRecord = 0;
	public static int TypeRecord = 0;
	public static int SpecRecord = 0;
	public static int ModelRecord = 0;
	public static int UnitRecord = 0;
	public static int CurrencyRecord = 0;
	
	private static boolean hasInited = false;
	
	public static void startInitRecord(Connection conn){
		if (hasInited) {
			return;
		} else {
			try {
				initRecord(conn);
				hasInited = true;
			} catch (SQLException e) {
				hasInited = false;
			}
		}
	}
	
	private static void initRecord(Connection conn) throws SQLException {
		Map<String, String> keyMap = new MaterialsDAO().getAllMaxIDKey(conn);
		String materialsKey = keyMap.get("materialsIdKey");
		String typeKey = keyMap.get("typeIdKey");
		String unitKey = keyMap.get("unitIdKey");
		String currencyKey = keyMap.get("currencyIdKey");
		String modelKey = keyMap.get("modelIdKey");
		String specKey = keyMap.get("specIdKey");
		if (!StringUtil.checkNotNull(materialsKey)) {
			MaterialsRecord = 1;
		} else {
			if (StringUtil.isInt(materialsKey)) {
				MaterialsRecord = Integer.valueOf(materialsKey) + 1;
			}
		}
		
		if (!StringUtil.checkNotNull(typeKey)) {
			TypeRecord = 1;
		} else {
			if (StringUtil.isInt(typeKey)) {
				TypeRecord = Integer.valueOf(typeKey) + 1;
			}
		}
		
		
		if (!StringUtil.checkNotNull(unitKey)) {
			UnitRecord = 1;
		} else {
			if (StringUtil.isInt(unitKey)) {
				UnitRecord = Integer.valueOf(unitKey) + 1;
			}
		}
		
		if (!StringUtil.checkNotNull(currencyKey)) {
			CurrencyRecord = 1;
		} else {
			if (StringUtil.isInt(currencyKey)) {
				CurrencyRecord = Integer.valueOf(currencyKey) + 1;
			}
		}
		
		if (!StringUtil.checkNotNull(modelKey)) {
			ModelRecord = 1;
		} else {
			if (StringUtil.isInt(modelKey)) {
				ModelRecord = Integer.valueOf(modelKey) + 1;
			}
		}
		
		if (!StringUtil.checkNotNull(specKey)) {
			SpecRecord = 1;
		} else {
			if (StringUtil.isInt(specKey)) {
				SpecRecord = Integer.valueOf(specKey) + 1;
			}
		}
	}
	
	// BM+TYPEKEY+(MaterialsRecord+1)
	public static String getMaID(String typeKey) {
		StringBuffer sb = new StringBuffer("BM");
		if (!StringUtil.checkNotNull(typeKey)) {
			typeKey = "0000";
		} else {
			typeKey = typeKey.substring(2,6);
		}
		sb.append(typeKey);
		String maRecord = "" + MaterialsRecord;
		MaterialsRecord += 1;
		for (int i = 0; i < 6-maRecord.length(); i++) {
			sb.append('0');
		}
		sb.append(maRecord);
		return sb.toString();
	}
	
	public static String getMaCPID(String typeKey) {
		StringBuffer sb = new StringBuffer("CP");
		if (!StringUtil.checkNotNull(typeKey)) {
			typeKey = "0000";
		} else {
			typeKey = typeKey.substring(2,6);
		}
		sb.append(typeKey);
		String maRecord = "" + MaterialsRecord;
		MaterialsRecord += 1;
		for (int i = 0; i < 6-maRecord.length(); i++) {
			sb.append('0');
		}
		sb.append(maRecord);
		return sb.toString();
	}

	// BT+(KEY+1)
	public static String getTypeID() {
		StringBuffer sb = new StringBuffer("BT");
		String typeRecordStr = "" + TypeRecord;
		TypeRecord += 1;
		for (int i = 0; i < 4-typeRecordStr.length(); i++) {
			sb.append('0');
		}
		sb.append(typeRecordStr);
		return sb.toString();
	}

	// BU+YYMMDD+###
	public static String getUnitID() {
		StringBuffer sb = new StringBuffer("BN");
		String unitRecordStr = "" + UnitRecord;
		UnitRecord += 1;
		for (int i = 0; i < 4-unitRecordStr.length(); i++) {
			sb.append('0');
		}
		sb.append(unitRecordStr);
		return sb.toString();
	}

	// BS+YYMMDD+###
	public static String getCurrencyID() {
		StringBuffer sb = new StringBuffer("");
		String currencyRecordStr = "" + CurrencyRecord;
		CurrencyRecord += 1;
		for (int i = 0; i < 3-currencyRecordStr.length(); i++) {
			sb.append('0');
		}
		sb.append(currencyRecordStr);
		return sb.toString();
	}

	public static String getModelID() {
		StringBuffer sb = new StringBuffer("MO");
		String modelRecordStr = "" + ModelRecord;
		ModelRecord += 1;
		for (int i = 0; i < 5-modelRecordStr.length(); i++) {
			sb.append('0');
		}
		sb.append(modelRecordStr);
		return sb.toString();
	}

	public static String getSpecID() {
		StringBuffer sb = new StringBuffer("BS");
		String specRecordStr = "" + SpecRecord;
		SpecRecord += 1;
		for (int i = 0; i < 5-specRecordStr.length(); i++) {
			sb.append('0');
		}
		sb.append(specRecordStr);
		return sb.toString();
	}

	public static boolean isHasInited() {
		return hasInited;
	}

	public static void setHasInited(boolean hasInited) {
		IDGenerator.hasInited = hasInited;
	}
	
}
