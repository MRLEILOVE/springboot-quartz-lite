package com.leigq.quartz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leigq.quartz.bean.common.Response;
import com.leigq.quartz.bean.vo.SysTaskLogListVO;
import com.leigq.quartz.service.SysTaskLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final Response response;

    public SysTaskLogController(SysTaskLogService sysTaskLogService, Response response) {
        this.sysTaskLogService = sysTaskLogService;
        this.response = response;
    }


    /**
     * 查询任务日志列表
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 13:49 <br>
     */
    @GetMapping("/logs/{task_id}/{page_num}/{page_size}")
    public Response queryTaskLogList(@PathVariable("task_id") Long taskId, @PathVariable("page_num") Integer pageNum, @PathVariable("page_size") Integer pageSize) {
        final IPage<SysTaskLogListVO> sysTaskLogList = sysTaskLogService.taskLogList(taskId, pageNum, pageSize);
        return response.success(sysTaskLogList);
    }


}
