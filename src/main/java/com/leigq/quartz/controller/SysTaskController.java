package com.leigq.quartz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leigq.quartz.bean.common.Response;
import com.leigq.quartz.bean.vo.DetailJobVO;
import com.leigq.quartz.bean.vo.SimpleJobVO;
import com.leigq.quartz.bean.vo.JobAndTriggerVO;
import com.leigq.quartz.service.SysTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

	/**
	 * 系统自己创建的任务表服务
	 */
	private final SysTaskService sysTaskService;

	private final Response response;

	public SysTaskController(SysTaskService sysTaskService, Response response) {
		this.sysTaskService = sysTaskService;
		this.response = response;
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
	@PostMapping("/jobs")
	public Response addJob(@Validated DetailJobVO detailJobVO) {
		sysTaskService.addJob(detailJobVO.getJobClassName(), detailJobVO.getJobGroupName(), detailJobVO.getCronExpression(), detailJobVO.getJobDescription());
		return response.success("创建定时任务成功！");
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
	@PostMapping("/jobs/action/execute")
	public Response executeJob(@Validated SimpleJobVO simpleJobVO) {
		sysTaskService.executeJob(simpleJobVO.getJobClassName(), simpleJobVO.getJobGroupName());
		return response.success("执行任务成功！");
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
	@PostMapping("/jobs/action/pause")
	public Response pauseJob(@Validated SimpleJobVO simpleJobVO) {
		sysTaskService.pauseJob(simpleJobVO.getJobClassName(), simpleJobVO.getJobGroupName());
		return response.success("暂停任务成功！");
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
	@PostMapping("/jobs/action/resume")
	public Response resumeJob(@Validated SimpleJobVO simpleJobVO) {
		sysTaskService.resumeJob(simpleJobVO.getJobClassName(), simpleJobVO.getJobGroupName());
		return response.success("恢复任务成功！");
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
	@PutMapping("/jobs")
	public Response rescheduleJob(@Validated DetailJobVO detailJobVO) {
		sysTaskService.rescheduleJob(detailJobVO.getJobClassName(), detailJobVO.getJobGroupName(), detailJobVO.getCronExpression());
		return response.success("更新任务成功！");
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
	@DeleteMapping("/jobs/{job_class_name}/{job_group_Name}")
	public Response deleteJob(@PathVariable("job_class_name") String jobClassName, @PathVariable("job_group_Name") String jobGroupName) {
		sysTaskService.deleteJob(jobClassName, jobGroupName);
		return response.success("删除任务成功！");
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
	@GetMapping("/jobs/{page_num}/{page_size}")
	public Response queryJob(@PathVariable("page_num") Integer pageNum, @PathVariable("page_size") Integer pageSize) {
		final IPage<JobAndTriggerVO> jobAndTriggerDetails = sysTaskService.getJobAndTriggerDetails(pageNum, pageSize);
		return response.success(jobAndTriggerDetails);
	}
}
