package utils.prop;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class EditProp {

	public EditProp() {
	}

	/**
	 * 修改jdbc配置文件
	 * 
	 * @param IP
	 * @param port
	 * @param SID
	 */
	public static void editJDBCProp(String IP, String port, String SID,
			String userName, String passWord) {
		
		String jdbcUrl = "jdbc:oracle:thin:@"+IP+":"+port+":"+SID;
		Properties prop = new Properties();
		InputStream is = null;
		OutputStream os = null;
		try {
			is = ClassLoader.getSystemResourceAsStream("jdbc.properties");
			prop.load(is);
			
			prop.setProperty("c3p0.jdbcUrl", jdbcUrl);
			prop.setProperty("c3p0.user", userName);
			prop.setProperty("c3p0.password", passWord);
			
			is.close();
			
			
			os = new FileOutputStream("src/jdbc.properties");
			prop.store(os, "sakura");
			os.close();
		} catch (Exception e) {
			System.out.println("发生错误");
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e2) {
				System.out.println("流关闭失败");
				e2.printStackTrace();
			}
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (Exception e2) {
				System.out.println("流关闭失败");
				e2.printStackTrace();
			}
		}

	}

}
