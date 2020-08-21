package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/19 10:37
 * @Description:
 */
@Data
public class NonRevolvingLoanSummary {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("loanCount")
    private  String loanCount;

    @ExcelProperty("openLoanCount")
    private  String openLoanCount;

    @ExcelProperty("remainingAmount")
    private  String remainingAmount;

    @ExcelProperty("remainingOverdueLoanCount")
    private  String remainingOverdueLoanCount;

    @ExcelProperty("remainingOverdueAmount")
    private  String remainingOverdueAmount;

    @ExcelProperty("remainingMaxOverdueStatus")
    private String remainingMaxOverdueStatus;

    @ExcelProperty("overdueCount")
    private  String overdueCount;

    @ExcelProperty("maxOverdueStatus")
    private String maxOverdueStatus;

}
