package com.leigq.quartz.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * MVC配置，在这里可以加入定义的拦截器
 * <br/>
 * 有两种方式配置，推荐使用 "实现 WebMvcConfigurer 接口 " 实现
 * <br/>
 * 参考： https://blog.csdn.net/cp026la/article/details/86518655
 * <p>
 * <br/>
 * {@link @SpringBootConfiguration} 继承自 {@link @Configuration}，二者功能也一致，标注当前类是配置类，
 * 并会将当前类内声明的一个或多个以@Bean注解标记的方法的实例纳入到spring容器中，并且实例名就是方法名。
 * {@link @EnableWebMvc} 启用此注解，SpringBoot 关于 MVC 的自动装载会失效，交由开发者自定义
 * 创建人：LeiGQ <br>
 * 创建时间：2019-05-24 23:50 <br>
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

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
}