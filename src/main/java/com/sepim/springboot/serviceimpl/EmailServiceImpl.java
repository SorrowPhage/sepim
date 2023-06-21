package com.sepim.springboot.serviceimpl;


import com.sepim.springboot.entity.Email;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.EmailService;
import com.sepim.springboot.utils.MySessionUtil;
import com.sepim.springboot.utils.StringRedisUtils;
import com.sepim.springboot.utils.VerCodeGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 发送验证码
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ResultData resultData;

    @Autowired
    private StringRedisUtils stringRedisUtils;


    @Value("${spring.mail.username}")
    private String username;

    @Override
    public ResultData sendVerCode(Email email) {
        //生成验证码
        String verCode = VerCodeGenerateUtil.generateVerCode();
        //设置邮件发送内容
        email.setContent("验证码：" + verCode);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email.getTos());
        message.setSubject(email.getSubject());
        message.setText(email.getContent());
        javaMailSender.send(message);

        MySessionUtil.setSession("ver_code", verCode);

        stringRedisUtils.addRedisTime(email.getTos(), verCode);

        resultData.setFlag("send_succeed");
        return resultData;
    }
}
