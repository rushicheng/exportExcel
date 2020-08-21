package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/19 10:42
 * @Description:
 */
@Data
public class RevolvingLoanD160 {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("applyTenantCount")
    private  String applyTenantCount;

    @ExcelProperty("accountCount")
    private  String accountCount;

    @ExcelProperty("creditLimitSum")
    private  String creditLimitSum;

    @ExcelProperty("lendingAmount")
    private  String lendingAmount;

    @ExcelProperty("overdueAccountCount")
    private  String overdueAccountCount;

}
