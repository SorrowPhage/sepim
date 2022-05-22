package com.sepim.springboot.entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Component
public class Email {
    //    邮件接收方
    private String tos;
    //    邮件主题
    private String subject;
    //    邮件内容
    private String content;
}
