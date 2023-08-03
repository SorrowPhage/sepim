package com.sepim.springboot.service.serviceimpl;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.TransferPythonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: SorrowPhage
 * @date: 2023/8/1
 */
@Service
@Slf4j
public class TransferPythonServiceImpl implements TransferPythonService {

    @Value("${python.script}")
    private String pythonScriptPath;

    @Value("${python.path}")
    private String pythonPath;


    public ResultData recommendationFolders(String id) {
        ResultData resultData = new ResultData();
        String[] arg = new String[]{pythonPath, pythonScriptPath, id};
        Process process;
        BufferedReader in = null;
        try {
            process = Runtime.getRuntime().exec(arg);
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
//                打印python脚本输出
                log.info(line);
            }
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultData;
    }
}
