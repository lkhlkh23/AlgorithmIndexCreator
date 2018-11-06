package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelCreator {

    private static final String DIRECTORY_PATH = "/Users/lee_ki_hyun/Desktop";
    private static final int MAXIMUM_DIGIT_WIDTH = 7;
    private static final int PADDING_WIDTH = 10;
    private static final String SHEET_NAME = "BaekJoon Algorithm List";
    private static Sheet sheet;
    private static Workbook xlsWb;
    private static int rowIndex = 0;
    private static Map<String, CellStyle> cellStyleMap = new HashMap<>();

    static {
        /* Workbook 생성 */
        xlsWb = new HSSFWorkbook();
        /* Sheet 생성 */
        sheet = xlsWb.createSheet(SHEET_NAME);
        /* CellStyle 초기화 */
        cellStyleMap.put("sorted_bold_lined_big", createStyleCell(true, true, true, false, true));
        cellStyleMap.put("sorted_bold", createStyleCell(true, true, false, false, false));
        cellStyleMap.put("sorted", createStyleCell(true, false, false, false, false));
        cellStyleMap.put("colored", createStyleCell(false, false, false, true, false));
        cellStyleMap.put("sorted_colored", createStyleCell(true, false, false, true, false));
        cellStyleMap.put("default", createStyleCell(false, false, false, false, false));
    }

    private ExcelCreator() {

    }

    /* 컬럼 너비 설정하는 메소드 */
    public static void setSheetInfo() {
        sheet.setColumnWidth(0, calculateColumnWidth(10));
        sheet.setColumnWidth(1, calculateColumnWidth(40));
        sheet.setColumnWidth(2, calculateColumnWidth(40));
        sheet.setColumnWidth(3, calculateColumnWidth(10));
        sheet.setColumnWidth(4, calculateColumnWidth(10));
    }

    /* 칼럼 너비 길이 계산하는 메소드 */
    public static int calculateColumnWidth(int numberOfCharacter) {
        /* Truncate([{Number of Visible Characters} * {Maximum Digit Width} + {5 pixel padding}]/{Maximum Digit Width}*256) */
        /* Maximum Digit : 7px(11pt) */
        return Math.round((numberOfCharacter * MAXIMUM_DIGIT_WIDTH + PADDING_WIDTH) / MAXIMUM_DIGIT_WIDTH * 256);
    }

    public static CellStyle createStyleCell(boolean isSorted, boolean isBold, boolean isLined, boolean isColored, boolean isBig) {
        CellStyle cellStyle = xlsWb.createCellStyle();
        Font font = xlsWb.createFont();
        cellStyle.setWrapText(true);

        if(isSorted) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER); //가운데 정렬
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //높이 가운데 정렬
        }

        if(isLined) {
            font.setUnderline(Font.U_SINGLE);
        }

        if(isColored) {
            //배경색
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        if(isBig) {
            font.setFontHeight((short)(14*20));
        }

        font.setBold(isBold);
        cellStyle.setFont(font);

        return cellStyle;
    }

    public static void createExcelFile(List<FileComponent> fileComponents) {
        for(FileComponent fileComponent : fileComponents) {
            createRow(fileComponent);
        }
    }

    public static void createSummary() throws IOException {
        // 병합 열시작, 열종료, 행시작, 행종료
        sheet.addMergedRegion(new CellRangeAddress(0,2,0,2));
        Row row = sheet.createRow(rowIndex++);
        createCell(row, 0, "백준 알고리즘 문제풀이 현황", cellStyleMap.get("sorted_bold_lined_big"));

        createCell(row, 3, "총 문제", cellStyleMap.get("sorted_bold"));
        createCell(row, 4, String.valueOf(SourceReader.getQustionCount()), cellStyleMap.get("sorted"));


        row = sheet.createRow(rowIndex++);
        createCell(row, 3, "해결", cellStyleMap.get("sorted_bold"));
        createCell(row, 4, String.valueOf(SourceReader.getQustionCount() - SourceReader.getNotSolvedQuestionCount())
                    , cellStyleMap.get("sorted"));


        row = sheet.createRow(rowIndex++);
        createCell(row, 3, "미해결", cellStyleMap.get("sorted_bold"));
        createCell(row, 4, String.valueOf(SourceReader.getNotSolvedQuestionCount())
                    , cellStyleMap.get("sorted"));
    }


    public static void createHeader() {
        Row row = sheet.createRow(rowIndex++);
        CellStyle cellStyle = cellStyleMap.get("sorted_bold");
        createCell(row, 0, "번호", cellStyle);
        createCell(row, 1, "문제명", cellStyle);
        createCell(row, 2, "URL", cellStyle);
        createCell(row, 3, "완료유무", cellStyle);
        createCell(row, 4, "재풀이필요유무", cellStyle);
    }

    public static void createRow(FileComponent fileComponent) {
        // 줄 생성
        boolean isComplete = fileComponent.isComplete();
        String cellStyleText1 = "colored";
        String cellStyleText2 = "sorted_colored";
        if(isComplete) {
            cellStyleText1 = "default";
            cellStyleText2 = "sorted";
        }
        Row row = sheet.createRow(rowIndex++);
        createCell(row, 0, fileComponent.getQustionNo(), cellStyleMap.get(cellStyleText1));
        createCell(row, 1, fileComponent.getQustionName(), cellStyleMap.get(cellStyleText1));
        createCell(row, 2, fileComponent.getUrl(), cellStyleMap.get(cellStyleText1));
        createCell(row, 3, getCompelteSign(fileComponent.isComplete()), cellStyleMap.get(cellStyleText2));
        createCell(row, 4, fileComponent.getReviewed(), cellStyleMap.get(cellStyleText2));
    }

    public static String getCompelteSign(boolean flag) {
        if(flag) {
            return "O";
        }
        return "X";
    }

    public static void createCell(Row row, int cellIndex, String contents, CellStyle cellStyle) {
        Cell cell = null;
        cell = row.createCell(cellIndex);
        cell.setCellValue(contents);
        cell.setCellStyle(cellStyle); // 셀 스타일 적용
    }

    public static void saveExcel() {
        try {
            File xlsFile = new File(DIRECTORY_PATH + "/baekJoonAlgorithm.xls");
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            xlsWb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}