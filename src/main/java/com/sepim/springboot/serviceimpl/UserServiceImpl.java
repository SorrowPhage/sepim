package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.mapper.UserMapper;
import com.sepim.springboot.service.UserService;
import com.sepim.springboot.utils.AccountGenerateUtil;
import com.sepim.springboot.utils.MySessionUtil;
import com.sepim.springboot.utils.MyTokenUtil;
import com.sepim.springboot.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private ResultData resultData;



    /**
     * 用户注册
     * @param user 用户上传的注册信息
     * @return 注册结果
     */
    public ResultData register(User user) {
        String verCode = (String) MySessionUtil.getSession("ver_code");
        if (!verCode.equals(user.getVerCode())) {
            resultData.setFlag("ver_defeat");
            resultData.setData(null);
        } else {
            String account = AccountGenerateUtil.generateAccount();
            //保证账号的唯一性
            while (this.getById(account) != null) {
                account = AccountGenerateUtil.generateAccount();
            }
            user.setId(AccountGenerateUtil.generateAccount());
            MySessionUtil.removeSession("ver_code");
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
            //生成token
            admin.setToken(MyTokenUtil.getToken(admin));
            resultData.setFlag("login_succeed");
            admin.setPassword(null);
            resultData.setData(admin);
        } else {
            resultData.setFlag("login_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    /**
     * 用户验证身份信息
     * @param user 用户上传的信息
     * @return 验证结果
     */
    public ResultData retrieve(User user) {
        String verCode = (String) MySessionUtil.getSession("ver_code");
        if (!verCode.equals(user.getVerCode())) {
            resultData.setFlag("ver_defeat");
            resultData.setData(null);
        } else {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", user.getId()).eq("email", user.getEmail());
            User admin = this.getOne(wrapper);
            if (admin != null) {
                resultData.setFlag("succeed");
                resultData.setData(admin);
                MySessionUtil.removeSession("ver_code");
            } else {
                resultData.setFlag("ver_status_defeat");
                resultData.setData(null);
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
            resultData.setData(null);
        } else {
            resultData.setFlag("defeat");
            resultData.setData(null);
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
            FileUploadUtil.delete(old_avatar);

            //上传图像文件到服务器
            String avatarUrl = FileUploadUtil.upload(file);

            //修改用户新的图像访问路径
            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.set("avatar_url", avatarUrl).eq("id", id);
            boolean update = this.update(wrapper);
            if (update) {
                resultData.setFlag("avatar_set_succeed");
                resultData.setData(avatarUrl);
            } else {
                resultData.setFlag("avatar_set_defeat");
                resultData.setData(null);
            }
        } else {
            resultData.setFlag("avatar_set_null");
            resultData.setData(null);
        }
        return resultData;
    }

    @Override
    public ResultData userInfoUpdate(User user) {
        boolean b = this.saveOrUpdate(user);
        if (b) {
            resultData.setFlag("user_info_update_succeed");
            resultData.setData(this.getById(user.getId()));
        } else {
            resultData.setFlag("user_info_update_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    @Override
    public ResultData userUpdatePassword(User user) {
        if (this.getById(user.getId()).getPassword().equals(user.getOldPassword())) {
            this.saveOrUpdate(user);
            resultData.setFlag("user_info_update_password_succeed");
            resultData.setData(null);
        } else {
            resultData.setFlag("user_info_update_password_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    @Override
    public ResultData getUser(String id) {
        User user = this.getById(id);
        if (user == null) {
            resultData.setFlag("user_center_get_defeat");
            resultData.setData(null);
        } else {
            resultData.setFlag("user_center_get_succeed");
            resultData.setData(user);
        }
        return resultData;
    }

    @Override
    public ResultData readme(User user) {
        User byId = this.getById(user.getId());
        if (byId == null ) {
            resultData.setFlag("readme_defeat");
            resultData.setData(null);
        } else if (byId.getOverviewUrl() == null) {
            byId.setContent(null);
            byId.setMdContent(null);
            resultData.setFlag("readme_succeed");
            resultData.setData(byId);
        } else {
            byId.setContent(FileUploadUtil.readMdFile(byId.getOverviewUrl()));
            byId.setMdContent(FileUploadUtil.readMdFile(byId.getOverviewMdUrl()));
            resultData.setFlag("readme_succeed");
            resultData.setData(byId);
        }
        return resultData;
    }

    @Override
    public ResultData searchUser(String q) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("username", q);
        List<User> list = this.list(wrapper);
        if (list.size() > 0) {
            resultData.setFlag("search_user_succeed");
            resultData.setData(list);
        } else {
            resultData.setFlag("search_user_defeat");
            resultData.setData(null);
        }
        return resultData;
    }
}
