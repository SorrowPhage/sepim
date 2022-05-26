package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {

    public ResultData register(User user);

    public ResultData login(User user);

    public ResultData retrieve(User user);

    public ResultData changePassword(User user);

    public ResultData settingAvatar(MultipartFile file, String id);

    public ResultData userInfoUpdate(User user);

    public ResultData userUpdatePassword(User user);
}
