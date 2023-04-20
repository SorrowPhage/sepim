package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.Music;
import com.sepim.springboot.entity.ResultData;
import org.springframework.web.multipart.MultipartFile;

public interface MusicService extends IService<Music> {

    public ResultData saveMusic(MultipartFile file);

    public ResultData getMusicList();

    public ResultData getMusicListByMusicListId(String id);

    public ResultData getMusicLrc(String id);

}
