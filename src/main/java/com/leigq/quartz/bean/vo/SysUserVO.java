package com.leigq.quartz.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * 系统用户
 *
 * @author leiguoqing
 * @date 2020-08-08 22:30:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVO {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

}
