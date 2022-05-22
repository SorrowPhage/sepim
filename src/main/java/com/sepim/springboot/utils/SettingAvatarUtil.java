package com.sepim.springboot.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 功能描述：上传图片的工具类
 *
 * 作者：phage
 */
public class SettingAvatarUtil {
    //图片上传的路径
    private static final String BASE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\avatar\\";

    //图片的访问路径
    private static final String SERVER_PATH = "http://localhost:8088/upload/sepim/avatar/";

    public static String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        //使用uuid保证图片名字的唯一
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String newFile = uuid + originalFilename;

        File image = new File(BASE_PATH, newFile);
        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SERVER_PATH + newFile;
    }

    public static void delete(String imgUrl) {
        if (imgUrl == null) {
            return;
        }
        String deleteUrl = imgUrl.replace(SERVER_PATH, BASE_PATH);
        File file = new File(deleteUrl);
        if (file.exists()) {
            file.delete();
        }
    }
}
