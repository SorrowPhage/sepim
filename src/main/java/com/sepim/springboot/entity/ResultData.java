package com.sepim.springboot.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResultData {
    private String flag;
    private Object data;
}
