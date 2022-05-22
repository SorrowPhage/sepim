package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class User {
    private String id;
    private String password;
    private String username;
    private String type;
    private String avatarUrl;
    private String email;
    //中无此字段，但是注册和找回密码的时候需要
    @TableField(exist = false)
    private String verCode;
    @TableField(exist = false)
    private String token;
    //表无此字段，但是修改密码的时候需要上传原密码确认身份
    @TableField(exist = false)
    private String oldPassword;
}
