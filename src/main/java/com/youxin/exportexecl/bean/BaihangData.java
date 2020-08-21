package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/18 16:58
 * @Description:
 */
@Data
@Builder
public class BaihangData {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("mobile")
    private String mobile;

    @ExcelProperty("name")
    private String name;

    @ExcelProperty("queryReason")
    private String queryReason;

    @ExcelProperty("applicationId")
    private String applicationId;

    @ExcelProperty("idno")
    private String idno;

    @ExcelProperty("loanKey")
    private String loanKey;

    @ExcelProperty("idNumber")
    private String idNumber;

    @ExcelProperty("job")
    private String job;

    @ExcelProperty("type")
    private String type;

    @ExcelProperty("timestamp")
    private String timestamp;
}
