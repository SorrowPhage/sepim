package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.Holiday;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.HolidayMapper;
import com.sepim.springboot.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceImpl extends ServiceImpl<HolidayMapper, Holiday> implements HolidayService {

    @Autowired
    private ResultData resultData;

    public ResultData leave(Holiday leave) {
        boolean save = this.save(leave);
        if (save) {
            resultData.setFlag("leave_succeed");
        } else {
            resultData.setFlag("leave_defeat");
        }
        return resultData;
    }

    @Override
    public ResultData leaveList(String userId) {
        QueryWrapper<Holiday> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Holiday> list = this.list(wrapper);
        resultData.setFlag("leave_succeed");
        resultData.setData(list);
        return resultData;
    }

    @Override
    public ResultData auditList() {
        QueryWrapper<Holiday> wrapper = new QueryWrapper<>();
        wrapper.eq("state", "待审核");
        List<Holiday> list = this.list(wrapper);
        resultData.setData(list);
        resultData.setFlag("audit_succeed");
        return resultData;
    }

    @Override
    public ResultData changeState(Holiday holiday) {
        UpdateWrapper<Holiday> wrapper = new UpdateWrapper<>();
        wrapper.set("state", holiday.getState()).eq("id", holiday.getId());
        boolean update = this.update(wrapper);
        if (update) {
            resultData.setFlag("change_state_succeed");
        } else {
            resultData.setFlag("change_state_defeat");
        }
        return resultData;
    }


}
