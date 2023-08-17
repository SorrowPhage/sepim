package com.sepim.springboot.service.serviceimpl;

import com.sepim.springboot.entity.LoginLog;
import com.sepim.springboot.mapper.LoginLogMapper;
import com.sepim.springboot.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-08-15
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
