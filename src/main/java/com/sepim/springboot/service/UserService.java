package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.SearchCondition;
import com.sepim.springboot.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {

    ResultData register(User user);

    ResultData login(User user);

    ResultData retrieve(User user);

    ResultData changePassword(User user);

    ResultData settingAvatar(MultipartFile file, String id);

    ResultData userInfoUpdate(User user);

    ResultData userUpdatePassword(User user);

    ResultData getUser(String id);

    ResultData readme(User user);

    ResultData searchUser(SearchCondition condition);

    ResultData uploadFaceImage(MultipartFile file, String id);
}
