package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.Holiday;
import com.sepim.springboot.entity.ResultData;

public interface HolidayService extends IService<Holiday> {
    public ResultData leave(Holiday leave);

    public ResultData leaveList(String userId);

    public ResultData auditList();

    public ResultData changeState(Holiday holiday);
}
