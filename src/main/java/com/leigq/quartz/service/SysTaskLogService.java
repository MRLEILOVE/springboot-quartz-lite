package com.leigq.quartz.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leigq.quartz.domain.entity.SysTaskLog;
import com.leigq.quartz.domain.mapper.SysTaskLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type Sys task log service.
 * @author leigq
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SysTaskLogService extends ServiceImpl<SysTaskLogMapper, SysTaskLog> {

}
