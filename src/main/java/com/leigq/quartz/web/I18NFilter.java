package com.leigq.quartz.web;

import com.leigq.quartz.bean.i18n.CnI18nPropertiesStrategy;
import com.leigq.quartz.bean.i18n.EnI18nPropertiesStrategy;
import com.leigq.quartz.bean.i18n.I18nPropertiesStrategy;
import com.leigq.quartz.bean.i18n.I18nPropertiesStrategyContext;
import com.leigq.quartz.bean.common.Response;
import com.leigq.quartz.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 国际化过滤器
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/24 10:47
 */
@WebFilter(filterName = "响应国际化过滤器", urlPatterns = "/*")
@Slf4j
public class I18NFilter implements Filter {

	/**
	 * 前端国际化请求头 key
	 */
	private static final String LANGUAGE_HEADER = "Language";

	/*
	 * 前端请求头 value
	 * */
	// 中文
	private static final String ZH_CN = "cn";
	// 英文
	private static final String EN_US = "en";

	/**
	 * 用于保存请求头中的语言参数
	 */
	private String language;

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		log.info(">>>>>>>>>>请求进入国际化过滤器<<<<<<<<<");
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		// 获取请求头中的语言参数
		language = req.getHeader(LANGUAGE_HEADER);
		if (StringUtils.isNotBlank(language)) {
			handleResponse(request, response, resp, chain);
		} else {
			log.info(">>>>>> 国际化过滤器不做处理 <<<<<<");
			try {
				response.setCharacterEncoding("UTF-8");
				chain.doFilter(request, response);
			} catch (Exception e) {
				log.info("处理国际化返回结果失败", e);
			}
		}
	}

	/**
	 * 处理响应
	 */
	private void handleResponse(ServletRequest request, ServletResponse response, HttpServletResponse resp, FilterChain chain) {
		// 包装响应对象 resp 并缓存响应数据
		WrapperResponse mResp = new WrapperResponse(resp);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			// 防止出现乱码
			mResp.setCharacterEncoding("UTF-8");
			chain.doFilter(request, mResp);
			// 获取缓存的响应数据
			byte[] bytes = mResp.getBytes();
			// 响应字符串
			String responseStr = new String(bytes, "UTF-8");
			// 将 String 类型响应数据转成 Response 对象
			Response returnResponse = JSONUtils.json2pojo(responseStr, Response.class);
			// meta 对象
			Response.Meta meta = returnResponse.getMeta();
			// 返回信息
			String msg = meta.getMsg();
			if (meta.getIsI18n()) {
				// 返回信息参数
				Object[] msgParams = meta.getMsgParams();
				// 处理国际化
				if (Objects.isNull(msgParams)) {
					// 直接用 value 替换 key
					responseStr = responseStr.replace(msg, getI18nVal(msg));
				} else {
					// 循环用 value 替换 key
					String[] keys = msg.split("\\{}");
					for (String key : keys) {
						responseStr = responseStr.replaceFirst(key, getI18nVal(key));
					}
					// 循环处理返回结果参数
					for (Object param : msgParams) {
						responseStr = responseStr.replaceFirst("\\{}", param.toString());
					}
				}
			}
			out.write(responseStr.getBytes());
		} catch (Exception e) {
			log.error("处理国际化返回结果失败", e);
		} finally {
			try {
				assert out != null;
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据properties文件中属性的key获取对应的值
	 * 说明：
	 * <p>
	 * 创建人: LGQ <br>
	 * 创建时间: 2018年8月13日 下午8:10:20 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	private String getI18nVal(String langKey) {
		I18nPropertiesStrategy i18nPropertiesStrategy;
		switch (language) {
			case ZH_CN:
				i18nPropertiesStrategy = new CnI18nPropertiesStrategy();
				break;
			case EN_US:
				i18nPropertiesStrategy = new EnI18nPropertiesStrategy();
				break;
			default:
				i18nPropertiesStrategy = new CnI18nPropertiesStrategy();
				break;
		}
		I18nPropertiesStrategyContext context = new I18nPropertiesStrategyContext();
		context.setI18nPropertiesStrategy(i18nPropertiesStrategy);
		String value = context.getValue(langKey);
		log.info("I18N Filter ### key = {} ---->  value = {}", langKey, value);
		return value;
	}

	@Override
	public void destroy() {
	}
}
