package org.mei.config.context;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 4. 21.
 */
@Configuration
public class DataSourceContext {

	@Autowired private Properties mei;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(mei.getProperty("dataSource.driverClassName"));
		dataSource.setUrl(mei.getProperty("dataSource.url"));
		dataSource.setUsername(mei.getProperty("dataSource.username"));
		dataSource.setPassword(mei.getProperty("dataSource.password"));

		return dataSource;
	}
}
