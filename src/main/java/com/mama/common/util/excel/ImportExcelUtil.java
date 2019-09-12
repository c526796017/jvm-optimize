package com.mama.common.util.excel;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportExcelUtil {

    //excel2003扩展名
    public static final String EXCEL03_EXTENSION = ".xls";
    //excel2007扩展名
    public static final String EXCEL07_EXTENSION = ".xlsx";



    /**
     * @Author 杨浩宇
     * @Description 获取整个Excel的内容
     * @Date 10:18 2019/1/23
     * @Param [file]
     * @return 目前最多只读取三个sheet，可以自行扩展
     **/
    public static ImportExcelData readExcel(File excelFile) throws Exception {
        // 获取文件名
        if (excelFile == null) {
            return null;
        }
        String fileName = excelFile.getName();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        if (!prefix.toLowerCase().contains("xls") && !prefix.toLowerCase().contains("xlsx")) {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }

        Map<String, List<List<String>>> result;
        if (fileName.endsWith(EXCEL03_EXTENSION)) { //处理excel2003文件
            ExcelXlsReader excelXls = new ExcelXlsReader();
            result = excelXls.process(excelFile);
        } else if (fileName.endsWith(EXCEL07_EXTENSION)) {//处理excel2007文件
            ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader();
            result = excelXlsxReader.process(excelFile);
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        ImportExcelData data = new ImportExcelData();
        for (Map.Entry<String, List<List<String>>> entry : result.entrySet()) {
            if ("first".equals(entry.getKey())) {
                data.setFirstSheetList(result.get(entry.getKey()));
            }
            if ("second".equals(entry.getKey())) {
                data.setSecondSheetList(result.get(entry.getKey()));
            }
            if ("third".equals(entry.getKey())) {
                data.setThirdSheetList(result.get(entry.getKey()));
            }
        }
        //删除临时转换的文件
        if (excelFile.exists()) {
            excelFile.delete();
        }
        return data;
    }


}
