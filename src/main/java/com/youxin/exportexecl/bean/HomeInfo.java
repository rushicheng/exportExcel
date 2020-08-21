package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/19 10:34
 * @Description:
 */
@Data
public class HomeInfo {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("homeAddress")
    private String homeAddress;

    @ExcelProperty("date")
    private String date;

}
