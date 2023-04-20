package com.sepim.springboot.mapper;

import com.sepim.springboot.entity.Music;
import com.sepim.springboot.entity.MusicList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/2/24
 */
@Mapper
public interface MusicListMapper {

    List<MusicList> getUserMusicList(String id);

    MusicList getMusicListById(String id);

    int updateMusicListInformation(MusicList musicList);


}
