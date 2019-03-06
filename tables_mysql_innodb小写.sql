/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.21-log : Database - quartz
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*In your Quartz properties file, you'll need to set 
 org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate

By: Ron Cordell - roncordell
I didn't see this anywhere, so I thought I'd post it here. This is the script from Quartz to create the tables in a MySQL database, modified to use INNODB instead of MYISAM.
把官方SQL改为小写
Quartz表结构说明:https://www.cnblogs.com/meet/p/Quartz-biao-jie-gou-shuo-ming.html */

create database /*!32312 IF NOT EXISTS*/`quartz` /*!40100 DEFAULT CHARACTER SET utf8 */;

use `quartz`;

/*Table structure for table `qrtz_blob_triggers` */

drop table if exists `qrtz_blob_triggers`;

create table `qrtz_blob_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `blob_data` blob,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  key `sched_name` (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_blob_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) engine=innodb default charset=utf8 comment='以Blob 类型存储的触发器。';

/*Data for the table `qrtz_blob_triggers` */

/*Table structure for table `qrtz_calendars` */

drop table if exists `qrtz_calendars`;

create table `qrtz_calendars` (
  `sched_name` varchar(120) not null,
  `calendar_name` varchar(200) not null,
  `calendar` blob not null,
  primary key (`sched_name`,`calendar_name`)
) engine=innodb default charset=utf8 comment='存放日历信息， quartz可配置一个日历来指定一个时间范围。';

/*Data for the table `qrtz_calendars` */

/*Table structure for table `qrtz_cron_triggers` */

drop table if exists `qrtz_cron_triggers`;

create table `qrtz_cron_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `cron_expression` varchar(120) not null,
  `time_zone_id` varchar(80) default null,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_cron_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) engine=innodb default charset=utf8 comment='存放cron类型的触发器。';

/*Data for the table `qrtz_cron_triggers` */

/*Table structure for table `qrtz_fired_triggers` */

drop table if exists `qrtz_fired_triggers`;

create table `qrtz_fired_triggers` (
  `sched_name` varchar(120) not null,
  `entry_id` varchar(95) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `instance_name` varchar(200) not null,
  `fired_time` bigint(13) not null,
  `sched_time` bigint(13) not null,
  `priority` int(11) not null,
  `state` varchar(16) not null,
  `job_name` varchar(200) default null,
  `job_group` varchar(200) default null,
  `is_nonconcurrent` varchar(1) default null,
  `requests_recovery` varchar(1) default null,
  primary key (`sched_name`,`entry_id`),
  key `idx_qrtz_ft_trig_inst_name` (`sched_name`,`instance_name`),
  key `idx_qrtz_ft_inst_job_req_rcvry` (`sched_name`,`instance_name`,`requests_recovery`),
  key `idx_qrtz_ft_j_g` (`sched_name`,`job_name`,`job_group`),
  key `idx_qrtz_ft_jg` (`sched_name`,`job_group`),
  key `idx_qrtz_ft_t_g` (`sched_name`,`trigger_name`,`trigger_group`),
  key `idx_qrtz_ft_tg` (`sched_name`,`trigger_group`)
) engine=innodb default charset=utf8 comment='存放已触发的触发器。';

/*Data for the table `qrtz_fired_triggers` */

/*Table structure for table `qrtz_job_details` */

drop table if exists `qrtz_job_details`;

create table `qrtz_job_details` (
  `sched_name` varchar(120) not null,
  `job_name` varchar(200) not null,
  `job_group` varchar(200) not null,
  `description` varchar(250) default null,
  `job_class_name` varchar(250) not null,
  `is_durable` varchar(1) not null,
  `is_nonconcurrent` varchar(1) not null,
  `is_update_data` varchar(1) not null,
  `requests_recovery` varchar(1) not null,
  `job_data` blob,
  primary key (`sched_name`,`job_name`,`job_group`),
  key `idx_qrtz_j_req_recovery` (`sched_name`,`requests_recovery`),
  key `idx_qrtz_j_grp` (`sched_name`,`job_group`)
) engine=innodb default charset=utf8 comment='存放一个jobDetail信息。';

/*Data for the table `qrtz_job_details` */

/*Table structure for table `qrtz_locks` */

drop table if exists `qrtz_locks`;

create table `qrtz_locks` (
  `sched_name` varchar(120) not null,
  `lock_name` varchar(40) not null,
  primary key (`sched_name`,`lock_name`)
) engine=innodb default charset=utf8 comment=' 存储程序的悲观锁的信息(假如使用了悲观锁)。';

