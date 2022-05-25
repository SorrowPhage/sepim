package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Holiday {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    private String userId;
    private String username;
    private String reason;
    private String leaveTime;
    private String releaseTime;
    private String state;
}
