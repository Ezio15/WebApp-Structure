package com.twitter.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author 
 *
 */
public class ExcelUtils {

	static HSSFWorkbook ExcelWBook;
	static HSSFSheet ExcelWSheet;
	static HSSFCell Cell;
	public static HSSFRow Row;
    public static PropertiesReader props = PropertiesReader.getInstance();
	private static final Logger logger = Logger.getLogger(ExcelUtils.class);
	
	static String excelPath = props.getInput("excelPath");

//	/** Read Input Data Sheet */
//	public int readInputDataSheet() throws IOException {
//		FileInputStream fs = new FileInputStream(props.getInput(Constants.INPUT_FILE));
//		HSSFSheet excelWSheet = null;
//		excelWSheet = ExcelUtils.readFile(fs);
//		logger.info("File Readed-" + excelWSheet.getRow(0).getCell(0));
//		HSSFSheet testCaseSheet = null;
//		testCaseSheet = ExcelUtils.readSheet(excelWSheet, props.getInput(Constants.TEST_DATA_SHEET_NAME));
//		int testCaseSheetRows = ExcelUtils.numberOfRows(testCaseSheet);
//
//		return testCaseSheetRows;
//	}

	/** Read File */
	public static HSSFSheet readFile(FileInputStream fs) throws IOException {
		try {
			ExcelWBook = new HSSFWorkbook(fs);
			ExcelWSheet = ExcelWBook.getSheet("Accounts");
			logger.info("Main sheet Readed");
		} catch (Exception e) {
			logger.error("Error occurred : While Reading main Sheet--" + e);
			e.printStackTrace();
		}
		return ExcelWSheet;
	}

	/** Reading sheet name */
	public static HSSFSheet readSheet(HSSFSheet excelWSheet, String testCaseSheetName) {
		try {
			excelWSheet = ExcelWBook.getSheet(testCaseSheetName);
			logger.info("Reading test case sheet --" + testCaseSheetName);
		} catch (Exception e) {
			logger.error("Error occurred : While Reading Test case sheet name -- " + e);
			e.printStackTrace();
		}
		return excelWSheet;
	}

	/** Writing data into sheet */
	@SuppressWarnings("deprecation")
	public static void setCellData(String sheetName, String Result, int Rows, int Columns) throws Exception {
		try {
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			Row = ExcelWSheet.getRow(Rows);
			if (Row == null)
				Row = ExcelWSheet.createRow(Rows);

			Cell = Row.getCell(Columns);
			

			if (Cell == null) {
				Cell = Row.createCell(Columns);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}

			FileOutputStream fileOut = new FileOutputStream(excelPath);
			ExcelWBook.write(fileOut);
			logger.info("Writing values into sheet");
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			logger.error("Error Occurred : While Writing data into Sheet -- " + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Error Occurred : While Writing data into Sheet -- " + e);
			throw (e);
		}
	}

	/** Getting total number of rows in sheet */
	public static int numberOfRows(HSSFSheet excelWSheet) {
		int k = 0, f = 0, rowcount = 0;
		boolean bool = true;
		/** Getting spreadsheet row value */
		try{
		while (bool) {
			HSSFCell Cell = null;
			try {
				Cell = excelWSheet.getRow(k).getCell(f);
			} catch (Exception e) {
				logger.error("Getting Number of Rows");
			}
			if (isCellEmpty(Cell))
				bool = false;
			else
				rowcount++;
		k++;
		}
		}catch (Exception e) {
			logger.error("Getting Number of Rows");
		}
		return rowcount;
	}

	/** Getting total number of columns in sheet */
	public static int numberOfCols(HSSFSheet excelWSheet) {
		int k = 0, f = 0, colCount = 0;
		boolean bool = true;
		try{
		/** Getting spreadsheet column value */
		while (bool) {
			HSSFCell Cell = null;
			try {
				Cell = excelWSheet.getRow(k).getCell(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (isCellEmpty(Cell))
				bool = false;
			else
				colCount++;
			f++;
		}
		}catch (Exception e) {
			logger.error("Getting Number of Cols");
		}
		return colCount;
	}

	/** Check Cell is Empty */
	@SuppressWarnings({ "static-access", "deprecation" })
	private static boolean isCellEmpty(HSSFCell cell) {
		if (cell == null || cell.getCellType() == cell.CELL_TYPE_BLANK) {
			return true;
		}

		if (cell.getCellType() == cell.CELL_TYPE_STRING && cell.getStringCellValue().isEmpty()) {
			return true;
		}

		return false;
	}

//	/** Read Input Data */
//	public static HSSFCell readInputData(int row, int col) throws FileNotFoundException {
//		HSSFCell cell = null;
//		@SuppressWarnings("unused")
//		HSSFWorkbook excelWBook;
//		HSSFSheet excelWSheet = null;
//		try {
//			FileInputStream fs = new FileInputStream(props.getInput(Constants.INPUT_FILE));
//			excelWBook = new HSSFWorkbook(fs);
//			excelWSheet = ExcelWBook.getSheet(props.getInput(Constants.TEST_DATA_SHEET_NAME));
//			cell = excelWSheet.getRow(row).getCell(col);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return cell;
//	}
	
}
