package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultMessage;
import com.sepim.springboot.service.CzpIntoTribeTempService;
import com.sepim.springboot.service.CzpUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/9/6
 */
@RestController
@RequestMapping("/czp")
@RequiredArgsConstructor
@CrossOrigin
public class CzpController {

    private final CzpUserService czpUserService;

    private final CzpIntoTribeTempService czpIntoTribeTempService;

    /**
     * 获取树型结构数据（不包括迁移出去的）
     * @param param 族群id
     * @return 树型结构
     */
    @PostMapping("/rc")
    public ResultMessage getRelationChat(@RequestBody Map<String, String> param) {
        return czpUserService.getRelationChat(param);
    }

    /**
     * 获取包括迁移出去的数据
     * @param param
     * @return
     */
    @PostMapping("/all_rc")
    public ResultMessage getRelationChat2All(@RequestBody Map<String, String> param) {
        // return czpUserService.getRelationChat2All(param);
        return czpUserService.relationChatAll(param);
    }

    @PostMapping("/upload")
    public ResultMessage uploadCzpData(@RequestParam("file")MultipartFile file,@RequestParam("userId")String userId) {
        return czpUserService.uploadCzpData(file,userId);
    }

    @GetMapping("/batchnos")
    public ResultMessage batchNoList(@RequestParam("userId") String userId) {
        return czpIntoTribeTempService.batchNoList(userId);
    }




}
