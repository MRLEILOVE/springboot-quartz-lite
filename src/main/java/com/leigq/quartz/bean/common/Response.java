package com.leigq.quartz.bean.common;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 响应对象。包含处理结果（Meta）和返回数据（Data）两部分，在 Controller 处理完请求后将此对象转换成 json 返回给前台。注意：
 * <ul>
 * <li>处理成功一般返回处理结果和返回数据，失败只返回处理结果。具体返回什么需看接口文档。</li>
 * <li>处理成功结果码一般是200，失败码具体看出了什么错，对照 HTTP 响应码填。</li>
 * <li>默认处理方法慎用，前台最想要拿到的还是具体的结果码和信息。</li>
 * </ul>
 * <p>
 * @author ：leigq <br>
 * 创建时间：2017年10月9日 下午3:26:17 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Component
@Scope("prototype")
@SuppressWarnings(value = "all")
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    /**
     * 默认成功响应码
     */
    private static final Integer DEAFAULT_SUCCESS_CODE = HttpStatus.OK.value();

    /**
     * 默认成功响应信息
     */
    private static final String DEAFAULT_SUCCESS_MSG = "请求/处理成功！";

    /**
     * 默认国际化成功响应信息
     */
    private static final String DEAFAULT_I18N_SUCCESS_MSG = "REQUEST_SUCCESS";

    /**
     * 默认失败响应码
     */
    private static final Integer DEAFAULT_FAILURE_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 默认失败响应信息
     */
    private static final String DEAFAULT_FAILURE_MSG = "请求/处理失败！";

    /**
     * 默认国际化失败响应信息
     */
    private static final String DEAFAULT_I18N_FAILURE_MSG = "REQUEST_FAIL";

    @Getter
    @Setter
    private Meta meta;

    @Getter
    @Setter
    private Object data;



    /*******处理成功响应*************************************************************************************/

    /*↓↓↓↓↓↓默认(200)响应码，默认信息，无返回数据↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 处理成功响应，默认(200)响应码，默认信息，无返回数据
     *
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response success() {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, DEAFAULT_SUCCESS_MSG, false, null);
        this.data = null;
        return this;
    }

    /**
     * 处理国际化成功响应，默认(200)响应码，默认信息，无返回数据
     *
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response successI18n() {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, DEAFAULT_I18N_SUCCESS_MSG, true, null);
        this.data = null;
        return this;
    }


    /*↓↓↓↓↓↓默认(200)响应码，自定义信息，无返回数据↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 处理成功响应，默认(200)响应码，自定义信息，无返回数据
     *
     * @param msg  处理结果信息
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response success(String msg) {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, msg, false, null);
        this.data = null;
        return this;
    }

    /**
     * 处理成功响应，默认(200)响应码，自定义信息，无返回数据
     *
     * @param msg  处理结果信息
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response successI18n(String msg) {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, msg, true, null);
        this.data = null;
        return this;
    }

    /**
     * 处理成功响应，默认(200)响应码，自定义信息，无返回数据
     *
     * @param msg  处理结果信息
     * @param msgParams 结果信息参数
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response successI18n(String msg, Object[] msgParams) {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, msg, true, msgParams);
        this.data = null;
        return this;
    }


    /*↓↓↓↓↓↓默认(200)响应码，默认信息，有返回数据。↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 处理成功响应，默认(200)响应码，默认信息，有返回数据。
     *
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response success(Object data) {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, DEAFAULT_SUCCESS_MSG, false, null);
        this.data = data;
        return this;
    }

    /**
     * 处理成功响应，默认(200)响应码，默认信息，有返回数据。
     *
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response successI18n(Object data) {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, DEAFAULT_I18N_SUCCESS_MSG, true, null);
        this.data = data;
        return this;
    }


    /*↓↓↓↓↓↓默认(200)响应码，自定义信息，有返回数据↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/


    /**
     * 处理成功响应，默认(200)响应码，自定义信息，有返回数据
     *
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response success(String msg, Object data) {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, msg, false, null);
        this.data = data;
        return this;
    }

    /**
     * 处理成功响应，默认(200)响应码，自定义信息，有返回数据
     *
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response successI18n(String msg, Object data) {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, msg, true, null);
        this.data = data;
        return this;
    }

    /**
     * 处理成功响应，默认(200)响应码，自定义信息，有返回数据
     *
     * @param msg  处理结果信息
     * @param data 返回数据
     * @param msgParams 结果信息参数
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response successI18n(String msg, Object data, Object[] msgParams) {
        this.meta = new Meta(DEAFAULT_SUCCESS_CODE, msg, true, msgParams);
        this.data = data;
        return this;
    }


    /*↓↓↓↓↓↓自定义响应码，自定义信息，有返回数据↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 处理成功响应，自定义响应码，自定义信息，有返回数据
     *
     * @param httpStatus HTTP 响应码
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response success(HttpStatus httpStatus, String msg, Object data) {
        this.meta = new Meta(httpStatus.value(), msg, false, null);
        this.data = data;
        return this;
    }

    /**
     * 处理成功响应，自定义响应码，自定义信息，有返回数据
     *
     * @param httpStatus HTTP 响应码
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response successI18n(HttpStatus httpStatus, String msg, Object data) {
        this.meta = new Meta(httpStatus.value(), msg, true, null);
        this.data = data;
        return this;
    }

    /**
     * 处理成功响应，自定义响应码，自定义信息，有返回数据
     *
     * @param httpStatus HTTP 响应码
     * @param msg  处理结果信息
     * @param data 返回数据
     * @param msgParams 结果信息参数
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:25 <br>
     */
    public Response successI18n(HttpStatus httpStatus, String msg, Object data, Object[] msgParams) {
        this.meta = new Meta(httpStatus.value(), msg, true, msgParams);
        this.data = data;
        return this;
    }


    /*******处理失败响应*************************************************************************************/

    /*↓↓↓↓↓↓默认(500)响应码，默认信息，无返回数据↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 处理失败响应，返回默认(500)响应码、默认信息，无返回数据。
     *
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failure() {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, DEAFAULT_FAILURE_MSG, false, null);
        this.data = null;
        return this;
    }

    /**
     * 处理国际化失败响应，返回默认(500)响应码、默认信息，无返回数据。
     *
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failureI18n() {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, DEAFAULT_I18N_FAILURE_MSG, true, null);
        this.data = null;
        return this;
    }

    /*↓↓↓↓↓↓默认(500)响应码，自定义信息，无返回数据↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 处理失败响应，返回默认(500)响应码、自定义信息，无返回数据。
     *
     * @param msg 处理结果信息
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failure(String msg) {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, msg, false, null);
        this.data = null;
        return this;
    }

    /**
     * 处理国际化失败响应，返回默认(500)响应码、自定义信息，无返回数据。
     *
     * @param msg 处理结果信息
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failureI18n(String msg) {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, msg, true, null);
        this.data = null;
        return this;
    }

    /**
     * 处理国际化失败响应，返回默认(500)响应码、自定义信息，无返回数据。
     *
     * @param msg 处理结果信息
     * @param msgParams 结果信息参数
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failureI18n(String msg, Object[] msgParams) {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, msg, true, msgParams);
        this.data = null;
        return this;
    }

    /*↓↓↓↓↓↓默认(500)响应码，默认信息，有返回数据。↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 处理失败响应，默认(500)响应码，默认信息，有返回数据。
     *
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failure(Object data) {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, DEAFAULT_FAILURE_MSG, false, null);
        this.data = data;
        return this;
    }

    /**
     * 处理国际化失败响应，默认(500)响应码，默认信息，有返回数据。
     *
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failureI18n(Object data) {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, DEAFAULT_I18N_FAILURE_MSG, true, null);
        this.data = data;
        return this;
    }


    /*↓↓↓↓↓↓默认(500)响应码，自定义信息，有返回数据↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/


    /**
     * 处理失败响应，默认(500)响应码，自定义信息，有返回数据
     *
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failure(String msg, Object data) {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, msg, false, null);
        this.data = data;
        return this;
    }

    /**
     * 处理国际化失败响应，默认(500)响应码，自定义信息，有返回数据，有结果信息参数。
     *
     * @param msg  处理结果信息
     * @param data 返回数据
     * @param msgParams 结果信息参数
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failureI18n(String msg, Object data, Object[] msgParams) {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, msg, true, msgParams);
        this.data = data;
        return this;
    }

    /**
     * 处理国际化失败响应，默认(500)响应码，自定义信息，有返回数据，无结果信息参数。
     *
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failureI18n(String msg, Object data) {
        this.meta = new Meta(DEAFAULT_FAILURE_CODE, msg, true, null);
        this.data = data;
        return this;
    }


    /*↓↓↓↓↓↓自定义响应码，自定义信息，有返回数据↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 处理失败响应，自定义响应码，自定义信息，有返回数据
     *
     * @param httpStatus HTTP 响应码
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failure(HttpStatus httpStatus, String msg, Object data) {
        this.meta = new Meta(httpStatus.value(), msg, false, null);
        this.data = data;
        return this;
    }

    /**
     * 处理国际化失败响应，自定义响应码，自定义信息，有返回数据，有结果信息参数。
     *
     * @param httpStatus HTTP 响应码
     * @param msg  处理结果信息
     * @param data 返回数据
     * @param msgParams 结果信息参数
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failureI18n(HttpStatus httpStatus, String msg, Object data, Object[] msgParams) {
        this.meta = new Meta(httpStatus.value(), msg, true, msgParams);
        this.data = data;
        return this;
    }

    /**
     * 处理国际化失败响应，自定义响应码，自定义信息，有返回数据，无结果信息参数。
     *
     * @param httpStatus HTTP 响应码
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     * <p>
     * @author ：LeiGQ <br>
     * @date ：2019-05-20 15:22 <br>
     */
    public Response failureI18n(HttpStatus httpStatus, String msg, Object data) {
        this.meta = new Meta(httpStatus.value(), msg, true, null);
        this.data = data;
        return this;
    }


    /**
     * 元数据，包含响应码和信息。
     * <p>
     * 创建人：袁炜林 <br>
     * 创建时间：2017年10月9日 下午3:31:17 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Meta {

        /**
         * 处理结果代码，与 HTTP 状态响应码对应
         */
        private Integer code;

        /**
         * 处理结果信息
         */
        private String msg;

        /**
         * 处理结果信息是否国际化
         */
        private Boolean isI18n;

        /**
         * 处理结果信息参数
         */
        private Object[] msgParams;
    }

}
