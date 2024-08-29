package com.ogon.umv.common.utility;

import com.ogon.entity.TestDataEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {
    static String SHEET = "Policy";

    public static List<TestDataEntity> excelToDatabase(InputStream is) {

        try {
            List<TestDataEntity> testDataEntities = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            for (Row row : sheet) {
                // skip Excel Header
                if (row.getRowNum() == 0 || convertCellValueToString(row.getCell(0)).isEmpty()) {
                    continue;
                }
                TestDataEntity testdata = new TestDataEntity();
                for (Cell cell : row) {
                    String cellValue = convertCellValueToString(cell);

                    switch (cell.getColumnIndex()) {
                        case 0:
                            testdata.setCarrierId(cellValue);
                            break;
                        case 1:
                            testdata.setStateCd(cellValue);
                            break;
                        case 2:
                            testdata.setLob(cellValue);
                            break;
                        case 3:
                            testdata.setTestCaseId(cellValue);
                            break;
                        case 4:
                            testdata.setTestCaseName(cellValue);
                            break;
                        case 5:
                            testdata.setUiName(cellValue);
                            break;
                        case 6:
                            testdata.setUiSequence(Integer.parseInt(cellValue));
                            break;
                        case 7:
                            testdata.setFieldSequence(Integer.parseInt(cellValue));
                            break;
                        case 8:
                            testdata.setFieldName(cellValue);
                            break;
                        case 9:
                            testdata.setFieldId(cellValue);
                            break;
                        case 10:
                            testdata.setFieldType(cellValue);
                            break;
                        case 11:
                            testdata.setDefaultValue(cellValue);
                            break;
                        case 12:
                            testdata.setShowField(cellValue);
                            break;
                        case 13:
                            testdata.setReadOnly(cellValue);
                            break;
                        case 14:
                            testdata.setValidations(cellValue);
                            break;
                        case 15:
                            testdata.setIsSleepNeeded(cellValue);
                            break;
                        case 16:
                            testdata.setSleepTime(cellValue);
                            break;
                        case 17:
                            testdata.setShouldWaitForCompletion(cellValue);
                            break;
                        case 18:
                            testdata.setShouldWaitForAddrVerification(cellValue);
                            break;
                        case 19:
                            testdata.setAddrVerificationImgField(cellValue);
                            break;
                        case 20:
                            testdata.setShouldPressTab(cellValue);
                            break;
                        case 21:
                            testdata.setDriverName(cellValue);
                            break;
                        case 22:
                            testdata.setLinkedFieldId(cellValue);
                            break;
                        default:
                            break;
                    }
                }
                testDataEntities.add(testdata);
            }
            workbook.close();
            return testDataEntities;
        } catch (Exception e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private static String convertCellValueToString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING ->
                    cell.getStringCellValue().isEmpty() ? "" : cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }
}
