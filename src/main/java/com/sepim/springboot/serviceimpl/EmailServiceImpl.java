package com.sepim.springboot.serviceimpl;


import com.sepim.springboot.entity.Email;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.EmailService;
import com.sepim.springboot.utils.MySessionUtil;
import com.sepim.springboot.utils.StringRedisUtils;
import com.sepim.springboot.utils.VerCodeGenerateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 发送验证码
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;


    private final StringRedisUtils stringRedisUtils;


    @Value("${spring.mail.username}")
    private String username;

    /**
     * 发送验证码
     * @param email 接收方验证码
     * @return 返回发送结果
     */
    @Override
    public ResultData sendVerCode(Email email) {
        ResultData resultData = new ResultData();
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
        //将验证嘛存到redis中，并设置5分钟的过期时间
        stringRedisUtils.addRedisTime(email.getTos(), verCode);

        resultData.setFlag("send_succeed");
        return resultData;
    }
}
