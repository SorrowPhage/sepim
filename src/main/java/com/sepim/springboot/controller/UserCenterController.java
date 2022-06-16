package com.sepim.springboot.controller;

import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.FolderService;
import com.sepim.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCenterController {
    @Autowired
    private UserService userService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private ResultData resultData;

    @PostMapping("/user/center/get")
    public ResultData getUser(@RequestBody User user) {
        resultData = userService.getUser(user.getId());
        return resultData;
    }

    @PostMapping("/user/center/files")
    public ResultData getPublicFolders(@RequestBody Folder folder) {
        resultData = folderService.getPublicFolders(folder.getUserId());
        return resultData;
    }
}
