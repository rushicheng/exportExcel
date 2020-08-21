package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/19 10:21
 * @Description:
 */
@Data
public class ReportHeader {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("reportId")
    private String reportId;

    @ExcelProperty("queryResult")
    private String queryResult;

    @ExcelProperty("reportTime")
    private String reportTime;
}
