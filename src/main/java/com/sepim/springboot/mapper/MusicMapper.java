package com.sepim.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sepim.springboot.entity.Music;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MusicMapper extends BaseMapper<Music> {

    List<Music> getMusicListByMusicListId(String id);

    Music getMusicById(String id);


}
