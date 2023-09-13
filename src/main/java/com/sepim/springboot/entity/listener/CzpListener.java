package com.sepim.springboot.entity.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.sepim.springboot.entity.excelmodel.CzpUpload;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/9/11
 */
@Slf4j
public class CzpListener extends AnalysisEventListener<CzpUpload> {

    public static List<CzpUpload> czpCache = new ArrayList<>();

    @Override
    public void invoke(CzpUpload czpUpload, AnalysisContext analysisContext) {
        log.info("解析中······");
        czpCache.add(czpUpload);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析完成");
    }
}
