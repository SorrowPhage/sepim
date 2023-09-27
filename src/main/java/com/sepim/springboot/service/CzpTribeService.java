package com.sepim.springboot.service;

import com.sepim.springboot.entity.CzpTribe;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-08
 */
public interface CzpTribeService extends IService<CzpTribe> {

    void deleteData(List<CzpTribe> list);

    void deleteDataSimple(List<CzpTribe> list);

}
