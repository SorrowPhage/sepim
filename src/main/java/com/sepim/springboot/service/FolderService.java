package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface FolderService extends IService<Folder> {
    public ResultData saveMd(Folder folder);

    public ResultData editMd(Folder folder);

    public ResultData getMdList(String id);

    public ResultData readMd(String id);

    public ResultData readMdContent(String id);

    public ResultData uploadMdImg(MultipartFile file);

    public ResultData deleteImgUrl(String url);

    public ResultData deleteMd(String id);

    public ResultData recommendMd();

    public ResultData getPublicFolders(String id);

    public ResultData getRankList();

    public ResultData overview(User user);

    public ResultData searchRep(String q);
}
