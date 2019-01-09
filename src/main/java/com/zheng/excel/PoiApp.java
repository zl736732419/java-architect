package com.zheng.excel;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhenglian
 * @Date 2019/1/9
 */
public class PoiApp {
    private static String fileName = "学生信息表" + System.currentTimeMillis() + ".xlsx";
    
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    public static void main(String[] args) throws Exception {
//        printExcel1();
        printExcel2();
    }

    private static void printExcel2() throws Exception {
        List<String> headers = Lists.newArrayList("名称", "性别", "年龄", "学校", "班级");
        List<String> headerFields = Lists.newArrayList("stuName", "stuSex", "stuAge", "stuSchoolName", "stuClassName");
        List<Map<String, Object>> list = getData();
        FileOutputStream os = new FileOutputStream(new File("C:\\Users\\zhenglian\\Desktop\\" + fileName));
        ExcelUtils.createExcel(os, headers, headerFields, list);
    }

    private static void printExcel1() {
        List<Map<String, Object>> list = getData();
        String[] title = {"名称", "性别", "年龄", "学校", "班级"};
        String sheetName = "学生信息表";
        String[][] content = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            Map<String, Object> obj = list.get(i);
            content[i][0] = (String) obj.get("stuName");
            content[i][1] = (String) obj.get("stuSex");
            content[i][2] = (String) obj.get("stuAge");
            content[i][3] = (String) obj.get("stuSchoolName");
            content[i][4] = (String) obj.get("stuClassName");
        }
        HSSFWorkbook wb = getHSSFWorkbook(sheetName, title, content, null);
        try {
            FileOutputStream os = new FileOutputStream(new File("C:\\Users\\zhenglian\\Desktop\\" + fileName));
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Map<String, Object>> getData() {
        List<Map<String, Object>> students = new ArrayList<>();
        Map<String, Object> student = new HashMap<>();
        student.put("stuName", "zhangsan");
        student.put("stuSex", "m");
        student.put("stuAge", "20");
        student.put("stuSchoolName", "xx school");
        student.put("stuClassName", "No. 4 class 6");
        students.add(student);
        return students;
    }
}
