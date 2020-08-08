package com.leigq.quartz.web.exception;

/**
 * 登录超时异常
 *
 * @author ：leigq
 * @date ：2019/7/31 22:04
 */
public class LoginTimeOutException extends ServiceException {
	public LoginTimeOutException() {
		super();
	}

	public LoginTimeOutException(String message) {
		super(message);
	}

	public LoginTimeOutException(String message, Throwable cause) {
		super(message, cause);
	}
}
