package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.Music;
import com.sepim.springboot.entity.ResultData;
import org.springframework.web.multipart.MultipartFile;

public interface MusicService extends IService<Music> {

    ResultData saveMusic(MultipartFile file);

    ResultData getMusicList();

    ResultData getMusicListByMusicListId(String id);

    ResultData getMusicLrc(String id);

}
