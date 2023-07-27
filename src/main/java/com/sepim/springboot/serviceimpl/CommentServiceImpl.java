package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.Comment;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.CommentMapper;
import com.sepim.springboot.service.CommentService;
import com.sepim.springboot.service.UserService;
import com.sepim.springboot.utils.CommentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    private final UserService userService;

    /**
     * 获取评论列表的数据
     * @param folderId 文章id
     * @return 评论列表
     */
    @Override
    public List<Comment> getComments(String folderId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("folder_id", folderId);
        List<Comment> list = this.list(wrapper);
        for (Comment comment : list) {
            comment.setUser(userService.getById(comment.getAccount()));
        }
        return CommentUtil.processComments(list);
    }

    /**
     *保存评论
     * @param comment 评论
     * @return 返回保存结果
     */
    @Override
    public ResultData release(Comment comment) {
        ResultData resultData = new ResultData();
        boolean save = this.save(comment);
        if (save) {
            resultData.setFlag("md_comment_release_succeed");
            resultData.setData(null);
        } else {
            resultData.setFlag("md_comment_release_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    /**
     * 删除文字的所有评论
     * @param id 文章id
     */
    @Override
    public void deleteByFolderId(String id) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("folder_id", id);
        this.remove(wrapper);
    }
}
