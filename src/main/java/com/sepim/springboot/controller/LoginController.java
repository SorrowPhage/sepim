package com.sepim.springboot.controller;

import com.sepim.springboot.entity.Email;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.EmailService;
import com.sepim.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    private final EmailService emailService;

    /**
     * 用户发送验证码
     * @param email   前端传的邮箱账号
     * @return 返回发送结果
     */
    @PostMapping("/sendcode")
    public ResultData sendVerCode(@RequestBody Email email) {
        return emailService.sendVerCode(email);
    }

    /**
     * 用户注册，需要先判断验证码是否正确
     * @param user 用户数据
     * @return 返回用户注册结果
     */
    @PostMapping("/enrol")
    public ResultData register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 用户登录
     * @param user 用户登录数据
     * @return 返回用户登录结果
     */
    @PostMapping("/login")
    public ResultData login(@RequestBody User user, HttpServletRequest request) {
        return userService.login(user,request);
    }

    /**
     * 找回密码，
     * @param user 用户数据
     * @return 返回结果
     */
    @PostMapping("/retrieve")
    public ResultData retrieve(@RequestBody User user) {
        return userService.retrieve(user);
    }

    /**
     * 用户修改密码，
     * @param user 用户数据
     * @return 返回修改结果
     */
    @PostMapping("/change")
    public ResultData changePassword(@RequestBody User user) {
        return userService.changePassword(user);
    }
}
