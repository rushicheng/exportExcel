package com.youxin.exportexecl.web;

import com.sun.media.sound.InvalidDataException;
import com.youxin.exportexecl.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author rushicheng
 * @create 2020/8/19 14:07
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ExportController {

    @Resource
    private ExportService exportService;

    @GetMapping("/export")
    @ResponseBody
    public String exportExecl(@RequestParam String jsonFileName){

        String result = "";

        try {
            result = this.exportService.export(jsonFileName);
        } catch (InvalidDataException e) {
            log.error("exportExcel error,e:",e);
            return e.getMessage();
        }

        log.info("exportExcel success, outputFileName:{}",result);
        return result;
    }
}
