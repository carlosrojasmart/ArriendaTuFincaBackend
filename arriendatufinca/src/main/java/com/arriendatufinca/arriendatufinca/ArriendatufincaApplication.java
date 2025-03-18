package com.arriendatufinca.arriendatufinca;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.arriendatufinca.arriendatufinca.Conections.SshTunnelStarter;

@SpringBootApplication
public class ArriendatufincaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArriendatufincaApplication.class, args);
    }

    @Bean(initMethod = "init", destroyMethod = "shutdown")
    public SshTunnelStarter sshTunnelStarter() {
        return new SshTunnelStarter();
    }

    @Bean
    @DependsOn("sshTunnelStarter")
    public DataSource dataSource() {
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		String url = bundle.getString("spring.datasource.url");
		String username = bundle.getString("spring.datasource.username");
		String password = bundle.getString("spring.datasource.password");
		String driverClassName = bundle.getString("spring.datasource.driver-class-name");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
    }
}
