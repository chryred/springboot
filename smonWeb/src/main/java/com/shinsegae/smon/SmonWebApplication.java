package com.shinsegae.smon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:${jdbc.config}" })
//@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class SmonWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmonWebApplication.class, args);
	}

}
