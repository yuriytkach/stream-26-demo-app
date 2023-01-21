package com.yuriytkach.demo.stream26.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "es")
public class ESProperties {

  private final String url;
}
