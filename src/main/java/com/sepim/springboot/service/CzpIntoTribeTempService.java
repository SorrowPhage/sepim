package com.sepim.springboot.service;

import com.sepim.springboot.entity.CzpIntoTribeTemp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.ResultMessage;
import com.sepim.springboot.entity.vo.CzpIntoTribeTempVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    ResultMessage getTempData(String userId, String batchNo);

    ResultMessage delete(CzpIntoTribeTempVO czpIntoTribeTempVO);

    ResultMessage confirm(Map<String,String> param);
}
