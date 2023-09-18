package com.sepim.springboot.service.serviceimpl;

import com.sepim.springboot.entity.CzpTribe;
import com.sepim.springboot.mapper.CzpTribeMapper;
import com.sepim.springboot.service.CzpTribeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CzpTribeServiceImpl extends ServiceImpl<CzpTribeMapper, CzpTribe> implements CzpTribeService {

    @Autowired
    private CzpTribeMapper czpTribeMapper;


    @Override
    public void deleteData(List<CzpTribe> list) {
        // for (CzpTribe czpTribe : list) {
        //     czpTribeMapper.deleteBatch(czpTribe);
        // }
        System.out.println("==================");
        czpTribeMapper.dleteByBatch(list);
        System.out.println("==================");
    }
}
