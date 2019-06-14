package com.cloud.dto.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class Header implements Serializable{
    //os os_version version
    private String os;               //操作系统
    private String os_version;      //操作系统版本
    private String version;          //手机型号
}
