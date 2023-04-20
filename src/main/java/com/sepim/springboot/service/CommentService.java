package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.Comment;
import com.sepim.springboot.entity.ResultData;

import java.util.List;

public interface CommentService extends IService<Comment> {
    public List<Comment> getComments(String folderId);

    public ResultData release(Comment comment);

    public void deleteByFolderId(String id);
}
