package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/19 10:36
 * @Description:
 */
@Data
public class WorkInfo {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("workName")
    private String workName;

    @ExcelProperty("workAddress")
    private String workAddress;

    @ExcelProperty("date")
    private String date;
}
