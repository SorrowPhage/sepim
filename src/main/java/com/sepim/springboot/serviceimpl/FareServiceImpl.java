package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.FareService;
import com.sepim.springboot.service.UserService;
import com.sepim.springboot.utils.FareUtils;
import com.sepim.springboot.utils.FileUploadUtil;
import com.sepim.springboot.utils.MyTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/6/21
 */
@Service
public class FareServiceImpl implements FareService {

    @Autowired
    private UserService userService;

    @Autowired
    private ResultData resultData;

    @Override
    public ResultData fare(MultipartFile file) {
        String faceUrl = FileUploadUtil.uploadFare(file);
        String s = FareUtils.checkFace(faceUrl);
        if (s != null) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("face_url", "http://localhost:8088/upload/sepim/face/" + s);
            // List<User> list = userService.list(queryWrapper);
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
