package com.sepim.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sepim.springboot.entity.Folder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FolderMapper extends BaseMapper<Folder> {

    List<Folder> getRecent14Data(Map<String,String> param);

}
