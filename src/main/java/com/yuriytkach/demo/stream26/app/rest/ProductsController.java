package com.yuriytkach.demo.stream26.app.rest;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yuriytkach.demo.stream26.app.model.Product;
import com.yuriytkach.demo.stream26.app.repo.ProductRepository;
import com.yuriytkach.demo.stream26.app.repo.ProductSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductsController {

  private final ProductRepository repository;
  private final ProductSearchService service;

  @GetMapping("/products")
  public Iterable<Product> allProducts(
    @RequestParam(required = false) final String name,
    @RequestParam(required = false) final String q
  ) {
    if (name != null) {
      return repository.findByName(name);
    }

    if (q != null) {
      return service.findWithMatch(q);
    }

//    return repository.findAll();
    return service.findAllWithScroll();
  }

  @PostMapping("/products")
  @ResponseStatus(HttpStatus.CREATED)
  public void addProduct(@RequestBody final ProductInput input) {
    final var product = Product.builder()
      .name(input.name())
      .description(input.description())
      .value(input.value())
      .archived(input.archived())
      .createdLocation(input.location())
      .build();

    repository.save(product);
  }

  record ProductInput(
    String name,
    String description,
    Integer value,
    Boolean archived,
    GeoPoint location
  ) {}
}
