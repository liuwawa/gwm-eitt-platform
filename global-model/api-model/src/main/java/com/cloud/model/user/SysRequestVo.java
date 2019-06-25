package com.cloud.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
public class SysRequestVo<T extends Model> extends Model<T> {
    @TableField(exist = false)
    private String loginAdminName;
}
