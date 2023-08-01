package com.sepim.springboot.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.Music;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.MusicMapper;
import com.sepim.springboot.service.MusicService;
import com.sepim.springboot.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/2/21
 */
@Service
@RequiredArgsConstructor
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {


    private final MusicMapper musicMapper;


    /**
     * 保存音乐
     * @param file 音乐
     * @return 返回保存结果
     */
    @Override
    public ResultData saveMusic(MultipartFile file) {
        ResultData resultData = new ResultData();
        String s = FileUploadUtil.uploadMusic(file);
        resultData.setFlag(s);
        return resultData;
    }


    @Override
    public ResultData getMusicList() {
        ResultData resultData = new ResultData();
        resultData.setData(this.list());
        resultData.setFlag("music_get_list_success");
        return resultData;
    }


    /**
     *获取歌单
     * @param id 歌单id
     * @return 返回获取结果
     */
    @Override
    public ResultData getMusicListByMusicListId(String id) {
        ResultData resultData = new ResultData();
        List<Music> musicListByMusicListId = musicMapper.getMusicListByMusicListId(id);
        resultData.setData(musicListByMusicListId);
        resultData.setFlag("music_music_list_get_byid_success");
        return resultData;
    }

    /**
     * 获取音乐歌词
     * @param id 音乐id
     * @return 返回获取结果
     */
    @Override
    public ResultData getMusicLrc(String id) {
        ResultData resultData = new ResultData();
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
