package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResultData resultData;


    @PostMapping("/avatar")
    public ResultData settingAvatar(@RequestParam(value = "file") MultipartFile file,
                                    @RequestParam(value = "id") String id) {
        resultData = userService.settingAvatar(file, id);
        return resultData;
    }

    @PostMapping("/update")
    public ResultData userInfoUpdate(@RequestBody User user) {
        resultData = userService.userInfoUpdate(user);
        return resultData;
    }

    @PostMapping("/update/password")
    public ResultData userUpdatePassword(@RequestBody User user) {
        resultData = userService.userUpdatePassword(user);
        return resultData;
    }
}
