package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.FareService;
import com.sepim.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    private final FareService fareService;


    /**
     * 用户修改头像
     * @param file 用户头像
     * @param id 用户id
     * @return 返回修改结果
     */
    @PostMapping("/avatar")
    public ResultData settingAvatar(@RequestParam(value = "file") MultipartFile file,
                                    @RequestParam(value = "id") String id) {
        return userService.settingAvatar(file, id);
    }

    /**
     * 用户修改个人信息
     * @param user 用户个人信息
     * @return 返回修改结果
     */
    @PostMapping("/update")
    public ResultData userInfoUpdate(@RequestBody User user) {
        return userService.userInfoUpdate(user);
    }

    /**
     * 用户修改密码
     * @param user 用户修改密码的信息：用户id，用户新密码，旧密码
     * @return 修改结果
     */
    @PostMapping("/update/password")
    public ResultData userUpdatePassword(@RequestBody User user) {
        return userService.userUpdatePassword(user);
    }

    /**
     * 用户上传人脸绑定账号
     * @param file 用户人脸
     * @param id 用户id
     * @return 上传结果
     */
    @PostMapping("/user/face")
    public ResultData uploadFaceImage(@RequestParam(value = "file") MultipartFile file,
                                      @RequestParam(value = "id") String id) {
        return userService.uploadFaceImage(file, id);
    }

    /**
     * 用户人脸识别登录
     * @param file 登录时的人脸
     * @return 登录结果
     */
    @PostMapping("/user/fare")
    public ResultData faceLogin(@RequestParam(value = "file") MultipartFile file) {
        return fareService.fare(file);
    }
}
