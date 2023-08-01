package com.sepim.springboot.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.FareService;
import com.sepim.springboot.service.UserService;
import com.sepim.springboot.utils.FareUtils;
import com.sepim.springboot.utils.FileUploadUtil;
import com.sepim.springboot.utils.MyTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: SorrowPhage
 * @date: 2023/6/21
 */
@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {

    private final UserService userService;


    /**
     * 人脸识别
     * 上传人脸图片，在人脸图片库中查找形似的人脸的图片名字，若找到，则说明人脸识别成功，可以进行登录
     * 识别过程：先将图片上传，人脸识别工具通过url识别图片库中相似度在0.85以上的，若找到，通过图片地
     * 址查出对应的用户，返回登录数据
     * @param file 人脸图片
     * @return 返回识别结果与登录数据
     */
    @Override
    public ResultData fare(MultipartFile file) {
        ResultData resultData = new ResultData();
        String faceUrl = FileUploadUtil.uploadFare(file);
        String s = FareUtils.checkFace(faceUrl);
        if (s != null) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("face_url", "http://localhost:8088/upload/sepim/face/" + s);
            User one = userService.getOne(queryWrapper);
            one.setToken(MyTokenUtil.getToken(one));
            one.setPassword(null);
            resultData.setFlag("200");
            resultData.setData(one);
        } else {
            resultData.setFlag("400");
            resultData.setData(null);
        }
        FileUploadUtil.deleteFare(faceUrl);
        return resultData;
    }
}
