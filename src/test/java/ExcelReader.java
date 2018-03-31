
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	// String excelLocation = "user.dir" + "\\src\\Login.xlsx";
	// String sheetName = "Login";
	public String[][] DataReader(String excelLocation, String sheetName) throws IOException {
		String[][] dataSet;
		FileInputStream file = new FileInputStream(new File(excelLocation));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet Sheet = workbook.getSheet(sheetName);
		int totalRow = Sheet.getLastRowNum() + 1;
		int totalcellColumn = Sheet.getRow(0).getLastCellNum();
		dataSet = new String[totalRow - 1][totalcellColumn];
		Iterator<Row> rowIterator = Sheet.iterator();
		int i = 0;
		int t = 0;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			if (i++ != 0) {
				int k = t;
				t++;
				Iterator<Cell> cellIterator = row.cellIterator();
				int j = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						dataSet[k][j++] = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_STRING:
						dataSet[k][j++] = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						dataSet[k][j++] = cell.getStringCellValue();
						break;
					}
				}
			}

		}
		return dataSet;
	}

}
