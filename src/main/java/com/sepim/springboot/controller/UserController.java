package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.FareService;
import com.sepim.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResultData resultData;

    @Autowired
    private FareService fareService;


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

    @PostMapping("/user/face")
    public ResultData uploadFaceImage(@RequestParam(value = "file") MultipartFile file,
                                      @RequestParam(value = "id") String id) {
        return userService.uploadFaceImage(file, id);
    }

    @PostMapping("/user/fare")
    public ResultData faceLogin(@RequestParam(value = "file") MultipartFile file) {
        return fareService.fare(file);
    }
}
