package com.sepim.springboot.controller;

import com.sepim.springboot.common.aop.LogAnnotation;
import com.sepim.springboot.entity.Comment;
import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.FolderCondition;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.CommentService;
import com.sepim.springboot.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class MarkDownController {


    private final FolderService folderService;

    private final CommentService commentService;


    /**
     * 文章上传
     * @param folder 文章数据
     * @return 返回上传结果
     */
    @PostMapping("/md/save")
    public ResultData saveMd(@RequestBody Folder folder) {
        return folderService.saveMd(folder);
    }

    /**
     * 分页查询
     * @param condition 查询规则
     * @return 返回查询结果
     */
    @GetMapping("/md/list/page")
    public ResultData getMdListPage(@RequestBody FolderCondition condition) {
        return folderService.queryBYCondition(condition);
    }

    @PostMapping("/md/update")
    public ResultData editMd(@RequestBody Folder folder) {
        return folderService.editMd(folder);
    }

    /**
     * 获取文章列表
     * @param folder 这个参数应该直接使用String
     * @return
     */
    @PostMapping("/md/list")
    public ResultData getMdList(@RequestBody Folder folder) {
        return folderService.getMdList(folder.getUserId());
    }

    @GetMapping("/md/read")
    @LogAnnotation(module = "文章",operator = "获取文章")
    public ResultData readMd(@RequestParam("id") String id) {
        return folderService.readMd(id);
    }

    @GetMapping("/md/readMd")
    @LogAnnotation(module = "文章",operator = "获取文章")
    public ResultData readMdContent(@RequestParam("id") String id) {
        return folderService.readMdContent(id);
    }

    @PostMapping("/md/upload")
    public ResultData uploadMdImg(@RequestParam(value = "file") MultipartFile file) {
        return folderService.uploadMdImg(file);
    }

    @GetMapping("/md/delete")
    public ResultData deleteImgUrl(@RequestParam("url") String url) {
        return folderService.deleteImgUrl(url);
    }

    @GetMapping("/md/remove")
    public ResultData deleteMd(@RequestParam("id") String id) {
        return folderService.deleteMd(id);
    }



    /**
     *
     * @param comment
     * @return
     */
    @PostMapping("/md/comment/release")
    public ResultData release(@RequestBody Comment comment) {
        return commentService.release(comment);
    }

    @GetMapping("/md/comment/get")
    @LogAnnotation(module = "文章",operator = "评论")
    public ResultData getComments(@RequestParam("folderId") String folderId) {
        ResultData resultData = new ResultData();
        log.info("asdasdasd");
        List<Comment> comments = commentService.getComments(folderId);
        resultData.setFlag("md_comment_get_succeed");
        resultData.setData(comments);
        return resultData;
    }

    @GetMapping("/md/rank")
    public ResultData getRankList() {
        return folderService.getRankList();
    }

    /**
     * 用户上传本地文章
     * @param file 文件
     * @param userId 用户id
     * @param roughly 文章描述
     * @param title 文章标题
     * @param type 文章类型
     * @return 上传结果返回
     */
    @PostMapping("/md/store")
    public ResultData storeMd(@RequestParam(value = "file") MultipartFile file,@RequestParam("userId") String userId,
                             @RequestParam("roughly") String roughly,@RequestParam("title")String title,@RequestParam("type") String type) {
        Folder folder = new Folder();
        folder.setUserId(userId);
        folder.setRoughly(roughly);
        folder.setTitle(title);
        folder.setType(type);
        return folderService.storeMd(file, folder);
    }
}
