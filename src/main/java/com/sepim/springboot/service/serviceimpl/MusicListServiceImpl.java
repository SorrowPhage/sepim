package com.sepim.springboot.service.serviceimpl;

import com.sepim.springboot.entity.MusicList;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.MusicListMapper;
import com.sepim.springboot.service.MusicListService;
import com.sepim.springboot.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/2/24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MusicListServiceImpl implements MusicListService {


    private final MusicListMapper mapper;

    /**
     * 获取用户的歌单
     * @param id 用户id
     * @return 返回获取结果
     */
    @Override
    public ResultData getUserMusicList(String id) {
        ResultData resultData = new ResultData();
        List<MusicList> musicLists=mapper.getUserMusicList(id);
        resultData.setData(musicLists);
        resultData.setFlag("get_musiclist_success");
        return resultData;
    }


    /**
     * 修改用户的歌单信息
     * @param file 歌单封面
     * @param id 歌单id
     * @param name 歌单名字
     * @param describe 歌单描述
     * @return 处理结果
     */
    @Override
    public ResultData updateMusicListInformation(MultipartFile file, String id,String name, String describe) {
        ResultData resultData = new ResultData();
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


    /**
     * 获取歌单中所有音乐
     * @param id 用户id
     * @return 返回获取结果
     */
    public ResultData getMusicListName(String id) {
        ResultData resultData = new ResultData();
        MusicList musicListById = mapper.getMusicListById(id);
        resultData.setData(musicListById);
        resultData.setFlag("200");
        return resultData;
    }
}
