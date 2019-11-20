package com.shinsegae.smon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("cubeone")
@Getter
@Setter
public class CubeoneProperties {
	private String server;
}
