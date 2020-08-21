package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/19 10:45
 * @Description:
 */
@Data
public class QueryHistory {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("tenantType")
    private String tenantType;

    @ExcelProperty("tenantName")
    private String tenantName;

    @ExcelProperty("userId")
    private String userId;

    @ExcelProperty("date")
    private String date;

    @ExcelProperty("reason")
    private String reason;
}
