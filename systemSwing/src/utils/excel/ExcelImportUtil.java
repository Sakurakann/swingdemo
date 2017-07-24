package utils.excel;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelImportUtil {

	private final static String excel = ".xls"; // 2003- 版本的excel
	private final static String excelx = ".xlsx"; // 2007+ 版本的excel

	public ExcelImportUtil() {
	}

	public List<List<Object>> getBankListByExcel(InputStream iStream,
			String fileName) throws Exception {
		List<List<Object>> list = null;

		Workbook workbook = this.getWorkbook(iStream, fileName);
		
		if (null == workbook) {
			System.out.println("文件为空");
			throw new Exception("文件为空");
		}
		
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		
		int firstRowNum = 0;
		int lastRowNum = 0;
		
		list = new ArrayList<List<Object>>();
		int sheetNum = workbook.getNumberOfSheets();
//		System.out.println(sheetNum +":sheetNum");
		//编历所有的sheet
		for (int i = 0; i < sheetNum; i++) {
			sheet = workbook.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			
			firstRowNum = sheet.getFirstRowNum();
			lastRowNum = sheet.getLastRowNum();
			//编历当前sheet的所有行
			for (int j = firstRowNum; j <= lastRowNum; j++) {
				row = sheet.getRow(j);
				if (null == row) {
					continue;
				}
				
				int firstCellNum = row.getFirstCellNum();
				int lastCellNum = row.getLastCellNum();
				//编历所有的列
				List<Object> list2 = new ArrayList<Object>();
				for (int k = firstCellNum; k <= lastCellNum; k++) {
					cell = row.getCell(k);
					if (null == cell) {
						continue;
					}
					list2.add(this.getCellValue(cell));
				}
				list.add(list2);
			}
		}
		try {
			iStream.close();
		} catch (Exception e) {
			System.out.println("流关闭失败");
		}
		return list;
	}

	
	/**
	 * 对表中数值进行格式化
	 * @param cell
	 * @return
	 */
	private Object getCellValue(Cell cell) {
		Object obj = null;
		DecimalFormat df = new DecimalFormat("0");//format number\string
		DecimalFormat df2 = new DecimalFormat("0.00");//format double
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //format date
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			obj = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if("General".equals(cell.getCellStyle().getDataFormatString())){  
                obj = df.format(cell.getNumericCellValue());  
            }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){  
                obj = sdf.format(cell.getDateCellValue());  
            }else{  
                obj = df2.format(cell.getNumericCellValue());  
            }  
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			obj = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			obj = "";
			break;
			
		case Cell.CELL_TYPE_FORMULA:
			obj = "";
			break;
		case Cell.CELL_TYPE_ERROR:
			obj = "";
			break;
		default:
			obj = "";
		}
		return obj;
	}

	/**
	 * 根据文件后缀，自适应文件版本
	 * @param iStream
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private Workbook getWorkbook(InputStream iStream, String fileName)throws Exception {
		Workbook workbook = null;
		
		String fileType = fileName.substring(fileName.lastIndexOf("."));  
        if(excel.equals(fileType)){  
            workbook = new HSSFWorkbook(iStream);  //2003-  .xls
        }else if(excelx.equals(fileType)){  
            workbook = new XSSFWorkbook(iStream);  //2007+  .xlsx
        }else{  
            throw new Exception("解析的文件格式有误！");  
        }  
		return workbook;
	}
	
	
}
