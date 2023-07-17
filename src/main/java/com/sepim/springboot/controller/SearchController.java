package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.FolderService;
import com.sepim.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private ResultData resultData;

    @GetMapping("/search/user")
    public ResultData searchUser(@RequestParam("q") String q) {
        resultData = userService.searchUser(q);
        return resultData;
    }


    /**
     * 搜索文章
     * @param q
     * @return
     */
    @GetMapping("/search/rep")
    public ResultData searchRep(@RequestParam("q") String q) {
        resultData = folderService.searchRep(q);
        return resultData;
    }
}
