package com.fy.wetoband.tool.commons;

/**
 * 物料对象导入导出Execl文件时字段对应的表头
 */
public class MaterialsExeclField {

	public final static String MATERIALSID = "物料代码";
	public final static String DESRCIPTION = "物料名称";
	public final static String TYPE = "类别";
	public final static String UNIT = "单位";
	public final static String SPEC = "规格";
	public final static String MODEL = "型号";
	public final static String SOURCE = "物料来源";
	public final static String CURRENTSTOCK = "库存";
	public final static String MINSTOCK = "最低库存";
	public final static String MAXSTOCK = "最高库存";
	public final static String SAFESTOCK = "安全库存";
	public final static String PRICE = "参考单价";
	public final static String CURRENCY = "货币";
	public final static String REMARK = "备注";

	public static String[] getExeclModelArray() {
		return new String[] { DESRCIPTION, TYPE, UNIT, SPEC, MODEL, SOURCE,
				MINSTOCK, MAXSTOCK, SAFESTOCK, PRICE, CURRENCY, REMARK };
	}

	public static String[] getExeclHeadArray() {
		return new String[] { MATERIALSID, DESRCIPTION, TYPE, UNIT, SPEC,
				MODEL, SOURCE, CURRENTSTOCK, MINSTOCK, MAXSTOCK, SAFESTOCK,
				PRICE, CURRENCY, REMARK };
	}

	public static String[] getExeclNumArray() {
		return new String[] { CURRENTSTOCK, MINSTOCK, MAXSTOCK, SAFESTOCK,
				PRICE };
	}

}
