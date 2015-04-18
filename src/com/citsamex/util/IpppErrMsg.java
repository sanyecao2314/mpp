package com.citsamex.util;

public interface IpppErrMsg {
	
	/**
	 * 配置的不是一个文件夹.
	 */
	public static String ERR001 = "配置的不是一个文件夹.";

	/**
	 * 不是一个可用文件;\t
	 */
	public static String ERR002 = "不是一个可用文件;\t";

	/**
	 * 数据数量和头信息标示不同; 
	 */
	public static String DC_0001 = "DC_NUM";
	
	/**
	 * 数据格式不正确
	 */
	public static String DC_0002 = "DC_DF";


	/**
	 * 数据长度不正确
	 */
	public static String DC_0003 = "DC_DLEN";
	
	/**
	 * 不是约定的值
	 */
	public static String DC_0004 = "DC_NON";
	
	/**
	 * 数据列数量不正确
	 */
	public static String DC_0005 = "DC_COL";
	
	/**
	 * 未找到匹配值
	 */
	public static String DC_0006 = "DC_PNO";
	
	/**
	 * 存在相同的值
	 */
	public static String DC_0007 = "DC_DUP";

	/**
	 * 导入异常;(调用系统本身的方法时,抛出异常)
	 */
	public static String DC_0008 = "IMP_ERR";

}
