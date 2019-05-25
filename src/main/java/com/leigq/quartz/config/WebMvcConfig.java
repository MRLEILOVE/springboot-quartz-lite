package com.leigq.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * MVC配置，在这里可以加入定义的拦截器
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019-05-24 23:50 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 添加资源处理程序
     * @author ：LeiGQ <br>
     * @date ：2019-05-24 23:52 <br>
     * <p>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("job-manager.html")
                .addResourceLocations("classpath:/templates/");
    }

    /**
     * 解决SpringBoot PUT请求不能接收到参数的问题
     * <br/>
     * 参考：https://www.cnblogs.com/aliyunpang/p/8962694.html
     * 开启：HttpPutFormContentFilter
     * @author ：LeiGQ <br>
     * @date ：2019-05-25 11:01 <br>
     * <p>
     */
    @Bean
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }
}