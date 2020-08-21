package com.youxin.exportexecl.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author rushicheng
 * @create 2020/8/19 10:34
 * @Description:
 */
@Data
public class PersonalProfile {

    @ExcelProperty("dataId")
    private String dataId;

    @ExcelProperty("name")
    private String name;

    @ExcelProperty("pid")
    private String pid;

    @ExcelProperty("mobileCount")
    private String mobileCount;
}
