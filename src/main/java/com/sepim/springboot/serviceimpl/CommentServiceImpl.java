package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.Comment;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.CommentMapper;
import com.sepim.springboot.service.CommentService;
import com.sepim.springboot.utils.CommentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private ResultData resultData;

    @Override
    public List<Comment> getComments(String folderId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("folder_id", folderId);
        return CommentUtil.processComments(this.list(wrapper));
    }

    @Override
    public ResultData release(Comment comment) {
        boolean save = this.save(comment);
        if (save) {
            resultData.setFlag("md_comment_release_succeed");
        } else {
            resultData.setFlag("md_comment_release_defeat");
        }
        return resultData;
    }
}
