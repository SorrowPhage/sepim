package com.sepim.springboot.service;

import com.sepim.springboot.entity.ResultData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/7/7
 */
@Service
public interface PredDataSetService {

    ResultData uploadPredFile(MultipartFile file, String id);

    ResultData getFileList(Map map);

    ResultData readCSV(Integer id);

}
