package com.yuriytkach.demo.stream26.app.repo;

import java.util.List;

import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsIterator;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.yuriytkach.demo.stream26.app.model.Product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchService {

  private final ElasticsearchOperations ops;

  private final ElasticsearchClient elasticsearchClient;

  public List<Product> findWithMatch(final String text) {
    final Query query = NativeQuery.builder()
      .withQuery(q -> q.multiMatch(mq -> mq.fields("name", "description", "value.keyword").query(text)))
      .withAggregation("sum", Aggregation.of(agg -> agg.terms(t -> t.field("value").size(1))))
      .build();

    final SearchHits<Product> search = ops.search(query, Product.class);

    final ElasticsearchAggregations aggregations = (ElasticsearchAggregations) search.getAggregations();
    final org.springframework.data.elasticsearch.client.elc.Aggregation agg = aggregations.get("sum").aggregation();
    final Aggregate aggregate = agg.getAggregate();
    log.info("Current aggregation is lterms (Terms of 'Long's): {}", aggregate.isLterms());
    if (aggregate.isLterms()) {
      aggregate.lterms().buckets().array()
        .forEach(b -> log.info("Value: {} DocCount: {}", b.key(), b.docCount()));
    }

    return search
      .stream().map(SearchHit::getContent).toList();
  }

  public List<Product> findAllWithScroll() {
    final Query searchQuery = NativeQuery.builder()
      .withQuery(q -> q.matchAll(ma -> ma))
      .build();

    try (SearchHitsIterator<Product> result = ops.searchForStream(searchQuery, Product.class)) {
      return result.stream().map(SearchHit::getContent).toList();
    }
  }

}
