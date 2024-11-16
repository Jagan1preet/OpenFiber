package pageEvents.adminPageEvents;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import static base.BaseTest.logger;

public class ExcelComparator {
    public static void compareFiles() {
        String newfile = "CMTFile/Computo_Ripianificato_TEST_SEC.xlsx";
        String oldfile = "dataToCompare/Computo_Ripianificato_TEST_SEC.xlsx";
        try {
            compareExcelFiles(newfile, oldfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void compareExcelFiles(String newfile, String oldfile) throws IOException {
        logger.info("Read Data from the Excel Files");
        try (FileInputStream newCMTFile1 = new FileInputStream(newfile);
             FileInputStream oldCMTFile = new FileInputStream(oldfile);
             Workbook workbook1 = new XSSFWorkbook(newCMTFile1);
             Workbook workbook2 = new XSSFWorkbook(oldCMTFile)) {
            Sheet sheet1 = workbook1.getSheetAt(0);
            Sheet sheet2 = workbook2.getSheetAt(0);
            logger.info("Compare Data in both the files");
            int rowCount = Math.max(sheet1.getPhysicalNumberOfRows(), sheet2.getPhysicalNumberOfRows());
            boolean identical = true;
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                Row row1 = sheet1.getRow(rowIndex);
                Row row2 = sheet2.getRow(rowIndex);
                if (row1 == null && row2 == null) {
                    continue; // Both rows are empty
                }
                if (row1 == null || row2 == null) {
                    identical = false;
                    System.out.println("Difference found at row " + (rowIndex + 1) + ": One of the rows is empty.");
                    continue;
                }
                int cellCount = Math.max(row1.getPhysicalNumberOfCells(), row2.getPhysicalNumberOfCells());
                for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                    Cell cell1 = row1.getCell(cellIndex);
                    Cell cell2 = row2.getCell(cellIndex);
                    String value1 = (cell1 == null) ? "" : cell1.toString();
                    String value2 = (cell2 == null) ? "" : cell2.toString();
                    if (!value1.equals(value2)) {
                        identical = false;
                        System.out.println("Difference found at row " + (rowIndex + 1) + ", column " + (cellIndex + 1) + ": " +
                                "newfile has '" + value1 + "'; oldfile has '" + value2 + "'");
                    }
                }
            }
            if (identical) {
                System.out.println("Data in Both The Files are Same");
            }
        }
    }
}