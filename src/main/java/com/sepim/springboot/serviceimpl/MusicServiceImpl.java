package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.Music;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.MusicMapper;
import com.sepim.springboot.service.MusicService;
import com.sepim.springboot.utils.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/2/21
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Autowired
    private ResultData resultData;

    @Autowired
    private MusicMapper musicMapper;



    @Override
    public ResultData saveMusic(MultipartFile file) {
        String s = FileUploadUtil.uploadMusic(file);
        resultData.setFlag(s);
        return resultData;
    }

    @Override
    public ResultData getMusicList() {
        resultData.setData(this.list());
        resultData.setFlag("music_get_list_success");
        return resultData;
    }

    @Override
    public ResultData getMusicListByMusicListId(String id) {
        List<Music> musicListByMusicListId = musicMapper.getMusicListByMusicListId(id);
        resultData.setData(musicListByMusicListId);
        resultData.setFlag("music_music_list_get_byid_success");
        return resultData;
    }

    @Override
    public ResultData getMusicLrc(String id) {
        Music musicById = musicMapper.getMusicById(id);
        if (musicById.getLrc() == null) {
            resultData.setData(null);
            resultData.setFlag("400");
            return resultData;
        }
        String s = FileUploadUtil.readMusicLrc(musicById.getLrc());
        resultData.setData(s);
        resultData.setFlag("success");
        return resultData;
    }
}
