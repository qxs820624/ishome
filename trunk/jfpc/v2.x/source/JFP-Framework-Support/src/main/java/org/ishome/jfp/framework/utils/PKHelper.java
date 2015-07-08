package org.ishome.jfp.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.ishome.jfp.framework.constants.ISFrameworkConstants;

/**
 * 系统唯一识别ID
 * 
 * @author Spook
 * @since 1.1.0
 * @version 1.1.0 2014/2/8
 */
public class PKHelper implements ISFrameworkConstants {
//	private static final String MACHINE_FILE = "/machine.properties";
//	private static final String MACHINE_CODE = "machinecode";
	
	private static String machinecode = "1";
	private static long systemTimeMillis = 0l;
	private static long synchronizedNum = 0l;
	
	private static StringBuilder primaryUniqueKey;

//	static {
//		try {
//			Properties props = new Properties();
//			InputStream in = PKHelper.class.getResourceAsStream(MACHINE_FILE);
//			props.load(in);
//			machinecode = props.getProperty(MACHINE_CODE);
//		} catch (Exception e) {
//			System.out.println("It's not exit File (machine.properties)!");
//		}
//	}

	public static void main(String[] args) {
		try {
			for (int i = 0; i < 100; i++) {
				String puk = creatBarCodeKey();
				System.out.println(puk);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * 系统时间戳
	 * 
	 * @return
	 */
	public static long currentTime(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return Long.parseLong(format.format(new Date()));
	}
	/**
	 * 获得一个数据记录的主键Key<br>
	 * 时间+序号
	 * 
	 * @return 全局唯一主键
	 */
	public synchronized static String creatBarCodeKey() {
		//日期+（小时分钟）+流水号
		primaryUniqueKey = new StringBuilder(12);
		long currentTimeMillis = currentTime("yyMMddHHmm");// currentTimeMillis
		// 同步判定
		if (currentTimeMillis > systemTimeMillis) {
			synchronizedNum = 10l;
			systemTimeMillis = currentTimeMillis;
		} else {
			synchronizedNum++;
		}
		primaryUniqueKey.append(systemTimeMillis);
		primaryUniqueKey.append(synchronizedNum);
		return primaryUniqueKey.toString();
	}
	
	/**
	 * 获得一个数据记录的主键Key<br>
	 * 时间+序号
	 * 
	 * @return 全局唯一主键
	 */
	public synchronized static String creatUniqueKey() {
		primaryUniqueKey = new StringBuilder(25);
		long currentTimeMillis = System.nanoTime();// currentTimeMillis
		// 同步判定
		if (currentTimeMillis > systemTimeMillis) {
			synchronizedNum = 0l;
			systemTimeMillis = currentTimeMillis;
		} else {
			synchronizedNum++;
		}
		primaryUniqueKey.append(machinecode);
		primaryUniqueKey.append(systemTimeMillis);
		primaryUniqueKey.append(synchronizedNum);
		return primaryUniqueKey.toString();
	}
	
	
	
	/**
	 * 获得一个UUID<br>
	 * 
	 * @return 全局唯一主键
	 */
	public synchronized static String creatPUKey() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
