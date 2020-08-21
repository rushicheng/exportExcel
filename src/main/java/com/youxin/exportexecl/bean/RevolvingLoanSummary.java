package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/19 10:41
 * @Description:
 */
@Data
public class RevolvingLoanSummary {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("accountCount")
    private  String accountCount;

    @ExcelProperty("validAccountCount")
    private  String validAccountCount;

    @ExcelProperty("creditLimitSum")
    private  String creditLimitSum;

    @ExcelProperty("maxCreditLimitPerTenant")
    private  String maxCreditLimitPerTenant;

    @ExcelProperty("remainingAmount")
    private  String remainingAmount;

    @ExcelProperty("remainingOverdueAccountCount")
    private  String remainingOverdueAccountCount;

    @ExcelProperty("remainingOverdueAmount")
    private  String remainingOverdueAmount;

    @ExcelProperty("remainingMaxOverdueStatus")
    private String remainingMaxOverdueStatus;

    @ExcelProperty("overdueCount")
    private String overdueCount;

    @ExcelProperty("maxOverdueStatus")
    private String maxOverdueStatus;
}
