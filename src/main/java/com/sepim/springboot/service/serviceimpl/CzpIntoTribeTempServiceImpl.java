package com.sepim.springboot.service.serviceimpl;

import com.alibaba.excel.EasyExcel;
import com.sepim.springboot.entity.CzpIntoTribeTemp;
import com.sepim.springboot.entity.ResultMessage;
import com.sepim.springboot.entity.excelmodel.CzpUpload;
import com.sepim.springboot.entity.listener.CzpListener;
import com.sepim.springboot.mapper.CzpIntoTribeTempMapper;
import com.sepim.springboot.service.CzpIntoTribeTempService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @Override
    public ResultMessage batchNoList(String userId) {
        List<String> list = czpIntoTribeTempMapper.batchNoList(userId);
        return ResultMessage.success(list);
    }


}
