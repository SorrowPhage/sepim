package com.sepim.springboot.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 功能描述：上传图片的工具类
 *
 * 作者：phage
 */
public class FileUploadUtil {
    //图片上传的路径
    private static final String BASE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\avatar\\";

    //图片的访问路径
    private static final String SERVER_PATH = "http://localhost:8088/upload/sepim/avatar/";

    private static final String MD_IMG_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\md\\img\\";

    private static final String SERVER_MD_IMG_PATH = "http://localhost:8088/upload/sepim/md/img/";



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

    public static boolean uploadMdFile(String content,String absPath) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(absPath);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(content));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char[] buf = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static String readMdFile(String fileStr) {
        String encoding = "UTF-8";
        File file = new File(fileStr);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in=null;
        try {
            in = new FileInputStream(file);
            in.read(filecontent);
            return new String(filecontent, encoding);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static boolean deleteMd(String url) {
        File file = new File(url);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

    public static String uploadMdImg(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        //使用uuid保证图片名字的唯一
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String newFile = uuid + originalFilename;

        File image = new File(MD_IMG_PATH, newFile);
        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SERVER_MD_IMG_PATH + newFile;
    }

    public static void deleteImgUrl(String imgUrl) {
        if (imgUrl == null) {
            return;
        }
        String deleteUrl = imgUrl.replace(SERVER_MD_IMG_PATH,MD_IMG_PATH);
        File file = new File(deleteUrl);
        if (file.exists()) {
            file.delete();
        }
    }

}