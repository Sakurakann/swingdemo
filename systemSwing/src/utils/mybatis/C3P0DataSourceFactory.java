package utils.mybatis;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 重写 以实现Mybatis数据库连接池的配置
 * @author Administrator
 *
 */
public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {

	public C3P0DataSourceFactory() {
		this.dataSource = new ComboPooledDataSource();
	}

}
