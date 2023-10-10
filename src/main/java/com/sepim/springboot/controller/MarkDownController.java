package com.sepim.springboot.controller;

import com.google.zxing.WriterException;
import com.sepim.springboot.common.aop.Idempotent;
import com.sepim.springboot.common.aop.LogExecuteMethod;
import com.sepim.springboot.entity.*;
import com.sepim.springboot.service.CommentService;
import com.sepim.springboot.service.FolderLikeService;
import com.sepim.springboot.service.FolderService;
import com.sepim.springboot.utils.QrCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class MarkDownController {


    private final FolderService folderService;

    private final CommentService commentService;

    private final FolderLikeService folderLikeService;

    @Value("${sepim.folder.src}")
    private String FOLDER_SRC_PREFIX;


    /**
     * 文章上传
     *
     * @param folder 文章数据
     * @return 返回上传结果
     */
    @PostMapping("/md/save")
    public ResultData saveMd(@RequestBody Folder folder) {
        return folderService.saveMd(folder);
    }

    /**
     * 分页查询
     *
     * @param condition 查询规则
     * @return 返回查询结果
     */
    @PostMapping("/md/list/page")
    public ResultData getMdListPage(@RequestBody SearchCondition condition) {
        return folderService.queryBYCondition(condition);
    }

    @PostMapping("/md/update")
    public ResultData editMd(@RequestBody Folder folder) {
        return folderService.editMd(folder);
    }

    /**
     * 获取文章列表
     *
     * @param folder 这个参数应该直接使用String
     * @return 返回获取数据
     */
    @PostMapping("/md/list")
    public ResultData getMdList(@RequestBody Folder folder) {
        return folderService.getMdList(folder.getUserId());
    }

    @GetMapping("/md/read")
    @LogExecuteMethod(module = "文章", operator = "获取文章")
    public ResultData readMd(@RequestParam("id") String id) {
        return folderService.readMd(id);
    }

    @GetMapping("/md/readMd")
    @LogExecuteMethod(module = "文章", operator = "获取文章")
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
     * 发布评论
     *
     * @param comment 评论
     * @return 返回发布结果
     */
    @PostMapping("/md/comment/release")
    public ResultData release(@RequestBody Comment comment) {
        return commentService.release(comment);
    }

    @GetMapping("/md/comment/get")
    @LogExecuteMethod(module = "文章", operator = "评论")
    public ResultData getComments(@RequestParam("folderId") String folderId) {
        ResultData resultData = new ResultData();
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
     *
     * @param file    文件
     * @param userId  用户id
     * @param roughly 文章描述
     * @param title   文章标题
     * @param type    文章类型
     * @return 上传结果返回
     */
    @PostMapping("/md/store")
    public ResultData storeMd(@RequestParam(value = "file") MultipartFile file, @RequestParam("userId") String userId,
                              @RequestParam("roughly") String roughly, @RequestParam("title") String title, @RequestParam("type") String type) {
        Folder folder = new Folder();
        folder.setUserId(userId);
        folder.setRoughly(roughly);
        folder.setTitle(title);
        folder.setType(type);
        return folderService.storeMd(file, folder);
    }

    @PostMapping("/md/like")
    @LogExecuteMethod(module = "文章", operator = "点赞")
    public ResultMessage likeMd(@RequestBody Map<String, String> param) {
        // return folderLikeService.like(param);// redis
        return folderLikeService.liek2DB(param);// 存入DB中
    }


    @PostMapping("/md/unlike")
    @LogExecuteMethod(module = "文章", operator = "取消点赞")
    public ResultMessage unlikeMd(@RequestBody Map<String, String> param) {
        // return folderLikeService.unlike(param);// redis
        return folderLikeService.unlike2DB(param);//DB
    }

    @PostMapping("/md/like/status")
    public ResultData getLikeStatus(@RequestBody Map<String, String> param) {
        // return folderLikeService.getLikeStatus(param);//redis
        return folderLikeService.getLikeStatus2DB(param);
    }

    /**
     * 获取echarts的数据
     *
     * @param param 用户id
     * @return
     */
    @PostMapping("md/echarts-data")
    public ResultData getEchartsData(@RequestBody Map<String, String> param) {
        return folderService.getEchartsData(param);
    }

    @GetMapping("/md/qr")
    public void createQrCode(@RequestParam("id") String id, HttpServletResponse response) throws IOException, WriterException {
        QrCodeUtils.encode(FOLDER_SRC_PREFIX + id, null, response.getOutputStream(), false);
    }
}
