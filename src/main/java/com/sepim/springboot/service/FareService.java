package com.sepim.springboot.service;

import com.sepim.springboot.entity.ResultData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FareService {
    public ResultData fare(MultipartFile file);
}
