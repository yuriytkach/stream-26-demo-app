package com.yuriytkach.demo.stream26.app.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(indexName = "products")
public class Product implements Persistable<String> {

  @Id
  private final String id;

  @Field(type = FieldType.Keyword)
  private final String name;

  @Field(type = FieldType.Text)
  private final String description;

  @MultiField(
    mainField = @Field(type = FieldType.Integer),
    otherFields = {
      @InnerField(type = FieldType.Keyword, suffix = "keyword")
    }
  )
  private final Integer value;

  @Field
  private final Boolean archived;

  @Field(type = FieldType.Date)
  @CreatedDate
  private final Instant createdDate;

  private final GeoPoint createdLocation;

  @Override
  public boolean isNew() {
    return id == null || createdDate == null;
  }
}
