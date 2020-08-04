package com.leigq.quartz.controller;

import com.leigq.quartz.service.SysTaskLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务日志 Controller
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2020/08/04 14:23 <br>
 * </p>
 */
@Slf4j
@RestController
public class SysTaskLogController {

	private final SysTaskLogService sysTaskLogService;

	public SysTaskLogController(SysTaskLogService sysTaskLogService) {
		this.sysTaskLogService = sysTaskLogService;
	}



}
