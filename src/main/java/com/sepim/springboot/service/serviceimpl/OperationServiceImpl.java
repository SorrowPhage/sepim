package com.sepim.springboot.service.serviceimpl;

import cn.hutool.core.date.DateUtil;
import com.sepim.springboot.entity.Operation;
import com.sepim.springboot.entity.example.OperationExample;
import com.sepim.springboot.mapper.OperationMapper;
import com.sepim.springboot.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author: SorrowPhage
 * @date: 2023/7/31
 */
@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationMapper operationMapper;

    @Override
    public void insert(String id,String uid) {
        Operation operation = new Operation();
        operation.setFolderId(id);
        operation.setUserId(uid);
        operation.setTime(DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd"));
        operation.setOperationType("新增");
        operationMapper.insertSelective(operation);
    }

    @Override
    public void update(String id) {
        Operation operation = new Operation();
        operation.setFolderId(id);
        operation.setTime(DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd"));
        operation.setOperationType("修改");
        operationMapper.insertSelective(operation);
    }

    @Override
    public void delete(String id) {
        OperationExample operationExample = new OperationExample();
        OperationExample.Criteria criteria = operationExample.createCriteria();
        criteria.andFolderIdEqualTo(id);
        operationMapper.deleteByExample(operationExample);
    }
}
