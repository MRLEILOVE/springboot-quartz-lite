package com.leigq.quartz.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * 读取Properties文件工具类
 * <br>
 * 参考:<a href='https://note.youdao.com/share/?id=1a9228ce7c45ad383921eccf6c0580f2&type=note#/'>spring中如何读取.properties配置文件</a>
 * <p>
 * 创建人：leigq <br>
 * 创建时间：2018-11-09 13:17 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
public class PropertiesUtils {

    private Logger log = LoggerFactory.getLogger(PropertiesUtils.class);

    private String path;
    private Properties properties;

    private PropertiesUtils(String path) {
        this.path = path;
        try {
            this.properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(path));
        } catch (IOException e) {
            log.error(String.format("地址为 %s 的文件不存在", path), e);
        }
    }

    /**
     * 构建PropertiesUtil
     * <br>创建人： leigq
     * <br>创建时间： 2018-11-09 13:56
     * <br>
     *
     * @param path 国际化资源文件路径,如:i18n/zh_CN.properties
     */
    public static PropertiesUtils init(String path) {
        return new PropertiesUtils(path);
    }

    /**
     * 获取配置文件中的值
     * <br>创建人： leigq
     * <br>创建时间： 2018-11-09 14:05
     * <br>
     *
     * @param key 键
     */
    public String getValue(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException(String.format("配置文件 %s 中找不到这个Key，key = %s", path, key));
        }
        key = key.trim();
        String property = properties.getProperty(key);
        if (StringUtils.isBlank(property)) {
            throw new NullPointerException(String.format("配置文件 %s 中 key = %s 的 value 为空", path, key));
        }
        return property.trim();
    }

}
