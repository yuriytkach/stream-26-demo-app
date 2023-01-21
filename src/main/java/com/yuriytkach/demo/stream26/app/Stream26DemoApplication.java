package com.yuriytkach.demo.stream26.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.yuriytkach.demo.stream26.app.config.ESProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ESProperties.class })
public class Stream26DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Stream26DemoApplication.class, args);
	}

}
