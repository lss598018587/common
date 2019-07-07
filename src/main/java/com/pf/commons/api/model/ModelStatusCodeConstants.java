package com.pf.commons.api.model;

/**
 * @author miaomiao
 * 
 */
public class ModelStatusCodeConstants {
	/**
	 * 正常状态, 请求被正确处理,并且获得预期的结果.
	 */
	public static final int OK = 0;
	
	/**
	 * 业务错误
	 */
	public static final int BUSINESS_ERROR = 1;
	
	/**
	 * 参数错误
	 */
	@Deprecated
	public static final int ARGUMENT_ERROR = 601;

	/**
	 * 状态错误
	 */
	@Deprecated
	public static final int STATUS_ERROR = 602;
	
	/**
	 * 系统内部错误
	 */
	public static final int INTERNAL_ERROR = 2;
	
	/**
	 * 需要身份验证
	 */
	public static final int UNAUTHORIZED = 10;
	
	/**
	 * 客户端版本号过低
	 */
	public static final int CLIENT_VERSION_LOW = 20;
	
	/**
	 * 拒绝访问.主要是一些非法访问<br>
	 * 比如非手机客户端请求只能由手机客户端访问的 URL.
	 */
	public static final int FORBIDDEN = 11;
	
}
