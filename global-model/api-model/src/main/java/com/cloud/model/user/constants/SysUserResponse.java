package com.cloud.model.user.constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUserResponse implements Serializable {
    private String duties;
    private String nickname;
    private String groupName;

}
