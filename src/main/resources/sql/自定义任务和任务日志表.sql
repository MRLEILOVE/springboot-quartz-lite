/*Table structure for table `sys_task` */

DROP TABLE IF EXISTS `sys_task`;

CREATE TABLE `sys_task` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `task_name` varchar(50) DEFAULT NULL COMMENT '任务名',
  `task_group` varchar(50) DEFAULT NULL COMMENT '任务组',
  `task_class` varchar(255) DEFAULT NULL COMMENT '执行类',
  `note` varchar(255) DEFAULT NULL COMMENT '任务说明',
  `cron` varchar(50) DEFAULT NULL COMMENT '定时规则',
  `exec_params` text COMMENT '执行参数',
  `exec_date` datetime DEFAULT NULL COMMENT '执行时间',
  `exec_result` tinyint(1) DEFAULT NULL COMMENT '执行结果（成功:1、失败:0)',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否启用，0(false)：禁用 1（true）：启用',
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `concurrent` tinyint(4) DEFAULT '0' COMMENT '是否允许并发，0(false)：不允许 1（true）：允许',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8


/*Data for the table `sys_task` */

/*Table structure for table `sys_task_log` */

DROP TABLE IF EXISTS `sys_task_log`;

CREATE TABLE `sys_task_log` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `task_name` varchar(50) DEFAULT NULL COMMENT '任务名',
  `exec_date` datetime DEFAULT NULL COMMENT '执行时间',
  `exec_result` tinyint(1) DEFAULT NULL COMMENT '执行结果（成功:1、失败:0)',
  `exec_result_text` text COMMENT '成功信息或抛出的异常信息',
  `task_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8


/*Data for the table `sys_task_log` */