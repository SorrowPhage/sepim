package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.SearchCondition;
import com.sepim.springboot.service.FolderService;
import com.sepim.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SearchController {


    private final UserService userService;

    private final FolderService folderService;


    /**
     * 搜索用户
     *
     * @param q 用户名
     * @return 搜索结果
     */
    @PostMapping("/search/user")
    public ResultData searchUser(@RequestBody SearchCondition condition) {
        return userService.searchUser(condition);
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