/*Data for the table `qrtz_locks` */

/*Table structure for table `qrtz_paused_trigger_grps` */

drop table if exists `qrtz_paused_trigger_grps`;

create table `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) not null,
  `trigger_group` varchar(200) not null,
  primary key (`sched_name`,`trigger_group`)
) engine=innodb default charset=utf8 comment='存放暂停掉的触发器。';

/*Data for the table `qrtz_paused_trigger_grps` */

/*Table structure for table `qrtz_scheduler_state` */

drop table if exists `qrtz_scheduler_state`;

create table `qrtz_scheduler_state` (
  `sched_name` varchar(120) not null,
  `instance_name` varchar(200) not null,
  `last_checkin_time` bigint(13) not null,
  `checkin_interval` bigint(13) not null,
  primary key (`sched_name`,`instance_name`)
) engine=innodb default charset=utf8 comment='调度器状态。';

/*Data for the table `qrtz_scheduler_state` */

/*Table structure for table `qrtz_simple_triggers` */

drop table if exists `qrtz_simple_triggers`;

create table `qrtz_simple_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `repeat_count` bigint(7) not null,
  `repeat_interval` bigint(12) not null,
  `times_triggered` bigint(10) not null,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_simple_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) engine=innodb default charset=utf8 comment='简单触发器的信息。';

/*Data for the table `qrtz_simple_triggers` */

/*Table structure for table `qrtz_simprop_triggers` */

drop table if exists `qrtz_simprop_triggers`;

create table `qrtz_simprop_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `str_prop_1` varchar(512) default null,
  `str_prop_2` varchar(512) default null,
  `str_prop_3` varchar(512) default null,
  `int_prop_1` int(11) default null,
  `int_prop_2` int(11) default null,
  `long_prop_1` bigint(20) default null,
  `long_prop_2` bigint(20) default null,
  `dec_prop_1` decimal(13,4) default null,
  `dec_prop_2` decimal(13,4) default null,
  `bool_prop_1` varchar(1) default null,
  `bool_prop_2` varchar(1) default null,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_simprop_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) engine=innodb default charset=utf8;

/*Data for the table `qrtz_simprop_triggers` */

/*Table structure for table `qrtz_triggers` */

drop table if exists `qrtz_triggers`;

create table `qrtz_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `job_name` varchar(200) not null,
  `job_group` varchar(200) not null,
  `description` varchar(250) default null,
  `next_fire_time` bigint(13) default null,
  `prev_fire_time` bigint(13) default null,
  `priority` int(11) default null,
  `trigger_state` varchar(16) not null,
  `trigger_type` varchar(8) not null,
  `start_time` bigint(13) not null,
  `end_time` bigint(13) default null,
  `calendar_name` varchar(200) default null,
  `misfire_instr` smallint(2) default null,
  `job_data` blob,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  key `idx_qrtz_t_j` (`sched_name`,`job_name`,`job_group`),
  key `idx_qrtz_t_jg` (`sched_name`,`job_group`),
  key `idx_qrtz_t_c` (`sched_name`,`calendar_name`),
  key `idx_qrtz_t_g` (`sched_name`,`trigger_group`),
  key `idx_qrtz_t_state` (`sched_name`,`trigger_state`),
  key `idx_qrtz_t_n_state` (`sched_name`,`trigger_name`,`trigger_group`,`trigger_state`),
  key `idx_qrtz_t_n_g_state` (`sched_name`,`trigger_group`,`trigger_state`),
  key `idx_qrtz_t_next_fire_time` (`sched_name`,`next_fire_time`),
  key `idx_qrtz_t_nft_st` (`sched_name`,`trigger_state`,`next_fire_time`),
  key `idx_qrtz_t_nft_misfire` (`sched_name`,`misfire_instr`,`next_fire_time`),
  key `idx_qrtz_t_nft_st_misfire` (`sched_name`,`misfire_instr`,`next_fire_time`,`trigger_state`),
  key `idx_qrtz_t_nft_st_misfire_grp` (`sched_name`,`misfire_instr`,`next_fire_time`,`trigger_group`,`trigger_state`),
  constraint `qrtz_triggers_ibfk_1` foreign key (`sched_name`, `job_name`, `job_group`) references `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) engine=innodb default charset=utf8 comment='触发器的基本信息。';

/*Data for the table `qrtz_triggers` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
