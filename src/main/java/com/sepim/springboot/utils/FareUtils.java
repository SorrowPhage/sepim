package com.sepim.springboot.utils;


import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @author: SorrowPhage
 * @date: 2023/6/21
 */
@Slf4j
public class FareUtils {
    public static String checkFace(String faceUrl){
        // 输入id和key
        String appId = "9irKT9Ks7iVsUVEw2LY7UuRxGBgZM3S63oZacFFTMMHK";
        String sdkKey = "9uqG5395qu3qsX1jKGJ4Rik9sFEhnCAsAFPu5MdZoDgT";

        // 引擎sdk存放位置
        FaceEngine faceEngine = new FaceEngine("D:\\A-SpringBoot\\sepim\\lib\\face\\WIN64");

        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.info("引擎激活失败");
        }
        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.info("获取激活文件信息失败");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.info("初始化引擎失败");
        }

        //人脸检测，这里我从图片的缓存库中获取文件，一般要处理的那张图片我会放在缓存库中，缓存库在			项目的face_data_cache文件夹下
        File fileInput  = new File("E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\face");
        File[] filesInput=fileInput.listFiles();

        //测试的图片，获取缓存库的第一张照片
        // String fileName = "D:\\A-SpringBoot\\sepim\\sepim_py\\data\\test\\1.png";

        log.info(faceUrl);
        ImageInfo imageInfo = getRGBData(new File(faceUrl));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        System.out.println(faceInfoList);

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        try {
            errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        }catch (Exception e){
            return null;
        }
        log.info("提交图片的特征值大小：" + faceFeature.getFeatureData().length);

        //遍历人脸数据库
        File file  = new File("E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\face");

        File[] files=file.listFiles();

        log.info("图片总数:" + files.length);
        //我这里使用简单的for循环，数据库过大考虑用分布式
        for(int i=0;i<files.length;i++) {
            System.out.println(files[i].getName());

            //System.getProperty("user.dir");
            //人脸检测2
            ImageInfo imageInfo2 = getRGBData(new File("E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\face\\"+files[i].getName()));
            //"C:/Users/Administrator/Desktop/ArcSoft_ArcFace_Java_Windows_x64_V3.0/photo/"+files[i].getName()));
            List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
            errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2);
            log.info(String.valueOf(faceInfoList));

            //特征提取2
            FaceFeature faceFeature2 = new FaceFeature();
            errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2.get(0), faceFeature2);
            log.info(i+"图片特征值大小：" + faceFeature.getFeatureData().length);

            //特征比对
            FaceFeature targetFaceFeature = new FaceFeature();
            targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
            FaceFeature sourceFaceFeature = new FaceFeature();
            sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
            FaceSimilar faceSimilar = new FaceSimilar();

            errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

            log.info(i+"与该图片相似度：" + faceSimilar.getScore());
            if (faceSimilar.getScore()>=0.85){
                return files[i].getName();
            }
        }


        //设置活体测试
        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);

        //人脸属性检测
        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);

        //性别检测
        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
        errorCode = faceEngine.getGender(genderInfoList);
        System.out.println("该测试性别：" + genderInfoList.get(0).getGender());

        //年龄检测
        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
        errorCode = faceEngine.getAge(ageInfoList);
        System.out.println("年龄：" + ageInfoList.get(0).getAge());

        //3D信息检测
        List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
        errorCode = faceEngine.getFace3DAngle(face3DAngleList);
        System.out.println("3D角度：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());

        //引擎卸载
        errorCode = faceEngine.unInit();
        return null;
    }
}
