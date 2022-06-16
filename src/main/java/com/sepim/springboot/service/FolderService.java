package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.ResultData;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FolderService extends IService<Folder> {
    public ResultData saveMd(Folder folder);

    public ResultData getMdList(String id);

    public ResultData readMd(String id);

    public ResultData uploadMdImg(MultipartFile file);

    public ResultData deleteImgUrl(String url);

    public ResultData deleteMd(String id);

    public ResultData recommendMd();

    public ResultData getPublicFolders(String id);
}
