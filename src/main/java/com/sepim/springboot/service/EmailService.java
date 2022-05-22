package com.sepim.springboot.service;

import com.sepim.springboot.entity.Email;
import com.sepim.springboot.entity.ResultData;


public interface EmailService {
    public ResultData sendVerCode(Email email);
}
