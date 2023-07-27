package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.SearchCondition;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.mapper.UserMapper;
import com.sepim.springboot.service.UserService;
import com.sepim.springboot.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private final StringRedisUtils stringRedisUtils;

    private final UserMapper userMapper;



    /**
     * 用户注册
     * @param user 用户上传的注册信息
     * @return 注册结果
     */
    public ResultData register(User user) {
        ResultData resultData = new ResultData();
        String verCode = stringRedisUtils.getRedis(user.getEmail());
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
            // user.setType("user");
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
        ResultData resultData = new ResultData();
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
        ResultData resultData = new ResultData();
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
        ResultData resultData = new ResultData();
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
        ResultData resultData = new ResultData();
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

    /**
     * 用户信息更新
     * @param user 用户信息
     * @return 返回更新结果
     */
    @Override
    public ResultData userInfoUpdate(User user) {
        ResultData resultData = new ResultData();
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

    /**
     * 修改用户密码
     * @param user 用户信息
     * @return 返回更新结果
     */
    @Override
    public ResultData userUpdatePassword(User user) {
        ResultData resultData = new ResultData();
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

    /**
     * 获取用户信息
     * @param id 用户id
     * @return 返回结果
     */
    @Override
    public ResultData getUser(String id) {
        ResultData resultData = new ResultData();
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


    /**
     * 获取用户的readme文档
     * @param user 用户id
     * @return 获取结果
     */
    @Override
    public ResultData readme(User user) {
        ResultData resultData = new ResultData();
        User byId = this.getById(user.getId());
        if (byId == null ) {
            resultData.setFlag("readme_defeat");
            resultData.setData(null);
        } else if (byId.getOverviewMdUrl() == null) {
            byId.setMdContent(null);
            resultData.setFlag("200");
            resultData.setData(byId);
        } else {
            byId.setMdContent(FileUploadUtil.readMdFile(byId.getOverviewMdUrl()));
            resultData.setFlag("200");
            resultData.setData(byId);
        }
        return resultData;
    }

    /**
     * 搜索用户
     * @param condition 分页查询信息
     * @return 搜索结果
     */
    @Override
    public ResultData searchUser(SearchCondition condition) {
        log.info(condition.toString());
        ResultData resultData = new ResultData();
        Page<User> page = new Page<>();
        page.setSize(condition.getPageSize());
        page.setCurrent(condition.getPageIndex());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("username", condition.getTitle());

        Page<User> page1 = userMapper.selectPage(page, wrapper);
        // List<User> list = this.list(wrapper);
        if (page1 != null) {
            resultData.setFlag("200");
            resultData.setData(page1);
        } else {
            resultData.setFlag("400");
            resultData.setData(null);
        }
        return resultData;
    }

    /**
     * 上传人脸
     * @param file 人脸
     * @param id 用户id
     * @return 返回上传结果
     */
    @Override
    public ResultData uploadFaceImage(MultipartFile file, String id) {
        ResultData resultData = new ResultData();
        if (file != null) {
            String face_Url = FileUploadUtil.uploadFare(file);
            String s = FareUtils.checkFace(face_Url);
            if (s!=null) {
                FileUploadUtil.delete(face_Url);
                resultData.setFlag("300");
                resultData.setData(null);
            } else {
                //删除原来的图像
                String old_avatar = this.getById(id).getFaceUrl();
                FileUploadUtil.deleteFaceUrl(old_avatar);

                //上传图像文件到服务器
                String faceUrl = FileUploadUtil.uploadFace(file);

                //修改用户新的图像访问路径
                UpdateWrapper<User> wrapper = new UpdateWrapper<>();
                wrapper.set("face_url", faceUrl).eq("id", id);
                boolean update = this.update(wrapper);
                if (update) {
                    resultData.setFlag("200");
                    resultData.setData(faceUrl);
                } else {
                    resultData.setFlag("400");
                    resultData.setData(null);
                }
            }
        } else {
            resultData.setFlag("400");
            resultData.setData(null);
        }
        return resultData;
    }
}
