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

    //音乐上传路径
    private static final String MUSIC_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\music\\";

    //音乐访问路径
    private static final String SERVER_MUSIC_PATH = "http://localhost:8088/upload/sepim/music/";

    //歌单图片上传路径
    private static final String MUSIC_LIST_PICTURE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\music\\picture\\";

    //歌单土拍你访问路径
    private static final String SERVE_MUSIC_LIST_PICTURE_PATH = "http://localhost:8088/upload/sepim/music/picture/";

    private static final String LRC_BASE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\music\\lrc\\";

    private static final String LRC_SERVE_PATH = "http://localhost:8088/upload/sepim/music/lrc/";

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