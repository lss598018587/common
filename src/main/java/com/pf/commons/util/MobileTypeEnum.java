package com.pf.commons.util;

import org.apache.commons.lang.StringUtils;

/**
 * 其实应该更名为 ClientType，但用到的地方有点多，不好改，凑合着用吧
 * Account中有ClientTypeEnum枚举类，Account单独使用，
 * 多了一种LOW_VERSION枚举，其他枚举值作修改时需要同步一下
 */
public enum MobileTypeEnum {

	ANDROID("0", "ANDROID_PHONE"),
	IPHONE("1", "IPHONE"),
	ANDROID_PAD("2","ANDROID_PAD"),
	IPAD("3","IPAD"),
    @Deprecated
    BROWSER("4","BROWSER"),
	WAP("5", "WAP"),
	WEB("6", "WEB"),
	OPEN("7", "OPEN");

	private String key;
	private String value;

	private MobileTypeEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static MobileTypeEnum getByValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (MobileTypeEnum type : values()) {
				if (type.value.equals(value)) {
					return type;
				}
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
