package com.sepim.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sepim.springboot.entity.Folder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FolderMapper extends BaseMapper<Folder> {

}
