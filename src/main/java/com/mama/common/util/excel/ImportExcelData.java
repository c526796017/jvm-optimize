package com.mama.common.util.excel;


import java.util.List;

/**
 * 功能描述: 导入Excel表解析出来的数据，暂时最多支持三个sheet
 * 版本信息: Copyright (c)2018
 * 公司信息: 康之家
 * 开发人员: cjw
 * 版本日志: 1.0
 * 创建日期:  2018/12/25
 * 修改历史:
 * 时间 开发者 版本号 修改内容
 * ——————————————————————
 * 2018/12/25 cjw 1.0 1.0 Version
 * ——————————————————————
 */


public class ImportExcelData {

    //第一个sheet
    private List<List<String>> firstSheetList;

    //第二个sheet
    private List<List<String>> secondSheetList;

    //第三个sheet
    private List<List<String>> thirdSheetList;

    public List<List<String>> getFirstSheetList() {
        return firstSheetList;
    }

    public void setFirstSheetList(List<List<String>> firstSheetList) {
        this.firstSheetList = firstSheetList;
    }

    public List<List<String>> getSecondSheetList() {
        return secondSheetList;
    }

    public void setSecondSheetList(List<List<String>> secondSheetList) {
        this.secondSheetList = secondSheetList;
    }

    public List<List<String>> getThirdSheetList() {
        return thirdSheetList;
    }

    public void setThirdSheetList(List<List<String>> thirdSheetList) {
        this.thirdSheetList = thirdSheetList;
    }
}
