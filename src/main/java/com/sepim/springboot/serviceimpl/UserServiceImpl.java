package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.mapper.UserMapper;
import com.sepim.springboot.service.UserService;
import com.sepim.springboot.utils.AccountGenerateUtil;
import com.sepim.springboot.utils.SettingAvatarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private ResultData resultData;

    @Autowired
    private MySessionService mySessionService;


    /**
     * 用户注册
     * @param user 用户上传的注册信息
     * @return 注册结果
     */
    public ResultData register(User user) {
        String verCode = (String) mySessionService.getSession("ver_code");
        if (!verCode.equals(user.getVerCode())) {
            resultData.setFlag("ver_defeat");
        } else {
            user.setId(AccountGenerateUtil.generateAccount());
            mySessionService.removeSession("ver_code");
            user.setType("user");
            this.save(user);
            resultData.setData(user);
            resultData.setFlag("succeed");
        }
        return resultData;
    }

    /**
     * 用户登录
     * @param user 用户登录上传的信息
     * @return 用户登录结果
     */
    public ResultData login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", user.getId()).eq("password", user.getPassword());
        User admin = this.getOne(wrapper);
        if (admin != null) {
            resultData.setFlag("login_succeed");
            resultData.setData(admin);
        } else {
            resultData.setFlag("login_defeat");
        }
        return resultData;
    }

    /**
     * 用户验证身份信息
     * @param user 用户上传的信息
     * @return 验证结果
     */
    public ResultData retrieve(User user) {
        String verCode = (String) mySessionService.getSession("ver_code");
        if (!verCode.equals(user.getVerCode())) {
            resultData.setFlag("ver_defeat");
        } else {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", user.getId()).eq("email", user.getEmail());
            User admin = this.getOne(wrapper);
            if (admin != null) {
                resultData.setFlag("succeed");
                resultData.setData(admin);
                mySessionService.removeSession("ver_code");
            } else {
                resultData.setFlag("ver_status_defeat");
            }
        }
        return resultData;
    }

    /**
     * 改变密码
     * @param user 用户改变密码上传的用户信息
     * @return 改变密码结果
     */
    public ResultData changePassword(User user) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("password", user.getPassword()).eq("id", user.getId());
        boolean tag = this.update(wrapper);
        if (tag) {
            resultData.setFlag("succeed");
        } else {
            resultData.setFlag("defeat");
        }
        return resultData;
    }

    /**
     * 用户修改图像
     * @param file 用户上传的图像文件
     * @param id  用户的id
     * @return 修改结果
     */
    public ResultData settingAvatar(MultipartFile file, String id) {
        if (file != null) {
            //删除原来的图像
            String old_avatar = this.getById(id).getAvatarUrl();
            SettingAvatarUtil.delete(old_avatar);

            //上传图像文件到服务器
            String avatarUrl = SettingAvatarUtil.upload(file);

            //修改用户新的图像访问路径
            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.set("avatar_url", avatarUrl).eq("id", id);
            boolean update = this.update(wrapper);
            if (update) {
                resultData.setFlag("avatar_set_succeed");
                resultData.setData(avatarUrl);
            } else {
                resultData.setFlag("avatar_set_defeat");
            }
        } else {
            resultData.setFlag("avatar_set_null");
        }
        return resultData;
    }

    @Override
    public ResultData userInfoUpdate(User user) {
        this.saveOrUpdate(user);
        resultData.setFlag("user_info_update_succeed");
        resultData.setData(this.getById(user.getId()));
        return resultData;
    }

    @Override
    public ResultData userUpdatePassword(User user) {
        if (this.getById(user.getId()).getPassword().equals(user.getOldPassword())) {
            this.saveOrUpdate(user);
            resultData.setFlag("user_info_update_password_succeed");
        } else {
            resultData.setFlag("user_info_update_password_defeat");
        }
        return resultData;
    }
}
