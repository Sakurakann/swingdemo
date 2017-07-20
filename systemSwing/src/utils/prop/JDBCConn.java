package utils.prop;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConn {

	public JDBCConn() {
	}
	
	public static boolean connTest(String IP, String port, String SID,
			String userName, String passWord){
		String jdbcUrl = "jdbc:oracle:thin:@"+IP+":"+port+":"+SID;
		Connection connection = null;
        try {
            // 加 载驱动
            Class.forName("oracle.jdbc.OracleDriver");
            // 通过驱动管理类获取数据库连接
            //设置连接超时时间 2s
            DriverManager.setLoginTimeout(2);
            connection = DriverManager.getConnection(jdbcUrl,userName,passWord);
            if (connection != null) {
            	return true;
			} else {
				return false;
			}
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
        	try {
        		// 关闭连接
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static boolean connTest(){
		Properties prop = new Properties();
		InputStream is = null;
		Connection connection = null;
		
		try {
			is = ClassLoader.getSystemResourceAsStream("jdbc.properties");
			prop.load(is);
			String url = prop.getProperty("c3p0.jdbcUrl");
			String user = prop.getProperty("c3p0.user");
			String password = prop.getProperty("c3p0.password");
			
			try {
				 // 加 载驱动
	            Class.forName("oracle.jdbc.OracleDriver");
	            // 通过驱动管理类获取数据库连接
	            //设置连接超时时间 2s
	            DriverManager.setLoginTimeout(2);
	            connection = DriverManager.getConnection(url,user,password);
	            if (connection != null) {
	            	return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			
			try {
        		// 关闭连接
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
