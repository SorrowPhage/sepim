package com.sepim.springboot.serviceimpl;

import com.sepim.springboot.entity.MusicList;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.MusicListMapper;
import com.sepim.springboot.service.MusicListService;
import com.sepim.springboot.utils.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/2/24
 */
@Service
@Slf4j
public class MusicListServiceImpl implements MusicListService {

    @Autowired
    private ResultData resultData;

    @Autowired
    private MusicListMapper mapper;

    @Override
    public ResultData getUserMusicList(String id) {
        List<MusicList> musicLists=mapper.getUserMusicList(id);
        resultData.setData(musicLists);
        resultData.setFlag("get_musiclist_success");
        return resultData;
    }



    @Override
    public ResultData updateMusicListInformation(MultipartFile file, String id,String name, String describe) {
        MusicList musicList = mapper.getMusicListById(id);
        if (name != null) {
            log.info(name);
            musicList.setName(name);
        }
        if (describe != null) {
            musicList.setDescribe(describe);
        }
        if (file != null) {
            //没写删除原来的图片，运行成功后会写
            boolean b = FileUploadUtil.deleteMusicListPicture(musicList.getListImage());
            if (b) musicList.setListImage(FileUploadUtil.uploadMusicListPicture(file));
        }
        mapper.updateMusicListInformation(musicList);
        resultData.setData(musicList);
        resultData.setFlag("music_list_upload_success");
        return resultData;
    }

    public ResultData getMusicListName(String id) {
        MusicList musicListById = mapper.getMusicListById(id);
        resultData.setData(musicListById);
        resultData.setFlag("200");
        return resultData;
    }
}
