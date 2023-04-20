package com.sepim.springboot.service;


import com.sepim.springboot.entity.ResultData;
import org.springframework.web.multipart.MultipartFile;

public interface MusicListService {

    ResultData getUserMusicList(String id);

    ResultData updateMusicListInformation(MultipartFile file,String id,String name, String describe);

    ResultData getMusicListName(String id);
}
