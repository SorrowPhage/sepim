package com.sepim.springboot.service;

import com.sepim.springboot.entity.CzpIntoTribeTemp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.ResultMessage;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-08
 */
public interface CzpIntoTribeTempService extends IService<CzpIntoTribeTemp> {

    ResultMessage uploadCzpData(MultipartFile file, String userId);

    ResultMessage batchNoList(String userId);
}
