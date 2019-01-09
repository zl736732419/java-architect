package com.zheng.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static void createExcel(OutputStream out,
                                   List<String> head, List<String> headFields,
                                   List<Map<String, Object>> dataList) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet();

        CellStyle headStyle = createHeadCellStyle(workbook);
        CellStyle contentStyle = createContentCellStyle(workbook);

        Row headRow = sheet.createRow(0);
        for (int i = 0; i < head.size(); i++) {
            if (i >= 1) {
                sheet.setColumnWidth(i, 12 * 256);
            }else{
                sheet.setColumnWidth(i, 15 * 256);
            }
            Cell cell = headRow.createCell(i);
            cell.setCellValue(head.get(i));
            cell.setCellStyle(headStyle);
        }

        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        for (int i = 1; i < dataList.size() + 1; i++) {
            Row contentRow = sheet.createRow(i);
            for (int j = 0; j < headFields.size(); j++) {
                Cell cell = contentRow.createCell(j);
                String headField = headFields.get(j);
                Object value = dataList.get(i - 1).get(headField);
                // cell.cloneStyleFrom会导致所有单元格样式发生改变
//                cell.getCellStyle().cloneStyleFrom(contentStyle);
                cell.setCellStyle(contentStyle);
                setCellValue(cell, value);
            }
        }

        workbook.write(out);
    }

    private static CellStyle createHeadCellStyle(Workbook workbook) {
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font headFont = workbook.createFont();
        headFont.setFontName("微软雅黑");
        headFont.setFontHeightInPoints((short) 8);
        headFont.setBold(true);
        headStyle.setFont(headFont);

        headStyle.setBorderBottom(BorderStyle.MEDIUM);
        headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderLeft(BorderStyle.MEDIUM);
        headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderRight(BorderStyle.MEDIUM);
        headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderTop(BorderStyle.MEDIUM);
        headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return headStyle;
    }

    private static void setCellValue(Cell cell,Object value) {
        cell.setCellValue(String.valueOf(value));
    }

    private static CellStyle createContentCellStyle(Workbook workbook) {
        CellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font contentFont = workbook.createFont();
        contentFont.setFontName("微软雅黑");
        contentFont.setFontHeightInPoints((short) 8);
        contentStyle.setFont(contentFont);

        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        contentStyle.setBorderRight(BorderStyle.THIN);
        contentStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return contentStyle;
    }
}