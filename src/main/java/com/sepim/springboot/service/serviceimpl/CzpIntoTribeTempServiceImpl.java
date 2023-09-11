package com.sepim.springboot.service.serviceimpl;

import com.sepim.springboot.entity.CzpIntoTribeTemp;
import com.sepim.springboot.entity.ResultMessage;
import com.sepim.springboot.mapper.CzpIntoTribeTempMapper;
import com.sepim.springboot.service.CzpIntoTribeTempService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-08
 */
@Service
@RequiredArgsConstructor
public class CzpIntoTribeTempServiceImpl extends ServiceImpl<CzpIntoTribeTempMapper, CzpIntoTribeTemp> implements CzpIntoTribeTempService {


    @Override
    public ResultMessage uploadCzpData(MultipartFile file, String userId) {

        return null;
    }


}
