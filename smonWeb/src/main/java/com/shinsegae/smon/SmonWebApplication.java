package com.shinsegae.smon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;


@SpringBootApplication
@EnableEncryptableProperties
@EnableWebSecurity
@PropertySource(value = { "classpath:/config/jdbc.properties", "classpath:/config/cubeone.properties" })
@ImportResource({
	 "classpath:/config/context-aspect.xml"
   , "classpath:/config/context-datasource.xml"
   , "classpath:/config/context-mvc-servlet.xml"
   , "classpath:/config/context-security.xml"
})
public class SmonWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmonWebApplication.class, args);
	}
	
	
}
