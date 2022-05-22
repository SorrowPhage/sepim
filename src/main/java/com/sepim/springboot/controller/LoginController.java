package com.sepim.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sepim.springboot.entity.Email;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.EmailService;
import com.sepim.springboot.service.UserService;
import com.sepim.springboot.utils.AccountGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@CrossOrigin //解决跨域
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private ResultData resultData;

    @Autowired
    private EmailService emailService;

    /**
     * 用户发送验证码
     *
     * @param email   前端传的邮箱账号
     */
    @PostMapping("/sendcode")
    public ResultData sendVerCode(@RequestBody Email email) {
        resultData = emailService.sendVerCode(email);
        return resultData;
    }

    /**
     * 用户注册，需要先判断验证码是否正确
     * @param user 用户数据
     * @return 返回用户注册结果
     */
    @PostMapping("/enrol")
    public ResultData register(@RequestBody User user) {
        resultData = userService.register(user);
        return resultData;
    }

    /**
     * 用户登录
     * @param user 用户登录数据
     * @return 返回用户登录结果
     */
    @PostMapping("/login")
    public ResultData login(@RequestBody User user) {
        resultData = userService.login(user);
        return resultData;
    }

    /**
     * 找回密码，
     * @param user 用户数据
     * @return 返回结果
     */
    @PostMapping("/retrieve")
    public ResultData retrieve(@RequestBody User user) {
        resultData = userService.retrieve(user);
        return resultData;
    }

    /**
     * 用户修改密码，
     * @param user 用户数据
     * @return 返回修改结果
     */
    @PostMapping("/change")
    public ResultData changePassword(@RequestBody User user) {
        resultData = userService.changePassword(user);
        return resultData;
    }
}
