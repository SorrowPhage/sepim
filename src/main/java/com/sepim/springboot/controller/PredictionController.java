package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.PredDataSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/7/7
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PredictionController {

    private final PredDataSetService predDataSetService;


    @PostMapping("/file/save")
    public ResultData saveFile(@RequestParam(value = "file") MultipartFile file, @RequestParam("id") String id) {
        return predDataSetService.uploadPredFile(file, id);
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
