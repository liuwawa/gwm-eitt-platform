package com.cloud.model.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用来接收和返回组织信息和组织拓展信息的封装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GroupWithExpand implements Serializable {
    private SysGroup sysGroup;
    private SysGroupExpand sysGroupExpand;
}
