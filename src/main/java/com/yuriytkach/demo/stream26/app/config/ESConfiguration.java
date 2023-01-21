package com.yuriytkach.demo.stream26.app.config;

import java.time.Duration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableElasticsearchRepositories
@EnableElasticsearchAuditing
@RequiredArgsConstructor
public class ESConfiguration extends ElasticsearchConfiguration {

  private final ESProperties properties;

  @Override
  public ClientConfiguration clientConfiguration() {
    return ClientConfiguration.builder()
      .connectedTo(properties.getUrl())
      .withConnectTimeout(Duration.ofSeconds(2))
      .withSocketTimeout(Duration.ofSeconds(10))
      .build();
  }
}
