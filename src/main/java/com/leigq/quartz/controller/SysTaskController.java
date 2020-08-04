package com.leigq.quartz.controller;

import com.leigq.quartz.service.SysTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务 Controller
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2020/08/04 14:23 <br>
 * </p>
 */
@Slf4j
@RestController
public class SysTaskController {

	private final SysTaskService sysTaskService;

	public SysTaskController(SysTaskService sysTaskService) {
		this.sysTaskService = sysTaskService;
	}



}
