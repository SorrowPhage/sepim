package com.sepim.springboot.serviceimpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.FolderCondition;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.mapper.FolderMapper;
import com.sepim.springboot.service.CommentService;
import com.sepim.springboot.service.FolderService;
import com.sepim.springboot.service.UserService;
import com.sepim.springboot.utils.FileUploadUtil;
import com.sepim.springboot.utils.StringRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {
    @Autowired
    private ResultData resultData;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private FolderMapper folderMapper;

    @Autowired
    private StringRedisUtils stringRedisUtils;

    private static final String MD_FILE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\md\\file\\";


    // private static final String SERVER_MD_FILE_PATH = "http://localhost:8088/upload/sepim/md/file/";


    /**
     * 分页查询，FolderCondition中有用户的id，查询的使该用户的所有文章
     * @param condition
     * @return
     */
    public ResultData queryBYCondition(FolderCondition condition) {
        Page<Folder> page = new Page<>();
        page.setCurrent(condition.getPageIndex());
        page.setSize(condition.getPageSize());
        QueryWrapper<Folder> qw = new QueryWrapper<>();
        if (condition.getUserId() != null && !condition.getUserId().isEmpty()) {
            qw.eq("user_id", condition.getUserId());
        }
        IPage<Folder> iPage = folderMapper.selectPage(page, qw);
        resultData.setFlag("200");
        resultData.setData(iPage);
        return resultData;
    }

    /**
     * 保存MD文件
     * @param folder
     * @return
     */
    @Override
    public ResultData saveMd(Folder folder) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String absPath = MD_FILE_PATH + uuid + folder.getTitle() + ".txt";
        String mdAbsPath = MD_FILE_PATH + uuid + folder.getTitle() + ".md";
        folder.setTime(DateUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd"));
        // folder.setTime(DateUtil.now());
        if (FileUploadUtil.uploadMdFile(folder.getContent(), absPath)&&
            FileUploadUtil.uploadMdFile(folder.getMdContent(), mdAbsPath)) {
            folder.setUrl(absPath);
            folder.setMdUrl(mdAbsPath);
            this.save(folder);
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
     * @param folder
     * @return
     */
    @Override
    public ResultData editMd(Folder folder) {
        if (FileUploadUtil.deleteMd(this.getById(folder.getId()).getUrl())&&FileUploadUtil.deleteMd(this.getById(folder.getId()).getMdUrl())) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String absPath = MD_FILE_PATH + uuid + folder.getTitle() + ".txt";
            String mdAbsPath = MD_FILE_PATH + uuid + folder.getTitle() + ".md";
            if (FileUploadUtil.uploadMdFile(folder.getContent(), absPath) &&
                FileUploadUtil.uploadMdFile(folder.getMdContent(), mdAbsPath)) {
                folder.setUrl(absPath);
                folder.setMdUrl(mdAbsPath);
                this.saveOrUpdate(folder);
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
     * @param id
     * @return
     */
    @Override
    public ResultData getMdList(String id) {
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
     * @param id
     * @return
     */
    public ResultData readMd(String id) {
        Folder file = this.getById(id);

        file.setUser(userService.getById(file.getUserId()));

        file.setReadNum(file.getReadNum() + 1);
        this.updateById(file);
        file.setContent(FileUploadUtil.readMdFile(file.getUrl()));
        file.setComments(commentService.getComments(id));
        resultData.setFlag("md_read_succeed");
        resultData.setData(file);
        return resultData;
    }

    @Override
    public ResultData readMdContent(String id) {
        Folder file = this.getById(id);
        file.setMdContent(FileUploadUtil.readMdFile(file.getMdUrl()));
        resultData.setFlag("md_read_succeed");
        resultData.setData(file);
        return resultData;
    }

    /**
     * 上传文件中图片
     * @param file
     * @return
     */
    public ResultData uploadMdImg(MultipartFile file) {
        String imgStr = FileUploadUtil.uploadMdImg(file);
        resultData.setFlag("md_upload_img_succeed");
        resultData.setData(imgStr);
        return resultData;
    }

    /**
     * 删除文件中的图片
     * @param url
     * @return
     */
    public ResultData deleteImgUrl(String url) {
        FileUploadUtil.deleteImgUrl(url);
        resultData.setFlag("md_delete_succeed");
        resultData.setData(null);
        return resultData;
    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @Override
    public ResultData deleteMd(String id) {
        if (FileUploadUtil.deleteMd(this.getById(id).getUrl())) {
            commentService.deleteByFolderId(id);
            boolean b = this.removeById(id);
            if (b) {
                resultData.setFlag("md_delete_succeed");
                resultData.setData(null);
            }
            else {
                resultData.setFlag("md_delete_defeat");
                resultData.setData(null);
            }
        }
        else {
            resultData.setFlag("md_delete_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    @Override
    public ResultData recommendMd() {
        QueryWrapper<Folder> wrapper = new QueryWrapper<>();
        wrapper.eq("type", "公开的").eq("state", "1");
        List<Folder> list = this.list(wrapper);
        resultData.setFlag("md_recommend_succeed");
        resultData.setData(list);
        return resultData;
    }

    @Override
    public ResultData getPublicFolders(String id) {
        QueryWrapper<Folder> wrapper = new QueryWrapper<>();
        wrapper.eq("type", "公开的").eq("state", "1").eq("user_id", id);
        List<Folder> list = this.list(wrapper);
        if (list.size() > 0) {
            resultData.setFlag("user_center_getPublicFolders_succeed");
            resultData.setData(list);
        } else {
            resultData.setFlag("user_center_getPublicFolders_defeat");
            resultData.setData(null);
        }
        return resultData;
    }

    @Override
    public ResultData getRankList() {
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

    @Override
    public ResultData overview(User user) {
        User userById = userService.getById(user.getId());
        if (userById.getOverviewUrl() == null) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String absPath = MD_FILE_PATH + uuid + "readMe.txt";
            String mdAbsPath = MD_FILE_PATH + uuid + "readMe.md";
            if (FileUploadUtil.uploadMdFile(user.getContent(), absPath) &&
                    FileUploadUtil.uploadMdFile(user.getMdContent(), mdAbsPath)) {
                userById.setOverviewUrl(absPath);
                userById.setOverviewMdUrl(mdAbsPath);
                userService.saveOrUpdate(userById);
                resultData.setFlag("md_overview_succeed");
                resultData.setData(null);
            } else {
                resultData.setFlag("md_overview_defeat");
                resultData.setData(null);
            }
        } else {
            if (FileUploadUtil.deleteMd(userById.getOverviewUrl())&&FileUploadUtil.deleteMd(userById.getOverviewMdUrl())) {
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String absPath = MD_FILE_PATH + uuid +  "readMe.txt";
                String mdAbsPath = MD_FILE_PATH + uuid  + "readMe.md";
                if (FileUploadUtil.uploadMdFile(user.getContent(), absPath) &&
                        FileUploadUtil.uploadMdFile(user.getMdContent(), mdAbsPath)) {
                    userById.setOverviewUrl(absPath);
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
     * @param q
     * @return
     */
    @Override
    public ResultData searchRep(String q) {
        QueryWrapper<Folder> wrapper = new QueryWrapper<>();
        wrapper.like("title", q);
        List<Folder> list = this.list(wrapper);

        for (Folder folder : list) {
            // log.info("设置用户信息，", folder.getUserId());
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
}
