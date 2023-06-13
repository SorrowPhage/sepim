package com.sepim.springboot.utils;

import com.sepim.springboot.entity.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentUtil {

    /**
     * 评论列表处理
     * @param comments 所有的评论
     * @return 树状评论列表
     */
    public static List<Comment> processComments(List<Comment> comments) {
        Map<String, Comment> map = new HashMap<>();
        List<Comment> list = new ArrayList<>();

        for (Comment comment : comments) {
            map.put(comment.getId(), comment);
        }
        for (Comment comment : comments) {
            if (comment.getParentId() != null) {
                Comment parentComment = map.get(comment.getParentId());
                comment.setParentUsername(parentComment.getUsername());
                comment.setParentAccount(parentComment.getAccount());
            }
            if (comment.getRootId() != null) {
                Comment rootComment = map.get(comment.getRootId());
                if (rootComment.getChild() == null) {
                    rootComment.setChild(new ArrayList<>());
                }
                rootComment.getChild().add(comment);
            }
        }

        for (Comment comment : comments) {
            if (comment.getParentId() == null) {
                list.add(comment);
            }
        }
        return list;
    }
}
