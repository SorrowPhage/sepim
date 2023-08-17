package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.SearchCondition;
import com.sepim.springboot.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FolderService extends IService<Folder> {
    ResultData saveMd(Folder folder);

    ResultData editMd(Folder folder);

    ResultData queryBYCondition(SearchCondition condition);

    ResultData getMdList(String id);

    ResultData readMd(String id);

    ResultData readMdContent(String id);

    ResultData uploadMdImg(MultipartFile file);

    ResultData deleteImgUrl(String url);

    ResultData deleteMd(String id);

    ResultData getRankList();

    ResultData overview(User user);

    ResultData searchRep(String q);

    ResultData storeMd(MultipartFile file, Folder folder);

    ResultData getPublicFolders(String userId);

    ResultData getEchartsData(Map<String, String> param);

}
