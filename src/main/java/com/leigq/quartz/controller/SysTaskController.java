package com.leigq.quartz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leigq.quartz.bean.common.Response;
import com.leigq.quartz.bean.vo.AddSysTaskVO;
import com.leigq.quartz.bean.vo.SysTaskListVO;
import com.leigq.quartz.bean.vo.UpdateSysTaskVO;
import com.leigq.quartz.service.SysTaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 任务 Controller
 * <p>
 * @author leigq <br>
 * @date 2020/08/04 14:23 <br>
 * </p>
 */
@Slf4j
@RestController
public class SysTaskController {

	/**
	 * 系统自己创建的任务表服务
	 */
	private final SysTaskService sysTaskService;

	public SysTaskController(SysTaskService sysTaskService) {
		this.sysTaskService = sysTaskService;
	}

	/**
	 * 添加任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:37 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PostMapping("/tasks")
	public Response addTask(@Valid AddSysTaskVO addSysTaskVO) {
		// 验证表达式格式
		if (!CronExpression.isValidExpression(addSysTaskVO.getCron())) {
			return Response.fail("表达式格式错误！");
		}
		sysTaskService.addTask(addSysTaskVO);
		return Response.success("创建定时任务成功！");
	}


	/**
	 * 更新任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:49 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PutMapping("/tasks")
	public Response updateTask(@Valid UpdateSysTaskVO updateSysTaskVO) {
		// 验证表达式格式
		if (!CronExpression.isValidExpression(updateSysTaskVO.getCron())) {
			return Response.fail("表达式格式错误！");
		}
		sysTaskService.updateTask(updateSysTaskVO);
		return Response.success("更新任务成功！");
	}

	/**
	 * 执行任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:48 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PostMapping("/tasks/action/execute/{task_name}/{task_group}")
	public Response executeTask(@PathVariable("task_name") String taskName, @PathVariable("task_group") String taskGroup) {
		sysTaskService.executeTask(taskName, taskGroup);
		return Response.success("执行任务成功！");
	}


	/**
	 * 暂停任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:48 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PostMapping("/tasks/action/pause/{task_name}/{task_group}")
	public Response pauseTask(@PathVariable("task_name") String taskName, @PathVariable("task_group") String taskGroup) {
		sysTaskService.pauseTask(taskName, taskGroup);
		return Response.success("暂停任务成功！");
	}

	/**
	 * 恢复任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:48 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PostMapping("/tasks/action/resume/{task_name}/{task_group}")
	public Response resumeTask(@PathVariable("task_name") String taskName, @PathVariable("task_group") String taskGroup) {
		sysTaskService.resumeTask(taskName, taskGroup);
		return Response.success("恢复任务成功！");
	}


	/**
	 * 删除任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:49 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@DeleteMapping("/tasks/{task_name}/{task_group}")
	public Response deleteTask(@PathVariable("task_name") String taskName, @PathVariable("task_group") String taskGroup) {
		sysTaskService.deleteTask(taskName, taskGroup);
		return Response.success("删除任务成功！");
	}

	/**
	 * 查询任务列表
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:49 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@GetMapping("/tasks/{page_num}/{page_size}")
	public Response queryTaskList(@PathVariable("page_num") Integer pageNum, @PathVariable("page_size") Integer pageSize) {
		final IPage<SysTaskListVO> sysTaskList = sysTaskService.taskList(pageNum, pageSize);
		return Response.success(sysTaskList);
	}
}
