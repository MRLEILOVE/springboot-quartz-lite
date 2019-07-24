package com.leigq.quartz.bean.i18n;

import com.leigq.quartz.utils.PropertiesUtils;

import java.util.Objects;

/**
 * 中国国际化属性文件策略
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/24 13:04
 */
public class CnI18nPropertiesStrategy implements I18nPropertiesStrategy {

	private volatile static PropertiesUtils propertiesUtils;

	@Override
	public String getValue(String key) {
		return getPropertiesUtils().getValue(key);
	}

	private PropertiesUtils getPropertiesUtils() {
		if (Objects.isNull(propertiesUtils)) {
			synchronized (CnI18nPropertiesStrategy.class) {
				if (Objects.isNull(propertiesUtils)) {
					propertiesUtils = PropertiesUtils.init("i18n/lang_zh_CN.properties");
				}
			}
		}
		return propertiesUtils;
	}
}
