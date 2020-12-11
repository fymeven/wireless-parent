package cn._51even.wireless.core.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/6/11.
 */
public class ExcelUtils {

    private static final Logger logger=LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * title样式
     * @param workbook
     * @return
     */
    public static CellStyle titleStytle(SXSSFWorkbook workbook){
        CellStyle cellStyle=workbook.createCellStyle();
        //设置水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        //字体设置
        Font font = workbook.createFont();
        //设置字体格式
        font.setFontName("黑体");
        //设置字体大小
        font.setFontHeightInPoints((short)28);
        //设置粗体
        font.setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * header样式
     * @param workbook
     * @return
     */
    public static CellStyle headerStyle(SXSSFWorkbook workbook){
        CellStyle cellStyle=workbook.createCellStyle();
        //设置水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //字体设置
        Font font = workbook.createFont();
        //设置字体格式
        font.setFontName("黑体");
        //设置字体大小
        font.setFontHeightInPoints((short)16);
        cellStyle.setFont(font);
        return cellStyle;
    }

    //内容样式
    public static CellStyle cellStyle(SXSSFWorkbook workbook){
        CellStyle cellStyle=workbook.createCellStyle();
        //设置水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        //字体设置
        Font font = workbook.createFont();
        //设置字体格式
        font.setFontName("宋体");
        //设置字体大小
        font.setFontHeightInPoints((short)12);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 导出excel
     * @param response
     * @param exportPath 导出路径
     * @param fileName 导出文件名
     * @param sheetName sheet页名称
     * @param titleName 标题
     * @param headers 表头
     * @param dataList 导出数据List集合
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, String exportPath, String fileName, String sheetName, String titleName, String[] headers, List<?> dataList){
        OutputStream outputStream=null;
        SXSSFWorkbook workbook=null;
        // linux下图形环境不可用，指定无head模式
        System.setProperty("java.awt.headless","true");
        try {
            if (exportPath !=null){
                outputStream=new FileOutputStream(exportPath);
            }else {
                response.reset();
                response.setContentType("application/lat-msdownload;charset=UTF-8");
                response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8")+".xls");
                outputStream = response.getOutputStream();
            }
            workbook= new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);
            ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
            CellStyle titleStyle = titleStytle(workbook);
            CellStyle headerStyle = headerStyle(workbook);
            CellStyle cellStyle = cellStyle(workbook);
            //标题
            Row titleRow = sheet.createRow(0);
            //设置title行高为40
            titleRow.setHeightInPoints(40);
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,headers.length-1));
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(titleStyle);
            titleCell.setCellValue(titleName);
            //header头
            Row headerRow = sheet.createRow(1);
            //设置header行高为28
            headerRow.setHeightInPoints(28);
            for (int headerCellIndex = 0; headerCellIndex < headers.length; headerCellIndex++) {
                Cell headerCell = headerRow.createCell(headerCellIndex);
                headerCell.setCellStyle(headerStyle);
                String cellValue = headers[headerCellIndex];
                headerCell.setCellValue(cellValue);
            }
            //内容
            int rowStart=2;
            for (int rowIndex = rowStart; rowIndex < dataList.size()+rowStart; rowIndex++) {
                Row row = sheet.createRow(rowIndex);
                //设置内容行高为22
                row.setHeightInPoints(22);
                Object rowData = dataList.get(rowIndex - rowStart);
                if (rowData instanceof Map){
                    Map map = (Map) rowData;
                    int cellIndex = 0;
                    for (Object o : map.keySet()) {
                        String key = (String) o;
                        Cell cell = row.createCell(cellIndex);
                        cell.setCellStyle(cellStyle);
                        String cellValue = String.valueOf(map.get(key));
                        cell.setCellValue(cellValue);
                        cellIndex++;
                    }
                }else{
                    Field[] fields = rowData.getClass().getDeclaredFields();
                    for (int cellIndex = 0; cellIndex < fields.length; cellIndex++) {
                        Cell cell = row.createCell(cellIndex);
                        cell.setCellStyle(cellStyle);
                        Field field = fields[cellIndex];
                        if (!field.isAccessible()){
                            field.setAccessible(true);
                        }
                        String cellValue = String.valueOf(field.get(rowData));
                        cell.setCellValue(cellValue);
                    }
                }
            }
            //自动设宽
            for (int column = 0; column < headers.length; column++) {
                sheet.autoSizeColumn(column);
            }
            workbook.write(outputStream);
        }catch (Exception e){
            logger.error("导出excel时出错",e);
        }finally {
            try {
                if (workbook != null){
                    workbook.close();
                }
                if (outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error("资源关闭发生错误"+e);
            }
        }
    }

    public static <T> List<T> getBeanList(MultipartFile file, Class<T> beanClass){
        try {
            List<T> beanList=new ArrayList<>();
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Field[] fields = beanClass.getDeclaredFields();
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                if (sheet != null) {
                    int startRow=2;
                    for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Map<String,String> dataMap=new HashMap<>();
                        Row row = sheet.getRow(rowIndex);
                        if (row != null) {
                            for (int cellIndex = 0; cellIndex < fields.length; cellIndex++) {
                                Cell cell = row.getCell(cellIndex);
                                dataMap.put(fields[cellIndex].getName(), getCellValue(cell));
                            }
                            T bean = beanClass.newInstance();
                            BeanMap beanMap = BeanMap.create(bean);
                            beanMap.putAll(dataMap);
                            beanList.add(bean);
                        }
                    }
                }
            }
            return beanList;
        } catch (Exception e) {
            logger.error("excel转换实体出错", e);
        }
        return Collections.emptyList();
    }

    /**
     * 获取合并单元格集合
     * @param sheet
     * @return
     */
    public static List<CellRangeAddress> getMergeRegionList(Sheet sheet){
        List<CellRangeAddress> list=new ArrayList<>();
        int numMergedRegions = sheet.getNumMergedRegions();
        for (int i = 0; i < numMergedRegions; i++) {
            CellRangeAddress cellRangeAddress=sheet.getMergedRegion(i);
            list.add(cellRangeAddress);
        }
        return list;
    }

    /**
     * 判断是否合并单元格
     * @param sheet
     * @param row
     * @param cell
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int cell){
        int sheetMergeCount=sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress cellRangeAddress=sheet.getMergedRegion(i);
            int firstColumn = cellRangeAddress.getFirstColumn();
            int lastColumn = cellRangeAddress.getLastColumn();
            int firstRow = cellRangeAddress.getFirstRow();
            int lastRow = cellRangeAddress.getLastRow();
            if (row >=firstRow && row <=lastRow){
                if (cell >= firstColumn && cell<=lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row){
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i<sheetMergeCount;i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if(row >= firstRow && row <= lastRow){
                return true;
            }
        }
        return false;
    }

    /**
     * 解析excel不同类型的值
     * @param cell
     * @return
     */
    @SuppressWarnings(value = {"rawtypes"})
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    cellValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    double db = cell.getNumericCellValue();
                    BigDecimal bigDecimal = BigDecimal.valueOf(db);
                    cellValue = bigDecimal.toPlainString();
                }
                break;
            case FORMULA:
                cell.setCellType(CellType.STRING);
                cellValue = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return cellValue;
    }
}
