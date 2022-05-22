package com.sepim.springboot;

import com.sepim.springboot.entity.Email;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.EmailService;
import com.sepim.springboot.service.UserService;
import com.sepim.springboot.serviceimpl.MySessionService;
import com.sepim.springboot.utils.AccountGenerateUtil;
import com.sepim.springboot.utils.VerCodeGenerateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SepimApplicationTests {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        Email email = new Email();
        email.setTos("2250270287@qq.com");
        email.setSubject("注册");
        email.setContent("验证码：" + VerCodeGenerateUtil.generateVerCode());
        System.out.println(email.getTos());
        System.out.println(email.getContent());
        System.out.println(emailService);
        // emailService.send(email);
    }

    @Test
    void testRegister() {
        User user = new User();
        user.setId (AccountGenerateUtil.generateAccount());
        user.setUsername("sorrow");
        user.setPassword("12346");
        user.setType("user");
        userService.save(user);
    }

    @Autowired
    private MySessionService mySessionService;

    @Test
    void testSession() {
        User user = new User();
        mySessionService.setSession("key", user);
        System.out.println(mySessionService.getSession("key"));
    }
}
