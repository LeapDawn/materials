package com.fy.wetoband.tool.commons;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String changeEncoding(String str) {
		if (!checkNotNull(str)){
			return str;
		}
		try {
			return new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
//		return str;
	}

	public static boolean checkNotNull(String str) {
		if (str != null && !"".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInt(String string) {
		Pattern pattern = Pattern.compile("[0-9]{1}\\d*");
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}
	
	public static boolean isDouble(String string) {
		Pattern pattern = Pattern.compile("\\d+.\\d+");
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}
	
	public static boolean isNum(String str) {
		if (!checkNotNull(str)) {
			return true;
		}
		return isInt(str) || isDouble(str);
	}
}
