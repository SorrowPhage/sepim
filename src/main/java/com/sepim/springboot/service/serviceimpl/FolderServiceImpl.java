package com.sepim.springboot.service.serviceimpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.*;
import com.sepim.springboot.mapper.FolderMapper;
import com.sepim.springboot.mapper.LoginLogMapper;
import com.sepim.springboot.service.*;
import com.sepim.springboot.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {

    private final CommentService commentService;

    private final UserService userService;

    private final FolderMapper folderMapper;

    private final OperationService operationService;

    private final FolderLikeRedisService folderLikeRedisService;

    private final LoginLogMapper loginLogMapper;


    private static final String MD_FILE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\md\\file\\";



    /**
     * 分页查询，FolderCondition中有用户的id，查询的使该用户的所有文章
     *
     * @param condition 查询信息
     * @return 返回查询结果
     */
    public ResultData queryBYCondition(SearchCondition condition) {
        ResultData resultData = new ResultData();
        Page<Folder> page = new Page<>();
        page.setCurrent(condition.getPageIndex());
        page.setSize(condition.getPageSize());
        QueryWrapper<Folder> qw = new QueryWrapper<>();
        if (condition.getTitle() != null && !condition.getTitle().isEmpty()) {
            qw.like("title", condition.getTitle()).eq("type", "公开的");
        }
        IPage<Folder> iPage = folderMapper.selectPage(page, qw);
        List<Folder> records = iPage.getRecords();
        for (Folder record : records) {
            record.setUser(userService.getById(record.getUserId()));
        }
        iPage.setRecords(records);
        resultData.setFlag("200");
        resultData.setData(iPage);
        return resultData;
    }

    /**
     * 保存MD文件
     *
     * @param folder md文件
     * @return 返回保存结果
     */
    @Override
    public ResultData saveMd(Folder folder) {
        ResultData resultData = new ResultData();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String mdAbsPath = MD_FILE_PATH + uuid + folder.getTitle() + ".md";
        folder.setTime(DateUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd"));
        // folder.setTime(DateUtil.now());
        if (FileUploadUtil.uploadMdFile(folder.getMdContent(), mdAbsPath)) {
            folder.setMdUrl(mdAbsPath);
            this.save(folder);

            //记录新增
            operationService.insert(folder.getId(), folder.getUserId());

            resultData.setFlag("md_save_succeed");
            resultData.setData(null);
        } else {
            resultData.setFlag("md_save_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    /**
     * 修改文件内容
     *
     * @param folder 文件
     * @return 返回修改结果
     */
    @Override
    public ResultData editMd(Folder folder) {
        ResultData resultData = new ResultData();
        if (FileUploadUtil.deleteMd(this.getById(folder.getId()).getMdUrl())) {
            String uuid = UUID.randomUUID().toString().replace("-", "");

            String mdAbsPath = MD_FILE_PATH + uuid + folder.getTitle() + ".md";
            if (FileUploadUtil.uploadMdFile(folder.getMdContent(), mdAbsPath)) {
                folder.setMdUrl(mdAbsPath);
                this.saveOrUpdate(folder);

                operationService.update(folder.getId());

                resultData.setFlag("md_edit_succeed");
                resultData.setData(null);
            } else {
                resultData.setFlag("md_edit_defeat");
                resultData.setData(null);
            }
        } else {
            resultData.setFlag("md_edit_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    /**
     * 获取文件列表
     *
     * @param id 用户id
     * @return 返回获取结果
     */
    @Override
    public ResultData getMdList(String id) {
        ResultData resultData = new ResultData();
        QueryWrapper<Folder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<Folder> list = this.list(wrapper);
        // for (Folder folder : list) {
        //     String redis = stringRedisUtils.getRedis("folder_" + folder.getUserId());
        //     if (redis == null) {
        //         stringRedisUtils.addRedisTime("folder_" + folder.getUserId(), userService.getById(folder.getUserId()));
        //     }
        //     // folder.setUser();
        // }
        resultData.setFlag("md_list_succeed");
        resultData.setData(list);
        return resultData;
    }

    /**
     * 读取文件
     *
     * @param id 用户id
     * @return 返回读取结果
     */
    public ResultData readMd(String id) {
        ResultData resultData = new ResultData();
        Folder file = this.getById(id);
        file.setUser(userService.getById(file.getUserId()));
        file.setReadNum(file.getReadNum() + 1);
        this.updateById(file);
        log.info(file.getMdUrl());
        file.setContent(FileUploadUtil.readMdFile(file.getMdUrl()));
        file.setComments(commentService.getComments(id));
        resultData.setFlag("md_read_succeed");

        // Integer likedCountFromRedis = folderLikeRedisService.getLikedCountFromRedis(id);
        // if (likedCountFromRedis != null) {
        //     file.setLikeCount(file.getLikeCount() + likedCountFromRedis);
        // }
        resultData.setData(file);
        return resultData;
    }

    @Override
    public ResultData readMdContent(String id) {
        ResultData resultData = new ResultData();
        Folder file = this.getById(id);
        file.setMdContent(FileUploadUtil.readMdFile(file.getMdUrl()));
        resultData.setFlag("md_read_succeed");
        resultData.setData(file);
        return resultData;
    }

    /**
     * 上传文件中图片
     *
     * @param file 图片
     * @return 返回上传结果
     */
    public ResultData uploadMdImg(MultipartFile file) {
        ResultData resultData = new ResultData();
        String imgStr = FileUploadUtil.uploadMdImg(file);
        resultData.setFlag("md_upload_img_succeed");
        resultData.setData(imgStr);
        return resultData;
    }

    /**
     * 删除文件中的图片
     *
     * @param url 图片url
     * @return 返回删除结果
     */
    public ResultData deleteImgUrl(String url) {
        ResultData resultData = new ResultData();
        FileUploadUtil.deleteImgUrl(url);
        resultData.setFlag("md_delete_succeed");
        resultData.setData(null);
        return resultData;
    }

    /**
     * 删除文件
     * @param id 文件id
     * @return 返回删除结果
     */
    @Override
    public ResultData deleteMd(String id) {
        ResultData resultData = new ResultData();
        if (FileUploadUtil.deleteMd(this.getById(id).getMdUrl())) {
            commentService.deleteByFolderId(id);
            boolean b = this.removeById(id);
            if (b) {
                operationService.delete(id);
                resultData.setFlag("md_delete_succeed");
                resultData.setData(null);
            } else {
                resultData.setFlag("md_delete_defeat");
                resultData.setData(null);
            }
        } else {
            resultData.setFlag("md_delete_defeat");
            resultData.setData(null);
        }
        return resultData;
    }



    @Override
    public ResultData getRankList() {
        ResultData resultData = new ResultData();
        QueryWrapper<Folder> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("read_num");
        List<Folder> list = this.list(wrapper);
        if (list.size() != 0) {
            resultData.setFlag("md_get_rank_succeed");
            resultData.setData(list);
        } else {
            resultData.setFlag("md_get_rank_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    /**
     * 修改用户的描述md文档
     * @param user 用户的id，文档的信息
     * @return 返回获取结果
     */
    @Override
    public ResultData overview(User user) {
        ResultData resultData = new ResultData();
        User userById = userService.getById(user.getId());
        if (userById.getOverviewMdUrl() == null) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String mdAbsPath = MD_FILE_PATH + uuid + "readMe.md";
            if (FileUploadUtil.uploadMdFile(user.getMdContent(), mdAbsPath)) {
                userById.setOverviewMdUrl(mdAbsPath);
                userService.saveOrUpdate(userById);
                resultData.setFlag("md_overview_succeed");
                resultData.setData(null);
            } else {
                resultData.setFlag("md_overview_defeat");
                resultData.setData(null);
            }
        } else {
            if (FileUploadUtil.deleteMd(userById.getOverviewMdUrl())) {
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String mdAbsPath = MD_FILE_PATH + uuid + "readMe.md";
                if (FileUploadUtil.uploadMdFile(user.getMdContent(), mdAbsPath)) {
                    userById.setOverviewMdUrl(mdAbsPath);
                    userService.saveOrUpdate(userById);
                    resultData.setFlag("md_overview_succeed");
                    resultData.setData(null);
                } else {
                    resultData.setFlag("md_overview_defeat");
                    resultData.setData(null);
                }
            } else {
                resultData.setFlag("md_overview_defeat");
                resultData.setData(null);
            }
        }
        return resultData;
    }

    /**
     * 搜索功能---文章列表
     * @param q 文章标题
     * @return 搜索结果
     */
    @Override
    public ResultData searchRep(String q) {
        ResultData resultData = new ResultData();
        QueryWrapper<Folder> wrapper = new QueryWrapper<>();
        wrapper.like("title", q);
        List<Folder> list = this.list(wrapper);

        for (Folder folder : list) {
            folder.setUser(userService.getById(folder.getUserId()));
        }


        if (list.size() > 0) {
            resultData.setFlag("search_rep_succeed");
            resultData.setData(list);
        } else {
            resultData.setFlag("search_res_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    /**
     * 用户上传本地文章
     * @param file 上传文件
     * @param folder 文章数据
     * @return 返回上传结果
     */
    @Override
    public ResultData storeMd(MultipartFile file, Folder folder) {
        ResultData resultData = new ResultData();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String mdAbsPath = MD_FILE_PATH + uuid + folder.getTitle() + ".md";
        folder.setTime(DateUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd"));
        if (FileUploadUtil.storeMd(file, mdAbsPath)) {
            folder.setMdUrl(mdAbsPath);
            this.save(folder);
            operationService.insert(folder.getId(), folder.getUserId());
            resultData.setFlag("md_save_succeed");
            resultData.setData(null);
        } else {
            resultData.setFlag("md_save_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    @Override
    public ResultData getPublicFolders(String userId) {
        ResultData resultData = new ResultData();
        QueryWrapper<Folder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("type", "公开的");
        List<Folder> list = this.list(wrapper);
        resultData.setData(list);
        resultData.setFlag("200");
        return resultData;
    }

    @Override
    public ResultData getEchartsData(Map<String, String> param) {
        ResultData resultData = new ResultData();
        List<LoginLog> loginLogList = loginLogMapper.getRecent14Data(param);
        List<Folder> folderList = folderMapper.getRecent14Data(param);

        Map<String, List> dataList = new HashMap<>();
        dataList.put("folderList", folderList);
        dataList.put("loginList", loginLogList);

        resultData.setFlag("200");
        resultData.setData(dataList);
        return resultData;
    }
}
