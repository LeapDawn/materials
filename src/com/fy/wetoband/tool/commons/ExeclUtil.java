package com.fy.wetoband.tool.commons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExeclUtil {
	
	private String[] headArray;
	private List<Map<String, Object>> bodyList;
	private String[] modelArray;
	private String[] numberArray;
	
	/**
	 * 读取execl文件
	 * 1.执行该方法前需先设置modelArray,以供验证execl
	 * 2.读取execl表头
	 * 3.验证execl是否符合模版(不符合则抛出ExeclException)
	 * 4.读取execl具体数据
	 * 5.通过getter获取execl表头和数据
	 * @param file execl文件
	 * @throws BiffException
	 * @throws IOException
	 * @throws ExeclException 
	 */
	public void readExecl(File file) throws BiffException, IOException, ExeclException {
		Workbook book = Workbook.getWorkbook(file);
		Sheet sheet = book.getSheet(0);
		// 获取表头
		headArray = new String[sheet.getColumns() + 1];
	    for (int i = 0, size = sheet.getColumns(); i < size; i++) {
	    	headArray[i] = sheet.getCell(i, 0).getContents().trim();
		}
	    // 验证表头,验证不通过则抛出ExeclException给调用者处理
	    try {
			checkExeclHead();
		} catch (ExeclException e) {
			throw e;
		}
	    
	    // 遍历表格内容
	    bodyList = new ArrayList<Map<String, Object>>();
		for (int i = 1, rows = sheet.getRows(); i < rows; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (!sheet.getCell(0, i).getContents().trim().equals("")) {
				for (int j = 0, columns = sheet.getColumns(); j < columns; j++) {
						String value = sheet.getCell(j, i).getContents().trim();
						map.put(headArray[j], value);
				}
				bodyList.add(map);
			}
		}
	}
	
	/**
	 * 将数据写入execl文件中
	 * 1.执行该方法前,需先设置bodyList和HeadList
	 * 2.写入表头
	 * 3.写入具体数据
	 * 4.写入传入的file文件中
	 * @param file
	 * @param sheetName
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void writeExecl(File file, String sheetName) throws IOException, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(file);
		WritableSheet sheet = workbook.createSheet(sheetName, 0);
		WritableCellFormat decimalFormat = new WritableCellFormat(new NumberFormat("0.00"));
		WritableCellFormat intFormat = new WritableCellFormat(new NumberFormat("0"));
		for (int i = 0; i < headArray.length; i++) {
			sheet.addCell(new Label(i, 0, headArray[i]));
		}
		for (int i = 1; i <= bodyList.size(); i++) {
			Map<String, Object> map = bodyList.get(i-1);
			for (int j = 0; j < headArray.length; j++) {
				if (checkIsSpecial(headArray[j])) {
					double num = ((Double) map.get(headArray[j])).doubleValue();
					if (num != (int) num) {
						sheet.addCell(new Number(j, i, num, decimalFormat));
					} else {
						sheet.addCell(new Number(j, i, num, intFormat));
					}
					
				} else {
					sheet.addCell(new Label(j, i, (String) map.get(headArray[j])));
				}
			}
		}

		workbook.write();
		workbook.close();
	}
	
	/**
	 * 验证execl是否符合模版
	 * @throws ExeclException 
	 */
	private void checkExeclHead() throws ExeclException{
		String errorMsg = "上传文件格式错误， 缺少列:\n";
		boolean flag=true;
		for(int i=0; i<modelArray.length; i++){
			String modelStr = modelArray[i];
			boolean flag_1 = false;
			for (int j=0; j < headArray.length && flag_1 == false; j++) {
				if (modelStr.equals(headArray[j])) {
					flag_1 = true;
				}
			}
			
			if(flag_1==false){
				flag=false;
				errorMsg += "\""+modelStr+"\", ";
			}
		}
		if (!flag) {
			throw new ExeclException(errorMsg);
		}
	}
	
	/**
	 * 验证该字段是否属于该数组
	 */
	private boolean checkIsSpecial(String str){
		for (int i = 0; i < modelArray.length; i++) {
			if(modelArray[i].trim().equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	public String[] getHeadArray() {
		return headArray;
	}

	public void setHeadArray(String[] headArray) {
		this.headArray = headArray;
	}

	public List<Map<String, Object>> getBodyList() {
		return bodyList;
	}

	public void setBodyList(List<Map<String, Object>> bodyList) {
		this.bodyList = bodyList;
	}

	public String[] getModelArray() {
		return modelArray;
	}

	public void setModelArray(String[] modelArray) {
		this.modelArray = modelArray;
	}

	public String[] getNumberArray() {
		return numberArray;
	}

	public void setNumberArray(String[] numberArray) {
		this.numberArray = numberArray;
	}
}
