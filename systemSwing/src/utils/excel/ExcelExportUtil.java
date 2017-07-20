package utils.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

//import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelExportUtil<T extends Object> {

	public ExcelExportUtil() {
	}

	// public void exportExceptionExcel(HttpServletResponse response,
	public void exportExceptionExcel(File file, String[] title, List<T> beanList)
			throws FileNotFoundException {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		// 设置行宽
		sheet.setDefaultColumnWidth(10);
		// 生成一个样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中

		// 创建表头标题
		HSSFRow row0 = sheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = row0.createCell(i);
			// HSSFRichTextString textString = new
			// HSSFRichTextString(title[i]);
			// 建议使用富文本格式 为方便起 直接使用string
			cell.setCellValue(title[i]);
		}
		// 创建数据
		int index = 1;
		HSSFRow row = null;
		for (Iterator<T> iterator = beanList.iterator(); iterator.hasNext(); index++) {

			row = sheet.createRow(index);
			T t = (T) iterator.next();

			Class<? extends Object> clazz = t.getClass();
			// 通过反射获取包括私有的全部属性
			// Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < title.length; i++) {
				// 根据表头来创建单元格
				HSSFCell cell = row.createCell(i);
				String fieldName = title[i];
				String methodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				String textValue = null;
				try {
					Method method = clazz.getMethod(methodName, new Class[] {});
					Object value = method.invoke(t, new Object[] {});
					if (value instanceof Double) {
						textValue = NumberFormat.getPercentInstance().format(
								value);
					} else if (null == value || "" == value) {
						textValue = null;
					} else {
						textValue = value.toString();
					}
					cell.setCellValue(textValue);

				} catch (Exception e) {
					System.out.println("反射出错");
					// throw new Exception("反射出错");
					e.printStackTrace();
					// guanbiziyuan
				}
			}
		}

		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			// os = response.getOutputStream();
			wb.write(os);
			System.out.println("导出成功");
		} catch (Exception e) {
			System.out.println("写出失败");
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					System.out.println("输出流关闭失败");
					e.printStackTrace();
				}
			}
		}
	}
}
