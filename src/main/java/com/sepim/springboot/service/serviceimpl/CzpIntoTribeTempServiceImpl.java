package com.sepim.springboot.service.serviceimpl;

import com.alibaba.excel.EasyExcel;
import com.sepim.springboot.db.DS;
import com.sepim.springboot.entity.CzpIntoTribeTemp;
import com.sepim.springboot.entity.ResultMessage;
import com.sepim.springboot.entity.excelmodel.CzpUpload;
import com.sepim.springboot.entity.listener.CzpListener;
import com.sepim.springboot.entity.vo.CzpIntoTribeTempVO;
import com.sepim.springboot.mapper.CzpIntoTribeTempMapper;
import com.sepim.springboot.service.CzpIntoTribeTempService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-08
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CzpIntoTribeTempServiceImpl extends ServiceImpl<CzpIntoTribeTempMapper, CzpIntoTribeTemp> implements CzpIntoTribeTempService {

    private final CzpIntoTribeTempMapper czpIntoTribeTempMapper;


    @DS("czpDB")
    @Override
    public ResultMessage uploadCzpData(MultipartFile file, String userId) {
        try {
            EasyExcel.read(file.getInputStream(), CzpUpload.class, new CzpListener());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (CzpUpload czpUpload : CzpListener.czpCache) {
            System.out.println(czpUpload);
        }
        return ResultMessage.success(CzpListener.czpCache);
    }


    /**
     * 获取批次号列表
     * @param userId 用户id
     * @return 列表数据
     */
    @DS("czpDB")
    @Override
    public ResultMessage batchNoList(String userId) {
        List<String> list = czpIntoTribeTempMapper.batchNoList(userId);
        return ResultMessage.success(list);
    }

    /**
     * 获得临时表中的数据
     * @param userId 用户id
     * @param batchNo 批次号
     * @return 临时表数据
     */
    @DS("czpDB")
    @Override
    public ResultMessage getTempData(String userId, String batchNo) {
        List<CzpIntoTribeTempVO> tempData = czpIntoTribeTempMapper.getTempData(userId, batchNo);
        return ResultMessage.success(tempData);
    }

    /**
     * ES 删除
     * 删除，删除一条数据
     * @param czpIntoTribeTempVO 删除条件
     * @return 删除状态
     */
    @DS("czpDB")
    @Override
    public ResultMessage delete(CzpIntoTribeTempVO czpIntoTribeTempVO) {

        return null;
    }

    /**
     * ES 确认
     * 确认
     * @param param 带批次，用户id，父id，操作人id
     * @return 确认状态
     */
    @DS("czpDB")
    @Override
    public ResultMessage confirm(Map<String, String> param) {
        return null;
    }


}
