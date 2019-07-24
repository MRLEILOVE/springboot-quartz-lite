package com.leigq.quartz.bean.i18n;

/**
 * 国际化属性文件策略上下文
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/24 13:10
 */
public class I18nPropertiesStrategyContext {

	private I18nPropertiesStrategy i18nPropertiesStrategy;

	public void setI18nPropertiesStrategy(I18nPropertiesStrategy i18nPropertiesStrategy) {
		this.i18nPropertiesStrategy = i18nPropertiesStrategy;
	}

	public String getValue(String key) {
		return i18nPropertiesStrategy.getValue(key);
	}
}
