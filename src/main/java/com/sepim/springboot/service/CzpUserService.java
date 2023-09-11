package com.sepim.springboot.service;

import com.sepim.springboot.entity.CzpUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.ResultMessage;

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

    ResultMessage getRelationChat(Map<String, String> param);

    ResultMessage getRelationChat2All(Map<String, String> param);

    ResultMessage getRelationChatList2Tribe(Map<String, String> param);
}
