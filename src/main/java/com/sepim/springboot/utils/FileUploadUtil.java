package com.sepim.springboot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 功能描述：上传图片的工具类
 *
 * @author: phage
 */
@Slf4j
public class FileUploadUtil {
    //图片上传的路径
    private static final String BASE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\avatar\\";

    //图片的访问路径
    private static final String SERVER_PATH = "http://localhost:8088/upload/sepim/avatar/";

    private static final String MD_IMG_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\md\\img\\";

    private static final String SERVER_MD_IMG_PATH = "http://localhost:8088/upload/sepim/md/img/";

    //音乐上传路径
    private static final String MUSIC_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\music\\";

    //音乐访问路径
    private static final String SERVER_MUSIC_PATH = "http://localhost:8088/upload/sepim/music/";

    //歌单图片上传路径
    private static final String MUSIC_LIST_PICTURE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\music\\picture\\";

    //歌单图片访问路径
    private static final String SERVE_MUSIC_LIST_PICTURE_PATH = "http://localhost:8088/upload/sepim/music/picture/";

    private static final String LRC_BASE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\music\\lrc\\";

    private static final String LRC_SERVE_PATH = "http://localhost:8088/upload/sepim/music/lrc/";

    private static final String FACE_BASE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\face\\";

    private static final String FARE_BASE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\fare\\";

    private static final String FACE_SERVER_PATH = "http://localhost:8088/upload/sepim/face/";

    public static final String PRED_BASE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\file\\";

    public static final String PRED_SERVER_PATH = "http://localhost:8088/upload/sepim/file/";

    /**
     * 用户上传本地文章
     * @param file 文件
     * @param url 上传地址
     * @return 上传结果
     */
    public static Boolean storeMd(MultipartFile file, String url) {
        File file1 = new File(url);
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }











    public static String uploadPred(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        //使用uuid保证图片名字的唯一
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String newFile = uuid + originalFilename;

        File image = new File(PRED_BASE_PATH, newFile);
        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PRED_SERVER_PATH + newFile;
    }

    public static void deletePred(String imgUrl) {
        if (imgUrl == null) {
            return;
        }
        String deleteUrl = imgUrl.replace(PRED_SERVER_PATH, PRED_BASE_PATH);
        File file = new File(deleteUrl);
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * 上传文件
     * @param file 文件
     * @return 上传结果
     */
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

    /**
     * 上传人脸
     * @param file 人脸
     * @return 返回上传url
     */
    public static String uploadFace(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        //使用uuid保证图片名字的唯一
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String newFile = uuid + originalFilename;

        File image = new File(FACE_BASE_PATH, newFile);
        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FACE_SERVER_PATH + newFile;
    }

    /**
     * 验证人脸
     * @param file 人脸
     * @return 返回识别url
     */
    public static String uploadFare(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        //使用uuid保证图片名字的唯一
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String newFile = uuid + originalFilename;

        File image = new File(FARE_BASE_PATH, newFile);
        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FARE_BASE_PATH + newFile;
    }
    public static void deleteFare(String imgUrl) {
        if (imgUrl == null) {
            return;
        }
        File file = new File(imgUrl);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void deleteFaceUrl(String imgUrl) {
        if (imgUrl == null) {
            return;
        }
        String deleteUrl = imgUrl.replace(FACE_SERVER_PATH, FACE_BASE_PATH);
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

    /**
     * 删除文章
     *
     * @param url 文章地址
     * @return 返回删除结果
     */
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

    public static String uploadMusic(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        //使用uuid保证图片名字的唯一
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String newFile = uuid + originalFilename;

        File image = new File(MUSIC_PATH, newFile);
        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SERVER_MUSIC_PATH + newFile;
    }


    //这是删除图片的方法，之前用的方法会占用更多的空间
    public static boolean deleteMusicListPicture(String servePath) {
        if (servePath == null) {
            return false;
        }
        String deleteUrl = servePath.replace(SERVE_MUSIC_LIST_PICTURE_PATH,MUSIC_LIST_PICTURE_PATH);
        File file = new File(deleteUrl);
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }

    }

    public static String uploadMusicListPicture(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        //使用uuid保证图片名字的唯一
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String newFile = uuid + originalFilename;

        File image = new File(MUSIC_LIST_PICTURE_PATH, newFile);
        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SERVE_MUSIC_LIST_PICTURE_PATH + newFile;
    }

    public static String readMusicLrc(String url) {
        String encoding = "UTF-8";
        String fileStr = url.replace(LRC_SERVE_PATH,LRC_BASE_PATH);
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

}