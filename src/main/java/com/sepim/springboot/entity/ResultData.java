package com.sepim.springboot.entity;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Data
@Component
public class ResultData {
    private String flag;
    private Object data;
}
