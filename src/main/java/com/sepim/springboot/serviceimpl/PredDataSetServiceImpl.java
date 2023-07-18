package com.sepim.springboot.serviceimpl;

import com.sepim.springboot.entity.PredDataSet;
import com.sepim.springboot.entity.example.PredDataSetExample;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.PredDataSetMapper;
import com.sepim.springboot.service.PredDataSetService;
import com.sepim.springboot.utils.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/7/7
 */
@Service
@Slf4j
public class PredDataSetServiceImpl implements PredDataSetService {

    @Autowired
    private PredDataSetMapper predDataSetMapper;

    @Override
    public ResultData uploadPredFile(MultipartFile file, String id) {
        PredDataSet predDataSet = new PredDataSet();
        predDataSet.setUserId(id);
        predDataSet.setUrl(FileUploadUtil.uploadPred(file));
        predDataSetMapper.insert(predDataSet);
        ResultData resultData = new ResultData();
        resultData.setFlag("200");
        resultData.setData(null);
        return resultData;
    }

    @Override
    public ResultData getFileList(Map map) {
        PredDataSetExample predDataSetExample = new PredDataSetExample();
        PredDataSetExample.Criteria criteria = predDataSetExample.createCriteria();
        criteria.andUserIdEqualTo((String) map.get("id"));
        List<PredDataSet> predDataSets = predDataSetMapper.selectByExample(predDataSetExample);
        ResultData resultData = new ResultData();
        resultData.setFlag("200");
        resultData.setData(predDataSets);
        return resultData;
    }

    @Override
    public ResultData readCSV(Integer id) {
        PredDataSet predDataSet = predDataSetMapper.selectByPrimaryKey(id);
        ResultData resultData = new ResultData();
        if (predDataSet != null) {
            String s = FileUploadUtil.readMdFile(predDataSet.getUrl().replace(FileUploadUtil.PRED_SERVER_PATH, FileUploadUtil.PRED_BASE_PATH));
            resultData.setData(s);
            resultData.setFlag("200");
        } else {
            resultData.setFlag("400");
            resultData.setData(null);
        }
        return resultData;
    }
}
