package com.sepim.springboot.controller;

import com.sepim.springboot.common.aop.LogAnnotation;
import com.sepim.springboot.entity.Comment;
import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.CommentService;
import com.sepim.springboot.service.FolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
public class MarkDownController {

    @Autowired
    private FolderService folderService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ResultData resultData;

    @PostMapping("/md/save")
    public ResultData saveMd(@RequestBody Folder folder) {
        resultData = folderService.saveMd(folder);
        return resultData;
    }

    @PostMapping("/md/update")
    public ResultData editMd(@RequestBody Folder folder) {
        resultData = folderService.editMd(folder);
        return resultData;
    }

    @PostMapping("/md/list")
    public ResultData getMdList(@RequestBody Folder folder) {
        resultData = folderService.getMdList(folder.getUserId());
        return resultData;
    }

    @GetMapping("/md/read")
    @LogAnnotation(module = "文章",operator = "获取文章")
    public ResultData readMd(@RequestParam("id") String id) {
        resultData = folderService.readMd(id);
        return resultData;
    }

    @GetMapping("/md/readMd")
    @LogAnnotation(module = "文章",operator = "获取文章")
    public ResultData readMdContent(@RequestParam("id") String id) {
        resultData = folderService.readMdContent(id);
        return resultData;
    }

    @PostMapping("/md/upload")
    public ResultData uploadMdImg(@RequestParam(value = "file") MultipartFile file) {
        resultData = folderService.uploadMdImg(file);
        return resultData;
    }

    @GetMapping("/md/delete")
    public ResultData deleteImgUrl(@RequestParam("url") String url) {
        resultData = folderService.deleteImgUrl(url);
        return resultData;
    }

    @GetMapping("/md/remove")
    public ResultData deleteMd(@RequestParam("id") String id) {
        resultData = folderService.deleteMd(id);
        return resultData;
    }

    @PostMapping("/md/recommend")
    public ResultData recommendMd() {
        resultData = folderService.recommendMd();
        return resultData;
    }

    @PostMapping("/md/comment/release")
    public ResultData release(@RequestBody Comment comment) {
        resultData = commentService.release(comment);
        return resultData;
    }

    @GetMapping("/md/comment/get")
    @LogAnnotation(module = "文章",operator = "评论")
    public ResultData getComments(@RequestParam("folderId") String folderId) {
        log.info("asdasdasd");
        List<Comment> comments = commentService.getComments(folderId);
        resultData.setFlag("md_comment_get_succeed");
        resultData.setData(comments);
        return resultData;
    }

    @GetMapping("/md/rank")
    public ResultData getRankList() {
        resultData = folderService.getRankList();
        return resultData;
    }
}
