package com.sepim.springboot.controller;

import com.sepim.springboot.entity.Folder;
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
     * 访问其它用户时获取该用户的公告文章列表
     * @param folder 用户
     * @return 返回数据
     */
    @PostMapping("/user/center/files")
    public ResultData getPublicFolders(@RequestBody Folder folder) {
        return folderService.getPublicFolders(folder.getUserId());
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
