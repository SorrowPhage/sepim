package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.UserFollowsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/8/17
 */
@RestController
@RequiredArgsConstructor
public class UserFollowsController {

    private final UserFollowsService userFollowsService;

    @PostMapping("/uf/follow")
    public ResultData follow(@RequestBody Map<String, String> param) {
        return userFollowsService.follow(param);
    }

    @PostMapping("/uf/unfollow")
    public ResultData unfollow(@RequestBody Map<String, String> param) {
        return userFollowsService.unfollow(param);
    }

    @PostMapping("/uf/list-follow")
    public ResultData followList(@RequestBody Map<String, String> param) {
        return userFollowsService.followList(param);
    }

    @PostMapping("/uf/list-fans")
    public ResultData fansList(@RequestBody Map<String, String> param) {
        return userFollowsService.fansList(param);
    }

    @PostMapping("/uf/num-fans")
    public ResultData fansNums(@RequestBody Map<String, String> param) {
        return userFollowsService.getFansNums(param);
    }

    @PostMapping("/uf/num-follow")
    public ResultData followNums(@RequestBody Map<String, String> param) {
        return userFollowsService.getFollowNUms(param);
    }

    @PostMapping("/uf/status")
    public ResultData getStatus(@RequestBody Map<String, String> param) {
        return userFollowsService.getFollowStatus(param);
    }
}
