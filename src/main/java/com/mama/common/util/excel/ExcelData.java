package com.mama.common.util.excel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: cjw
 * @Date: 2018/8/14 11:49
 * @Description: 导出Excel实体类
 */
@Data
public class ExcelData implements Serializable {

    // 表头导航列名称
    private List<String> titles;

    //对应数据的列字段名称
    private List<String> keys;

    // 数据
    private List<?> rows;

    // 页签名称
    private String sheetName;
    
    //错误提示
    private String msg;

    public ExcelData() {
    }

    public ExcelData(List<String> titles, List<String> keys, List<?> rows, String sheetName) {
        this.titles = titles;
        this.keys = keys;
        this.rows = rows;
        this.sheetName = sheetName;
    }


}
