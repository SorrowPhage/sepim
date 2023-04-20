package com.sepim.springboot.serviceimpl;

import com.sepim.springboot.entity.Email;
import com.sepim.springboot.service.EmailService;
import com.sepim.springboot.service.MusicListService;
import com.sepim.springboot.utils.VerCodeGenerateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailTest {
    @Autowired
    private EmailServiceImpl emailService;

    @Test
    void test() {
        Email email = new Email();
        email.setTos("2250270287@qq.com");
        email.setSubject("注册");
        email.setContent("验证码：" + VerCodeGenerateUtil.generateVerCode());
        System.out.println(email.getTos());
        System.out.println(email.getContent());
        System.out.println(emailService);
        // emailService.send(email);
    }





}
