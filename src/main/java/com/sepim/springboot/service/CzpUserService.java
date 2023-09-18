package com.sepim.springboot.service;

import com.sepim.springboot.entity.CzpUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.ResultMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-06
 */
public interface CzpUserService extends IService<CzpUser> {

    /**
     * 获取zp树型结构（不包括分出去的）
     * @param param
     * @return
     */
    ResultMessage getRelationChat(Map<String, String> param);

    /**
     * 获取zp树型结构（包括分出去的），这个方法只能查到从查的这里分出去的，分出去的分出去的就查不到
     * @param param
     * @return
     */
    ResultMessage getRelationChat2All(Map<String, String> param);

    /**
     * 获取zp树型结构（包括分出去的）
     * @param param
     * @return
     */
    ResultMessage relationChatAll(Map<String, String> param);

    /**
     * 上传excel
     * @param file
     * @param userId
     * @return
     */
    ResultMessage uploadCzpData(MultipartFile file, String userId);

    /**
     * 查询某个用户下面所属
     * @param userId
     * @return
     */
    ResultMessage relationChatByUserId(String userId);

}
