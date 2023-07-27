package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.Comment;
import com.sepim.springboot.entity.ResultData;

import java.util.List;

public interface CommentService extends IService<Comment> {
    List<Comment> getComments(String folderId);

    ResultData release(Comment comment);

    void deleteByFolderId(String id);
}
