package com.sepim.springboot.controller;

import com.sepim.springboot.entity.Holiday;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private ResultData resultData;

    @PostMapping("/leave")
    public ResultData leave(@RequestBody Holiday leave) {
        resultData = holidayService.leave(leave);
        return resultData;
    }


    @PostMapping("/leaves")
    public ResultData leaveList(@RequestBody Holiday holiday) {
        resultData = holidayService.leaveList(holiday.getUserId());
        return resultData;
    }

    @PostMapping("/audits")
    public ResultData auditList() {
        resultData = holidayService.auditList();
        return resultData;
    }

    @PostMapping("/cstate")
    public ResultData changeState(@RequestBody Holiday holiday) {
        resultData = holidayService.changeState(holiday);
        return resultData;
    }
}
