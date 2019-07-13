package com.pf.commons.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public abstract class MobileUtils {
	public static final String MOBILE_DEVICE_NAME = "MOBILE_DEVICE";
	public static final String CLIENT_VERSION = "CLIENT_VERSION";
	public static final String CHANNEL_NAME = "CHANNEL_NAME";
	public static final String TERMINAL_BIZ_TYPE = "TERMINAL_BIZ_TYPE";
	public static final String COOKIE = "Cookie";

	/**
	 * 插件名称
	 */
	public static final String PLUGIN_NAME = "plugin_name";

	/**
	 * 插件版本号
	 */
	public static final String PLUGIN_VERSION = "plugin_version";
	
	public static final String VERSION520 = "5.2.0";
	public static final String VERSION620 = "6.2.0";
	public static final String VERSION630 = "6.3.0";
	public static final String VERSION669 = "6.6.9";


	public static final int SERVER_VERSION = 3 * 10000 + 0 * 100 + 0 * 1;

	private static final ThreadLocal<MobileTypeEnum> mobileType = new ThreadLocal<MobileTypeEnum>();
	private static final ThreadLocal<String> clientVersion = new ThreadLocal<String>();
	private static final ThreadLocal<String> channelName = new ThreadLocal<String>();

	public static String getMobileDeviceFromRequest(HttpServletRequest request) {
		return request.getHeader(MobileUtils.MOBILE_DEVICE_NAME);
	}

	public static String getClientVersionFromRequest(HttpServletRequest request) {
		return request.getHeader(MobileUtils.CLIENT_VERSION);
	}

	public static String getChannelNameFromRequest(HttpServletRequest request) {
		return request.getHeader(MobileUtils.CHANNEL_NAME);
	}
	
	public static String getTerminalBizTypeFromRequest(HttpServletRequest request) {
		return request.getHeader(MobileUtils.TERMINAL_BIZ_TYPE);
	}

	/**
	 * 获取 iPhone Scale , 从 User-Agent 数据做判断;
	 * 
	 * @param request
	 * @return
	 */
	public static IPhoneScale getIPhoneScale(HttpServletRequest request) {
		/*
		 * User-Agent: TBJPro/1.0 (iPhone; iOS 8.1.2; Scale/2.00)
		 */
		String userAgent = request.getHeader("User-Agent");
		IPhoneScale defaultScale = IPhoneScale.Scale200;
		
		if (StringUtils.isBlank(userAgent)) {
			return defaultScale;
		}

		if (userAgent.contains("Scale/2.00")) {
			return IPhoneScale.Scale200;
		}

		if (userAgent.contains("Scale/3.00")) {
			return IPhoneScale.Scale300;
		}

		return defaultScale;

	}

	public static MobileTypeEnum getMobileType() {
		return mobileType.get();
	}

	public static String getClientVersion() {
		return clientVersion.get();
	}

	public static Integer getClientVersionInteger() {
		return versionToInteger(clientVersion.get());
	}

	public static void setMobileType(MobileTypeEnum type) {
		mobileType.set(type);
	}

	public static void setClientVersion(String version) {
		clientVersion.set(version);
	}

	public static void setChannelName(String channel) {
		channelName.set(channel);
	}

	public static String getChannelName() {
		return channelName.get();
	}

	public static String getMobileTypeAndVersion() {
		return getMobileType().getValue() + "-" + getClientVersion();
	}

	public static void remove() {
		mobileType.remove();
		clientVersion.remove();
		channelName.remove();
	}

	public static Integer versionToInteger(String versionStr) {
		if(StringUtils.isBlank(versionStr)){
			return null;
		}
		String versions[] = versionStr.split("\\.");
		if (versions.length != 3)
			return null;
		int version = Integer.parseInt(versions[0]) * 10000 + Integer.parseInt(versions[1]) * 100 + Integer.parseInt(versions[2]) * 1;
		return version;
	}


	public static String vertionToString(Integer version){
		if(version == null){
			return null;
		}
		String v = String.valueOf(version);
		String r = Integer.valueOf(v.substring(0, v.length()- 4))
				+ "." 
				+ Integer.valueOf(v.substring(v.length() - 4, v.length() - 2) )
				+ "."
				+ Integer.valueOf(v.substring(v.length() - 2, v.length()));
		return r;
	}
	
	public static void main(String[] args) {
		int v = versionToInteger("6.9.1");
		System.out.println(v);
		System.out.println(vertionToString(v));

		System.out.println(compareClientVersion("6.0.0", VERSION620));
	}

	public static enum IPhoneScale {
		/**
		 * Scale/2.00
		 */
		Scale200,
		/**
		 * Scale/3.00
		 */
		Scale300;
	}
	
	/**
	 * 客户端版本的比较：
	 * versionToCompare-->版本参照。若客户端版本高于此版本，返回true,否则返回false
	 */
	public static boolean compareClientVersion(String clientVersion, String versionToCompare) {
		if (StringUtils.isNotBlank(clientVersion)) {
			Integer versionInt = versionToInteger(clientVersion);
			if (versionInt != null && versionInt.compareTo(versionToInteger(versionToCompare)) >= 0) {
				return true;
			}
		}
		return false;
	}

	public static String getPluginNameFromRequest(HttpServletRequest request) {
		String pluginName = request.getParameter(PLUGIN_NAME);
		return pluginName;
	}

	public static String getPluginVersionFromRequest(HttpServletRequest request) {
		String pluginVersion = request.getParameter(PLUGIN_VERSION);
		return pluginVersion;
	}
	
}