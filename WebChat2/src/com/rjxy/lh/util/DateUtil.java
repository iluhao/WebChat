package com.rjxy.lh.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssms");
	// 生成数据库ID
	public static String getId() {
		Date date = new Date();
		return simpleDateFormat.format(date);
	}
}
