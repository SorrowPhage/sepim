package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.FolderMapper;
import com.sepim.springboot.service.CommentService;
import com.sepim.springboot.service.FolderService;
import com.sepim.springboot.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements FolderService {
    @Autowired
    private ResultData resultData;

    @Autowired
    private CommentService commentService;

    private static final String MD_FILE_PATH = "E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\md\\file\\";

    private static final String SERVER_MD_FILE_PATH = "http://localhost:8088/upload/sepim/md/file/";

    @Override
    public ResultData saveMd(Folder folder) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String absPath = MD_FILE_PATH + uuid + folder.getTitle() + ".txt";
        if (FileUploadUtil.uploadMdFile(folder.getContent(), absPath)) {
            folder.setUrl(SERVER_MD_FILE_PATH + uuid + folder.getTitle() + ".txt");
            this.save(folder);
            resultData.setFlag("md_save_succeed");
        } else {
            resultData.setFlag("md_save_defeat");
        }
        return resultData;
    }

    @Override
    public ResultData getMdList(String id) {
        QueryWrapper<Folder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<Folder> list = this.list(wrapper);
        resultData.setFlag("md_list_succeed");
        resultData.setData(list);
        return resultData;
    }

    public ResultData readMd(String id) {
        Folder file = this.getById(id);
        file.setReadNum(file.getReadNum() + 1);
        this.updateById(file);
        file.setContent(FileUploadUtil.readMdFile(file.getUrl().replace(SERVER_MD_FILE_PATH, MD_FILE_PATH)));
        file.setComments(commentService.getComments(id));
        resultData.setFlag("md_read_succeed");
        resultData.setData(file);
        return resultData;
    }

    public ResultData uploadMdImg(MultipartFile file) {
        String imgStr = FileUploadUtil.uploadMdImg(file);
        resultData.setFlag("md_upload_img_succeed");
        resultData.setData(imgStr);
        return resultData;
    }

    public ResultData deleteImgUrl(String url) {
        FileUploadUtil.deleteImgUrl(url);
        resultData.setFlag("md_delete_succeed");
        return resultData;
    }

    @Override
    public ResultData deleteMd(String id) {
        if (FileUploadUtil.deleteMd(this.getById(id).getUrl().replace(SERVER_MD_FILE_PATH, MD_FILE_PATH))) {
            boolean b = this.removeById(id);
            if (b) {
                resultData.setFlag("md_delete_succeed");
            }
            else {
                resultData.setFlag("md_delete_defeat");
            }
        }
        else {
            resultData.setFlag("md_delete_defeat");
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
}
