package com.leigq.quartz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leigq.quartz.bean.common.Response;
import com.leigq.quartz.bean.job.DetailJobDTO;
import com.leigq.quartz.bean.job.SimpleJobDTO;
import com.leigq.quartz.domain.entity.JobAndTrigger;
import com.leigq.quartz.service.JobAndTriggerService;
import com.leigq.quartz.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Job Controller
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2019/5/19 13:36 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
@Slf4j
@RestController
public class JobController {

	private final JobAndTriggerService jobAndTriggerService;
	private final Response response;
	private final JobService jobService;

	public JobController(JobAndTriggerService jobAndTriggerService, Response response, JobService jobService) {
		this.jobAndTriggerService = jobAndTriggerService;
		this.response = response;
		this.jobService = jobService;
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
	public Response addJob(@Validated DetailJobDTO detailJobDTO) {
		final Boolean addJobResult = jobService.addJob(detailJobDTO.getJobClassName(), detailJobDTO.getJobGroupName(),
				detailJobDTO.getCronExpression(), detailJobDTO.getJobDescription());
		return addJobResult ? response.success("创建定时任务成功！") : response.failure("创建定时任务失败！");
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
	public Response executeJob(@Validated SimpleJobDTO simpleJobDTO) {
		final Boolean executeJobResult = jobService.executeJob(simpleJobDTO.getJobClassName(), simpleJobDTO.getJobGroupName());
		return executeJobResult ? response.success("执行任务成功！") : response.failure("执行任务失败！");
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
	public Response pauseJob(@Validated SimpleJobDTO simpleJobDTO) {
		final Boolean pauseJobResult = jobService.pauseJob(simpleJobDTO.getJobClassName(), simpleJobDTO.getJobGroupName());
		return pauseJobResult ? response.success("暂停任务成功！") : response.failure("暂停任务失败！");
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
	public Response resumeJob(@Validated SimpleJobDTO simpleJobDTO) {
		final Boolean resumeJobResult = jobService.resumeJob(simpleJobDTO.getJobClassName(), simpleJobDTO.getJobGroupName());
		return resumeJobResult ? response.success("恢复任务成功！") : response.failure("恢复任务失败！");
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
	public Response rescheduleJob(@Validated DetailJobDTO detailJobDTO) {
		final Boolean rescheduleJobResult = jobService.rescheduleJob(detailJobDTO.getJobClassName(), detailJobDTO.getJobGroupName(),
				detailJobDTO.getCronExpression());
		return rescheduleJobResult ? response.success("更新任务成功！") : response.failure("更新任务失败！");
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
		final Boolean deleteJobResult = jobService.deleteJob(jobClassName, jobGroupName);
		return deleteJobResult ? response.success("删除任务成功！") : response.failure("删除任务失败！");
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
		final IPage<JobAndTrigger> jobAndTriggerDetails = jobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
		return response.success(jobAndTriggerDetails);
	}

}
