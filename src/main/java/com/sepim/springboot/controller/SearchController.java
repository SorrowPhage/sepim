package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.FolderService;
import com.sepim.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {


    private final UserService userService;

    private final FolderService folderService;


    /**
     * 搜索用户
     * @param q 用户名
     * @return 搜索结果
     */
    @GetMapping("/search/user")
    public ResultData searchUser(@RequestParam("q") String q) {
        return userService.searchUser(q);
    }


    /**
     * 搜索文章
     * @param q 文章标题
     * @return 搜索结果
     */
    @GetMapping("/search/rep")
    public ResultData searchRep(@RequestParam("q") String q) {
        return folderService.searchRep(q);
    }
}
