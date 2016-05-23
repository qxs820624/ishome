package org.isotope.jfp.framework.search;

import org.isotope.jfp.framework.constants.ISFrameworkConstants;

public interface ISSentenceConstants extends ISFrameworkConstants{

	public final static String VERSION = "341:";
	
	/**
	 * 最后更新时间
	 */
	public final static String SENTENCE_UTD = "SENTENCE"+VERSION+"UTD:";
	/**
	 * 更新语句
	 */
	public final static String SENTENCE_UPD = "SENTENCE"+VERSION+"UPD:";
	/**
	 * 创建语句
	 */
	public final static String SENTENCE_CRT = "SENTENCE"+VERSION+"CRT:";
	/**
	 * 查询语句
	 */
	public final static String SENTENCE_SCH = "SENTENCE"+VERSION+"SCH:";
	/**
	 * 最后同步日期
	 */
	public final static String SENTENCE_SYN = "SENTENCE"+VERSION+"SYN:";
	
	/**
	 * 工商URL地址
	 */
	public static final String COMPANY_URL = "INDEX"+VERSION+"CAPURL:";
	
	/**
	 * 工商URL地址
	 */
	public static final String COMPANY_DEL = "COMPANY"+VERSION+"DEL:";
}