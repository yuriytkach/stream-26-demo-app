package com.yuriytkach.demo.stream26.app.repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yuriytkach.demo.stream26.app.model.Product;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {

  List<Product> findByName(String name);
}
