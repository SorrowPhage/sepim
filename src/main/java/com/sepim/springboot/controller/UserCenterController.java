package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.FolderService;
import com.sepim.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserCenterController {

    private final UserService userService;

    private final FolderService folderService;

    /**
     * 获取用户信息
     * @param user 得到用户id
     * @return 返回获取结果
     */
    @PostMapping("/user/center/get")
    public ResultData getUser(@RequestBody User user) {
        return userService.getUser(user.getId());
    }



    /**
     * 修改用户的描述文件
     * @param user 用户的信息，文件的内容
     * @return 返回获取结果
     */
    @PostMapping("/md/overview")
    public ResultData overview(@RequestBody User user) {
        return folderService.overview(user);
    }

    /**
     * 获取readme文档
     * @param user 用户的id
     * @return 返回获取结果
     */
    @PostMapping("/md/readme")
    public ResultData readme(@RequestBody User user) {
        return userService.readme(user);
    }
}
