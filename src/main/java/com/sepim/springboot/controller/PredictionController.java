package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.PredDataSetService;
import com.sepim.springboot.utils.FileUploadUtil;
import lombok.extern.log4j.Log4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/7/7
 */
@CrossOrigin
@RestController
public class PredictionController {

    @Autowired
    private PredDataSetService predDataSetService;

    @PostMapping("/file/save")
    public ResultData saveFile(@RequestParam(value = "file") MultipartFile file, @RequestParam("id") String id) {
        ResultData resultData = predDataSetService.uploadPredFile(file, id);
        return resultData;
    }

    @PostMapping("/file/get")
    public ResultData getFileList(@RequestBody Map map) {
        return predDataSetService.getFileList(map);
    }

    @GetMapping("/file/csv")
    public ResultData readCSV(@RequestParam("id")Integer id) {
        return predDataSetService.readCSV(id);
    }

}
