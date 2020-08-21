package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/19 10:39
 * @Description:
 */
@Data
public class NonRevolvingLoanD60 {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("applyTenantCount")
    private  String applyTenantCount;

    @ExcelProperty("loanCount")
    private  String loanCount;

    @ExcelProperty("loanAmount")
    private  String loanAmount;

    @ExcelProperty("loanTenantCount")
    private  String loanTenantCount;

    @ExcelProperty("maxLoanAmount")
    private  String maxLoanAmount;

    @ExcelProperty("averageLoanAmount")
    private  String averageLoanAmount;

    @ExcelProperty("overdueLoanCount")
    private  String overdueLoanCount;

}
