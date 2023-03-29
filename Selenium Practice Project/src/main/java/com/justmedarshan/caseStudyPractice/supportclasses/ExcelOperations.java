package com.justmedarshan.caseStudyPractice.supportclasses;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelOperations {

    private static XSSFWorkbook excelWorkbook;
    private static XSSFSheet excelSheet;
    private static String excelPath;

    public ExcelOperations(String filePath) throws IOException {

        excelPath = filePath;
        excelWorkbook = new XSSFWorkbook(filePath);
    }

    public ExcelOperations(String filePath, String sheetName) throws IOException {

        excelPath = filePath;
        excelWorkbook = new XSSFWorkbook(filePath);
        excelSheet = excelWorkbook.getSheet(sheetName);
    }

    public static Object getCellData(int rowIndex, int columnIndex){

        DataFormatter formatter = new DataFormatter();
        Object cellData = formatter.formatCellValue(excelSheet.getRow(rowIndex).getCell(columnIndex));
        return cellData;
    }

    public static void updateLastAccessedRow(String filePath, String sheetName, int lastAccessedRowIndex, int columnIndex, int lastRowNum) throws IOException {

        FileInputStream inputStream = new FileInputStream(filePath);
        XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);
        XSSFSheet newSheet = newWorkbook.getSheet(sheetName);
        if(lastAccessedRowIndex == -1){

            ((newSheet.getRow(1)).getCell(columnIndex)).setCellValue("TRUE");
        }else if(lastAccessedRowIndex+1 >= lastRowNum){

            ((newSheet.getRow(1)).getCell(columnIndex)).setCellValue("TRUE");
            ((newSheet.getRow(lastAccessedRowIndex)).getCell(columnIndex)).setCellValue("");
        }else{

            ((newSheet.getRow(lastAccessedRowIndex + 1)).getCell(columnIndex)).setCellValue("TRUE");
            ((newSheet.getRow(lastAccessedRowIndex)).getCell(columnIndex)).setCellValue("");
        }
        inputStream.close();
        FileOutputStream outputStream = new FileOutputStream(filePath);
        newWorkbook.write(outputStream);
        newWorkbook.close();
        outputStream.close();
    }

    public ParabankUser getNextRowData() throws IOException {

        int lastColumnNum = excelSheet.getRow(0).getLastCellNum();
        int numberOfRows = excelSheet.getPhysicalNumberOfRows(), lastAccessFoundIndex = getLastAccessedRowIndex(excelSheet), dataIndex = -1;
        if(lastAccessFoundIndex == -1 || lastAccessFoundIndex + 1 >= numberOfRows)
            dataIndex = 0;
        else
            dataIndex = lastAccessFoundIndex;
        ParabankUser nextPerson = new ParabankUser();
        for (int j = 0; j < lastColumnNum - 1; j++) {

            nextPerson.setDataByIndex(j, getCellData(dataIndex + 1, j).toString());
        }
        updateLastAccessedRow(excelPath, excelSheet.getSheetName(), lastAccessFoundIndex, lastColumnNum-1, numberOfRows);
        return nextPerson;
    }

    public int getLastAccessedRowIndex(XSSFSheet givenSheet) throws IOException {

        int lastColumnNum = givenSheet.getRow(0).getLastCellNum();
        int numberOfRows = givenSheet.getPhysicalNumberOfRows(), lastAccessFoundIndex = -1, index = 1;
        for (;index < numberOfRows; index++) {

            if ((getCellData(index, lastColumnNum - 1).toString()).equals("TRUE")) {

                lastAccessFoundIndex = index;
                break;
            }else
                continue;
        }
        //System.out.println("Index = " + lastAccessFoundIndex);
        return lastAccessFoundIndex;
    }

    public boolean locateUserNameAndWriteAccountNo(String sheetName, String firstname, String lastname, long accountNumber) throws IOException{

        int foundIndex = -1;
        boolean foundFlag = false;
        FileInputStream fileInputStream = new FileInputStream(excelPath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        int numberOfRows = sheet.getPhysicalNumberOfRows();
        if(numberOfRows == 0){

            System.out.println("No rows data in the sheet." + numberOfRows);
            sheet.createRow(0).createCell(0).setCellValue(firstname + " " + lastname);
            foundFlag = true;
            foundIndex = 0;
        }else{

            System.out.println("There are more than one cell in 0th row." + numberOfRows);
            int index = 0;
            for(; index < numberOfRows; index++){

                if(sheet.getRow(index).getCell(0).getStringCellValue().equals(firstname + " " + lastname)){

                    foundIndex = index;
                    foundFlag = true;
                    break;
                }else
                    continue;
            }
            if(!foundFlag){

                sheet.createRow(index).createCell(0).setCellValue(firstname + " " + lastname);
                foundIndex = index;
                foundFlag = true;
            }
        }
        if(foundFlag){

            int numberOfCellsInColumn = sheet.getRow(foundIndex).getPhysicalNumberOfCells();
            System.out.println("cells in found row: " + numberOfCellsInColumn);
            sheet.getRow(foundIndex).createCell(numberOfCellsInColumn).setCellValue(accountNumber);
        }
        fileInputStream.close();
        FileOutputStream fileOutputStream = new FileOutputStream(excelPath);
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
        return foundFlag;
    }

}
