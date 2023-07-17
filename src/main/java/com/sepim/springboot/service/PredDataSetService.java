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

    public ResultData uploadPredFile(MultipartFile file,String id);

    public ResultData getFileList(Map map);

    public ResultData readCSV(Integer id);

}
