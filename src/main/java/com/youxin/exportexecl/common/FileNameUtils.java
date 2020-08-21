package com.youxin.exportexecl.common;

/**
 * @Author rushicheng
 * @create 2020/8/19 18:53
 * @Description:
 */
public class FileNameUtils {

    public static String getFileNameNoEx(String filename) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int dot = filename.lastIndexOf('.'); 
            if ((dot >-1) && (dot < (filename.length()))) { 
                return filename.substring(0, dot); 
            } 
        } 
        return filename; 
    }
}
