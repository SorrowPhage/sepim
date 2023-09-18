package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultMessage;
import com.sepim.springboot.entity.vo.CzpIntoTribeTempVO;
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
     *
     * @param param 族群id
     * @return 树型结构
     */
    @PostMapping("/rc")
    public ResultMessage getRelationChat(@RequestBody Map<String, String> param) {
        return czpUserService.getRelationChat(param);
    }

    /**
     * 获取包括迁移出去的数据
     *
     * @param param
     * @return
     */
    @PostMapping("/all_rc")
    public ResultMessage getRelationChat2All(@RequestBody Map<String, String> param) {
        // return czpUserService.getRelationChat2All(param);  //假的全部，只能带第一级子节点
        return czpUserService.relationChatAll(param); //真全部，所有子节点
    }


    @GetMapping("/rc_userid")
    public ResultMessage relationChatByUserId(@RequestParam("userId")String userId) {
        return czpUserService.relationChatByUserId(userId);
    }

    /**
     * 上传文件
     * @param file excel文件
     * @param userId 操作人id
     * @return 批次号
     */
    @PostMapping("/upload")
    public ResultMessage uploadCzpData(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {

        return czpUserService.uploadCzpData(file, userId);
    }

    /**
     * 获取批次号列表
     * @param userId 用户id
     * @return 列表数据
     */
    @GetMapping("/batchnos")
    public ResultMessage batchNoList(@RequestParam("userId") String userId) {
        return czpIntoTribeTempService.batchNoList(userId);
    }


    /**
     * 获得临时表数据
     * @param userId 用户id
     * @param batchNo 批次号
     * @return 临时表数据
     */
    @GetMapping("/get")
    public ResultMessage getTempData(@RequestParam("userId") String userId, @RequestParam("batchNo") String batchNo) {
        return czpIntoTribeTempService.getTempData(userId, batchNo);
    }

    /**
     * 删除数据
     * @param czpIntoTribeTempVO 删除条件
     * @return 删除状态
     */
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody CzpIntoTribeTempVO czpIntoTribeTempVO) {
        return czpIntoTribeTempService.delete(czpIntoTribeTempVO);
    }

    /**
     * 确认
     * @param param 批次号
     * @return 确认状态
     */
    @PostMapping("/confirm")
    public ResultMessage confirm(@RequestBody Map<String, String> param) {
        return czpIntoTribeTempService.confirm(param);
    }
}
